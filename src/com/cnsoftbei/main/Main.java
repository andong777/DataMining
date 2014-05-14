package com.cnsoftbei.main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.cnsoftbei.share.Variable;
import com.cnsoftbei.ui.Window;
import com.cnsoftbei.util.Connector;
import com.cnsoftbei.util.OptionLoader;
import com.cnsoftbei.error.Error;


//程序的入口

public class Main {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				//设置界面观感为系统观感
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e1) {
					Error.showOtherErrorMessage();
				}				
				//加载配置文件
				OptionLoader.load();
				//连接服务器
				Connector.connect();
				//加载R所需的包
				if(Connector.isConnected())
					Connector.initialize();
				//获取屏幕大小
				Toolkit kit = Toolkit.getDefaultToolkit();
				Dimension screenSize = kit.getScreenSize();
				Variable.scrWidth = screenSize.width;
				Variable.scrHeight = screenSize.height;
				
				//加载主窗口	
				new Window();

			}
		});
	}
}
