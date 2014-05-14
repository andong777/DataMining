package com.cnsoftbei.util;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;

import com.cnsoftbei.error.Error;
import com.cnsoftbei.share.Data;

import static com.cnsoftbei.share.Variable.rcon;

/**
 * 用于连接数据库和操作数据库的类<br/>
 * 
 * Version 0.1<br/>
 * 2013/5/31
 * 
 * @author andong
 * 
 */
public class DBHelper {

	private static final String varOracle = "conOracle";
	private static final String varMySQL = "conMySQL";
	private static final String varSQLite = "conSQLite";
	private static String varCon = null;
	private static String varDriver = null;
	private static String varUser = null;
	private static String varPasswd = null;
	private static String varHost = null;
	private static String varPort = null;

	public static boolean connectOracle(String IP, int port, String user,
			String passwd) {
		if (!Connector.isConnected()) {
			Error.showConnectFailMessage();
			return false;
		}
		varHost = IP;
		varPort = Integer.toString(port);
		varUser = user;
		varPasswd = passwd;
		varDriver = "dbDriver('Oracle')";
		try {
			rcon.voidEval(varOracle + "<-dbConnect(drv=" + varDriver
					+ "',user='" + varUser + "',password='" + varPasswd
					+ "',port=" + varPort + ",host='" + varHost + "')");
		} catch (RserveException e) {
			Error.showDBConnectFailMessage();
			return false;
		}
		varCon = varOracle;
		return true;
	}

	public static boolean connectSQLite(String IP) {
		if (!Connector.isConnected()) {
			Error.showConnectFailMessage();
			return false;
		}
		varHost = IP;
		varDriver = "dbDriver('SQLite')";
		try {
			rcon.eval(varMySQL + "<-dbConnect(drv=" + varDriver + ",host='"
					+ varHost + "')");
		} catch (RserveException e) {
			return false;
		}
		varCon = varSQLite;
		return true;
	}

	public static boolean connectMySQL(String IP, int port, String user,
			String passwd) {
		if (!Connector.isConnected()) {
			Error.showConnectFailMessage();
			return false;
		}
		varHost = IP;
		varPort = Integer.toString(port);
		varUser = user;
		varPasswd = passwd;
		varDriver = "dbDriver('MySQL')";
		try {
			rcon.eval(varMySQL + "<-dbConnect(drv=" + varDriver + ",user='"
					+ varUser + "',password='" + varPasswd + "',host='"
					+ varHost + "',port=" + varPort + ")");
		} catch (RserveException e) {
			e.printStackTrace();
			return false;
		}
		varCon = varMySQL;
		return true;
	}

	public static void disconnect() {
		if (varCon == null)
			return;
		try {
			rcon.voidEval("dbDisconnect(" + varCon + ")");
		} catch (RserveException e) {
		}
	}

	public static String execute(String sql) {
		String s = null;

		try {
			rcon.voidEval(Data.tablename+"<-dbGetQuery(" + varCon + ",\"" + sql + "\")");
			s = rcon.eval(
					"paste(capture.output(print("+Data.tablename+")),collapse=\"\\n\")").asString();
		} catch (Exception e) {
			Error.showExecuteSQLFailMessage();
		}

		return s;
	}
}
