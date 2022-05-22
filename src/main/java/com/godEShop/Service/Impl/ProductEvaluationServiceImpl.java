package com.godEShop.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.ProductEvaluationDAO;
import com.godEShop.Entity.ProductEvaluation;
import com.godEShop.Service.ProductEvaluationService;

@Service
public class ProductEvaluationServiceImpl implements ProductEvaluationService{

    @Autowired
    ProductEvaluationDAO productEvaluationDAO;
    
    @Override
    public ProductEvaluation getById(Integer id) {
	// TODO Auto-generated method stub
	return productEvaluationDAO.getById(id);
    }

}
