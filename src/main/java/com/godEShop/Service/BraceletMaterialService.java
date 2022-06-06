package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.BraceletMaterial;

public interface BraceletMaterialService {
    List<BraceletMaterial> findAll();

    BraceletMaterial getById(Integer id);
    
    List<BraceletMaterial> getAllBraceletByName(String name);
    
    void delete(Integer id);
    
    BraceletMaterial update(BraceletMaterial braceletMaterial);
    
    BraceletMaterial create(BraceletMaterial braceletMaterial);
    
    List<BraceletMaterial> findAllBraceletMaterial();
    
    
}
