package org.agro.adapters.out.persistence.repositories.jpa;

import org.agro.adapters.out.persistence.entities.OperacaoCreditoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OperacaoCreditoRepository extends JpaRepository<OperacaoCreditoEntity, UUID> {
}