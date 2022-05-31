package com.godEShop.Service.Impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    HttpSession session;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String name) {
	// TODO Auto-generated method stub
	return (T) session.getAttribute(name);
    }

    @Override
    public <T> T get(String name, T defaultValue) {
	// TODO Auto-generated method stub
	T value = get(name);
	return value != null ? value : defaultValue;
    }

    @Override
    public void set(String name, Object value) {
	// TODO Auto-generated method stub
	session.setAttribute(name, value);
    }

    @Override
    public void remove(String name) {
	// TODO Auto-generated method stub
	session.removeAttribute(name);
    }

}
