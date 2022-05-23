package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Entity.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.brand.id=?1")
    List<Product> findAllByBrandId(int id);

    Page<Product> findAllByNameLike(String keywords, Pageable pageable);

    @Query(value = "{CALL sp_getProductAndOneImage()}", nativeQuery = true)
    List<String> getProductAndOneImage();

    // tìm sản phẩm theo danh mục
    @Query("SELECT p FROM Product p WHERE p.category.id=?1 AND p.isDeleted = 0")
    Page<Product> findAllProductByCategoryId(int id, Pageable pageable);

    // tìm sản phẩm theo brands
    @Query("SELECT p FROM Product p WHERE p.brand.id=?1 AND p.isDeleted = 0")
    Page<Product> findAllProductByBrandId(int id, Pageable pageable);

    // sắp xếp giá tăng dần
    @Query("SELECT p FROM Product p WHERE p.name LIKE ?1 AND p.isDeleted = 0 ORDER BY p.price ASC")
    Page<Product> findAllPriceAsc(String keywords, Pageable pageable);

    // sắp xếp giá giảm dần
    @Query("SELECT p FROM Product p WHERE p.name LIKE ?1 AND p.isDeleted = 0 ORDER BY p.price DESC")
    Page<Product> findAllPriceDesc(String keywords, Pageable pageable);

    // sắp xếp sản phẩm mới nhất
    @Query("SELECT p FROM Product p WHERE p.name LIKE ?1 AND p.isDeleted = 0 ORDER BY p.createDate DESC")
    Page<Product> findAllNewProduct(String keywords, Pageable pageable);

    // lấy sản phẩm mới nhất
    @Query("SELECT p FROM Product p ORDER BY p.createDate DESC")
    List<Product> getAllNewProducts();

    // ------------------------------------------------------------------------
    // Product for shop page
    @Query("SELECT new com.godEShop.Dto.ProductShopDto"
	    + "(p.id, c.id, p.name, p.price, p.createDate, c.name, MIN(pp.id), pe.evaluation, pd.discount, p.detail) "
	    + "FROM Product p " 
	    + "FULL JOIN p.productPhotos pp " 
	    + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.brand pb " 
	    + "FULL JOIN p.productDiscounts pd " 
	    + "FULL JOIN p.category c "
	    + "WHERE p.isDeleted = 0 AND (p.name LIKE ?1 OR c.name LIKE ?1) AND c.name LIKE ?2 AND pb.name LIKE ?3 "
	    + "GROUP BY p.id, c.id, p.name, p.price, p.createDate, c.name, pe.evaluation, pd.discount, p.detail")
    Page<ProductShopDto> productShop(String kws, String categoryName, String brandName, Pageable pageable);

    // -------------------------------------------------------------------------
    // Product for deal of the day
    @Query("SELECT new com.godEShop.Dto.ProductDiscountDto"
	    + "(p.id, p.name, p.price, pd.discount, pe.evaluation, MIN(pp.id), pd.endDate, p.detail) "
	    + "FROM Product p " 
	    + "FULL JOIN p.productPhotos pp " 
	    + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.productDiscounts pd "
	    + "WHERE p.isDeleted = 0 AND pd.discount > 0 AND pd.endDate >= GETDATE() "
	    + "GROUP BY p.id, p.name, p.price, pd.discount, pe.evaluation, pd.endDate, p.detail "
	    + "ORDER BY pd.endDate ASC")
    List<ProductDiscountDto> productDealOfTheDay();

    // -------------------------------------------------------------------------
    // Product for best seller
    @Query("SELECT new com.godEShop.Dto.ProductDiscountDto"
	    + "(p.id, p.name, p.price, pd.discount, pe.evaluation, MIN(pp.id), pd.endDate, p.detail) "
	    + "FROM Product p " 
	    + "FULL JOIN p.productPhotos pp " 
	    + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.productDiscounts pd " 
	    + "FULL JOIN p.orderDetails pod " 
	    + "WHERE p.isDeleted = 0 "
	    + "GROUP BY p.id, p.name, p.price, pd.discount, pe.evaluation, pd.endDate, p.detail "
	    + "ORDER BY COUNT(pod.product.id) DESC")
    List<ProductDiscountDto> productBestSeller();

    // -------------------------------------------------------------------------
    // Product for new arrivals
    @Query("SELECT new com.godEShop.Dto.ProductDiscountDto"
	    + "(p.id, p.name, p.price, pd.discount, pe.evaluation, MIN(pp.id), pd.endDate, p.detail) "
	    + "FROM Product p " 
	    + "FULL JOIN p.productPhotos pp " 
	    + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.productDiscounts pd " 
	    + "FULL JOIN p.orderDetails pod " 
	    + "WHERE p.isDeleted = 0 "
	    + "GROUP BY p.id, p.name, p.price, pd.discount, pe.evaluation, pd.endDate, p.detail, p.createDate "
	    + "ORDER BY p.createDate DESC")
    List<ProductDiscountDto> productNewArrivals();

    // -------------------------------------------------------------------------
    @Query("SELECT new com.godEShop.Dto.ProductDiscountDto"
	    + "(p.id, p.name, p.price, pd.discount, pe.evaluation, MIN(pp.id), pd.endDate, p.detail) "
	    + "FROM Product p " 
	    + "FULL JOIN p.productPhotos pp " 
	    + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.productDiscounts pd " 
	    + "FULL JOIN p.orderDetails pod " 
	    + "WHERE p.isDeleted = 0 AND p.brand.id=?1 "
	    + "GROUP BY p.id, p.name, p.price, pd.discount, pe.evaluation, pd.endDate, p.detail, p.createDate "
	    + "ORDER BY p.price DESC")
    List<ProductDiscountDto> productByIdBrands(Integer id);
    
    
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
}
