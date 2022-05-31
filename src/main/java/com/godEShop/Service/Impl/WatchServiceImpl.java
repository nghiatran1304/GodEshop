package com.godEShop.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.WatchDAO;
import com.godEShop.Entity.Watch;
import com.godEShop.Service.WatchService;

@Service
public class WatchServiceImpl implements WatchService {

    @Autowired
    WatchDAO watchDAO;

    @Override
    public Watch update(Watch w) {
	// TODO Auto-generated method stub
	return watchDAO.save(w);
    }

    @Override
    public Watch create(Watch w) {
	// TODO Auto-generated method stub
	return watchDAO.save(w);
    }

    @Override
    public Watch getById(Integer watchId) {
	// TODO Auto-generated method stub
	return watchDAO.getById(watchId);
    }

}
