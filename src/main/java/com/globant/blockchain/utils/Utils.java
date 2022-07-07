package com.globant.blockchain.utils;


import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;

import com.globant.blockchain.BlockchainApplication;


/**
 *
 * @author gustavo.angeles
 */
public class Utils {

	/**
	 * @description Generate the hash with SHA-256 
	 * @param data
	 * @return hash string
	 */
    public static String generateHash(String data) {
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
        bytes = digest.digest(data.getBytes(UTF_8));
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    /**
     * @description create the first hash and is used in Genesis
     * @param lenght
     * @return Hash string
     */
    public static String randomHash(int lenght) {
        String hash = "";
        int number = 0;
        int count = 0;
        while(count < lenght) {
            number = (int)(Math.random() * 122);
            if( (number >= 48 && number <= 57) || (number >= 97 && number <= 122)) {
                count++;
                hash += (char)number;
            }
        }
        return hash;
    }

    /**
     * @description Add block at the final
     * @param filePath
     * @param lineToAppend
     */
    public static boolean writeFile(File file, String lineToAppend) {
        try {
            FileOutputStream f = new FileOutputStream(file, true);
            lineToAppend = "\r\n" + lineToAppend;
            byte[] byteArr = lineToAppend.getBytes();
            f.write(byteArr);
            f.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }    
        public static void main(String [] args) {
        	String hash = generateHash("Mensaje Hola Mundo 100");
        	System.out.println(hash);
        }
}
