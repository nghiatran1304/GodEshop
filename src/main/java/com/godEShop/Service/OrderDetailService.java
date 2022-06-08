package com.godEShop.Service;

import java.util.List;

import com.godEShop.Dto.OrderCartViewDto;

public interface OrderDetailService {

    List<OrderCartViewDto> findByIdOrderCartViewDto(Long id);

}
