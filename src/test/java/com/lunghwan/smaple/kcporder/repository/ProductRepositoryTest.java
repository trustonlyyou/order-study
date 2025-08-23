package com.lunghwan.smaple.kcporder.repository;

import com.lunghwan.smaple.kcporder.constant.Category;
import com.lunghwan.smaple.kcporder.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 생성 테스트")
    public void createProductTest() throws Exception {
        //give
        Product product = Product.builder()
                .productName("테스트 상품")
                .price(BigDecimal.valueOf(1000))
                .stock(2)
                .category(Category.BOTTOM)
                .build();

        //when
        Product saved = productRepository.save(product);

        //then
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getId()).isGreaterThan(0L);
        Assertions.assertThat(saved.getProductName()).isEqualTo(product.getProductName());
        Assertions.assertThat(saved.getPrice()).isEqualTo(product.getPrice());
        Assertions.assertThat(saved.getStock()).isEqualTo(product.getStock());
        Assertions.assertThat(saved.getCategory()).isEqualTo(product.getCategory());
    }

    @Test
    @DisplayName("상품 조회")
    public void getProductTest() throws Exception {
        //given
        Product product = Product.builder()
                .productName("테스트 상품")
                .price(BigDecimal.valueOf(1000))
                .stock(2)
                .category(Category.BOTTOM)
                .build();

        productRepository.save(product);

        //when
        Optional<Product> result = productRepository.findByProductName("테스트 상품");

        //then
        Assertions.assertThat(result).isPresent();

        Product foundProduct = result.get();
        System.out.println(foundProduct);

        Assertions.assertThat(product.getProductName()).isEqualTo(foundProduct.getProductName());
        Assertions.assertThat(product.getPrice()).isEqualTo(foundProduct.getPrice());
        Assertions.assertThat(product.getStock()).isEqualTo(foundProduct.getStock());
        Assertions.assertThat(product.getCategory()).isEqualTo(foundProduct.getCategory());
        Assertions.assertThat(foundProduct.getCreateTime()).isNotNull();
        Assertions.assertThat(foundProduct.getUpdatedTime()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 상품명으로 조회시 빈 Optional 반환")
    public void getProductNotFoundTest() throws Exception {
        //when
        Optional<Product> result = productRepository.findByProductName("존재하지 않는 상품");

        //then
        Assertions.assertThat(result).isEmpty();
    }
}