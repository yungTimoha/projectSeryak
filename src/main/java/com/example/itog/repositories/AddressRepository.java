package com.example.itog.repositories;

import com.example.itog.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Здесь вы можете добавить дополнительные методы для доступа к данным адреса, если необходимо
}