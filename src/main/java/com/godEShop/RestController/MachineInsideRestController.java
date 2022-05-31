package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Entity.MachineInside;
import com.godEShop.Service.MachineInsideService;

@CrossOrigin("*")
@RestController
public class MachineInsideRestController {

    @Autowired
    MachineInsideService miService;
    
    @GetMapping("/rest/machines")
    public List<MachineInside> getAll() {
	return miService.findAll();
    }
    
}
