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

@Service
public class FileManagerService {
    @Autowired
    ServletContext app;

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
	    throw new RuntimeException(e);
	}
    }

    public List<String> save(String folder, MultipartFile[] files) {
	List<String> filenames = new ArrayList<String>();
	for (MultipartFile file : files) {
	    String name = System.currentTimeMillis() + file.getOriginalFilename();
	    String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
	    Path path = this.getPath(folder, filename);
	    try {
		file.transferTo(path);
		System.out.println(">> Folder SAVE: " + folder);
		System.out.println(">> Folder SAVE: " + path);
		filenames.add(filename);
	    } catch (Exception e) {
		System.out.println("  >> Save file : " + e.getMessage());
		e.printStackTrace();
	    }
	}
	return filenames;
    }

    public void delete(String folder, String filename) {
	Path path = this.getPath(folder, filename);
	path.toFile().delete();
    }

    public List<String> list(String folder) {
	List<String> filenames = new ArrayList<>();
	File dir = Paths.get(app.getRealPath("/files/"), folder).toFile();
	System.out.println(" >> Path LIST: " + dir.getAbsolutePath());
	if (dir.exists()) {
	    File[] files = dir.listFiles();
	    for (File file : files) {
		filenames.add(file.getName());
	    }
	}
	return filenames;
    }
}
