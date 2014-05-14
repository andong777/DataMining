package com.cnsoftbei.error;

import javax.swing.JOptionPane;

/**
 * 用于显示错误信息<br/>
 * 
 * Version 1.1<br/>
 * Update: 修正消息框显示信息过长的问题<br/>
 * Update: 完善了错误提示，给出了错误原因和可能的解决办法。<br/>
 * 2013/8/22
 * 
 * @author andong
 * 
 */
public class Error {

	public static void showOtherErrorMessage() {
		JOptionPane.showMessageDialog(null,
				"程序出现了未知错误！请尝试重启软件并重新执行操作。\n如果问题仍然出现，请联系开发人员", "未知错误",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void showConnectFailMessage() {
		JOptionPane
				.showMessageDialog(
						null,
						"无法连接服务器！请先检查服务器是否开启，到服务器的网络是否连通。\n如果没有问题，请打开编辑>选项，输入正确的服务器地址和端口并重新连接。",
						"服务器错误", JOptionPane.ERROR_MESSAGE);
	}

	public static void showFileErrorMessage() {
		JOptionPane.showMessageDialog(null,
				"无法加载文件！请检查文件是否存在以及是否拥有读权限，并检查服务器是否正常。", "文件错误",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void showDBConnectFailMessage() {
		JOptionPane.showMessageDialog(null,
				"连接数据库时出现了错误！请确认输入的信息是否正确，并检查网络是否连通。", "数据库错误",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void showOpenHelpFailMessage() {
		JOptionPane.showMessageDialog(null, "无法打开帮助文件!帮助文件可能已经丢失。", "文件错误",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void showLoadTableFailMessage() {
		JOptionPane.showMessageDialog(null, "无法加载表格数据，请确认是否输入了正确的SQL语句。",
				"数据库错误", JOptionPane.ERROR_MESSAGE);
	}

	public static void showLoadInnerFailMessage() {
		JOptionPane.showMessageDialog(null, "无法加载R数据集，请检查是否输入了正确的名称。", "服务器错误",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void showExecuteSQLFailMessage() {
		JOptionPane.showMessageDialog(null, "执行SQL语句出错，请检查输入的语句。", "数据库错误",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void showLoadPackageFailMessage() {
		JOptionPane.showMessageDialog(null, "部分R包加载失败，服务器可能没有正确配置。", "服务器错误",
				JOptionPane.ERROR_MESSAGE);
	}
}
