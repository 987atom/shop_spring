package com.example.shop_spring.Entitys;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int count;

    @OneToMany(mappedBy = "products")
    private List<ProductCartEntity> product;

    public List<ProductCartEntity> getProduct() {
        return product;
    }

    public void setProduct(List<ProductCartEntity> product) {
        this.product = product;
    }

    private int coast;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

    public ProductEntity(String name, int count, List<ProductCartEntity> product, int coast) {
        this.name = name;
        this.count = count;
        this.product = product;
        this.coast = coast;
    }

    public ProductEntity() {
    }
}
