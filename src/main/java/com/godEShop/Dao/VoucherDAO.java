package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Voucher;

@Repository
public interface VoucherDAO extends JpaRepository<Voucher, Long> {

}
