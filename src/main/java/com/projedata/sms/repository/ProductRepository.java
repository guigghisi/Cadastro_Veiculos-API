package com.projedata.sms.repository;

import com.projedata.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p.id, p.name, p.price FROM Product p ORDER BY p.price DESC", nativeQuery = true)
    List<Product> findAllOrderByPriceDesc();

    @Query(value = "SELECT product_id, raw_materials_id, count(*) as quantity FROM product_raw_materials GROUP BY product_id, raw_materials_id;", nativeQuery = true)
    List<Object[]> findProductRawMaterialCount();

}
