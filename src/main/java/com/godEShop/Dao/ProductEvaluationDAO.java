package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.ProductEvaluation;

@Repository
public interface ProductEvaluationDAO extends JpaRepository<ProductEvaluation, Long> {

}
