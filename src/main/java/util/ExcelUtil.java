package util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import entity.Excel;


public class ExcelUtil {
	

	public static String singleGenExcel(Excel excel) {
		StringBuilder sb = new StringBuilder();
		sb.append(excel.getIp()).append("\t")
		.append(excel.getDbName()).append("\t")
		.append(excel.getTableName()).append("\t")
		.append(excel.getDataSource()).append("\t")
		.append(excel.getTotal()).append("\t")
		.append(excel.getCreateTime()).append("\t")
		.append(excel.getLastUpdateTiem()).append("\n");
		return sb.toString();
	}
	

	public static String singleGenExcel(List<Excel> excels) {
		StringBuilder sb = new StringBuilder();
		for(Excel excel: excels) {
			sb.append(singleGenExcel(excel));
		}
		return sb.toString();
	}
	
	private static void excelInit(RandomAccessFile raf) throws IOException {
		String head = "IP\tDATABASE\tTABLE_NAME\tDATA_SOURCE\tCRUUENT_TOTAL\tLAST_CREATE_TIME\tLAST_UPDATE_TIME\r\n";
		raf.write(head.getBytes());
	}
	

	public static void writeExcel(String path, String fileName, String context, boolean appendFlag) throws IOException {
		if(path == null || fileName == null) {
			throw new RuntimeException("File information cannot be empty...");
		}
		if(!fileName.endsWith(".xlsx")) {
			throw new RuntimeException("The file name must end with [.xlsx]...");
		}
		String fileAbsPath = path + File.separator + fileName;
		RandomAccessFile raf = new RandomAccessFile(fileAbsPath, "rw");
		if(appendFlag) {
			raf.seek(raf.length());
		}
		if(raf.length() == 0) {
			excelInit(raf);
		}
		raf.write(context.getBytes("utf-8"));
		raf.close();
	}
}
