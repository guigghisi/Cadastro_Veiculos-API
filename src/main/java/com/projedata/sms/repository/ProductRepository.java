package com.projedata.sms.repository;

import com.projedata.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT '*' from Product p order by p.price desc ", nativeQuery = true)
    List<Product> findAllOrderByPriceDesc();

}
