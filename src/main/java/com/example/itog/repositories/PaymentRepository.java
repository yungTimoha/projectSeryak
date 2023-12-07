package com.example.itog.repositories;

import com.example.itog.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Здесь вы можете добавить дополнительные методы для доступа к данным платежа, если необходимо
}