package com.snapShotTool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.snapShotTool.entry.Home;

public class ApplicationUtil {
	
	public static int getPathLength(String path){
		return path.length();
	}

	public static String getShortPath(String path) {
		path = path.substring(0,path.lastIndexOf(File.separator));
		String drive = path.substring(0, path.indexOf(":")+2);
		String folder = path.substring(path.lastIndexOf(File.separator),path.length());
		return drive + "...." + folder;
	}
	
	public static void setDataInPropertyFile (){
		Properties prop = new Properties();
		prop.setProperty("BASE_DIRECTORY", Constants.SRC_FOLDER);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(new File("application.properties"));
			prop.store(fileOut, "Application Properties");
			fileOut.close();
		} catch (FileNotFoundException e) {
			Home.lblMsg.setText(e.getMessage());
			Home.basePath.requestFocus();
		} catch (IOException e) {
			Home.lblMsg.setText(e.getMessage());
			Home.basePath.requestFocus();
		}
	}

	public static void getDataFromPropertyFile() {
		FileInputStream fileInput = null;
		try {
			Properties properties = new Properties();
			File file = new File("application.properties");
			if(file.exists()){
				fileInput = new FileInputStream(file);
				properties.load(fileInput);
				Constants.SRC_FOLDER = properties.getProperty("BASE_DIRECTORY");
			} else{
				Constants.SRC_FOLDER = Constants.DEFAULT_SRC_FOLDER;
				Properties prop = new Properties();
				prop.setProperty("BASE_DIRECTORY", Constants.SRC_FOLDER);
				file.createNewFile();
				FileOutputStream fileOut = new FileOutputStream(file);
				prop.store(fileOut, "Application Properties");
				fileOut.close();
			}
		} catch (FileNotFoundException e) {
			Home.lblMsg.setText(e.getMessage());
		} catch (IOException e) {
			Home.lblMsg.setText(e.getMessage());
		} finally {
			if(fileInput != null){
				try {
					fileInput.close();
				} catch (IOException e) {
					Home.lblMsg.setText(e.getMessage());
				}
			}
		}
	}
	public static void setSrcFolder(){
		if("".equalsIgnoreCase(Home.subFolder.getText().trim())){
			Constants.SRC_FOLDER = Home.basePath.getText() + File.separator;
		} else {
			Constants.SRC_FOLDER = Home.basePath.getText() + File.separator + Home.subFolder.getText() + File.separator;
		}
	}
	
	public static void clearCurrentDirectory() {
		try {
			if (Constants.SRC_FOLDER != null && !("".equals(Constants.SRC_FOLDER.trim()))) {
				File directory = new File(Constants.SRC_FOLDER);
				if (directory.isDirectory()) {
					File[] files = directory.listFiles();
					if (files.length > 0) {
						boolean deletionStatus = false;
						for (File fileToDelete : files) {
							if (fileToDelete.isFile()) {
								deletionStatus = fileToDelete.delete();
							}
						}
						if (deletionStatus) {
							Home.lblMsg.setText("Directory cleanup successfull.");
						} else {
							Home.lblMsg.setText("Error in directory cleanup.");
						}
					}
				}
			}
		} catch (Exception e) {
			Home.lblMsg.setText(e.getMessage());
		}
	}
}
