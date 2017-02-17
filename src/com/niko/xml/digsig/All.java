package com.niko.xml.digsig;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Signature;

/**
 * 테스팅 함수입니다.
 *
 * @author genius
 * @since 2017
 */
public class All {

	/* IS_CHUNKED의 논리값 참일 경우, 76자씩 개행 문자(&#13;)와 함께 인코딩된다. */
	private static final boolean IS_CHUNKED = false;
	private static final Nrypto util = new Nrypto();
	/* 공개키와 개인키의 경로 */
	private static final String privateKeyPath = "keys" + File.separator + "private.key";
	private static final String publicKeyPath = "keys" + File.separator + "public.key";

	/**
	 * 'Keys' 폴더가 존재하는지 확인한다.
	 * 
	 * @param dirPath
	 * @return flag, meaning of folder Exist(true:Exist, false:NOT Exist)
	 */
	public static boolean isExistKeysDir(String dirPath) {
		File keysDir = new File(dirPath);
		boolean flag = false;

		if (!keysDir.exists()) {
			System.out.println("creating keys directory: keys");
			keysDir.mkdirs();
			flag = true;
		} else {
			System.out.println("keys folder already created!");
			flag = false;
		}

		return flag;
	}

	/**
	 * 테스트: 키 생성
	 */
	public static void generateKeys() {
		String keyPath = "keys";
		if (true == isExistKeysDir(keyPath)) {
			util.storeKeyPair(keyPath);
		} else {
			// Nothing..
		}
	}

	/**
	 * 테스트: 파일 서명
	 * 
	 * @param source
	 * @throws Exception
	 */
	public static void genSig(String source) throws Exception {
		// Get an instance of Signature object and initialize it.
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(util.storedPrivateKey(privateKeyPath));
		// signature.initSign(privateKey);

		// Supply the data to be signed to the Signature object
		// using the update() method and generate the digital
		// signature.
		byte[] bytes = Files.readAllBytes(Paths.get(source));
		signature.update(bytes);
		byte[] digitalSignature = signature.sign();

		Files.write(Paths.get("verify", "signature"), digitalSignature);
		Files.write(Paths.get("verify", "publickey"), util.storedPublicKey(publicKeyPath).getEncoded());
	}

	/**
	 * 테스트: Base64 파일 인코딩
	 * 
	 * @param source
	 * @param dest
	 * @throws Exception
	 */
	public static void encodeFileAll(String source, String dest) throws Exception {
		Base64IO.encodeFile(source, dest, IS_CHUNKED);

		NewXmlDoc.extractEncodingValue(dest, "_File_" + source + ".xml");

		Base64IO.decodeFile(dest, "_dec_" + source);
	}

	/**
	 * 테스트: Base64 문서 인코딩
	 * 
	 * @param source
	 * @param dest
	 * @throws Exception
	 */
	public static void encodeDocAll(String source, String dest) throws Exception {
		Base64IO.encodeFile(source, dest, IS_CHUNKED);

		NewXmlDoc.extractEncodingValue(dest, "_Doc_" + source + ".xml");

		Base64IO.decodeFile(dest, "_dec_" + source);
	}

	/**
	 * 테스트: 키 존재 여부
	 */
	public static void keysAlive() {
		String keyPairPath = "keys";
		// Nrypto util = new Nrypto();

		if (util.areKeysPresent()) {
			System.out.println("Keys are already existed..!!");
		} else {
			System.out.println("Private & Public Keys generating...");
			System.out.println("===================================");
			System.out.println("Complete.");
			util.storeKeyPair(keyPairPath);
		}
	}

	/**
	 * 테스트: XML 전자 서명
	 * 
	 * @param oriXml
	 * @return destSignedXmlPath
	 */
	public static String generateAll(String oriXml) {
		String oriXmlPath = oriXml;
		String destSignedXmlPath = "_Signed" + oriXml;
		// String privateKeyPath = "keys" + File.separator + "private.key";
		// String publicKeyPath = "keys" + File.separator + "public.key";
		GenDigSigXml.generateXMLDigitalSignature(oriXmlPath, destSignedXmlPath, privateKeyPath, publicKeyPath);

		return destSignedXmlPath;
	}
}
