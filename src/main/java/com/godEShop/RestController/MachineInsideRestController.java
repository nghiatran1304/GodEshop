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

	@GetMapping("/rest/machines/{name}")
	public List<MachineInside> getAllMachineInsideByName(@PathVariable("name") String name) {
		return miService.getAllMachineInsideByName("%" + name + "%");
	}

	@DeleteMapping("/rest/deletemachine/{id}")
	public void deleteMachine(@PathVariable("id") Integer id) {
		miService.delete(id);
	}

	@PutMapping("rest/updatemachine/{id}")
	public MachineInside updateMachine(@PathVariable("id") Integer id, @RequestBody MachineInside machineInside) {
		return miService.update(machineInside);
	}

	@PostMapping("rest/createmachine")
	public MachineInside createBrand(@RequestBody MachineInside machineInside) {
		return miService.create(machineInside);
	}

}
