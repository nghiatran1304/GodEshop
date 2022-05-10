package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.VoucherList;

@Repository
public interface VoucherListDAO extends JpaRepository<VoucherList, Integer> {

}
