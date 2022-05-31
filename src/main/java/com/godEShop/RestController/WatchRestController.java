package com.godEShop.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Dto.ProductWatchInfoDto;
import com.godEShop.Entity.BraceletMaterial;
import com.godEShop.Entity.GlassMaterial;
import com.godEShop.Entity.MachineInside;
import com.godEShop.Entity.Watch;
import com.godEShop.Service.BraceletMaterialService;
import com.godEShop.Service.GlassMaterialService;
import com.godEShop.Service.MachineInsideService;
import com.godEShop.Service.ProductService;
import com.godEShop.Service.WatchService;

@CrossOrigin("*")
@RestController
public class WatchRestController {

    @Autowired
    WatchService watchService;

    @Autowired
    BraceletMaterialService bmService;

    @Autowired
    GlassMaterialService gmService;

    @Autowired
    MachineInsideService miService;

    @Autowired
    ProductService pService;

    @PutMapping("/rest/watches/{id}")
    public Watch update(@PathVariable("id") Integer id, @RequestBody ProductWatchInfoDto product) {

	BraceletMaterial bm = bmService.getById(product.getBraceletMaterialId());
	GlassMaterial gm = gmService.getById(product.getGlassMaterialId());
	MachineInside mi = miService.getById(product.getMachineInsideId());

	Watch w = watchService.getById(product.getWatchId());
	w.setAtm(product.getWatchATM());
	w.setCaseColors(product.getWatchCaseColor());
	w.setGender(product.getWatchGender());
	w.setGlassColors(product.getWatchGlassColor());
	w.setGlassSizes(product.getWatchGlassSize());
	w.setBraceletMaterial(bm);
	w.setGlassMaterial(gm);
	w.setMachineInside(mi);
	w.setProduct(w.getProduct());

	return watchService.update(w);
    }

    @PostMapping("/rest/watches")
    public Watch create(@RequestBody ProductWatchInfoDto product) {

	BraceletMaterial bm = bmService.getById(product.getBraceletMaterialId());
	GlassMaterial gm = gmService.getById(product.getGlassMaterialId());
	MachineInside mi = miService.getById(product.getMachineInsideId());

	Watch w = new Watch();
	w.setAtm(product.getWatchATM());
	w.setCaseColors(product.getWatchCaseColor());
	w.setGender(product.getWatchGender());
	w.setGlassColors(product.getWatchGlassColor());
	w.setGlassSizes(product.getWatchGlassSize());
	w.setBraceletMaterial(bm);
	w.setGlassMaterial(gm);
	w.setMachineInside(mi);
	w.setProduct(pService.getById(product.getProductId()));
	

	return watchService.create(w);
    }
}
