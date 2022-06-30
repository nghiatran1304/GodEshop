package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.AccessoryDAO;
import com.godEShop.Dao.ProductDAO;
import com.godEShop.Dto.ProductAccessoryInfoDto;
import com.godEShop.Entity.Accessory;
import com.godEShop.Service.AccessoryService;
@Service
public class AccessoryServiceImpl implements AccessoryService{
	@Autowired
	AccessoryDAO acDAO;
	@Autowired
	ProductDAO pDAO;
	@Override
	public List<ProductAccessoryInfoDto> lstFullInfoAccessory() {
		// TODO Auto-generated method stub
		return acDAO.lstFullInfoAccessory();
	}
	@Override
	public Accessory create(Accessory ac) {
		// TODO Auto-generated method stub
		return acDAO.save(ac);
	}
	@Override
	public Accessory getById(Integer accessoryId) {
		// TODO Auto-generated method stub
		return acDAO.getById(accessoryId);
	}
	@Override
	public Accessory update(Accessory ac) {
		// TODO Auto-generated method stub
		return acDAO.save(ac);
	}
	@Override
	public List<ProductAccessoryInfoDto> lstSearchFullInfoAccessory(String name) {
		// TODO Auto-generated method stub
		return acDAO.lstSearchFullInfoAccessory(name);
	}
	@Override
	public List<ProductAccessoryInfoDto> lstFullInfoAccessoryOutOfSoon() {
		// TODO Auto-generated method stub
		return acDAO.lstFullInfoAccessoryOutOfSoon();
	}

}
