package com.snapShotTool.util;

/**
 * A java class to store list of Constants and static variables used throughout
 * the application
 * 
 * @author Arpit K Tiwari
 * @version 1.0
 * @since 2017-08-22
 */

public class Constants {

	public static final String APPLICATION_TITLE = "ScreenShot Collaborator";
	public static final String DATE_FORMAT = "yyyyMMddhhmmss";
	public static final String EXTENSION_LABEL = "Extension :";
	public static final String FILE_CHOOSER_DIALOG_TITLE = "Select a Folder to Save Files";
	public static final String EXTENSION_TOOLTIP = "List of file format available to export the screenshots.";
	public static String[] EXTENSIONS = { "Word Document - DOCx", "Presentation Document - PPTx" };
	public static int SCREENSHOT_COUNT = 0;
	public static final String SHOT_CAPTURED_SUCCESS = "Screen Caputed Successfully!";
	public static final String SHOT_CAPTURED_FAILED = "Screen Capture Failed!";
	public static final String EXPORT_SUCCESS = "Export Successfull!";
	public static final String EXPORT_FAILED = "Export Failed";
	public static final String EXPORT_BUTTON_TOOLTIP = "Export Captured ScreenShots to a File";
	public static final String SRC_FOLDER_NOT_FOUND = "Source folder not found";
	public static final String NO_FILES_TO_EXPORT = "No Files Found To Export";
	public static final String ERROR_OCCURED_IN_SAVING_FILE = "Export to a File Failed.";
	public static final String FOLDER_SELECTED = "Base Folder Selected";
	public static String FILE_NAME = null;
	public static final String HYPHEN = "-";
	public static String UNDERSCORE = "_";
	public static final String DOT = ".";
	public static final String IMG_FORMAT = "JPG";
	public static final String FILE_FORMAT_DOCX = "DOCX";
	public static final String FILE_FORMAT_PPTX = "PPTX";
	public static final String DEFAULT_SRC_FOLDER = "C:\\Screenshots\\";
	public static final String FILE_NAME_MISSING = "File name is mandatory";
	public static final String SCREENSHOT = "ScreenShot";
	public static boolean EXPORT_STATUS = false;
	public static boolean APPLICATION_RUNNING_STATUS = true;
	public static String SRC_FOLDER = null;
	public static String BASE_FOLDER = null;
	public static String SUB_FOLDER = "";
	public static String EXPORTED_FILE_PATH = null;
	public static String LAST_SAVED_IMAGE = null;
	public static boolean ALT_PRESSED = false;
	public static boolean SHIFT_PRESSED = false;
	public static boolean Z_PRESSED = false;
}
