package com.example.itog.repositories;

import com.example.itog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Здесь вы можете добавить дополнительные методы для доступа к данным продукта, если необходимо
}
