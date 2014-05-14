package com.cnsoftbei.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.cnsoftbei.dialog.About;
import com.cnsoftbei.dialog.Export;
import com.cnsoftbei.dialog.DBDialog;
import com.cnsoftbei.dialog.InnerDialog;
import com.cnsoftbei.dialog.Option;
import com.cnsoftbei.dialog.ReadDialog;
import com.cnsoftbei.error.Error;
import com.cnsoftbei.share.Data;
import com.cnsoftbei.share.Variable;
import static com.cnsoftbei.share.Component.*;

public class Window extends JFrame implements ChangeListener{

	private static final long serialVersionUID = -8489872207380173437L;
	private final String title = "基于R语言的大数据挖掘平台";
	
	private JMenuBar menu = null;
	private JMenu file = null;
	private JMenu edit = null;
	private JMenu help = null;
	private JMenuItem impFile = null;
	private JMenuItem impDB = null;
	private JMenuItem impInner = null;
	private JMenuItem exp = null;
	private JMenuItem exit = null;
	private JMenuItem option = null;
	private JMenuItem manual = null;
	private JMenuItem about = null;
	
	private JTabbedPane pane = null;
	private JPanel datapnl = null;
	private JPanel classifypnl = null;
	private JPanel clusterpnl = null;
	private JPanel associatepnl = null;
	private JPanel visualizepnl = null;
	private JPanel evaluatepnl = null;
	private JPanel exportpnl = null;
	
	public Window(){
		
		setSize(Variable.scrWidth * 2 / 3, Variable.scrHeight * 2 / 3);
		setLocation(Variable.scrWidth / 6, Variable.scrHeight / 6);
		setTitle(title);
		ImageIcon logo = new ImageIcon(Variable.logoPath);
		setIconImage(logo.getImage());
		setLayout(new BorderLayout());
		initMenu();
		initBody();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void initBody(){
		datapnl = new DataPanel();
		classifypnl = new ClassifyPanel();
		clusterpnl = new ClusterPanel();
		associatepnl = new AssociatePanel();
		visualizepnl = new VisualizePanel();
		evaluatepnl = new EvaluatePanel();
		exportpnl = new ExportPanel();
		pane = new JTabbedPane();
		add(pane,BorderLayout.CENTER);
		pane.addTab("数据", datapnl);
		pane.addTab("分类",classifypnl);
		pane.addTab("聚类", clusterpnl);
		pane.addTab("关联", associatepnl);
		pane.addTab("可视化", visualizepnl);
		pane.addTab("模型评估", evaluatepnl);
		pane.addTab("导出",exportpnl);
		pane.addChangeListener(this);
		
	}
	
	private void initMenu(){
		menu = new JMenuBar();
		file = new JMenu("文件");
		edit = new JMenu("编辑");
		help = new JMenu("帮助");
		impFile = new JMenuItem("从文件导入...");
		impDB = new JMenuItem("从数据库导入...");
		impInner = new JMenuItem("使用R内置数据包...");
		exp = new JMenuItem("导出...");
		exit = new JMenuItem("退出");
		option = new JMenuItem("选项...");
		manual = new JMenuItem("帮助手册");
		about = new JMenuItem("关于");

		menu.add(file);
		menu.add(edit);
		menu.add(help);
		file.add(impFile);
		file.add(impDB);
		file.add(impInner);
		file.add(exp);
		file.add(exit);
		edit.add(option);
		help.add(manual);
		help.add(about);

		setJMenuBar(menu);

		impFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReadDialog.open();
			}
		});
		impDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DBDialog();
			}
		});
		impInner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InnerDialog();
			}
		});
		exp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Export();
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		option.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Option();
			}
		});
		manual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.awt.Desktop dp = java.awt.Desktop.getDesktop();
				File help = new File(Variable.helpPath);
				try {
					dp.open(help);
				} catch (IOException e1) {
					Error.showOpenHelpFailMessage();
				}
			}
		});
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new About();
			}
		});
	}

	public void stateChanged(ChangeEvent e) {
		Component panel = pane.getSelectedComponent();
		if(panel == classifypnl){
			if(Data.names!=null){
				classifyBox.removeAllItems();
				for (int i = 0; i < Data.names.length; i++) {
					classifyBox.addItem(Data.names[i]);
				}
			}
		}else if(panel == clusterpnl){
			if(Data.names!=null){
				clusterPanel.removeAll();
				clusterBoxes = new JCheckBox[Data.names.length];
				for(int i=0;i<Data.names.length;i++){
					clusterBoxes[i] = new JCheckBox(Data.names[i]);
					clusterPanel.add(clusterBoxes[i]);
				}
			}
		}else if(panel == visualizepnl){
			if(Data.names!=null){
				visualizeBox1.removeAllItems();
				visualizeBox2.removeAllItems();
				for (int i = 0; i < Data.names.length; i++) {
					visualizeBox1.addItem(Data.names[i]);
					visualizeBox2.addItem(Data.names[i]);
				}
			}
		}else if(panel == evaluatepnl){
			if(Data.names!=null){
				evaluateBox.removeAllItems();
				for (int i = 0; i < Data.names.length; i++) {
					evaluateBox.addItem(Data.names[i]);
				}
			}
		}
	}
}
