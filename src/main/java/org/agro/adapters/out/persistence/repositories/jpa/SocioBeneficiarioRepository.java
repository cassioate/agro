package org.agro.adapters.out.persistence.repositories.jpa;

import org.agro.adapters.out.persistence.entities.SocioBeneficiarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SocioBeneficiarioRepository extends JpaRepository<SocioBeneficiarioEntity, UUID> {
}