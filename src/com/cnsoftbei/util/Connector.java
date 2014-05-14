package com.cnsoftbei.util;

import static com.cnsoftbei.share.Variable.rcon;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import com.cnsoftbei.share.Variable;
import com.cnsoftbei.error.Error;

/**
 * 与Rserve连接的类<br/>
 * 
 * Version 0.3<br/>
 * 2013/8/21
 * 
 * @author andong
 * 
 */
public class Connector {

	private static String IP = Variable.IP;
	private static int port = Variable.port;
	private static final int tryTimes = 1;

	public static boolean connect() {
		return connect(IP, port);
	}

	public static boolean connect(String addr, int port) {
		int i = tryTimes;
		boolean connect = false;
		while (!connect && i-- > 0) {
			try {
				System.out.println(addr + " " + port);
				Variable.rcon = new RConnection(addr, port);
				connect = true;
			} catch (RserveException e) {
			}
		}
		if (!connect) {
			Error.showConnectFailMessage();
		}
		return connect;
	}

	public static boolean isConnected() {
		if (Variable.rcon == null) {
			return false;
		}
		return Variable.rcon.isConnected();
	}

	public static void disconnect() {
		if (!isConnected())
			return;
		Variable.rcon.close();
	}

	public static void initialize() {
		boolean fail = false;
		try {
			// 清除R环境中的所有对象
			Variable.rcon.eval("rm(list=ls())");
			Variable.rcon.eval("gc()");
			// 加载数据挖掘的R包
			Variable.rcon
					.eval("library(lattice);library(class);library(rpart);"
							+ "library(cluster);library(fpc);library(Hmisc);library(DMwR);");
			Variable.rcon.eval("library(car);library(nnet)");
		} catch (RserveException e) {
			fail = true;
		}
		try {
			// 加载数据库相关R包
			Variable.rcon
					.eval("library('DBI');library('RMySQL')");
		} catch (RserveException e) {
			fail = true;
		}
		try {
			// 加载RHadoop相关包
			Variable.rcon
					.eval("library('rJava');library('plyr');library('reshape2');"
							+ "library('Rcpp');library('iterators');library('itertools');"
							+ "library('digest');library('RJSONIO');library('functional');"
							+ "library('bitops');library('rhdfs');library('rmr2')");
			Variable.rcon.eval("hdfs.init()");
		} catch (RserveException e) {
			fail = true;
		}
		if (fail) {
			Error.showLoadPackageFailMessage();
		}
	}
}
