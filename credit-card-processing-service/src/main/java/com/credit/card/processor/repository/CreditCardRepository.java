package com.credit.card.processor.repository;

import com.credit.card.processor.model.entity.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Integer> {
}
