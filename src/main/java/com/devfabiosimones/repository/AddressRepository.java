package com.devfabiosimones.repository;

import com.devfabiosimones.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
