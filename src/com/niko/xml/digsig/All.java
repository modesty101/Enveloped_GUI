package com.niko.xml.digsig;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Signature;

public class All {
	/* IS_CHUNKED의 논리값 참일 경우, 76자씩 개행 문자(&#13;)와 함께 인코딩된다. */
	private static final boolean IS_CHUNKED = false;
	private static final Nrypto util = new Nrypto();
	private static final String privateKeyPath = "keys" + File.separator + "private.key";
	private static final String publicKeyPath = "keys" + File.separator + "public.key";

	public static void generateKeys() {
		String keyPath = "keys";
		util.storeKeyPair(keyPath);
	}

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

	public static void encodeFileAll(String source, String dest) throws Exception {
		Base64IO.encodeFile(source, dest, IS_CHUNKED);

		NewXmlDoc.extractEncodingValue(dest, "_File_" + source + ".xml");

		Base64IO.decodeFile(dest, "_dec_" + source);

	}

	public static void encodeDocAll(String source, String dest) throws Exception {
		Base64IO.encodeFile(source, dest, IS_CHUNKED);

		NewXmlDoc.extractEncodingValue(dest, "_Doc_" + source + ".xml");

		Base64IO.decodeFile(dest, "_dec_" + source);

	}

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

	public static String generateAll(String oriXml) {
		String oriXmlPath = oriXml;
		String destSignedXmlPath = "_Signed" + oriXml;
		// String privateKeyPath = "keys" + File.separator + "private.key";
		// String publicKeyPath = "keys" + File.separator + "public.key";
		GenDigSigXml.generateXMLDigitalSignature(oriXmlPath, destSignedXmlPath, privateKeyPath, publicKeyPath);
		
		return destSignedXmlPath;
	}

	public static void main(String[] args) throws Exception {

	}
}
