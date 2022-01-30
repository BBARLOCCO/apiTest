package com.crm.apiTest.storage.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.crm.apiTest.storage.config.StorageProperties;
import com.crm.apiTest.storage.exception.StorageException;
import com.crm.apiTest.storage.exception.StorageFileNotFoundException;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void storeImage(String base64file, String name, String mimeType) {
		try {
			
			if (base64file.contains(",")) {
				base64file = base64file.split(",")[1];
			}
			
			if (mimeType.contains("/")) {
				mimeType = mimeType.split("/")[1];
			}
			
			byte[] fileContent = Base64.getDecoder().decode(base64file);
			ByteArrayInputStream bais = new ByteArrayInputStream(fileContent);
			BufferedImage actualImage = ImageIO.read(bais);
			
			Path destinationPath = this.rootLocation.resolve(
					Paths.get(name))
					.normalize().toAbsolutePath();
			File destinationFile = destinationPath.toFile();
			destinationFile.getParentFile().mkdirs();
			
			Path root = this.rootLocation.toAbsolutePath();
			if (!destinationPath.getParent().equals(root)) {
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			
			ImageIO.write(actualImage, mimeType, destinationFile);
		}
		catch (IOException e) {
			throw new StorageException("Failed to store image.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
