package com.niko.xml.digsig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class Base64IO {

	/**
	 * Base64 인코딩할 파일을 불러오고 저장합니다.
	 * 
	 * @param encodeFile
	 * @param encodingFile
	 * @param isChunked
	 * @throws IOException
	 */
	public static void encodeFile(String encodeFile, String encodingFile, boolean isChunked) throws IOException {

		byte[] encodingImage = Base64.encodeBase64(loadFile(encodeFile), isChunked);

		writeFile(encodingFile, encodingImage);
	}

	/**
	 * Base64 디코딩할 파일을 불러오고 저장합니다.
	 * 
	 * @param decodeFile
	 * @param decodedFile
	 * @throws Exception
	 */
	public static void decodeFile(String decodeFile, String decodedFile) throws Exception {

		byte[] bytes = Base64.decodeBase64(loadFile(decodeFile));

		writeFile(decodedFile, bytes);
	}

	/**
	 * 파일을 불러옵니다.
	 * 
	 * @param encodeFile
	 * @return bytes
	 * @throws IOException
	 */
	public static byte[] loadFile(String fileName) throws IOException {
		File file = new File(fileName);
		int len = (int) file.length();

		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[len];
		reader.read(bytes, 0, len);
		reader.close();

		return bytes;
	}

	/**
	 * 파일을 저장합니다.
	 * 
	 * @param fileName
	 * @param encodedFile
	 * @throws IOException
	 */
	public static void writeFile(String fileName, byte[] encodedFile) throws IOException {
		File file = new File(fileName);
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
		writer.write(encodedFile);
		writer.flush();
		writer.close();
	}
}
