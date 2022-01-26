package com.crm.apiTest.util;

import java.nio.charset.Charset;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomStringUtil {
	public String generateRandomString(int length) {
		byte[] array = new byte[length]; // length is bounded by 7
	    new Random().nextBytes(array);
	    return new String(array, Charset.forName("UTF-8"));
	}
}
