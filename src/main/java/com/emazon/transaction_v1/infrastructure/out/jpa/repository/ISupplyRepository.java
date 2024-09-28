package com.emazon.transaction_v1.infrastructure.out.jpa.repository;

import com.emazon.transaction_v1.infrastructure.out.jpa.entity.SupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISupplyRepository extends JpaRepository<SupplyEntity, Long> {
}
