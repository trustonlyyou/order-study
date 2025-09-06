package com.lunghwan.smaple.kcporder.repository;

import com.lunghwan.smaple.kcporder.constant.Category;
import com.lunghwan.smaple.kcporder.constant.OrderStatus;
import com.lunghwan.smaple.kcporder.entity.Order;
import com.lunghwan.smaple.kcporder.entity.OrderItem;
import com.lunghwan.smaple.kcporder.entity.Product;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.saveAll(List.of(
                Product.builder()
                        .productName("아이폰 16 Pro")
                        .price(new BigDecimal("1990000"))
                        .stock(50)
                        .category(Category.ELECTRONICS)
                        .build(),

                Product.builder()
                        .productName("맥북 프로 16인치")
                        .price(new BigDecimal("3890000"))
                        .stock(30)
                        .category(Category.ELECTRONICS)
                        .build(),

                Product.builder()
                        .productName("나이키 농구화")
                        .price(new BigDecimal("189000"))
                        .stock(100)
                        .category(Category.SPORTS)
                        .build(),

                Product.builder()
                        .productName("농구공")
                        .price(new BigDecimal("59000"))
                        .stock(200)
                        .category(Category.SPORTS)
                        .build(),

                Product.builder()
                        .productName("원두커피 1kg")
                        .price(new BigDecimal("35000"))
                        .stock(500)
                        .category(Category.FOOD)
                        .build()
        ));
    }

    /**
     * 주문 번호 생성
     * @return String : 주문번호
     */
    private String createOrderNo() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Random random = new Random();

        int number = random.nextInt(10);

        return dateTime + number;
    }

    @Test
    @DisplayName("주문 생성 - (OrderItem 없는)")
    public void createOrderTest() throws Exception {
        // Create Order No

        //given
        Order order = Order.builder()
                .orderNo(createOrderNo())
                .orderStatus(OrderStatus.WAITING)
                .build();
        
        //when
        Order saved = orderRepository.save(order);
        
        //then
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getId()).isGreaterThan(0L);
        Assertions.assertThat(saved.getOrderNo()).isEqualTo(order.getOrderNo());
        Assertions.assertThat(saved.getOrderStatus()).isEqualTo(order.getOrderStatus());
        Assertions.assertThat(saved.getCreateTime()).isNotNull();
        Assertions.assertThat(saved.getUpdatedTime()).isNotNull();
        Assertions.assertThat(saved.getCreateTime()).isEqualTo(saved.getUpdatedTime());
    }


    @Test
    @DisplayName("주문 조회 테스트")
    public void getOrderTest() throws Exception {
        //given
        Order order = Order.builder()
                .orderNo(createOrderNo())
                .orderStatus(OrderStatus.WAITING)
                .build();

        Order saved = orderRepository.save(order);

        //when
        Optional<Order> result = orderRepository.findById(saved.getId());

        //then
        Assertions.assertThat(result).isPresent();

        Order foundOrder = result.get();
        System.out.println(foundOrder);

        Assertions.assertThat(foundOrder.getId()).isEqualTo(saved.getId());
        Assertions.assertThat(order.getOrderNo()).isEqualTo(foundOrder.getOrderNo());
        Assertions.assertThat(order.getOrderStatus()).isEqualTo(foundOrder.getOrderStatus());
        Assertions.assertThat(foundOrder.getCreateTime()).isNotNull();
        Assertions.assertThat(foundOrder.getUpdatedTime()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 주문 조회 시 빈 Optional 반환")
    public void findNonExistentOrderTest() {
        //given
        //when
        Optional<Order> result = orderRepository.findById(100L);

        //then
        Assertions.assertThat(result).isNotPresent();

    }

    @Test
    @DisplayName("주문과 주문상품 함께 저장 및 조회")
    public void createOrderWithOrderItemsTest() throws Exception {
        //given
        List<Product> findProducts = productRepository.findProductByCategory(Category.SPORTS);

        if (findProducts.isEmpty()) {
            throw new IllegalArgumentException("Empty findProducts");
        }

        Order order = Order.builder()
                .orderNo(createOrderNo())
                .orderStatus(OrderStatus.WAITING)
                .build();

        for (int i = 0; i < findProducts.size(); i++) {
            Product product = findProducts.get(i);
            int quantity = i + 1;

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .unitPrice(product.getPrice())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                    .build();

            order.addOrderItem(orderItem);
        }

        //when
        Order saved = orderRepository.save(order);


        //then
        Assertions.assertThat(saved.getOrderItems()).hasSize(findProducts.size());
        Assertions.assertThat(saved.getOrderItems().get(0).getProduct().getCategory())
                .isEqualTo(Category.SPORTS);
    }
}