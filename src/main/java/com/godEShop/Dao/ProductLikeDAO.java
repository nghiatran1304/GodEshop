package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.ProductLike;

@Repository
public interface ProductLikeDAO extends JpaRepository<ProductLike, Long> {
	
	 @Query("SELECT pl from ProductLike pl inner join "
	    		+ "pl.product p "
	    		+ "inner join pl.account a "
	    		+ "where pl.isLiked = 1 and a.username=?1")
	    List<ProductLike> wishList(String username);
	
	@Query("SELECT pl FROM ProductLike pl WHERE pl.account.username = ?1 and pl.product.id = ?2")
	ProductLike getProductLikeByUsernameAndProductId(String username, Long productId);
	
	
}
