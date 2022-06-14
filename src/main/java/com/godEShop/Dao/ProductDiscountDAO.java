package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Dto.ProductOnSaleDto;
import com.godEShop.Entity.Product;
import com.godEShop.Entity.ProductDiscount;

@Repository
public interface ProductDiscountDAO extends JpaRepository<ProductDiscount, Integer> {

    @Query("SELECT pd FROM ProductDiscount pd WHERE pd.product.id=?1")
    ProductDiscount getProductDiscount(Long productId);
    
    @Query("SELECT p FROM ProductDiscount pd "
    	+ "FULL JOIN pd.product p "
    	+ "WHERE p.isDeleted = 0 AND p.id NOT IN (SELECT p.id FROM ProductDiscount pd FULL JOIN pd.product p WHERE pd.endDate > GETDATE())")
    List<Product> getProductNonDiscount();
    
    @Query("SELECT new com.godEShop.Dto.ProductOnSaleDto"
    	+ "(pd.id, pd.createDate, pd.discount, pd.endDate, pd.account.username, p.id, p.name, p.price) "
    	+ "FROM ProductDiscount pd "
    	+ "FULL JOIN pd.product p "
    	+ "WHERE pd.endDate > GETDATE() AND p.isDeleted = 0"
    	+ "ORDER BY pd.endDate ASC")
    List<ProductOnSaleDto> getProductOnSale();

}
