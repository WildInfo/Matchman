package com.wild.utils;

import java.util.UUID;

/**
 * 自动生成ID
 * 
 * @author Wild
 *
 */
public class UUIDUtil {

	public static String createUUID() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(createUUID());
		}
	}
}
