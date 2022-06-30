package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Dto.ProductAccessoryInfoDto;
import com.godEShop.Entity.Accessory;

@Repository
public interface AccessoryDAO extends JpaRepository<Accessory, Integer> {
	
	@Query("SELECT new com.godEShop.Dto.ProductAccessoryInfoDto"
			+ "(p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, b.id, c.id ,MIN(pp.id), ac.id, ac.colors, bm.name, b.name, c.name, bm.id ) "
			+ "FROM Product p "
			+ "INNER JOIN p.accessories ac "
			+ "INNER JOIN ac.braceletMaterial bm "
			+ "INNER JOIN p.productPhotos pp "
			+ "INNER JOIN p.brand b "
			+ "INNER JOIN p.category c "
			+ "GROUP BY  p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, b.id, c.id, ac.id, ac.colors, bm.name, b.name, c.name, bm.id ")
	List<ProductAccessoryInfoDto> lstFullInfoAccessory();

	@Query("SELECT new com.godEShop.Dto.ProductAccessoryInfoDto"
			+ "(p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, b.id, c.id ,MIN(pp.id), ac.id, ac.colors, bm.name, b.name, c.name, bm.id ) "
			+ "FROM Product p "
			+ "INNER JOIN p.accessories ac "
			+ "INNER JOIN ac.braceletMaterial bm "
			+ "INNER JOIN p.productPhotos pp "
			+ "INNER JOIN p.brand b "
			+ "INNER JOIN p.category c "
			+ "WHERE p.name LIKE ?1 OR c.name LIKE ?1 "
			+ "GROUP BY  p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, b.id, c.id, ac.id, ac.colors, bm.name, b.name, c.name, bm.id ")
	List<ProductAccessoryInfoDto> lstSearchFullInfoAccessory(String name);
	@Query("SELECT new com.godEShop.Dto.ProductAccessoryInfoDto"
			+ "(p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, b.id, c.id ,MIN(pp.id), ac.id, ac.colors, bm.name, b.name, c.name, bm.id ) "
			+ "FROM Product p "
			+ "INNER JOIN p.accessories ac "
			+ "INNER JOIN ac.braceletMaterial bm "
			+ "INNER JOIN p.productPhotos pp "
			+ "INNER JOIN p.brand b "
			+ "INNER JOIN p.category c "
			+ "WHERE p.quantity <= 5 "
			+ "GROUP BY  p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, b.id, c.id, ac.id, ac.colors, bm.name, b.name, c.name, bm.id ")
	List<ProductAccessoryInfoDto> lstFullInfoAccessoryOutOfSoon();

}
