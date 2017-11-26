package com.snapShotTool.service;

import java.io.File;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.snapShotTool.entry.Home;
import com.snapShotTool.util.Constants;

/**
 * A java class built on top of jnativehook api to get details of
 * everyKeystroke.
 * 
 * @author Arpit K Tiwari
 * @version 1.0
 * @since 2017-08-22
 */

public class KeyLogger implements NativeKeyListener {

	/**
	 * Method to get invoked on keyPressedEvent
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @param NativeKeyEvent
	 * @return null.
	 */

	@Override
	public void nativeKeyPressed(NativeKeyEvent key) {
		if (Constants.APPLICATION_RUNNING_STATUS) {
			if (key.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN) {
				CaptureShot shot = new CaptureShot();
				String status = shot.takeScreenShot();
				Home.lblMsg.setText(status);
				Constants.SCREENSHOT_COUNT = Constants.SCREENSHOT_COUNT + 1;
				Home.lblCount.setText(String.valueOf(Constants.SCREENSHOT_COUNT));
			}
			
			if (NativeKeyEvent.VC_ALT == key.getKeyCode()) {
				Constants.ALT_PRESSED = true;
			}
			if (NativeKeyEvent.VC_SHIFT == key.getKeyCode()) {
				Constants.SHIFT_PRESSED = true;
			}
			if (NativeKeyEvent.VC_Z == key.getKeyCode()) {
				Constants.Z_PRESSED = true;
			}
			if (Constants.ALT_PRESSED && Constants.SHIFT_PRESSED && Constants.Z_PRESSED) {
				if(Constants.LAST_SAVED_IMAGE != null && !("".equals(Constants.LAST_SAVED_IMAGE.trim()))){
					File file = new File(Constants.LAST_SAVED_IMAGE);
					if (file != null && file.exists()) {
						boolean status = file.delete();
						if (status) {
							Constants.SCREENSHOT_COUNT = Constants.SCREENSHOT_COUNT - 1;
							Home.lblCount.setText(String.valueOf(Constants.SCREENSHOT_COUNT));
						}
					}					
				}
			}
		}
	}

	/**
	 * Method to get invoked on keyReleasedEvent
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @param NativeKeyEvent
	 * @return null.
	 */

	@Override
	public void nativeKeyReleased(NativeKeyEvent key) {
		if (NativeKeyEvent.VC_ALT == key.getKeyCode()) {
			Constants.ALT_PRESSED = false;
		}
		if (NativeKeyEvent.VC_SHIFT == key.getKeyCode()) {
			Constants.SHIFT_PRESSED = false;
		}
		if (NativeKeyEvent.VC_Z == key.getKeyCode()) {
			Constants.Z_PRESSED = false;
		}
	}

	/**
	 * Method to get invoked on keyTypedEvent
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @param NativeKeyEvent
	 * @return null.
	 */

	@Override
	public void nativeKeyTyped(NativeKeyEvent key) {
	}
}
