package com.mouritech.auth.util;

import org.springframework.core.io.ByteArrayResource;

public class FileSystemResource extends ByteArrayResource {

	private String fileName;

	public FileSystemResource(byte[] byteArray, String filename) {
		super(byteArray);
		this.fileName = filename;
	}

	@Override
	public String getFilename() {
		return fileName;
	}

	public void setFilename(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
