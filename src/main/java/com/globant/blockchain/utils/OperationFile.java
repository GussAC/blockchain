package com.globant.blockchain.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OperationFile {
	private String path;
	private String file;
	
	/**
	 * @description Use the method synchronized y throw a Thread until the file
	 * 				is free to write, verify each 1.5 second
	 * @param txt
	 */
    public void writeFileCSV(String txt) {
    	new Thread() {
			@Override
			public void run() {
				while( !sendToFileCSV(txt) ) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}				    		
    	}.start();

    }
    
    /**
     * @description method synchronized to send new line to file
     * @param txt
     * @return
     */
    private synchronized boolean sendToFileCSV(String txt) {
        try {
        	FileWriter fw = new FileWriter(path + file,true);
        	BufferedWriter bw = new BufferedWriter(fw);
        	PrintWriter out = new PrintWriter(bw);
            out.println(txt);            
            out.close();
            bw.close();
            fw.close();
            return true;            
        } catch (IOException e) {
        	System.out.println("LINE NOT ADDED");
        	System.out.println(e.getStackTrace());
        	return false;
        }
    } 
    
    /**
     * @Description Generate the file when start the blockchain
     * 				The path File must be in application.properties
     * 				with the identifier pathfile= 
     */
    public void generateFile() {    	
    	try {
        	Properties properties = new Properties();
        	properties.load(new FileReader(this.getClass().getResource("/application.properties").getFile()));
        	String pathfile = properties.getProperty("pathfile");
        	pathfile.replace("\\", "/");
        	String split[] = pathfile.split("/");
        	file = split[split.length - 1];
        	path = "";
        	for (int i = 0; i < split.length-1; i++) {
				path += split[i] + "/";
			}
        	File dir = new File(path);
        	if (!dir.exists()) {
        		dir.mkdirs();
        	}
        	File f = new File(path + file);
        	if (f.exists()) {
        		f.delete();        		
        	}         	
        	f.createNewFile();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}    	
    }
    
    
    public String getPath() {
		return path;
	}

	public String getFile() {
		return file;
	}


    
}
