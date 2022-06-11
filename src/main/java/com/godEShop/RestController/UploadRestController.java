package com.godEShop.RestController;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.godEShop.Service.FileManagerService;
import com.godEShop.Service.UploadService;

@CrossOrigin("*")
@RestController
public class UploadRestController {

    @Autowired
    UploadService uploadService;

    @PostMapping("/rest/upload/{folder}")
    public JsonNode upload(@PathParam("file") MultipartFile file, @PathVariable("folder") String folder) {
	File savedFile = uploadService.save(file, folder);
	ObjectMapper mapper = new ObjectMapper();
	ObjectNode node = mapper.createObjectNode();
	node.put("name", savedFile.getName());
	node.put("size", savedFile.length());
	return node;
    }

    // ----------------------------- UPLOAD MULTI IMAGE
    // ---------------------------------
    @Autowired
    FileManagerService fileService;

    @PostMapping("/rest/uploadImages/{productId}")
    public List<String> uploadImages(@PathVariable("productId") Long id, @PathParam("files") MultipartFile[] files) {
	return fileService.save(Long.parseLong(id.toString()), files);
    }

    @DeleteMapping("/rest/deleteImage/{imgName}")
    public void delete(@PathVariable("imgName") String imgName) {
	fileService.delete(imgName);
    }

    @GetMapping("/rest/getImages/{productId}")
    public List<String> listImages(@PathVariable("productId") Long id) throws Exception{
	return fileService.list(id);
    }

    // ------------------------------- UPLOAD FILE TẠM
    // ------------------------------------------
    
    @GetMapping("/rest/files/{folder}/{file}")
    public byte[] download(@PathVariable("folder") String folder, @PathVariable("file") String file, HttpServletResponse response) {
	response.setHeader("Content-Disposition", "attachment;filename=" + "OK");
	return fileService.read(folder, file);
    }
    
    
    @PostMapping("/rest/files/{folder}")
    public List<String> upload(@PathVariable("folder") String folder, @PathParam("files") MultipartFile[] files) {
	return fileService.save1(folder, files);
    }

    @DeleteMapping("/rest/files/{folder}/{file}")
    public void delete(@PathVariable("folder") String folder, @PathVariable("file") String file) {
	fileService.delete1(folder, file);
    }

    
    @GetMapping("/rest/files/{folder}")
    public List<String> list1(@PathVariable("folder") String folder) {
	return fileService.list1(folder);
    }
    
}
