package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Entity.GlassMaterial;
import com.godEShop.Service.GlassMaterialService;

@CrossOrigin("*")
@RestController
public class GlassMaterialRestController {
    @Autowired
    GlassMaterialService glService;

    @GetMapping("/rest/glasses")
    public List<GlassMaterial> getAll() {
	return glService.findAll();
    }
}
