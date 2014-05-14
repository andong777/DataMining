package com.cnsoftbei.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.rosuda.REngine.Rserve.RserveException;

import com.cnsoftbei.analysis.Tools;
import com.cnsoftbei.share.Data;
import com.cnsoftbei.share.Variable;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 导出数据及报告的类<br/>
 * Version 0.1
 * 
 * @author andong
 * 
 */
public class ExportHelper {

	private static String picsname = "pics.tar.gz";
	private static String pdfname = "report.pdf";
	private static String csvname = "data.csv";
	private static String txtname = "info.txt";

	public static void exportPictures() {
		try {
			Variable.rcon.eval("system(\"tar -zcvf " + picsname + " *.jpg \")");
			FileHelper.receive(picsname);
		} catch (RserveException e) {
			JOptionPane.showMessageDialog(null, "导出失败！");
		}
	}

	public static void exportPDF(String varname, boolean haspic) {
		Document doc=new Document(PageSize.A4,15,15,20,20);
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(pdfname));
			doc.open();
			doc.add(new Paragraph("Report",FontFactory.getFont(
                    FontFactory.TIMES, 24, Font.BOLD, BaseColor.BLACK)));
			
			doc.add(new Paragraph("Variable name: "+varname,FontFactory.getFont(
                    FontFactory.COURIER, 14, Font.NORMAL, BaseColor.BLACK)));
			doc.add(new Paragraph("Summary",FontFactory.getFont(
                    FontFactory.TIMES, 18, Font.NORMAL, BaseColor.BLUE)));
			String summary = Tools.summary(Data.tablename);
			doc.add(new Paragraph(summary,FontFactory.getFont(
                    FontFactory.COURIER, 14, Font.NORMAL, BaseColor.BLACK)));
			doc.add(new Paragraph("Head",FontFactory.getFont(
                    FontFactory.TIMES, 18, Font.NORMAL, BaseColor.BLUE)));
			String head = Tools.head(Data.tablename);
			doc.add(new Paragraph(head,FontFactory.getFont(
                    FontFactory.COURIER, 14, Font.NORMAL, BaseColor.BLACK)));
			doc.add(new Paragraph("Missing Value",FontFactory.getFont(
                    FontFactory.TIMES, 18, Font.NORMAL, BaseColor.BLUE)));
			String missing = Tools.summary(Data.tablename);
			doc.add(new Paragraph(missing,FontFactory.getFont(
                    FontFactory.COURIER, 14, Font.NORMAL, BaseColor.BLACK)));
			doc.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "导出失败");
		}

	}

	public static void exportCSV(String varname) {
		try {
			Variable.rcon.eval("write.csv(" + Data.tablename + ","
					+ "file='" + csvname + "',append=F)");
		} catch (RserveException e1) {
			JOptionPane.showMessageDialog(null, "导出失败！");
		}

		FileHelper.receive(csvname);
	}

	public static void exportTxt(String varname, boolean summary, boolean head,
			boolean missing) {
		try {
			File txt = new File(txtname);
			PrintWriter writer = new PrintWriter(txt);
			writer.println("Brief Information of Variable " + varname);
			writer.println("\n");
			if (summary) {
				String result = Tools.summary(Data.tablename);
				writer.println(result);
			}
			if (head) {
				String result = Tools.head(Data.tablename);
				writer.println(result);
			}
			if (missing) {
				String result = Tools.Missing(Data.tablename);
				writer.println(result);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "导出失败！");
		}
	}
}
