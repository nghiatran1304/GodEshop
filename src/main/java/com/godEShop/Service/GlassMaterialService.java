package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.GlassMaterial;

public interface GlassMaterialService {

    List<GlassMaterial> getAllGlassMaterialByName(String name);

    List<GlassMaterial> findAll();

    GlassMaterial getById(Integer glassMaterialId);

    void delete(Integer id);

    GlassMaterial update(GlassMaterial glassMaterial);

    GlassMaterial create(GlassMaterial glassMaterial);

    List<GlassMaterial> findAllGlassMaterial();

}
