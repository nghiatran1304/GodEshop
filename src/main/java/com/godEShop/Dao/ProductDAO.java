package com.godEShop.Dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Dto.AccessoryDto;
import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Dto.ProductImageDto;
import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Dto.ProductStatisticDto;
import com.godEShop.Dto.ProductWatchInfoDto;
import com.godEShop.Dto.ProductsStatisticDto;
import com.godEShop.Dto.WatchDto;
import com.godEShop.Entity.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Long> {

    @Query("SELECT new com.godEShop.Dto.ProductShopDto"
	    + "(p.id, c.id, p.name, p.price, p.createDate, c.name, MIN(pp.id), CAST(AVG(pe.evaluation) AS int), pd.discount, p.detail, MAX(pd.endDate), p.quantity, pd.createDate) "
	    + "FROM Product p " + "FULL JOIN p.productPhotos pp " + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.brand pb " + "FULL JOIN p.productDiscounts pd " + "FULL JOIN p.category c "
	    + "GROUP BY p.id, c.id, p.name, p.price, p.createDate, c.name, pd.discount, p.detail, p.quantity, pd.createDate")
    List<ProductShopDto> findAllProduct();

    // ------------------------------------------------------------------------
    // Product for shop page
    @Query("SELECT new com.godEShop.Dto.ProductShopDto"
	    + "(p.id, c.id, p.name, p.price, p.createDate, c.name, MIN(pp.id), CAST(AVG(pe.evaluation) AS int), pd.discount, p.detail, MAX(pd.endDate), p.quantity, pd.createDate) "
	    + "FROM Product p " + "FULL JOIN p.productPhotos pp " + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.brand pb " + "FULL JOIN p.productDiscounts pd " + "FULL JOIN p.category c "
	    + "FULL JOIN p.watches w " + "WHERE p.isDeleted = 0 AND c.available = 0 AND p.price BETWEEN ?4 and ?5 AND "
	    + "( w.gender = ?6 OR ( w.gender != ?7 AND w.gender != ?8 AND w.gender != ?9 ) OR w.gender is null ) AND "
	    + "(pd.id NOT IN (SELECT pd.id FROM ProductDiscount pd INNER JOIN pd.product p WHERE pd.endDate < GETDATE() GROUP BY pd.id) OR pd.createDate IS NULL) "
	    + "AND (p.name LIKE ?1 OR c.name LIKE ?1) AND c.name LIKE ?2 AND pb.name LIKE ?3 "
	    + "GROUP BY p.id, c.id, p.name, p.price, p.createDate, c.name, pd.discount, p.detail, p.quantity, pd.createDate "
	    + "")
    Page<ProductShopDto> productShop(String kws, String categoryName, String brandName, Double minPrice,
	    Double maxPrice, int is6, int is7, int is8, int is9, Pageable pageable);

    // -------------------------------------------------------------------------
    // Product for deal of the day
    @Query("SELECT new com.godEShop.Dto.ProductDiscountDto"
	    + "(p.id, p.name, p.price, pd.discount, pe.evaluation, MIN(pp.id), pd.endDate, p.detail) "
	    + "FROM Product p " + "FULL JOIN p.productPhotos pp " + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.productDiscounts pd " + "WHERE p.isDeleted = 0 AND pd.discount > 0 "
	    + "GROUP BY p.id, p.name, p.price, pd.discount, pe.evaluation, pd.endDate, p.detail "
	    + "HAVING MAX(pd.endDate) >= GETDATE() " + "ORDER BY pd.endDate ASC")
    List<ProductDiscountDto> productDealOfTheDay();

    // -------------------------------------------------------------------------
    // Product for best seller
    @Query("SELECT new com.godEShop.Dto.ProductDiscountDto"
	    + "(p.id, p.name, p.price, pd.discount, pe.evaluation, MIN(pp.id), pd.endDate, p.detail) "
	    + "FROM Product p " + "FULL JOIN p.productPhotos pp " + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.productDiscounts pd " + "FULL JOIN p.orderDetails pod " + "WHERE p.isDeleted = 0 "
	    + "GROUP BY p.id, p.name, p.price, pd.discount, pe.evaluation, pd.endDate, p.detail "
	    + "ORDER BY COUNT(pod.product.id) DESC")
    List<ProductDiscountDto> productBestSeller();

    // -------------------------------------------------------------------------
    // Product for new arrivals
    @Query("SELECT new com.godEShop.Dto.ProductDiscountDto"
	    + "(p.id, p.name, p.price, pd.discount, pe.evaluation, MIN(pp.id), pd.endDate, p.detail) "
	    + "FROM Product p " + "FULL JOIN p.productPhotos pp " + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.productDiscounts pd " + "FULL JOIN p.orderDetails pod " + "WHERE p.isDeleted = 0 "
	    + "GROUP BY p.id, p.name, p.price, pd.discount, pe.evaluation, pd.endDate, p.detail, p.createDate "
	    + "ORDER BY p.createDate DESC")
    List<ProductDiscountDto> productNewArrivals();

    // -------------------------------------------------------------------------
    @Query("SELECT new com.godEShop.Dto.ProductDiscountDto"
	    + "(p.id, p.name, p.price, pd.discount, pe.evaluation, MIN(pp.id), MAX(pd.endDate), p.detail) "
	    + "FROM Product p " + "FULL JOIN p.productPhotos pp " + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.productDiscounts pd " + "FULL JOIN p.orderDetails pod "
	    + "WHERE p.isDeleted = 0 AND p.brand.id=?1 "
	    + "GROUP BY p.id, p.name, p.price, pd.discount, pe.evaluation, p.detail, p.createDate "
	    + "ORDER BY p.price DESC")
    List<ProductDiscountDto> productByIdBrands(Integer id);

    // -------------------------------------------------------------------------
    @Query("SELECT new com.godEShop.Dto.ProductShopDto"
	    + "(p.id, c.id, p.name, p.price, p.createDate, c.name, MIN(pp.id), CAST(AVG(pe.evaluation) AS int), pd.discount, p.detail, MAX(pd.endDate), p.quantity, MAX(pd.createDate)) "
	    + "FROM Product p " + "FULL JOIN p.productPhotos pp " + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.brand pb " + "FULL JOIN p.productDiscounts pd " + "FULL JOIN p.category c "
	    + "WHERE p.id = ?1 AND p.isDeleted = 0 "
	    + "GROUP BY p.id, c.id, p.name, p.price, p.createDate, c.name, pd.discount, p.detail, p.quantity ")
//    ProductShopDto productShopById(Long id);
    List<ProductShopDto> productShopById(Long id);

    @Query("SELECT new com.godEShop.Dto.ProductShopDto"
	    + "(p.id, c.id, p.name, p.price, p.createDate, c.name, MIN(pp.id), CAST(AVG(pe.evaluation) AS int), pd.discount, p.detail, MAX(pd.endDate), p.quantity, MAX(pd.createDate)) "
	    + "FROM Product p " + "FULL JOIN p.productPhotos pp " + "FULL JOIN p.productEvaluations pe "
	    + "FULL JOIN p.brand pb " + "FULL JOIN p.productDiscounts pd " + "FULL JOIN p.category c "
	    + "WHERE p.id = ?1 AND p.isDeleted = 0 "
	    + "GROUP BY p.id, c.id, p.name, p.price, p.createDate, c.name, pd.discount, p.detail, p.quantity ")
    ProductShopDto productShopById1(Long id);

    // -------------------------------------------------------------------------
    @Query("SELECT new com.godEShop.Dto.WatchDto"
	    + "(p.id, p.name, c.name, pb.name, p.madeIn, p.warranty, w.glassSizes, w.atm, w.glassColors, w.caseColors, gm.name, bm.name, mi.name) "
	    + "FROM Product p " + "FULL JOIN p.watches w " + "FULL JOIN p.brand pb " + "FULL JOIN w.glassMaterial gm "
	    + "FULL JOIN w.braceletMaterial bm " + "FULL JOIN w.machineInside mi " + "FULL JOIN p.category c "
	    + "WHERE p.id = ?1 AND p.isDeleted = 0 "
	    + "GROUP BY p.id, p.name, c.name, pb.name, p.madeIn, p.warranty, w.glassSizes, w.atm, w.glassColors, w.caseColors, gm.name, bm.name, mi.name")
    WatchDto getWatchById(Long id);

    // -------------------------------------------------------------------------

    @Query("SELECT new com.godEShop.Dto.AccessoryDto"
	    + "(p.id, p.name, c.name, pb.name, p.madeIn, p.warranty, bm.name, a.colors) " + "FROM Product p "
	    + "FULL JOIN p.accessories a " + "FULL JOIN p.brand pb " + "FULL JOIN a.braceletMaterial bm "
	    + "FULL JOIN p.category c " + "WHERE p.id = ?1 AND p.isDeleted = 0 "
	    + "GROUP BY p.id, p.name, c.name, pb.name, p.madeIn, p.warranty, bm.name, a.colors")
    AccessoryDto getAccessoryDtoById(Long id);

    // ---------------------------------------------------------------------------

    @Query("SELECT new com.godEShop.Dto.ProductWatchInfoDto"
	    + "(p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, pb.id, c.id, MIN(pp.id), w.id, w.gender, w.glassSizes, w.atm, w.glassColors, w.caseColors, gm.id, bm.id, mi.id, pb.name, c.name, gm.name, bm.name, mi.name) "
	    + "FROM Product p " + "INNER JOIN p.watches w " + "INNER JOIN p.productPhotos pp "
	    + "INNER JOIN p.brand pb " + "INNER JOIN w.glassMaterial gm " + "INNER JOIN w.braceletMaterial bm "
	    + "INNER JOIN w.machineInside mi " + "INNER JOIN p.category c "
	    + "GROUP BY p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, pb.id, c.id, w.id, w.gender, w.glassSizes, w.atm, w.glassColors, w.caseColors, gm.id, bm.id, mi.id, pb.name, c.name, gm.name, bm.name, mi.name")
    List<ProductWatchInfoDto> lstFullInfoWatch();
    
    @Query("SELECT new com.godEShop.Dto.ProductWatchInfoDto"
	    + "(p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, pb.id, c.id, MIN(pp.id), w.id, w.gender, w.glassSizes, w.atm, w.glassColors, w.caseColors, gm.id, bm.id, mi.id, pb.name, c.name, gm.name, bm.name, mi.name) "
	    + "FROM Product p " + "INNER JOIN p.watches w " + "INNER JOIN p.productPhotos pp "
	    + "INNER JOIN p.brand pb " + "INNER JOIN w.glassMaterial gm " + "INNER JOIN w.braceletMaterial bm "
	    + "INNER JOIN w.machineInside mi " + "INNER JOIN p.category c "
	    + "WHERE p.quantity <= 5"
	    + "GROUP BY p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, pb.id, c.id, w.id, w.gender, w.glassSizes, w.atm, w.glassColors, w.caseColors, gm.id, bm.id, mi.id, pb.name, c.name, gm.name, bm.name, mi.name")
    List<ProductWatchInfoDto> lstFullInfoWatchOutOfSoon();

    // ---------------------------------------------------------------------------

    @Query("SELECT new com.godEShop.Dto.ProductWatchInfoDto"
	    + "(p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, pb.id, c.id, MIN(pp.id), w.id, w.gender, w.glassSizes, w.atm, w.glassColors, w.caseColors, gm.id, bm.id, mi.id, pb.name, c.name, gm.name, bm.name, mi.name) "
	    + "FROM Product p " + "INNER JOIN p.watches w " + "INNER JOIN p.productPhotos pp "
	    + "INNER JOIN p.brand pb " + "INNER JOIN w.glassMaterial gm " + "INNER JOIN w.braceletMaterial bm "
	    + "INNER JOIN w.machineInside mi " + "INNER JOIN p.category c " + "WHERE p.name LIKE ?1 OR c.name LIKE ?1 "
	    + "GROUP BY p.id, p.name, p.isDeleted, p.quantity, p.price, p.createDate, p.warranty, p.madeIn, p.detail, pb.id, c.id, w.id, w.gender, w.glassSizes, w.atm, w.glassColors, w.caseColors, gm.id, bm.id, mi.id, pb.name, c.name, gm.name, bm.name, mi.name")
    List<ProductWatchInfoDto> lstSearchFullInfoWatch(String name);

    // ---------------------------------------------------------------------------
    @Query("SELECT p FROM Product p " + "WHERE p.name = ?1")
    List<Product> findByNameOrderDetail(String productName);

    // ---------------------------------------------------------------------------

    @Query("SELECT new com.godEShop.Dto.ProductsStatisticDto" + "(p.id, p.name, p.quantity, sum(od.quantity)) "
	    + "FROM Product p " + "INNER JOIN p.orderDetails od " + "INNER JOIN od.order o "
	    + "WHERE o.orderStatus between 2 and 4 " + "GROUP BY p.name, p.id, p.quantity ")
    List<ProductsStatisticDto> getProductStatistic();
    
    @Query("SELECT new com.godEShop.Dto.ProductsStatisticDto" + "(p.id, p.name, p.quantity, sum(od.quantity)) "
    	    + "FROM Product p " + "INNER JOIN p.orderDetails od " + "INNER JOIN od.order o "
    	    + "WHERE o.orderStatus between 2 and 4 and o.createDate between ?1 and ?2 " + "GROUP BY p.name, p.id, p.quantity ")
        List<ProductsStatisticDto> getProductStatisticByTime(Date dateStart, Date dateEnd);

    @Query("SELECT new com.godEShop.Dto.ProductImageDto" + "(p.id, MIN(pp.id)) " + "FROM Product p "
	    + "INNER JOIN p.productPhotos pp " + "GROUP BY p.id")
    List<ProductImageDto> getProductImage();

    @Query("SELECT new com.godEShop.Dto.ProductsStatisticDto" + "(p.id, p.name, p.quantity, sum(od.quantity)) "
	    + "FROM Product p " + "INNER JOIN p.orderDetails od " + "INNER JOIN od.order o "
	    + "WHERE od.product != 0 AND p.name like ?1 and o.orderStatus between 2 and 4 "
	    + "GROUP BY p.name, p.id, p.quantity ")
    List<ProductsStatisticDto> get1ProductStatistic(String name);
    
    @Query("SELECT new com.godEShop.Dto.ProductsStatisticDto" + "(p.id, p.name, p.quantity, sum(od.quantity)) "
    	    + "FROM Product p " + "INNER JOIN p.orderDetails od " + "INNER JOIN od.order o "
    	    + "WHERE od.product != 0 AND p.name like ?1 and o.orderStatus between 2 and 4 and o.createDate between ?2 and ?3 "
    	    + "GROUP BY p.name, p.id, p.quantity ")
        List<ProductsStatisticDto> get1ProductStatisticByTime(String name, Date dateStart, Date dateEnd);
    
    @Query("SELECT new com.godEShop.Dto.ProductsStatisticDto" + "(p.id, p.name, p.quantity, sum(od.quantity)) "
    	    + "FROM Product p " + "INNER JOIN p.orderDetails od " + "INNER JOIN od.order o "
    	    + "WHERE od.product != 0 AND p.name like ?1 and o.orderStatus between 2 and 4 and o.createDate between ?2 and ?3 "
    	    + "GROUP BY p.name, p.id, p.quantity ")
        List<ProductsStatisticDto> get1ProductStatisticByTimeByTime(String name, Date dateStart, Date dateEnd);

    @Query("SELECT new com.godEShop.Dto.ProductImageDto" + "(p.id, MIN(pp.id)) " + "FROM Product p "
	    + "INNER JOIN p.productPhotos pp " + "WHERE p.name like ?1 " + "GROUP BY p.id, p.name")
    List<ProductImageDto> get1ProductImage(String name);

    @Query("SELECT new com.godEShop.Dto.ProductStatisticDto" + "(od.id, p.name, o.createDate, od.quantity, od.price) "
	    + "FROM Product p " + "INNER JOIN p.orderDetails od " + "INNER JOIN od.order o "
	    + "WHERE p.id = ?1 and o.orderStatus between 2 and 4")
    List<ProductStatisticDto> get1ProductStatistic(Long id);
    
    @Query("SELECT new com.godEShop.Dto.ProductStatisticDto" + "(od.id, p.name, o.createDate, od.quantity, od.price) "
    	    + "FROM Product p " + "INNER JOIN p.orderDetails od " + "INNER JOIN od.order o "
    	    + "WHERE p.id = ?1 and o.orderStatus between 2 and 4 and o.createDate between ?2 and ?3 ")
    List<ProductStatisticDto> get1ProductStatisticByTime(Long id, Date dateStart, Date dateEnd);

    @Query("SELECT new com.godEShop.Dto.ProductImageDto" + "(p.id, MIN(pp.id)) " + "FROM Product p "
	    + "INNER JOIN p.productPhotos pp " + "WHERE p.id like ?1 " + "GROUP BY p.id, p.name")
    List<ProductImageDto> getSingleProductImage(Long id);

    // ---------------------------------------------------------------------------

    @Query("SELECT new com.godEShop.Dto.ProductsStatisticDto" + "(p.id, p.name, p.quantity, sum(od.quantity)) "
	    + "FROM Product p " + "INNER JOIN p.orderDetails od " + "INNER JOIN od.order o " + "INNER JOIN o.account a "
	    + "INNER JOIN a.users u " + "WHERE o.orderStatus between 2 and 4 and u.gender = ?1 "
	    + "GROUP BY p.name, p.id, p.quantity "
	    + "ORDER BY  sum(od.quantity) DESC")
    List<ProductsStatisticDto> getBestProductStatisticByMale(int gender);
    
    @Query("SELECT new com.godEShop.Dto.ProductsStatisticDto" + "(p.id, p.name, p.quantity, sum(od.quantity)) "
    	    + "FROM Product p " + "INNER JOIN p.orderDetails od " + "INNER JOIN od.order o " + "INNER JOIN o.account a "
    	    + "INNER JOIN a.users u " + "WHERE o.orderStatus between 2 and 4 and u.gender = ?1 and o.createDate between ?2 and ?3 "
    	    + "GROUP BY p.name, p.id, p.quantity "
    	    + "ORDER BY  sum(od.quantity) DESC")
        List<ProductsStatisticDto> getBestProductStatisticByMaleByTime(int gender, Date dateStart, Date dateEnd);

    @Query("SELECT new com.godEShop.Dto.ProductImageDto" + "(p.id, MIN(pp.id)) " + "FROM Product p "
	    + "INNER JOIN p.productPhotos pp " + "GROUP BY p.id ")
    List<ProductImageDto> getBestProductImageByMale();
    

    @Query("SELECT new com.godEShop.Dto.ProductShopDto"
    	    + "(p.id, c.id, p.name, p.price, p.createDate, c.name, MIN(pp.id), CAST(AVG(pe.evaluation) AS int), pd.discount, p.detail, MAX(pd.endDate), p.quantity, pd.createDate) "
    	    + "FROM Product p " + "FULL JOIN p.productPhotos pp " + "FULL JOIN p.productEvaluations pe "
    	    + "FULL JOIN p.brand pb " + "FULL JOIN p.productDiscounts pd " + "FULL JOIN p.category c "
    	    + "FULL JOIN p.watches w "
    	    + "INNER JOIN p.productLikes pl " + "inner join pl.account a "
    	    + "WHERE p.isDeleted = 0 AND c.available = 0 AND pl.isLiked = 1 AND a.username = ?1 "
    	    + "GROUP BY p.id, c.id, p.name, p.price, p.createDate, c.name, pd.discount, p.detail, p.quantity, pd.createDate "
    	    + "")
        Page<ProductShopDto> productLike(String username,Pageable pageable );
}
