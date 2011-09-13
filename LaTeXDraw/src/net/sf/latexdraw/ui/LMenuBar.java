package net.sf.latexdraw.ui;

import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import org.malai.widget.MCheckBoxMenuItem;
import org.malai.widget.MMenu;

import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LSystem;

/**
 * This class defines the menu bar of the interactive system.<br>
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
 * 05/24/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	/** This menu contains the menu items related to the visibility of components. */
	protected MMenu displayMenu;

	/** This menu contains menu items related with drawings. */
	protected MMenu drawingMenu;

	/** This menu contains menu items related with the editing of shapes. */
	protected MMenu editMenu;

	/** This menu contains menu items to change the unit of the drawing. */
	protected MMenu unitMenu;

	/** This menu contains menu items related with the help. */
	protected MMenu helpMenu;


	/**
	 * Creates the menu bar.
	 * @param frame The user interface that contains all the instruments.
	 * @throws NullPointerException If one of the given arguments is null.
	 * @since 3.0
	 */
	public LMenuBar(final LFrame frame) {
		super();

		initialiseDrawingMenu(frame);
		initialiseEditMenu(frame);
		initialiseDisplayMenu(frame);
		initialiseHelpMenu(frame);
	}


	protected void initialiseDrawingMenu(final LFrame frame) {
		drawingMenu = new MMenu(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.91"), true); //$NON-NLS-1$
		unitMenu	= new MMenu("Unit", true);

		add(drawingMenu);

		drawingMenu.add(frame.fileLoader.getNewMenu());
		drawingMenu.add(frame.fileLoader.getLoadMenu());
		drawingMenu.add(frame.fileLoader.getSaveMenu());
		drawingMenu.add(frame.fileLoader.getSaveAsMenu());
		drawingMenu.addSeparator();
		drawingMenu.add(frame.exporter.getExportMenu());
		drawingMenu.addSeparator();
		drawingMenu.add(unitMenu);
		unitMenu.add(frame.scaleRulersCustomiser.getUnitCmItem());
		unitMenu.add(frame.scaleRulersCustomiser.getUnitInchItem());
	}


	protected void initialiseEditMenu(final LFrame frame) {
		editMenu = new MMenu(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.89"), true); //$NON-NLS-1$
		editMenu.add(frame.paster.getCutMenu());
		editMenu.add(frame.paster.getCopyMenu());
		editMenu.add(frame.paster.getPasteMenu());
		
		if (!LSystem.INSTANCE.isMacOSX()) {
			// OSX offers a special preferences Menu Item common for all applications.
			// We should use that and do not display this separate item.
			editMenu.addSeparator();
			editMenu.add(frame.prefActivator.getShowPreferencesMenu());
		}

		add(editMenu);
	}


	protected void initialiseHelpMenu(final LFrame frame) {
		helpMenu = new MMenu(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.93"), true); //$NON-NLS-1$
		helpMenu.add(frame.helper.getReportBugItem());
		helpMenu.add(frame.helper.getForumItem());
		helpMenu.add(frame.helper.getDonateItem());
		if (!LSystem.INSTANCE.isMacOSX()) {
			// OSX offers a special about Menu Item common for all applications.
			// We should use that and do not display this separate item.
			helpMenu.addSeparator();
			helpMenu.add(frame.helper.getAboutItem());
		}
		add(helpMenu);
	}


	/**
	 * Initialises the menu "Display"
	 * @param frame The frame that contains the instruments.
	 * @since 3.0
	 */
	protected void initialiseDisplayMenu(final LFrame frame) {
		displayMenu = new MMenu(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.90"), true); //$NON-NLS-1$

		add(displayMenu);

		MCheckBoxMenuItem menuCBItem = frame.scaleRulersCustomiser.getxRulerItem();
		menuCBItem.setSelected(true);
        displayMenu.add(menuCBItem);
        menuCBItem = frame.scaleRulersCustomiser.getyRulerItem();
        menuCBItem.setSelected(true);
        displayMenu.add(menuCBItem);
        menuCBItem = frame.codePanelActivator.getCloseMenuItem();
        menuCBItem.setSelected(true);
        displayMenu.addSeparator();
        menuCBItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        displayMenu.add(menuCBItem);
	}
}
