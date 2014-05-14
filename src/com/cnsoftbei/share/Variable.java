package com.cnsoftbei.share;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RFileInputStream;
import org.rosuda.REngine.Rserve.RFileOutputStream;

/**
 * 存放软件共有的变量<br/>
 * 
 * Version 0.1<br/>
 * 2013/5/31
 * 
 * @author andong
 * 
 */
public class Variable {

	public static RConnection rcon;
	public static RFileInputStream rin;
	public static RFileOutputStream rout;

	public static String IP = "127.0.0.1";
	public static int port = 6311;

	public static String status = "欢迎使用！";

	public static final String logoPath = "res/logo.png";
	public static final String sadPath = "res/sad.jpg";
	public static final String helpPath = "res/reference.pdf";
	public static final String intro = "<html>基于R语言的大数据挖掘平台<br/>作者：安东 韩玉强 康旭<br/><br/></html>";

	public static int scrWidth = 1366, scrHeight = 768;
	
	public static int reduceLength = 100000;

}
