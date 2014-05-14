package com.cnsoftbei.analysis;


import java.awt.Image;
import java.awt.Toolkit;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RserveException;

import com.cnsoftbei.share.Variable;

public class FenLei  {

	private static Image img = null;
	public static REXP xp = null;
    public static String s=null;
	 
	/**
	 * 决策树分类算法  :分类回归树
	 * @param variable   读入数据变量的名称
	 * @param colname    需要分类的属性名
	 * @param formula    和colname关联的的属性，各个属性用"+"连接，若是除variable之外的所有变量，可用 "."
	 * @return           分类回归树
	 */
	public static Image Trees(String variable, String colname, String formula) {
		
		try {
			Variable.rcon.voidEval("unlink('tree.jpg')");
			String device = "jpeg";
			REXP xp = Variable.rcon.parseAndEval("try(" + device
					+ "('tree.jpg',quality=90))");
			Variable.rcon.voidEval("samples <- sample(nrow(" + variable + "),0.7*nrow(" + variable + "))");
		
				Variable.rcon
						.voidEval("ir.tr=rpart("
								+  colname + "~" + formula +",data="
								+ variable +"[samples,]"
								+ ",method=\"class\",control=rpart.control(minsplit=1));" 
							//	+ "oldpar=par(mar=c(3,3,1.5,1),mgp=c(1.5,0.5,0),cex=0.7);"
								+ "plot(ir.tr,uniform=T,branch=0.4,compress=T);text(ir.tr);dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('tree.jpg','raw',1024*1024);r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
		} catch (Exception e) {
			img = Toolkit.getDefaultToolkit().createImage(Variable.sadPath);
		}

		return img;

	}
	
	/**
	 * 分类回归树summary分析结果
	 * @return   summary分析结果
	 */
	
	public static String Trees(){
		try {
			s=Variable.rcon.eval("paste(capture.output(print(summary(ir.tr))),collapse=\"\\n\")").asString();
        } catch (Exception e) {
			s = "Error!";
		}
		
		return s;
	}
	
	
	/**
	 * 分类回归树的建立过程及各个节点信息
	 * @return
	 */
	public static String TreeCutting(){
	
		try {
			s=Variable.rcon.eval("paste(capture.output(print(printcp(ir.tr))),collapse=\"\\n\")").asString();
		} catch (Exception e) {
			s = "Error!";
		}
	     return s;
	}
	
	
	/**
	 * 根据1—SE规则自动剪枝优化
	 * @param variable
	 * @param colname
	 * @return
	 */
	public static Image  AutoTreeCutting(String variable, String formula, String colname){
		
		try {
			Variable.rcon.voidEval("unlink('autotree.jpg')");
			String device = "jpeg";
			REXP xp = Variable.rcon.parseAndEval("try(" + device
					+ "('autotree.jpg',quality=90))");
			Variable.rcon.voidEval("ir <- rpartXse("+ colname + "~" + formula + ",data=" + variable +"[samples,])");
			Variable.rcon.parseAndEval("plot(ir,uniform=T,branch=0.8,compress=T);text(ir);dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('autotree.jpg','raw',1024*1024);r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());

	   } catch (Exception e) {
		   img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;
	}
	
	
	/**
	 * 自主设定cp值建立分类回归树
	 * @param variable
	 * @param colname
	 * @return
	 */
   public static Image  SelfTreeCutting(double cp){
		
		try {
			Variable.rcon.voidEval("unlink('selftree.jpg')");
			String device = "jpeg";
			REXP xp = Variable.rcon.parseAndEval("try(" + device
					+ "('selftree.jpg',quality=90))");
			Variable.rcon.voidEval(" ir<- prune(ir.tr,cp=" + cp + ")");
			Variable.rcon.parseAndEval("plot(ir,uniform=T,branch=0.8,compress=T);text(ir);dev.off()");
			xp = Variable.rcon
					.parseAndEval("r=readBin('selftree.jpg','raw',1024*1024);r");
			img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());

	   } catch (Exception  e) {
		   img = Toolkit.getDefaultToolkit().getImage(Variable.sadPath);
		}
		return img;
	}
	
	
	
	
	
  /* 分类回归树模型评估*/
	
	public static String Evaluate(String variable, String colname){
		
		try {
			Variable.rcon.voidEval("pre<- predict(ir," + variable + "[-samples,],type=\"class\")");
			s=Variable.rcon.parseAndEval("paste(capture.output(print("
			+ "round(100*table(pre," + variable + "[-samples,]$"
			+ colname + ",dnn=c(\"预测值\",\"实际值\"))/length(pre)) )),collapse=\"\\n\")").asString();
		} catch (Exception e) {
			s = "Error!";
		}
		return s;
	}
	
	
	
	
	
// 基于距离的分类方法
	public static String JuLi(String variable, int colnum) {
		try {
			
			Variable.rcon.voidEval("index <- sample(1:nrow(" + variable +"), as.integer(0.7*nrow(" 
			+ variable + ")))");
			Variable.rcon.voidEval("train <- " + variable + "[index,-" + colnum + "]");
			Variable.rcon.voidEval("test <- " + variable + "[-index,-" + colnum + "]");
			Variable.rcon.voidEval("cl<-" + variable + "[index," + colnum + "]");
		    Variable.rcon.voidEval("pre <- knn(train, test, cl, k = 3, prob=TRUE)");
            s=Variable.rcon.eval("paste(capture.output(print(table(pre, " + variable + "[-index," 
            + colnum + "]))),collapse=\"\\n\")").asString();
            
		} catch (Exception e) {
		    s = "选择的数据在强制转换过程中产生了NA值，请注意检查！";
		}
           return s;
}

	
	// 基于神经网络分类方法
	public static String Net(String variable, int colnum) {
		try {
		//	Variable.rcon.parseAndEval("library(nnet)");
			Variable.rcon.voidEval("model.nnet <- nnet( " + variable + "[," + colnum 
					+ "]~ ., linout = F,size = 10, decay = 0.01,maxit = 1000,trace = F,data = " 
					+ variable + ")");
			Variable.rcon.voidEval("pre=predict(model.nnet," + variable + ",type='class')");
			s=Variable.rcon.eval("paste(capture.output(print(table(pre," 
			+ variable + "[," + colnum + "]))),collapse=\"\\n\")").asString();
			
		} catch (Exception e) {
			s = "请选择正确的属性!";
		}
          return s;
	}
	}
