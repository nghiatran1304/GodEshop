package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.BraceletMaterial;

public interface BraceletMaterialService {
    List<BraceletMaterial> findAll();

    BraceletMaterial getById(Integer id);
    
}
