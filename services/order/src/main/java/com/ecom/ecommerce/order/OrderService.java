package com.ecom.ecommerce.order;

import com.ecom.ecommerce.customer.CustomerClient;
import com.ecom.ecommerce.exception.BusinessException;
import com.ecom.ecommerce.kafka.OrderConfirmation;
import com.ecom.ecommerce.kafka.OrderProducer;
import com.ecom.ecommerce.orderline.OrderLineRequest;
import com.ecom.ecommerce.orderline.OrderLineService;
import com.ecom.ecommerce.payment.PaymentClient;
import com.ecom.ecommerce.payment.PaymentRequest;
import com.ecom.ecommerce.product.ProductClient;
import com.ecom.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final PaymentClient paymentClient;
    private final OrderProducer orderProducer;
    private final OrderLineService orderLineService;
    private final OrderMapper mapper;
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    public Integer createOrder(@Valid OrderRequest request) {
        //check the customer
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can Not create order : No customer found with id:" + request.customerId()));

        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        //persist order line
        for(PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );

        }

        //start payment process (todo)
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getRefrence(),
                customer
        );

        //send the order confirmation --> notification-ms(kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.refrence(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with provided id: %d",orderId)));
    }
}
