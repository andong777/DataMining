package com.cnsoftbei.ui.material;

import javax.swing.JLabel;

/**
 * 显示空白内容的类，用于微调布局
 * @author andong
 * 
 */

public class Blank extends JLabel {

	private static final long serialVersionUID = -4076503167902075473L;

	public Blank(int n) {
		// n为控制空多少行
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		for (int i = 0; i < n; i++) {
			builder.append("<br/>");
		}
		builder.append("</html>");
		this.setText(builder.toString());
	}
}