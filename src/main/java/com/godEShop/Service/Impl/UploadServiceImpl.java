package com.godEShop.Service.Impl;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.godEShop.Service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

	@Override
	public File save(MultipartFile file, String folder, String type) {
		// TODO Auto-generated method stub
		File dir = new File("resources/static/upload/" + type);

		String absolutePath = dir.getAbsolutePath();
		System.out.println(" >> Path: " + absolutePath);

		String s = System.currentTimeMillis() + file.getOriginalFilename();
		String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
		try {
			File savedFile = new File(dir, name);
			file.transferTo(savedFile);
			System.out.println(" >> File just saved: " + savedFile.getAbsolutePath());
			return savedFile;
		} catch (Exception e) {
			System.out.println(" >> Error UploadServiceImpl : " + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
