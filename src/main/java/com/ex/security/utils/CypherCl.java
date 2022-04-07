package com.ex.security.utils;

import org.springframework.context.annotation.Bean;

import javax.crypto.Cipher;
import java.io.*;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CypherCl {

    public CypherCl(){}

    public void generateKeyPair(String publicDirectory, String privateDirectory) throws Exception {

        final KeyPairGenerator dsaKeyPair;
        ObjectOutputStream oos1 = null;
        ObjectOutputStream oos2 = null;

        try {

            dsaKeyPair = KeyPairGenerator.getInstance("RSA");
            dsaKeyPair.initialize(1024);
            KeyPair keyPair = dsaKeyPair.generateKeyPair();

            Key keyPublic = keyPair.getPublic();
            Key keyPrivate = keyPair.getPrivate();
            oos1 = new ObjectOutputStream( new FileOutputStream(publicDirectory));
            oos2 = new ObjectOutputStream( new FileOutputStream(privateDirectory));
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

    public String encrypt(String source, String file) throws Exception {

        Key publicKey = getKey(file);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] b = source.getBytes();

        byte[] b1 = cipher.doFinal(b);
        String encoder = Base64.getEncoder().encodeToString(b1);
        return encoder;
    }

    public String decrypt(String cryptograph, String file) throws Exception {

        Key privateKey = getKey(file);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
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

    public void setMessages(String fileName, String messsage) throws IOException {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream( new FileOutputStream(fileName));
            oos.writeObject(messsage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            oos.close();
        }
    }

    public void updateFile(String file, String message){
        try(FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
