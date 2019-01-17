package com.huboot.commons.utils;

import java.io.Serializable;

/*****
 * 二进制文件
 * @author Coollf
 *
 */
public class ByteFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ByteFile() {
	}

	public ByteFile(String fileName, byte[] data){
		this.fileName = fileName;
		this.content = data;
		this.size = data==null ? 0: data.length;
	}
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	private byte []  content;
	
	private long size;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	

}
