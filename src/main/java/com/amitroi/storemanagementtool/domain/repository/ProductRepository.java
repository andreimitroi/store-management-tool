package com.amitroi.storemanagementtool.domain.repository;

import com.amitroi.storemanagementtool.domain.entity.Product;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByUuid(UUID uuid);

}
