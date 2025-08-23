package com.lunghwan.smaple.kcporder.entity;

import com.lunghwan.smaple.kcporder.constant.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_NAME",  nullable = false)
    private String productName;

    @Column(name = "PRICE")
    @DecimalMin(value = "0.0", inclusive = false)  // 가격은 0보다 커야 함
    private BigDecimal price;

    @Column(name = "STOCK")
    @Min(value = 0)
    private Integer stock; // 재고량

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private Category category;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createTime;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedTime;


}
