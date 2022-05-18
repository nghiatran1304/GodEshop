package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Long> {

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
    Page<Product> findAllPriceDec(String keywords, Pageable pageable);

}
