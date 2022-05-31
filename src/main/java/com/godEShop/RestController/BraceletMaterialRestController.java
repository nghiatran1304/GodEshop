package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Entity.BraceletMaterial;
import com.godEShop.Service.BraceletMaterialService;

@CrossOrigin("*")
@RestController
public class BraceletMaterialRestController {
    
    @Autowired
    BraceletMaterialService bmService;

    @GetMapping("/rest/bracelets")
    public List<BraceletMaterial> getAll() {
	return bmService.findAll();
    }
}
