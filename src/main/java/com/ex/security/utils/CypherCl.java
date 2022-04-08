package com.ex.security.utils;

import org.springframework.context.annotation.Bean;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.*;
import java.util.Base64;

public class CypherCl {

    public CypherCl() {
    }

    public void generateKeyPair(String publicDirectory, String privateDirectory) throws Exception {

        final KeyPairGenerator dsaKeyPair;
        ObjectOutputStream oos1 = null;
        ObjectOutputStream oos2 = null;

        try {

            dsaKeyPair = KeyPairGenerator.getInstance("DSA");
            dsaKeyPair.initialize(1024);
            KeyPair keyPair = dsaKeyPair.generateKeyPair();

            Key keyPublic = keyPair.getPublic();
            Key keyPrivate = keyPair.getPrivate();
            oos1 = new ObjectOutputStream(new FileOutputStream(publicDirectory));
            oos2 = new ObjectOutputStream(new FileOutputStream(privateDirectory));
            oos1.writeObject(keyPublic);
            oos2.writeObject(keyPrivate);


        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            oos1.close();
            oos2.close();
        }
    }


    private static Key getKey(String fileName) throws Exception, IOException {
        Key key;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            key = (Key) ois.readObject();
        } catch (Exception e) {
            throw e;
        }
        return key;
    }

    private static PrivateKey getPrivateKey(String fileName) throws Exception, IOException {
        PrivateKey key;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            key = (PrivateKey) ois.readObject();
        } catch (Exception e) {
            throw e;
        }
        return key;
    }

    private static PublicKey getPublicKey(String fileName) throws Exception, IOException {
        PublicKey key;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            key = (PublicKey) ois.readObject();
        } catch (Exception e) {
            throw e;
        }
        return key;
    }


    public byte[] signMessage(String file, String message) throws Exception {
        PrivateKey privateKey = getPrivateKey(file);

        Signature dsa = Signature.getInstance("SHA1withDSA");
        dsa.initSign(privateKey);
        dsa.update(message.getBytes());
        byte[] sign = dsa.sign();

        return sign;
    }

    public Boolean verifySign(String file, String message, byte[] sign) throws Exception {
        PublicKey publicKey = getPublicKey(file);

        Signature verificadsa = Signature.getInstance("SHA1withDSA");
        verificadsa.initVerify(publicKey);
        verificadsa.update(message.getBytes());
        boolean check = verificadsa.verify(sign);
        if (check) {
            return true;
        } else {
            return false;
        }
    }

    public String encrypt(String source, String file) throws Exception {
        Key key = getKey(file);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] b1 = cipher.doFinal(source.getBytes());
        String encoder = Base64.getEncoder().encodeToString(b1);
        return encoder;
    }

    public void conversationKey(String password, String dir) throws Exception {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        KeyGenerator keyG = KeyGenerator.getInstance("AES");
        keyG.init(256, new SecureRandom(password.getBytes()));
        SecretKey secretKey = keyG.generateKey();

        ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(dir));
        oos1.writeObject(secretKey);
        oos1.close();

    }

    public String decrypt(String cryptograph, String file) throws Exception {
        Key key = getKey(file);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] b1 = Base64.getDecoder().decode(cryptograph);

        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }

    public String getMessages(String fileName) throws Exception, IOException {
        String source;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            source = (String) ois.readObject();
        } catch (Exception e) {
            throw e;
        }
        return source;
    }

    public void setMessages(String fileName, String message) throws IOException {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oos.close();
        }
    }

    public void updateFile(String file, String message) throws IOException {
        File fl = new File(file);
        if(fl.exists()){
            fl.delete();
        }

        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oos.close();
        }
    }
}