package com.godEShop.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.godEShop.Entity.ProductPhoto;

@Service
public class FileManagerService {
    @Autowired
    ServletContext app;
    
    @Autowired
    ProductPhotoService ppService;
    
    @Autowired
    ProductService productService;

    public List<String> save(Long id, MultipartFile[] files) {
	List<String> filenames = new ArrayList<String>();
	for (MultipartFile file : files) {
	    String name = System.currentTimeMillis() + file.getOriginalFilename();
	    String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
	    File dir = new File("src\\main\\resources\\static\\upload\\ProductImages");
	    ProductPhoto productPhoto = new ProductPhoto();
	    productPhoto.setId("zz" + filename.toString());
	    productPhoto.setProduct(productService.getById(id));
	    ppService.create(productPhoto);
	    try {
		File savedFile = new File(dir.getAbsolutePath(), "zz" + filename);
		System.out.println(" >> File just saved: " + savedFile.getAbsolutePath());
		file.transferTo(savedFile);
		filenames.add(filename);
	    } catch (Exception e) {
		System.out.println("  >> Save file : " + e.getMessage());
		e.printStackTrace();
	    }
	}
	return filenames;
    }

    public void delete(String filename) {
	File dir = new File("src\\main\\resources\\static\\upload\\ProductImages\\" + filename);
	dir.getAbsoluteFile().delete();
	ppService.deleteImageWatch(filename);
    }

    public List<String> list(Long id) {
	List<String> filenames = ppService.getAllNamePhotoByProductId(id);
	return filenames;
    }
}
