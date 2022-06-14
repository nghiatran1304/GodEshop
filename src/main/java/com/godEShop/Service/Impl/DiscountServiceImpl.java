package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.ProductDiscountDAO;
import com.godEShop.Dto.ProductOnSaleDto;
import com.godEShop.Entity.Product;
import com.godEShop.Entity.ProductDiscount;
import com.godEShop.Service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService{

    @Autowired
    ProductDiscountDAO pdDAO;
    
    @Override
    public List<Product> getProductNonDiscount() {
	// TODO Auto-generated method stub
	return pdDAO.getProductNonDiscount();
    }

    @Override
    public List<ProductOnSaleDto> getProductOnSale() {
	// TODO Auto-generated method stub
	return pdDAO.getProductOnSale();
    }

    @Override
    public ProductDiscount create(ProductDiscount pd) {
	// TODO Auto-generated method stub
	return pdDAO.save(pd);
    }

    @Override
    public void delete(Integer id) {
	// TODO Auto-generated method stub
	pdDAO.deleteById(id);
    }

    @Override
    public ProductDiscount update(ProductDiscount pd) {
	// TODO Auto-generated method stub
	return pdDAO.save(pd);
    }

}
