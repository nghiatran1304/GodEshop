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
    
    @GetMapping("/rest/glasses/{name}")
    public List<GlassMaterial>getAllGlassMaterialByName(@PathVariable("name") String name){
    	return glService.getAllGlassMaterialByName("%" +name+"%");
    }
    
    @DeleteMapping("/rest/deleteGlass/{id}")
    public void deleteGlass(@PathVariable("id") Integer id) {
    	glService.delete(id);
    }
    
    @PutMapping("rest/updateGlass/{id}")
    public GlassMaterial updateGlass(@PathVariable("id") Integer id, @RequestBody GlassMaterial glassMaterial) {
	return glService.update(glassMaterial);
    }

    @PostMapping("rest/createGlass")
    public GlassMaterial createGlass(@RequestBody GlassMaterial glassMaterial) {
	return glService.create(glassMaterial);
    }
    
}
