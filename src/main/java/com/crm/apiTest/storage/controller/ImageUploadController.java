package com.crm.apiTest.storage.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.apiTest.storage.dto.ImageRequest;
import com.crm.apiTest.storage.dto.ImageResponse;
import com.crm.apiTest.storage.exception.StorageFileNotFoundException;
import com.crm.apiTest.storage.service.StorageService;


@Controller
@RequestMapping("api/v1/images")
public class ImageUploadController {

	private final StorageService storageService;

	@Autowired
	public ImageUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveImage(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping
	public ResponseEntity<ImageResponse> handleImageUpload(@Valid @RequestBody ImageRequest image) {
		String prefixedName = String.valueOf(System.currentTimeMillis())  + "_" +  image.getName();
		
		
		storageService.storeImage(image.getBase64image(),prefixedName , image.getMimeType());
		
		return ResponseEntity.ok(new ImageResponse("api/v1/images/" + prefixedName));
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
