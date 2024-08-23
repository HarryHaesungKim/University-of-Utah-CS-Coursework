package assignment1cs4440;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

public class CryptUtil {

	public static byte[] createSha1(File file) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		InputStream fis = new FileInputStream(file);
		int n = 0;
		byte[] buffer = new byte[8192];
		while (n != -1) {
			n = fis.read(buffer);
			if (n > 0) {
				digest.update(buffer, 0, n);
			}
		}
		fis.close();
		return digest.digest();
	}

	public static boolean compareSha1(String filename1, String filename2) throws Exception {
		File file1 = new File(filename1);
		File file2 = new File(filename2);
		byte[] fsha1 = CryptUtil.createSha1(file1);
		byte[] fsha2 = CryptUtil.createSha1(file2);
		return Arrays.equals(fsha1, fsha2);
	}

	public static double getShannonEntropy(String s) {
		int n = 0;
		Map<Character, Integer> occ = new HashMap<>();

		for (int c_ = 0; c_ < s.length(); ++c_) {
			char cx = s.charAt(c_);
			if (occ.containsKey(cx)) {
				occ.put(cx, occ.get(cx) + 1);
			} else {
				occ.put(cx, 1);
			}
			++n;
		}

		double e = 0.0;
		for (Map.Entry<Character, Integer> entry : occ.entrySet()) {
			char cx = entry.getKey();
			double p = (double) entry.getValue() / n;
			e += p * log2(p);
		}
		return -e;
	}

	public static double getShannonEntropy(byte[] data) {

		if (data == null || data.length == 0) {
			return 0.0;
		}

		int n = 0;
		Map<Byte, Integer> occ = new HashMap<>();

		for (int c_ = 0; c_ < data.length; ++c_) {
			byte cx = data[c_];
			if (occ.containsKey(cx)) {
				occ.put(cx, occ.get(cx) + 1);
			} else {
				occ.put(cx, 1);
			}
			++n;
		}

		double e = 0.0;
		for (Map.Entry<Byte, Integer> entry : occ.entrySet()) {
			byte cx = entry.getKey();
			double p = (double) entry.getValue() / n;
			e += p * log2(p);
		}
		return -e;
	}

	public static double getFileShannonEntropy(String filePath) {
		try {
			byte[] content;
			content = Files.readAllBytes(Paths.get(filePath));
			return CryptUtil.getShannonEntropy(content);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}

	}

	private static double log2(double a) {
		return Math.log(a) / Math.log(2);
	}

	public static void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}

	public static Byte randomKey() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 8;
		Random random = new Random();
		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return generatedString.getBytes()[0];
	}

	/**
	 * Encryption (Bytes)
	 *
	 * @param data
	 * @param key
	 * @return encrypted bytes
	 */
	public static byte[] cs4440Encrypt(byte[] data, Byte key) {
		// TODO
		byte[] cipherdata = new byte[8];

		// Your code here
		for (int i = 0; i < 8; i++) {
			cipherdata[i] = (byte) (data[i] ^ key);
			// key = cipherdata[i];
		}

		return cipherdata;
	}

	/**
	 * Encryption (file)
	 *
	 * @param plainfilepath
	 * @param cipherfilepath
	 * @param key
	 */
	public static int encryptDoc(String plainfilepath, String cipherfilepath, Byte key) {
		try {
			// TODO

			// Reader and writer set up.
			FileInputStream fis = new FileInputStream(plainfilepath);
			FileOutputStream fos = new FileOutputStream(cipherfilepath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

			// Setting up initialization Vector and input array.
			byte[] initializationVector = "ABCDEFGH".getBytes();
			byte[] input = new byte[8];

			// Gathering inputs set up.
			byte[] encrypted = new byte[8];
			String eightCharString = "";

			// Initiating the first line.
			String line = reader.readLine();
			int lineCounter = 0;

			// First line indication to skip first line.
			boolean isFirstLine = true;

			// Indication that a block got cut off by a new line.
			boolean cutoff = false;
			int cutoffIndex = 0;

			while (line != null) {

				if (!isFirstLine) {
					writer.newLine();
				} else {
					isFirstLine = false;
				}

				// In each line
				for (int i = 0; i < line.length(); i++) {

					// add character by character
					eightCharString += line.charAt(i);

					// If we reach eight characters, decrypt it and write to the file
					if (eightCharString.length() == 8) {

						// XOR with iv before being encrypted.
						for (int j = 0; j < 8; j++) {
							input[j] = (byte) (eightCharString.getBytes()[j] ^ initializationVector[j]);
						}

						encrypted = CryptUtil.cs4440Encrypt(input, key);

						// Setting encrypted as new iv.
						initializationVector = encrypted;

						for (byte b : encrypted) {
							writer.write(b + " ");
						}

						// Resetting the string
						eightCharString = "";
					}

					// If we run out of characters, go to the next line and continue building to
					// eightCharString and writing to the file.
				}

				// Writing the last bit then
				if (eightCharString.length() != 0) {

					for (int i = eightCharString.length(); i < 8; i++) {
						input[i] = (byte) (8 - eightCharString.length());
					}

					// XOR with iv before being encrypted.
					for (int j = 0; j < eightCharString.length(); j++) {
						input[j] = (byte) (eightCharString.getBytes()[j] ^ initializationVector[j]);
					}
					
					for (int j = eightCharString.length(); j < 8; j++) {
						input[j] = (byte) (input[j] ^ initializationVector[j]);
					}

					encrypted = CryptUtil.cs4440Encrypt(input, key);

					// Setting encrypted as new iv.
					initializationVector = encrypted;

					// writer.write(new String(encrypted, StandardCharsets.UTF_8).substring(0,
					// eightCharString.length()));
					int counter = 0;
					for (byte b : encrypted) {
						writer.write(b + " ");
						counter++;
					}

					// Resetting the string
					eightCharString = "";
				}

				line = reader.readLine();
			}

			reader.close();
			writer.close();

			return 0;

		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	/**
	 * decryption
	 *
	 * @param data
	 * @param key
	 * @return decrypted content
	 */
	public static byte[] cs4440Decrypt(byte[] data, Byte key) {
		// TODO
		byte[] plaindata = new byte[8];

		if (data.length < 8) {
			for (int i = data.length; i < 8; i++) {
				plaindata[i] = (byte) (8 - data.length);
			}
		}

		// Your code here
		for (int i = 0; i < 8; i++) {
			plaindata[i] = (byte) (data[i] ^ key);
		}

		return plaindata;

		// return 0;
	}

	/**
	 * Decryption (file)
	 * 
	 * @param plainfilepath
	 * @param cipherfilepath
	 * @param key
	 */
	public static int decryptDoc(String cipherfilepath, String plainfilepath, Byte key) {
		// TODO

		int linecounter = 0;

		try {
			// TODO

			// Reader and writer set up.
			FileInputStream fis = new FileInputStream(cipherfilepath);
			FileOutputStream fos = new FileOutputStream(plainfilepath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

			// Setting up initialization Vector.
			byte[] initializationVector = "ABCDEFGH".getBytes();
			byte[] output = new byte[8];

			// Gathering inputs set up.
			byte[] encrypted = new byte[8];
			byte[] input = new byte[8];

			// Initiating first line.
			String line = reader.readLine();

			// First line indication to skip first line.
			boolean isFirstLine = true;

			while (line != null) {

				if (!isFirstLine) {
					writer.newLine();
				} else {
					isFirstLine = false;
				}

				String[] splited = line.split("\\s+");

				int tokenCounter = 0;

				if (splited[0].equals("")) {
					line = reader.readLine();
					linecounter++;
					continue;
				}

				// In each line
				for (int i = 0; i < splited.length; i++) {

					// add character by character
					// eightCharString += line.charAt(i);
					input[tokenCounter] = Byte.parseByte(splited[i]);

					tokenCounter++;

					// If we reach eight characters, decrypt it and write to the file
					if (tokenCounter == 8) {
						encrypted = CryptUtil.cs4440Decrypt(input, key);

						// XOR with iv after being decrypted.
						for (int j = 0; j < 8; j++) {
							output[j] = (byte) (encrypted[j] ^ initializationVector[j]);
						}
						
						// New initialization vector to be used in the next block.
						initializationVector = input;
						
						// Getting rid of padding.
						int paddingCounter = 0;
						for(byte b: output) {
							if(b < 8) {
								paddingCounter++;
							}
						}

						writer.write(new String(output, StandardCharsets.UTF_8).substring(0, 8 - paddingCounter));
						// Resetting the string
						tokenCounter = 0;
						input = new byte[8];
					}

					// If we run out of characters, go to the next line and continue building to
					// eightCharString and writing to the file.
				}

				// Writing the last bit then
				if (tokenCounter != 0) {

					encrypted = CryptUtil.cs4440Decrypt(input, key);

					// XOR with iv after being decrypted.
					for (int j = 0; j < tokenCounter; j++) {
						output[j] = (byte) (encrypted[j] ^ initializationVector[j]);
					}

					initializationVector = input;

					writer.write(new String(output, StandardCharsets.UTF_8).substring(0, tokenCounter));
					// Resetting the string
					tokenCounter = 0;
					input = new byte[8];
				}

				line = reader.readLine();
				linecounter++;
			}

			// Because of padding, we shouldn't have to add the last characters back.
			// We do need to somehow get rid of the last few paddings.

			reader.close();
			writer.close();

			return 0;

		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	public static void main(String[] args) {

		String targetFilepath = "";
		String encFilepath = "";
		String decFilepath = "";
		if (args.length == 3) {
			try {
				File file1 = new File(args[0].toString());
				if (file1.exists() && !file1.isDirectory()) {
					targetFilepath = args[0].toString();
				} else {
					System.out.println("File does not exist!");
					System.exit(1);
				}

				encFilepath = args[1].toString();
				decFilepath = args[2].toString();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			// targetFilepath = "cs4440-a1-testcase1.html";
			System.out.println("Usage: java CryptoUtil file_to_be_encrypted encrypted_file decrypted_file");
			System.exit(1);
		}

		Byte key = randomKey();
		String src = "ABCDEFGH";
		System.out.println("[*] Now testing plain sample： " + src);
		try {
			byte[] encrypted = CryptUtil.cs4440Encrypt(src.getBytes(), key);
			StringBuilder encsb = new StringBuilder();
			for (byte b : encrypted) {
				encsb.append(String.format("%02X ", b));
			}
			System.out.println("[*] The  encrypted sample  [Byte Format]： " + encsb);
			double entropyStr = CryptUtil.getShannonEntropy(encrypted.toString());
			System.out.printf("[*] Shannon entropy of the text sample (to String): %.12f%n", entropyStr);
			double entropyBytes = CryptUtil.getShannonEntropy(encrypted);
			System.out.printf("[*] Shannon entropy of encrypted message (Bytes): %.12f%n", entropyBytes);

			byte[] decrypted = CryptUtil.cs4440Decrypt(encrypted, key);
			if (Arrays.equals(decrypted, src.getBytes())) {
				System.out.println("[+] It works!  decrypted ： " + decrypted);
			} else {
				System.out.println("Decrypted message does not match!");
			}

			// File Encryption
			System.out.printf("[*] Encrypting target file: %s \n", targetFilepath);
			System.out.printf("[*] The encrypted file will be: %s \n", encFilepath);
			System.out.printf("[*] The decrypted file will be: %s \n", decFilepath);

			CryptUtil.encryptDoc(targetFilepath, encFilepath, key);
			CryptUtil.decryptDoc(encFilepath, decFilepath, key);

			System.out.printf("[+] [File] Entropy of the original file: %s \n",
					CryptUtil.getFileShannonEntropy(targetFilepath));
			System.out.printf("[+] [File] Entropy of encrypted file: %s \n",
					CryptUtil.getFileShannonEntropy(encFilepath));

			if (CryptUtil.compareSha1(targetFilepath, decFilepath)) {
				System.out.println("[+] The decrypted file is the same as the source file");
			} else {
				System.out.println("[+] The decrypted file is different from the source file.");
				System.out.println("[+] $ cat '<decrypted file>' to to check the differences");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}