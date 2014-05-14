package com.cnsoftbei.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cnsoftbei.error.Error;
import com.cnsoftbei.share.Variable;
import com.cnsoftbei.ui.material.Blank;
import com.cnsoftbei.ui.material.BorderedJPanel;
import com.cnsoftbei.util.DBHelper;
import com.cnsoftbei.util.DataChecker;

public class DBDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -7863870719833778349L;
	private JPanel mainPanel = null;
	private JPanel connectPanel = null;
	private JPanel queryPanel = null;
	private JPanel resultPanel = null;
	private JComboBox<String> box = null;
	private JLabel typelbl = null;
	private JLabel addrlbl = null;
	private JLabel userlbl = null;
	private JLabel passwdlbl = null;
	private JTextField addrtext = null;
	private JTextField porttext = null;
	private JTextField usertext = null;
	private JTextField passwdtext = null;
	private JTextArea query = null;
	private JButton execute = null;
	private JButton clear = null;
	private JTextArea result = null;
	private JButton connect = null;
	private JButton imp = null;
	private JButton exit = null;

	public DBDialog() {
		setTitle("从数据库导入");
		setLayout(new BorderLayout());
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		connectPanel = new BorderedJPanel("数据库设置");
		typelbl = new JLabel("数据库类型");
		addrlbl = new JLabel("数据库地址");
		userlbl = new JLabel("用户名");
		passwdlbl = new JLabel("密码");
		box = new JComboBox<String>(new String[] { "选择要连接的数据库", "MySQL",
				"Oracle", "SQLite" });
		addrtext = new JTextField(15);
		porttext = new JTextField(5);
		usertext = new JTextField(20);
		passwdtext = new JTextField(20);
		connect = new JButton("连接");
		connect.setToolTipText("连接到数据库");
		imp = new JButton("导入");
		imp.setToolTipText("将当前的查询导入到软件");
		exit = new JButton("退出");
		exit.setToolTipText("关闭对话框，未导入的查询将丢失");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel pnl = new JPanel();

		pnl.add(typelbl);
		pnl.add(box);
		panel.add(pnl);
		pnl = new JPanel();
		pnl.add(addrlbl);
		pnl.add(addrtext);
		pnl.add(new JLabel(":"));
		pnl.add(porttext);
		panel.add(pnl);
		pnl = new JPanel();
		pnl.add(userlbl);
		pnl.add(usertext);
		panel.add(pnl);
		pnl = new JPanel();
		pnl.add(passwdlbl);
		pnl.add(passwdtext);
		panel.add(pnl);

		connectPanel.add(panel, BorderLayout.CENTER);
		pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl,BoxLayout.Y_AXIS));
		pnl.add(new Blank(1));
		pnl.add(connect);
		pnl.add(new Blank(1));
		pnl.add(imp);
		pnl.add(new Blank(1));
		pnl.add(exit);
		pnl.add(new Blank(1));
		JPanel tmp = new JPanel(new BorderLayout());
		tmp.add(pnl,BorderLayout.CENTER);
		connectPanel.add(tmp, BorderLayout.EAST);

		queryPanel = new BorderedJPanel("查询");
		queryPanel.setLayout(new BorderLayout());
		query = new JTextArea(5, 20);
		query.setLineWrap(true);
		queryPanel.add(new JScrollPane(query), BorderLayout.CENTER);
		pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		execute = new JButton("执行");
		clear = new JButton("清空");
		pnl.add(new Blank(1));
		pnl.add(execute);
		execute.setEnabled(false);
		pnl.add(new Blank(2));
		pnl.add(clear);
		pnl.add(new Blank(1));
		queryPanel.add(pnl, BorderLayout.EAST);
		resultPanel = new BorderedJPanel("结果");
		resultPanel.setLayout(new BorderLayout());
		result = new JTextArea(5, 20);
		result.setLineWrap(true);
		result.setEditable(false);
		resultPanel.add(new JScrollPane(result), BorderLayout.CENTER);
		mainPanel.add(connectPanel);
		mainPanel.add(queryPanel);
		mainPanel.add(resultPanel);
		add(mainPanel, BorderLayout.CENTER);

		connect.addActionListener(this);
		exit.addActionListener(this);
		execute.addActionListener(this);
		clear.addActionListener(this);

		setSize(Variable.scrWidth / 3, Variable.scrHeight * 2 / 3);
		setMinimumSize(new Dimension(Variable.scrWidth / 3, Variable.scrHeight / 2));
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			DBHelper.disconnect();
			dispose();
		} else if (e.getSource() == connect) {
			boolean connected = false;
			if (box.getSelectedItem().equals("MySQL")) {
				connected = DBHelper.connectMySQL(addrtext.getText(),
						Integer.parseInt(porttext.getText()),
						usertext.getText(), passwdtext.getText());
			} else if (box.getSelectedItem().equals("Oracle")) {
				connected = DBHelper.connectOracle(addrtext.getText(),
						Integer.parseInt(porttext.getText()),
						usertext.getText(), passwdtext.getText());
			} else if (box.getSelectedItem().equals("SQLite")) {
				connected = DBHelper.connectSQLite(addrtext.getText());
			} else {
				JOptionPane.showConfirmDialog(null, "请选择要连接的数据库", "提示",
						JOptionPane.OK_CANCEL_OPTION);
			}
			if (connected) {
				execute.setEnabled(true);
			}
		} else if (e.getSource() == clear) {
			query.setText("");
		} else if (e.getSource() == execute) {
			String res = DBHelper.execute(query.getText());
			result.append(query.getText() + " 执行结果:\n" + res + "\n");
		} else if(e.getSource() == imp){
			try {
				DataChecker.generateData();
			} catch (Exception e1) {
				Error.showLoadTableFailMessage();
			}
		}
	}

}
