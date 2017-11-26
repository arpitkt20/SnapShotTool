package com.snapShotTool.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.snapShotTool.entry.Home;
import com.snapShotTool.service.ExportToFile;
import com.snapShotTool.util.Constants;

/**
 * A java class to generate word file having all the screenshots attached.
 * 
 * @author Arpit K Tiwari
 * @version 1.0
 * @since 2017-08-22
 */

public class ExportToWord extends ExportToFile {

	/**
	 * Method to export screenshots to word
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @param null
	 * @return exportStatus.
	 */
	@Override
	public String export() {
		String msg = "";
		XWPFDocument document = null;
		XWPFRun run = null;
		XWPFParagraph paragraph = null;
		try {
			File imgFolder = new File(Constants.SRC_FOLDER);
			if (imgFolder.exists()) {
				File[] imgFiles = getFilteredImgFilesName(imgFolder.getAbsolutePath());
				if (imgFiles.length > 0) {
					sortImgFiles(imgFiles);
					document = new XWPFDocument();
					int counter = 1;
					for (File imgFile : imgFiles) {
						paragraph = document.createParagraph();
						run = paragraph.createRun();
						run.setText("ScreenShot : " + counter++);

						paragraph = document.createParagraph();
						run = paragraph.createRun();
						run.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_JPEG,
								imgFile.getAbsolutePath(), Units.pixelToEMU(650), Units.pixelToEMU(400));
					}

					String wordFileName = getFileName(imgFolder.getAbsolutePath()) + Constants.DOT
							+ Constants.FILE_FORMAT_DOCX;
					Constants.EXPORTED_FILE_PATH = wordFileName;
					File wordFile = new File(wordFileName);

					FileOutputStream fileOutputStream = new FileOutputStream(wordFile);
					document.write(fileOutputStream);
					fileOutputStream.close();
					msg = Constants.EXPORT_SUCCESS + Constants.HYPHEN + wordFile.getAbsolutePath();
					Home.lblMsg.setToolTipText(msg);
					Constants.EXPORT_STATUS = true;
				} else {
					msg = Constants.NO_FILES_TO_EXPORT;
					Constants.EXPORT_STATUS = false;
				}
			} else {
				msg = Constants.SRC_FOLDER_NOT_FOUND;
				Constants.EXPORT_STATUS = false;
			}
		} catch (IOException e) {
			msg = Constants.ERROR_OCCURED_IN_SAVING_FILE;
			Constants.EXPORT_STATUS = false;
		} catch (InvalidFormatException e) {
			msg = Constants.ERROR_OCCURED_IN_SAVING_FILE;
			Constants.EXPORT_STATUS = false;
		} catch (Exception e) {
			msg = Constants.ERROR_OCCURED_IN_SAVING_FILE;
			Constants.EXPORT_STATUS = false;
		}
		return msg;
	}
}