package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.ProductComment;

@Repository
public interface ProductCommentDAO extends JpaRepository<ProductComment, Long> {

}
