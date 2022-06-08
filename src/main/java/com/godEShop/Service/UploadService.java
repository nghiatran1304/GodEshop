package com.godEShop.Service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.godEShop.Entity.User;

public interface UploadService {
    File save(MultipartFile file, String folder);
    
    File saveUser(MultipartFile file,User user);
    
}
