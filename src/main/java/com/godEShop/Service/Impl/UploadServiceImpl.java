package com.godEShop.Service.Impl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.godEShop.Service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    ServletContext app;

    @Override
    public File save(MultipartFile file, String folder) {
	// TODO Auto-generated method stub

//	File dir = new File("resources\\static\\upload\\" + folder);
	File dir = new File("src\\main\\resources\\static\\upload\\ProductImages");

	String absolutePath = dir.getAbsolutePath().toString();
	System.out.println(" >> Path: " + absolutePath);

	String s = System.currentTimeMillis() + file.getOriginalFilename();
	String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));

	try {
	    File savedFile = new File(dir.getAbsolutePath(), name);
	    file.transferTo(savedFile);
	    System.out.println(" >> File just saved: " + savedFile.getAbsolutePath());
	    return savedFile;
	} catch (Exception e) {
	    System.out.println(" >> Error UploadServiceImpl : " + e.getMessage());
	    throw new RuntimeException(e);
	}
    }

    // -------------------------------------------------------------------------

}
