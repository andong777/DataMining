package com.cnsoftbei.dialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cnsoftbei.analysis.Tools;
import com.cnsoftbei.share.Data;
import com.cnsoftbei.share.Variable;
import com.cnsoftbei.util.DataChecker;
import com.cnsoftbei.util.FileHelper;

/**
 * 用于导入数据时设置读取参数的对话框
 * 
 * @author andong
 * 
 */
public class ReadDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 2392808262248264903L;
	private JLabel fileLabel = null;
	private JLabel headerLabel = null;
	private JLabel sepLabel = null;
	private JLabel naLabel = null;
	private JTextField fileText = null;
	private JComboBox<String> box = null;
	private JTextField sepText = null;
	private JTextField naText = null;
	private JButton select = null;
	private JButton ok = null;
	private JPanel panel = null;

	private boolean loaded = false;
	private File file = null;

	public ReadDialog() {
		setTitle("参数设置对话框");
		setLayout(new GridLayout(5, 1));
		fileLabel = new JLabel("选择文件");
		fileText = new JTextField(15);
		fileText.setEditable(false);
		select = new JButton("浏览");
		select.addActionListener(this);
		panel = new JPanel();
		panel.add(fileLabel);
		panel.add(fileText);
		panel.add(select);
		add(panel);
		headerLabel = new JLabel("是否有表头");
		box = new JComboBox<String>(new String[] { "否", "是" });
		panel = new JPanel();
		panel.add(headerLabel);
		panel.add(box);
		add(panel);
		sepLabel = new JLabel("设置分割符");
		sepText = new JTextField(5);
		sepText.setText(",");
		panel = new JPanel();
		panel.add(sepLabel);
		panel.add(sepText);
		add(panel);
		naLabel = new JLabel("设置表示NA值的字符");
		naText = new JTextField(5);
		naText.setText("NA");
		panel = new JPanel();
		panel.add(naLabel);
		panel.add(naText);
		add(panel);
		ok = new JButton("确定");
		ok.addActionListener(this);
		add(ok);

		setSize(Variable.scrWidth / 3, Variable.scrHeight / 3);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(this.getFocusOwner());
		setVisible(true);
	}

	public static void open() {
		ReadDialog read = new ReadDialog();
		if (read.loaded)
			DataChecker.generateData();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == select) {
			JFileChooser chooser = new JFileChooser();
			chooser.setAcceptAllFileFilterUsed(true);
			chooser.setCurrentDirectory(new File("."));
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				String filename = file.getName();
				fileText.setText(filename);
			}

		} else if (e.getSource() == ok) {

			if (fileText.getText() == null || fileText.getText().equals("")
					|| sepText.getText() == null
					|| sepText.getText().equals("")
					|| sepText.getText().length() > 1
					|| naText.getText() == null || naText.getText().equals("")) {
				JOptionPane.showConfirmDialog(null, "您设置的参数不正确", "警告",
						JOptionPane.OK_CANCEL_OPTION);
				return;
			}
			
			FileHelper.send(file);
			boolean bigData = DataChecker.checkBigData(fileText.getText());
			boolean header = ((String) box.getSelectedItem()).equals("是") ? true
					: false;
			loaded = FileHelper.load(file.getName(), header, sepText.getText(),
					naText.getText());
			if(bigData){
				Tools.mapreduce(Data.tablename, Variable.reduceLength);
			}
			dispose();
			
		}
	}

}
