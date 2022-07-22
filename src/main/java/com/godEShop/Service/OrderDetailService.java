package com.godEShop.Service;

import java.util.List;

import com.godEShop.Dto.OrderCartViewDto;
import com.godEShop.Entity.OrderDetail;

public interface OrderDetailService {

    List<OrderCartViewDto> findByIdOrderCartViewDto(Long id);

    List<OrderDetail> findAllProductByOrderDetailId(Long id);
}
