package com.cnsoftbei.util;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;

import com.cnsoftbei.share.Data;
import com.cnsoftbei.share.Variable;

public class DataChecker {

	public static String[] names(String variable) {
		if (variable == null)
			return null;
		String[] names = null;
		try {
			REXP xp = Variable.rcon.eval("names(" + variable + ")");
			if (xp.asString() != "NULL") {
				names = xp.asStrings();
			} else {
				int ncol = Variable.rcon.eval("ncol(" + variable + ")")
						.asInteger();
				names = new String[ncol];
				for (int i = 1; i <= ncol; i++)
					names[i] = "COL" + i;
			}
		} catch (Exception e1) {

		}
		return names;
	}

	public static void generateData() {
		Data.names = names(Data.tablename);
		try {
			Data.length = Variable.rcon.eval("length(" + Data.tablename + ")")
					.asInteger();
			Data.type = Variable.rcon.eval("typeof(" + Data.tablename + ")")
					.asString();
		} catch (Exception e) {
		}

	}
	
	public static boolean checkBigData(String filepath){
		FileReader in = null;
		boolean bigData = false;
		try {
			in = new FileReader(filepath);
			LineNumberReader reader=new LineNumberReader(in);
			File file = new File(filepath);
			long size = file.length();
			reader.skip(size);
			long lines = reader.getLineNumber();
			reader.close();
			if(lines<100000){
				bigData = false;
			}else{
				System.out.println("big data");
				bigData = true;
			}
		} catch (Exception e) {

		}
		if(in!=null){
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		return bigData;
	}
}
