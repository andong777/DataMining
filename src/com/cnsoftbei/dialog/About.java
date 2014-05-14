package com.cnsoftbei.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.cnsoftbei.share.Variable;

/**
 * 关于对话框
 * 
 * @author andong
 *
 */
public class About extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = -1861861957843440244L;
	private JButton confirm = null;
	private JLabel imgLabel = null;
	private JLabel textLabel = null;
	
	public About(){
		setTitle("关于");
		confirm = new JButton("确定");
		confirm.addActionListener(this);
		imgLabel = new JLabel(new ImageIcon(Variable.logoPath));
		textLabel = new JLabel(Variable.intro);
		setLayout(new BorderLayout());
		add(imgLabel,BorderLayout.WEST);
		add(textLabel,BorderLayout.CENTER);
		add(confirm,BorderLayout.SOUTH);
		
		setSize(Variable.scrWidth/3,Variable.scrHeight/2);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(this.getFocusOwner());
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==confirm){
			dispose();
		}
	}

}
