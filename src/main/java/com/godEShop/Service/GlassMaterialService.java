package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.GlassMaterial;

public interface GlassMaterialService {
    
    List<GlassMaterial> findAll();

    GlassMaterial getById(Integer glassMaterialId);
    
}
