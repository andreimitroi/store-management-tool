package com.amitroi.storemanagementtool.domain.repository;

import com.amitroi.storemanagementtool.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
