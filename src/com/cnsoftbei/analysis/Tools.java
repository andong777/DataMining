package com.cnsoftbei.analysis;

import static com.cnsoftbei.share.Variable.rcon;

import java.awt.Image;
import java.awt.Toolkit;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RserveException;

import com.cnsoftbei.share.Variable;

/**
 * 调用R实现各种零散功能的工具类<br/>
 * 它的主要功能包括数据的summary，head，缺失值的检测和处理；
 * 绘制各种图像：直方图、散点图、QQ图、箱图、分位箱图；
 * 对模型进行评估的四个方法：MAE,MSE,NMSE等；
 * 此外，还有对大数据化简的mapreduce方法。<br/>
 * 
 * Update: 2013/8/23<br/>
 * @author han
 * 
 *
 */

public class Tools {

	private static Image img = null;
	public static String s = null;

	/**
	 * 检验读入的数据存在的缺失值数目大于列数1%的行号
	 * 
	 * @param variable
	 *            变量名称
	 * @return 缺失值检验结果，返回每一行存在的缺失值个数
	 */
	public static String Missing(String variable) {
		try {
			Variable.rcon.voidEval("ms<-manyNAs(" + variable + ",0.01)");
			s = Variable.rcon.eval(
					"paste(capture.output(print(ms)),collapse=\"\\n\")")
					.asString();
		} catch (Exception e) {
			e.printStackTrace();
			s = "Error";
		}

		return s;
	}

	/**
	 * 缺失值处理方法：直接删除
	 * 
	 * @param variable
	 */
	public static void DeleteSolve(String variable) {

		try {
			Variable.rcon.voidEval(variable + "<-" + variable + "[-ms,]");
		} catch (REngineException e) {
		}
	}

	/**
	 * 缺失值处理方法：用中心趋势值填补缺失值
	 * 
	 * @param variable
	 */
	public static void CenterSolve(String variable) {

		
			try {
				Variable.rcon.voidEval(variable + "<-centralImputation("
						+ variable + ")");
			} catch (RserveException e) {
				}
		}

	/**
	 * 缺失值处理方法： 利用案例之间的相似性来填补缺失值
	 * 
	 * @param variable
	 *            变量名称
	 */
	public static void Solve(String variable) {
		try {
			Variable.rcon.voidEval(variable + "<-knnImputation(" + variable
					+ "[,],k=20)");
		} catch (REngineException e) {
		}
	}

	/**
	 * 计算各个属性间的相关性
	 * 
	 * @param variable
	 *            变量名称
	 * @param col1
	 *            属性列号（较小者）
	 * @param col2
	 *            属性列号（较大者）
	 * @return 各个属性的相关性列表
	 */
	public static String Associate(String variable, int col1, int col2) {
		try {
			Variable.rcon.voidEval("sy<-symnum(cor(" + variable + "[,"
					+ col1 + ":" + col2 + "],use='complete.obs'))");
			s = Variable.rcon.eval(
					"paste(capture.output(print(sy)),collapse=\"\\n\")")
					.asString();
		} catch (Exception e) {
			s = "计算相关性的属性类型不能为字符型！请注意检查！";
		}
		return s;
	}

	/**
	 * 根据给定属性画直方图
	 * 
	 * @param variable
	 *            数据变量
	 * @param attribute
	 *            属性名
	 * @return
	 */
	public static Image Hist(String variable, int attribute) {
		try {
			Variable.rcon.voidEval("unlink('hist.jpg')");
			String device = "jpeg";
			REXP xp = Variable.rcon.parseAndEval("try(" + device
					+ "('hist.jpg',quality=90))");
			Variable.rcon.parseAndEval("hist(" + variable + "[," + attribute
					+ "],prob=T);lines(density(" + variable + "[," + attribute
					+ "],na.rm=T));rug(jitter(" + variable + "[," + attribute
					+ "]));dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('hist.jpg','raw',1024*1024); r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception e) {
			img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;
	}

	/**
	 * 画出选定属性的Q-Q图
	 * 
	 * @param variable
	 * @param attribute
	 * @return
	 */
	public static Image QQPlot(String variable, int attribute) {

		try {
			Variable.rcon.voidEval("unlink('qqplot.jpg')");
			String device = "jpeg";
			REXP xp = Variable.rcon.parseAndEval("try(" + device
					+ "('qqplot.jpg',quality=90))");
			Variable.rcon.parseAndEval("qqPlot(" + variable + "[," + attribute
					+ "],main='QQ Plot');dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('qqplot.jpg','raw',1024*1024);r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception e) {
			img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;
	}

	/**
	 * 根据选定属性绘制箱图
	 * 
	 * @param variable
	 * @param attribute
	 * @return
	 */
	public static Image BoxPlot(String variable, int attribute) {

		try {
			Variable.rcon.voidEval("unlink('boxplot.jpg')");
			String device = "jpeg";
			Variable.rcon.voidEval("try(" + device
					+ "('boxplot.jpg',quality=90))");
			Variable.rcon.parseAndEval("boxplot(" + variable + "[," + attribute
					+ "],ylab=\"boxplot\");rug(jitter(" + variable + "[,"
					+ attribute + "]),side=2);abline(h=mean(" + variable + "[,"
					+ attribute + "],na.rm=T),lty=2);dev.off()");
			REXP xp = Variable.rcon
					.parseAndEval("r=readBin('boxplot.jpg','raw',1024*1024); r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception e) {
			img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}

		return img;
	}

	/**
	 * 根据给定的条件 factor检测离群值 如 factor= ">190"
	 * 
	 * @param variable
	 * @param attribute
	 * @param factor
	 * @return
	 */
	public static String outliers(String variable, int attribute, String factor) {

		try {
			s = Variable.rcon.eval(
					"paste(capture.output(print(" + variable + "[!is.na("
							+ variable + "[," + attribute + "])&" + variable
							+ "[," + attribute + "]" + factor + ",])),"
							+ "collapse=\"\\n\")").asString();
		} catch (Exception e) {
			s = "Error";
		}
		return s;
	}

	/**
	 * 绘制选定的属性的分位箱图 ，colnum1一般为离散型
	 * 
	 * @param variable
	 * @param colnum1
	 * @param colnum2
	 * @return
	 */
	public static Image Bwplot(String variable, int colnum1, int colnum2) {

		try {
			Variable.rcon.voidEval("unlink('bwplot.jpg')");
			String device = "jpeg";
			REXP xp = Variable.rcon.parseAndEval("try(" + device
					+ "('bwplot.jpg',quality=90))");
			Variable.rcon.parseAndEval("bwplot(" + variable + "[," + colnum1
					+ "]~" + variable + "[," + colnum2 + "],data=" + variable
					+ ",panel=panel.bpplot,probs=seq(.01,.49,by=.01),"
					+ "datadensity=TRUE,ylab='分位箱图',xlab='' );dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('bwplot.jpg','raw',1024*1024);r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception e) {
			img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;
	}

	/**
	 * 根据给定的两个属性画出散点图
	 * 
	 * @param variable
	 *            变量名称
	 * @param x
	 *            x轴属性名
	 * @param y
	 *            y轴属性名
	 * @return
	 */
	public static Image SanDian(String variable, int x, int y) {
		try {
			Variable.rcon.voidEval("unlink('sandian.jpg')");
			String device = "jpeg";
			REXP xp = Variable.rcon.parseAndEval("try(" + device
					+ "('sandian.jpg',quality=90))");
			Variable.rcon.parseAndEval("plot(" + variable + "[," + x + "],"
					+ variable + "[," + y + "]);dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('sandian.jpg','raw',1024*1024);r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception e) {
			img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;

	}
	
	public static String summary(String variable){
		try {
			return rcon.eval(
					"paste(capture.output(print(summary(" + variable
							+ "))),collapse=\"\\n\")").asString();
		} catch (Exception e) {
			return "Error";
		}
	}
	
	public static String head(String variable){
		try {
			return rcon.eval(
					"paste(capture.output(print(head(" + variable
							+ "))),collapse=\"\\n\")").asString();
		} catch (Exception e) {
			return "Error";
		}

	}
	/*分类模型评估*/
	/*平均绝对误差 MAE*/
	  public  static  String MAE(String variable, String colname)	{
		  
		  try {
			Variable.rcon.voidEval("mae <- mean(abs(ir-" + variable +"$" + colname + "))");
		   
			s=Variable.rcon.eval("paste(capture.output(print(mae)),collapse=\"\\n\")").asString();
			
		} catch (Exception e) {
			
			s = "Error!";
		}
		  return s;
	  }
	  
	  
	  /*均方误差MSE*/
	  public static String MSE(String variable,String colname){
		 
			try {
				 Variable.rcon.voidEval("mse <- mean((ir-" + variable + "$" + colname +")^2)");
				s=Variable.rcon.eval("paste(capture.output(print(mse)),collapse=\"\\n\")").asString();
			} catch (Exception e) {
				s = "Error!";
			}
			return s;
	  }
	  
	  /*标准化后的平均绝对误差NMSE*/
	  public  static  String  NMSE(String variable, String colname){
		  
		 
	      try {
	    	  Variable.rcon.voidEval("nmse <- mean((ir-" + variable + "$" + colname +")^2)/"
	                                   +"mean(((mean(" + variable +"$" + colname +")-"
	    			                   + variable +"$" + colname + ")^2 ))");
			s=Variable.rcon.eval("paste(capture.output(print(nmse)),collapse=\"\\n\")").asString();
		} catch (Exception e) {
			s = "Error!";
		}
		  return s;
		  
	  }
	  /*聚类模型总体评估*/
	  public  static String  Evaluate(){
		  try {
			  s=Variable.rcon.eval("paste(capture.output(print(summary(jl))),collapse=\"\\n\")").asString();
			} catch (Exception e) {
			   s = "Error!";
			}
			return s;
	  }
	/**
	 * 对大数据进行缩减的方法，利用了mapreduce
	 * @param variable 要缩减的变量
	 * @param length 缩减后的长度
	 */
	public static void mapreduce(String variable, int length){
		try {
//			rcon.eval("system(\"start-all.sh\"");
			rcon.eval("fun<-function(x) mean(x,na.rm=T)");
			rcon.eval("all.mean<-function(x) apply(x,2,fun)");
			String command = "mapreduce(input="+variable+","+
					"map=function(k,v){keyval(sample(1:"+length+",nrow("+variable+"),replace=T),v),"+
					"reduce=function(k,v){keyval(k,all.mean(v))";
			rcon.equals("from.dfs("+command+")$val->"+variable);
			rcon.eval("gc()");
		} catch (RserveException e) {
		}
	}
}
