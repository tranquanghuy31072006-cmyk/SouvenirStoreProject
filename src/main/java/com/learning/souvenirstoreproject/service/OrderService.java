package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.dto.request.CreateOrderRequest;
import com.learning.souvenirstoreproject.dto.response.OrderResponse;
import com.learning.souvenirstoreproject.entity.*;
import com.learning.souvenirstoreproject.enums.OrderStatus;
import com.learning.souvenirstoreproject.enums.PaymentStatus;
import com.learning.souvenirstoreproject.enums.ProductVariantStatus;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.exception.ErrorCode;
import com.learning.souvenirstoreproject.mapper.OrderMapper;
import com.learning.souvenirstoreproject.mapper.PaymentMapper;
import com.learning.souvenirstoreproject.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    PaymentRepository paymentRepository;
    CartRepository cartRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;
    PaymentMapper paymentMapper;

    static final BigDecimal SHIPPING_FEE = new BigDecimal("30000");
    static final BigDecimal ZERO = BigDecimal.ZERO;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(()
                -> new AppException(ErrorCode.CART_NOT_FOUND));

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty())
            throw new AppException(ErrorCode.CART_EMPTY);

        Order order = Order.builder()
                .user(user)
                .orderCode("ORD-" + UUID.randomUUID())
                .receiverName(createOrderRequest.getReceiverName())
                .receiverAddress(createOrderRequest.getReceiverAddress())
                .receiverPhone(createOrderRequest.getReceiverPhone())
                .note(createOrderRequest.getNote())
                .paymentMethod(createOrderRequest.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .orderStatus(OrderStatus.PENDING)
                .shippingFee(SHIPPING_FEE)
                .discountAmount(ZERO)
                .build();

        BigDecimal totalAmount = ZERO;

        for (CartItem cartItem : cart.getCartItems()) {
            ProductVariant variant = cartItem.getProductVariant();

            if (variant.getStatus() != ProductVariantStatus.ACTIVE)
                throw new AppException(ErrorCode.PRODUCT_VARIANT_INACTIVE);

            int stock = variant.getStockQuantity() == null ? 0 : variant.getStockQuantity();

            if (cartItem.getQuantity() > stock) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }

            variant.setStockQuantity(stock - cartItem.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productVariant(variant)
                    .productName(variant.getProduct().getName())
                    .productImage(variant.getProduct().getThumbnail())
                    .size(variant.getSize())
                    .color(variant.getColor())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getPrice())
                    .totalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                    .build();

            order.getOrderItems().add(orderItem);

            totalAmount = totalAmount.add(orderItem.getTotalPrice());
        }

        order.setTotalAmount(totalAmount);

        order.setFinalAmount(totalAmount.add(SHIPPING_FEE));

        orderRepository.save(order);

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(createOrderRequest.getPaymentMethod())
                .transactionCode(UUID.randomUUID().toString())
                .amount(order.getFinalAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        paymentRepository.save(payment);

        cart.getCartItems().clear();

        cartRepository.save(cart);

        OrderResponse response = orderMapper.toOrderResponse(order);

        response.setPayment(paymentMapper.toPaymentResponse(payment));

        return response;
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderByOrderId(Long orderId) {
        User user = getCurrentUser();

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId()).orElseThrow(()
                -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        OrderResponse response = orderMapper.toOrderResponse(order);

        paymentRepository.findByOrderId(order.getId())
                .ifPresent(payment -> response.setPayment(paymentMapper.toPaymentResponse(payment)));

        return response;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUserId() {
        User user = getCurrentUser();

        return orderRepository.findByUserId(user.getId()).stream().map(orderMapper::toOrderResponse).toList();
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        User user = getCurrentUser();

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new AppException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }

        for (OrderItem orderItem : order.getOrderItems()) {
            ProductVariant variant = orderItem.getProductVariant();

            int stock = variant.getStockQuantity() == null ? 0 : variant.getStockQuantity();

            variant.setStockQuantity(stock + orderItem.getQuantity());
        }

        PaymentStatus newPaymentStatus = order.getPaymentStatus() == PaymentStatus.PAID
                ? PaymentStatus.REFUNDED : PaymentStatus.FAILED;

        order.setOrderStatus(OrderStatus.CANCELLED);

        order.setPaymentStatus(newPaymentStatus);

        Payment payment = paymentRepository.findByOrderId(order.getId()).orElse(null);

        if (payment != null)
            payment.setPaymentStatus(newPaymentStatus);

        OrderResponse response = orderMapper.toOrderResponse(order);

        if (payment != null)
            response.setPayment(paymentMapper.toPaymentResponse(payment));

        return response;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
    }
}
