package com.snapShotTool.service;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.snapShotTool.util.Constants;

/**
 * A java class to take screenshot and save it as a jpg file.
 * 
 * @author Arpit K Tiwari
 * @version 1.0
 * @since 2017-08-22
 */

public class CaptureShot {

	/**
	 * Method to take screenshots and save as jpg
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @param null
	 * @return status.
	 */

	public String takeScreenShot() {
		String status = "";
		Robot robot = null;
		String fileName = null;
		Rectangle screenRect = null;
		BufferedImage screenFullImage = null;
		File srcFolder = null;
		String imgFolder = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
			Date today = new Date();
			
			imgFolder = Constants.SRC_FOLDER + File.separator;
			srcFolder = new File(imgFolder);
			if (!(srcFolder.exists())) {
				srcFolder.mkdirs();
			}
			fileName = srcFolder.getAbsolutePath() + File.separator + Constants.SCREENSHOT + Constants.UNDERSCORE
					+ sdf.format(today) + Constants.DOT + Constants.IMG_FORMAT;
			robot = new Robot();
			screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			screenFullImage = robot.createScreenCapture(screenRect);
			ImageIO.write(screenFullImage, Constants.IMG_FORMAT, new File(fileName));
			Constants.LAST_SAVED_IMAGE = fileName;
			status = Constants.SHOT_CAPTURED_SUCCESS;
		} catch (IOException e) {
			status = Constants.SHOT_CAPTURED_FAILED;
		} catch (AWTException e) {
			status = Constants.SHOT_CAPTURED_FAILED;
		}
		return status;
	}
}