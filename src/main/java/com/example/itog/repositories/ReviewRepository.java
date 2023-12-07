package com.example.itog.repositories;

import com.example.itog.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Здесь вы можете добавить дополнительные методы для доступа к данным отзыва, если необходимо
}
