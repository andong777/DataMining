package com.cnsoftbei.dialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.cnsoftbei.share.Variable;
import com.cnsoftbei.ui.material.BorderedJPanel;
import com.cnsoftbei.util.Connector;
import com.cnsoftbei.util.OptionLoader;

/**
 * 设置对话框
 * 
 * @author andong
 * 
 */
public class Option extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1786987979276732438L;
	private BorderedJPanel panel = null;
	private JLabel iplabel = null;
	private JLabel portlabel = null;
	private JTextField iptext = null;
	private JTextField porttext = null;
	private JButton ok = null;

	public Option() {

		setTitle("设置");
		setLayout(new BorderLayout());
		panel = new BorderedJPanel("服务器设置");
		panel.setLayout(new GridLayout(2, 2));
		iplabel = new JLabel("服务器IP：");
		portlabel = new JLabel("服务器端口");
		iptext = new JTextField(Variable.IP);
		porttext = new JTextField(Variable.port + "");
		panel.add(iplabel);
		panel.add(iptext);
		panel.add(portlabel);
		panel.add(porttext);
		add(panel, BorderLayout.CENTER);
		ok = new JButton("确定");
		ok.addActionListener(this);
		add(ok, BorderLayout.SOUTH);

		setSize(Variable.scrWidth / 4, Variable.scrHeight / 4);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(this.getFocusOwner());
		setVisible(true);
		setModal(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			Variable.IP = iptext.getText();
			Variable.port = Integer.parseInt(porttext.getText());
			OptionLoader.save();
			int choice = JOptionPane.showConfirmDialog(null, "是否要立即连接新服务器？", "提示",
					JOptionPane.YES_NO_OPTION);
			if(choice==JOptionPane.YES_OPTION){
				Connector.disconnect();
				Connector.connect(Variable.IP,Variable.port);
				
			}
			dispose();
		}
	}

}
