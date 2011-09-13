package net.sf.latexdraw.ui;

import net.sf.latexdraw.ui.LFrame;
import net.sf.latexdraw.ui.dialog.AboutDialogueBox;
import net.sf.latexdraw.ui.dialog.PreferencesFrame;
import net.sf.latexdraw.util.LSystem;

import com.apple.eawt.*;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.PreferencesEvent;


/**
 * This class provides aids to make LaTeXDraw feel like a natural Mac OSX apppliaction.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 09/13/11<br>
 * @author Jan Molnar
 * @version 3.0
 */
public class MacOSApplicationHelper {

	/** We need to create this class just once. Therefore we keep a reference. */
	private static MacOSApplicationHelper helper;

	/** A references of the main frame. */
	LFrame frame;
	
	/**
	 * Creates the helper class.
	 * @since 3.0
	 */
	private MacOSApplicationHelper(LFrame frm) {
		this.frame = frm;
		Application app = Application.getApplication();
		
		System.setProperty("apple.laf.useScreenMenuBar", "true");

		app.setAboutHandler(new MyAboutHandler());
		app.setPreferencesHandler(new MyPreferencesHandler());
	}
	
	/**
	 * Checks whether running on OSX and enables OSX specific enhancements
	 * to make LaTeXDraw feel like a native OSX application.
	 * @since 3.0
	 */
	public static void EnableOSXEnhancementsIfNeccessary(LFrame frame) {
		if (LSystem.INSTANCE.isMacOSX() & helper == null) {
			helper = new MacOSApplicationHelper(frame);
		}
	}

	/**
	 * Handler for the system provided about menu item.
	 * @since 3.0
	 */
	class MyAboutHandler implements AboutHandler {

		@Override
		public void handleAbout(AboutEvent arg0) {
			AboutDialogueBox aboutFrame = frame.helper.initialiseAboutFrame();
			
			aboutFrame.setVisible(true);
		}

	}

	/**
	 * Handler for the system provided preferences menu item.
	 * @since 3.0
	 */
	class MyPreferencesHandler implements PreferencesHandler {

		@Override
		public void handlePreferences(PreferencesEvent arg0) {
			PreferencesFrame preferencesFrame = frame.prefActivator.initialisePreferencesFrame();
			
			preferencesFrame.setVisible(true);
		}

	}
}
