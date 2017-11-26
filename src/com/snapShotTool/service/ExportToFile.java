package com.snapShotTool.service;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import com.snapShotTool.util.Constants;

/**
 * An abstract java class used for the purpose of exporting images to a file.
 * 
 * @author Arpit K Tiwari
 * @version 1.0
 * @since 2017-08-22
 */

public abstract class ExportToFile {

	/**
	 * Abstract method to export screenshots to the file.
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @return String export status.
	 */

	abstract public String export();

	/**
	 * Method to return the list of jpg files in the batch folder
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @param srcFolder
	 * @return File[] List of jpg files in the batch folder.
	 */

	public File[] getFilteredImgFilesName(String srcFolder) {
		File batchFolder = new File(srcFolder);
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String fileName = pathname.getName();
				String extension = fileName.substring(fileName.lastIndexOf(Constants.DOT) + 1, fileName.length());
				if (extension.equalsIgnoreCase(Constants.IMG_FORMAT)) {
					return true;
				} else {
					return false;
				}
			}
		};
		return batchFolder.listFiles(filter);
	}

	/**
	 * Method to get the export File Name
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @param batchFolder
	 * @return String filePath.
	 */

	public String getFileName(String batchFolder) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date today = new Date();
		return batchFolder + File.separator + Constants.FILE_NAME + Constants.HYPHEN + sdf.format(today);
	}

	/**
	 * Method to sort the files based on modification time.
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @param imgFiles
	 * @return null
	 */

	public void sortImgFiles(File[] imgFiles) {
		Arrays.sort(imgFiles, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
			}
		});
	}
}
