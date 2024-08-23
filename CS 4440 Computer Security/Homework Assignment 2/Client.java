import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
	private static final int PORT = 1337;
	private static final String SRVPUBKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDAZzhQdUIHkWLtDIe0rXONPYAwGY8WxiOqc7DAfJL/xoXQkYG0zep766kqkCHFOzuu5EKU2g03QbbWINgxGt6t2LM6ZyqoRJhM7g3mLxZ4TsH5hwElc6eq/KHGRuPE/f/eOmBWAVOVLgKdpHDZGzdA7MZjuvjYEgRhISr3/YKnQIDAQAB";

	public static void main(String[] args) throws IOException {
		// put your code here;

		try {
			// Connect to the server
			Socket clientSocket = new Socket("localhost", PORT);
			
			System.out.println("Got Client's connetion: " + clientSocket.getLocalSocketAddress().toString());

			// Generate Key pair
			System.out.println("==== RSA ====");
			KeyPair keyPair = RSAUtils.getKeyPair();
			String privateKey = new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()), "UTF-8");
			String publicKey = new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()), "UTF-8");
			System.out.println("Private key's length: " + privateKey.length());
			System.out.println("Public key's length:" + publicKey.length());

			// The signature of client RSA public key.
			String clientSign = RSAUtils.sign(publicKey, RSAUtils.getPrivateKey(privateKey));
			System.out.println("Signature: " + clientSign);

			BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream socketOut = new DataOutputStream(clientSocket.getOutputStream());

			// Once connected to the server, the client will send the first message to the
			// server
			// The message will consist of two parts: client RSA public key + the signature
			// of client RSA public key
			System.out.println("[+] Client's sending public_key + signature");
			socketOut.writeBytes(clientSign + "\n" + publicKey + "\n");

			// The server will send a message back to the client, which the client should
			// receive.
			// Format of the message: [string of an encrypted AES key + “\n” + a random
			// string + “ ” + Client IP + “\n” + signature of the encrypted “AES key +
			// random string” ].
			String encryptedAESKey = socketIn.readLine();
			String randomStringPlusClientIP = socketIn.readLine();
			String[] splited = randomStringPlusClientIP.split("\\s+");
			String randomString = splited[0];
			String signatureEncryptedAESKeyPlusRandoString = socketIn.readLine();
			
			// 6
			// a
			// Verifying the signature of the encrypted AES key can reuse the verify
			// interface from RSAUtils.java with the public key of the server as an
			// argument. Line 329 in RSAUtils.java shows an example of doing so
			boolean result = RSAUtils.verify(encryptedAESKey + randomString, RSAUtils.getPublicKey(SRVPUBKEY), signatureEncryptedAESKeyPlusRandoString);
			if(result) {
				System.out.println("Server signature is valid");
			}

			// b
			String decryptedAESkey = RSAUtils.decryptByPrivateKey(encryptedAESKey, RSAUtils.getPrivateKey(privateKey));
			System.out.println("[+] Client received Server's AES key: " + decryptedAESkey);

			// c
			System.out.println("[+] Client received Server's Random String: " + randomString);
			String encryptedRandomString = RSAUtils.aesEncrypt(randomString, decryptedAESkey);
			System.out.println("[+] enString: " + encryptedRandomString);

			// d
			String signatureOfEncryptedRandomString = RSAUtils.sign(encryptedRandomString, RSAUtils.getPrivateKey(privateKey));
			socketOut.writeBytes(signatureOfEncryptedRandomString + "\n" + encryptedRandomString + "\n");
			
			// 7
			String okMessage = socketIn.readLine();
			String okMessageSig = socketIn.readLine();
			String clientIP2 = socketIn.readLine();
			System.out.println("[+] Client IP from server: " + clientIP2);
			System.out.println("[+] Received server's sig: " + okMessageSig);
			System.out.println("[+] Received server's message: " + okMessage);
			
			// 8
			boolean result2 = RSAUtils.verify(okMessage, RSAUtils.getPublicKey(SRVPUBKEY), okMessageSig);
			
			if(result2) {
				System.out.println("[+] Pass.");
				clientSocket.close();
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Error when encrypting/decrypting");
		}

	}
}
