package com.crm.apiTest.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequest {
	@NotBlank(message = "base64Image is mandatory")
	String base64image;
	@NotBlank(message = "name is mandatory")
	String name;
	@NotBlank(message = "mimeType is mandatory")
	String mimeType;
}
