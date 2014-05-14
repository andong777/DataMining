package com.cnsoftbei.dialog;

import javax.swing.JOptionPane;

import org.rosuda.REngine.Rserve.RserveException;

import com.cnsoftbei.error.Error;
import com.cnsoftbei.share.Data;
import com.cnsoftbei.share.Variable;
import com.cnsoftbei.util.DataChecker;

/**
 * 导入R数据集的对话框 Version 0.1 2013/8/22
 * 
 * @author andong
 * 
 */
public class InnerDialog {

	public InnerDialog() {
		String dataset = JOptionPane.showInputDialog("请输入要使用的数据包");
		if (dataset == null)
			return;
		try {
			Variable.rcon.eval(dataset+"->"+Data.tablename);
			DataChecker.generateData();
		} catch (RserveException e1) {
			Error.showLoadInnerFailMessage();
		}
	}
}
