package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @GetMapping("/rest/bracelets/{name}")
    public List<BraceletMaterial> getAllBraceletByName(@PathVariable("name") String name){
    	return bmService.getAllBraceletByName("%" + name + "%");
    }
    
    @DeleteMapping("/rest/deleteBracelet/{id}")
    public void deleteBracelet(@PathVariable("id") Integer id) {
    	bmService.delete(id);
    }
    
    @PutMapping("rest/updateBracelet/{id}")
    public BraceletMaterial updateBracelet(@PathVariable("id") Integer id, @RequestBody BraceletMaterial braceletMaterial) {
    	return bmService.update(braceletMaterial);
    }
    
    @PostMapping("/rest/createBracelet")
    public BraceletMaterial createBracelet(@RequestBody BraceletMaterial braceletMaterial) {
    	return bmService.create(braceletMaterial);
    }
   
    
}
