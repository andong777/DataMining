package com.cnsoftbei.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;

import com.cnsoftbei.share.Data;
import com.cnsoftbei.share.Variable;
import com.cnsoftbei.error.Error;

/**
 * 用于客户端与Rserve之间文件传输<br/>
 * 
 * Version 0.3<br/>
 * 2013/6/30
 * 
 * @author andong
 * 
 */
public class FileHelper {

	private static byte[] b = new byte[1024];
	private static BufferedInputStream bin;
	private static FileOutputStream fout;

	public static void receive(String fn) {
		if (!Connector.isConnected()) {
			Error.showConnectFailMessage();
			return;
		}
		File file = new File(fn);
		if (file.exists()) {
			return;
		}
		try {
			fout = new FileOutputStream(file);
			Variable.rin = Variable.rcon.openFile(fn);
			bin = new BufferedInputStream(Variable.rin);
			int len = 0;
			while ((len = bin.read(b, 0, b.length)) != -1) {
				fout.write(b, 0, len);
			}
			fout.flush();
			fout.close();
			bin.close();
			Variable.rin.close();

		} catch (IOException e) {
			Error.showFileErrorMessage();
		}
	}

	public static void send(File file) {
		if (!Connector.isConnected()) {
			Error.showConnectFailMessage();
			return;
		}
		if (file == null) {
			Error.showFileErrorMessage();
			return;
		}
		//用于减少文件传输时间：如果文件已经存在，则直接返回，可以大量减少读取大文件的时间
		try {
			String path = null;
			path = Variable.rcon.eval("getwd()").asString();
			System.out.println(path+"/"+file.getName());
			if(new File(path+"/"+file.getName()).exists()){
				System.out.println("exist");
				return;
			}
		} catch (Exception e1) {
			
		}
		if (!file.canRead() || !file.isFile()) {
			Error.showFileErrorMessage();
			return;
		}
		try {
			Variable.rout = Variable.rcon.createFile(file.getName());
			bin = new BufferedInputStream(new FileInputStream(file));
			int len = 0;
			while ((len = bin.read(b, 0, b.length)) != -1) {
				Variable.rout.write(b, 0, len);
			}
			Variable.rout.close();
			bin.close();

		} catch (IOException e) {
			Error.showFileErrorMessage();
		}
	}

	public static boolean load(String fn, boolean header,
			String sep, String na) {
		if (!Connector.isConnected()) {
			Error.showConnectFailMessage();
			return false;
		} else {
			try {
				
				Variable.rcon.eval(Data.tablename + "<-read.table(file='" + fn
						+ "',header=" + (header ? "T" : "F") + ",sep='" + sep
						+ "',na='" + na + "')");
			} catch (RserveException e) {
				return false;
			}
		}
		return true;
	}



}
