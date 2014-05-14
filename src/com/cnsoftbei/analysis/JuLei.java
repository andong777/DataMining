package com.cnsoftbei.analysis;


import java.awt.Image;
import java.awt.Toolkit;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import com.cnsoftbei.share.Variable;

public class JuLei {

	public static Image img = null;
    public static REXP xp=null;
    public static String s=null;
  
    /**
     * 划分聚类方法 K-means算法
     * @param variable  读入数据变量名称
     * @param attribute 需要聚类的属性的组合
     * @param number    聚类个数
     * @return   聚类图像       
     */
	public static Image Kmeans(String variable, String attribute,int number) {
																				
		try {
			Variable.rcon.voidEval(" unlink('kmeans.jpg')");
			String device = "jpeg";
			xp = Variable.rcon.parseAndEval("try(" + device
					+ "('kmeans.jpg',quality=90))");
			Variable.rcon.voidEval("x<-" + variable + "[,c(" + attribute + ")]");
			Variable.rcon.voidEval("pred<-kmeans(x," + number + ")");
		    xp = Variable.rcon
					.parseAndEval(" plotcluster(x,pred$cluster);dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('kmeans.jpg','raw',1024*1024);r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception e) {
			img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;
	}

	
	
	/**
	 * summary对Kmeans算法结果的分析
	 * @return  summary分析结果
	 */
	public static String Kmean(){
		
		try {
			 s=Variable.rcon.eval("paste(capture.output(print(pred)),collapse=\"\\n\")").asString();
		} catch (Exception  e) {
			
			s = "选择的属性类型中不能为字符！请注意检查！";
		}
		return s;
		
	}
	
	/**
	 * Pam算法 对于小数据非常有效，但对于大数据伸缩相差，效率不高
	 * @param variable  读入数据变量名称
	 * @param attribute 需要聚类属性的组合
	 * @param number    聚类个数
	 * @return     Pam算法聚类效果图
	 */
	public static Image Pam(String variable, String attribute, int number) {
		
		try {
			Variable.rcon.voidEval(" unlink('pam.jpg')");
			String device = "jpeg";
			xp = Variable.rcon.parseAndEval("try(" + device
					+ "('pam.jpg',quality=90))");
			//Variable.rcon.parseAndEval("library(cluster)");
			Variable.rcon.voidEval("x<-" + variable + "[,c(" + attribute + ")]");
			Variable.rcon.voidEval("pred<-pam(x," + number + ")");
			xp = Variable.rcon
					.parseAndEval("layout(matrix(1:2, 1, 2));"
							+ "plot(pred,main='pam 聚类方法的效果图');dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('pam.jpg','raw',1024*1024); r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception  e) {
			img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;
	}

	/**
	 * Pam算法suammary分析
	 * @return   summary分析结果
	 */
	public static String pam(){
		
		try {
			s=Variable.rcon.eval("paste(capture.output(print(pred)),collapse=\"\\n\")").asString();
		} catch (Exception e) {
		 s = "Error!";
		}
		return s;
	}
	
	/**
	 *  Clara算法
	 * @param variable     读入数据变量名称 
	 * @param attribute    需要聚类属性的组合      
	 * @param number       聚类个数
	 * @return            Clara算法聚类效果图
	 */
	public static Image Clara(String variable, String attribute, int number) {
		try {
			Variable.rcon.voidEval("unlink('clara.jpg')");
			String device = "jpeg";
			xp = Variable.rcon.parseAndEval("try(" + device
					+ "('clara.jpg',quality=90))");
			//Variable.rcon.parseAndEval("library(cluster)");
			Variable.rcon.voidEval("x<-" + variable + "[,c(" + attribute +")]");
			Variable.rcon.voidEval("pred<-clara(x," + number + ")");
			xp = Variable.rcon
					.parseAndEval("layout(matrix(1:2, 1, 2));"+
							"plot(pred);dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('clara.jpg','raw',1024*1024);r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception e) {
		      img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;
	}
	
	/**
	 * Clara算法结果
	 * @return   聚类结果
	 */

	public static  String clara(){
		
		try {
			s=Variable.rcon.eval("paste(capture.output(print(pred)),collapse=\"\\n\")").asString();
		} catch (Exception e) {
		    s = "Error!";
		}
		return s;
	}
	/**
	 * 模糊聚类方法 FCM算法
	 * @param variable   读入数据变量的名称
	 * @param attribute  需要聚类属性的组合
	 * @param number    聚类个数
	 * @return       FCM算法聚类效果图
	 */
	public static Image FCM(String variable, String attribute,int number) {
		
		try {
			Variable.rcon.voidEval(" unlink('fcm.jpg')");
			String device = "jpeg";
			xp = Variable.rcon.parseAndEval("try(" + device
					+ "('fcm.jpg',quality=90))");
			xp = Variable.rcon
					.parseAndEval("layout(matrix(1:2, 1, 2));"
							+ "pred <- fanny("
							+ variable+ "[,c("+ attribute  
							+ ")]," + number + ");plot(pred);dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('fcm.jpg','raw',1024*1024);r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception e) {
			img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;

	}
	
	/**
	 * FCM算法结果分析
	 * @return  summary结果
	 */
	public static String fcm(){
		try {
			s=Variable.rcon.eval("paste(capture.output(print(pred)),collapse=\"\\n\")").asString();
		} catch (Exception e) {
		   s = "Error!";
		}
		return s;
	}
}