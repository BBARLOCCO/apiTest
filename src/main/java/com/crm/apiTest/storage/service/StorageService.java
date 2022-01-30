package com.crm.apiTest.storage.service;

import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

	void storeImage(String base64file, String name, String mimeType);

}
