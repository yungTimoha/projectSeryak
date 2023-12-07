package com.example.itog.repositories;

import com.example.itog.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Здесь вы можете добавить дополнительные методы для доступа к данным корзины, если необходимо
}
