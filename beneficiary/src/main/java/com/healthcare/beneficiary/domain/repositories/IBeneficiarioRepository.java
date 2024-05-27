package com.healthcare.beneficiary.domain.repositories;

import com.healthcare.beneficiary.domain.entities.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBeneficiarioRepository extends JpaRepository<Beneficiario, Integer> {
}
