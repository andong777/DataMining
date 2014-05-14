package com.cnsoftbei.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.cnsoftbei.share.Variable;

/**
 * 用于载入保存在文件中的配置<br/>
 * 
 * Version 0.1<br/>
 * 2013/6/2
 * @author andong
 *
 */
public class OptionLoader {
	private static Properties prop;
	private static final String filename = ".properties";
	private static FileInputStream fin = null;
	private static FileOutputStream fout = null;
	private static File file = null;
	
	public static void load(){
		if(prop==null){
			prop = new Properties();
		}
		file = new File(filename);
		try {
			if(file.exists()){
				fin = new FileInputStream(file);
				prop.load(fin);
				Variable.IP = prop.getProperty("IP");
				Variable.port = Integer.parseInt(prop.getProperty("port"));
				
				fin.close();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void save(){
		if(prop==null){
			prop = new Properties();
		}
		File file = new File(".properties");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(file.exists()){
			prop.setProperty("IP", Variable.IP);
			prop.setProperty("port", Integer.toString(Variable.port));
			
			try {
				fout = new FileOutputStream(file);
				prop.store(fout, null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
