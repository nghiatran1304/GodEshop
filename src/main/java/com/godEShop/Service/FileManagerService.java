package com.godEShop.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	int numberImage = 0;
	for (MultipartFile file : files) {
	    String name = System.currentTimeMillis() + file.getOriginalFilename();
	    String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
	    File dir = new File("src\\main\\resources\\static\\upload\\ProductImages");
	    ProductPhoto productPhoto = new ProductPhoto();
	    productPhoto.setId("zz" + String.valueOf(numberImage) + filename.toString());
	    productPhoto.setProduct(productService.getById(id));
	    ppService.create(productPhoto);
	    try {
		File savedFile = new File(dir.getAbsolutePath(), "zz" + String.valueOf(numberImage) + filename);
		System.out.println(" >> File just saved: " + savedFile.getAbsolutePath());
		file.transferTo(savedFile);
		filenames.add(filename);
		numberImage++;
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

    // =======================================FILE
    // TẠM=============================================================
    private Path getPath(String folder, String filename) {
	File dir = Paths.get(app.getRealPath("/"), folder).toFile();
	if (!dir.exists()) {
	    dir.mkdir();
	}
	return Paths.get(dir.getAbsolutePath(), filename);
    }

    public byte[] read(String folder, String filename) {
	Path path = this.getPath(folder, filename);
	try {
	    return Files.readAllBytes(path);
	} catch (IOException e) {
	    byte a[] = new byte[1];
	    return a;
	}
    }

    public List<String> save1(String folder, MultipartFile[] files) {
	List<String> filenames = new ArrayList<String>();
	try {
	    for (MultipartFile file : files) {
		String name = System.currentTimeMillis() + file.getOriginalFilename();
		String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
		Path path = this.getPath(folder, filename);
		try {
		    file.transferTo(path);
		    System.out.println(">> Folder : " + folder);
		    filenames.add(filename);
		} catch (Exception e) {
		    System.out.println("  >> Save file : " + e.getMessage());
		    e.printStackTrace();
		}
	    }
	    return filenames;
	} catch (Exception e) {
	    return filenames;
	}

    }

    public void delete1(String folder, String filename) {
	Path path = this.getPath(folder, filename);
	path.toFile().delete();
    }

    public List<String> list1(String folder) {
	try {
	    List<String> filenames = new ArrayList<>();
	    File dir = Paths.get(app.getRealPath("/files/"), folder).toFile();
	    System.out.println(" >> Path : " + dir.getAbsolutePath());
	    if (dir.exists()) {
		File[] files = dir.listFiles();
		for (File file : files) {
		    filenames.add(file.getName());
		}
	    }
	    return filenames;
	} catch (Exception e) {
	    List<String> a = new ArrayList<>();
	    return a;
	}

    }

}
