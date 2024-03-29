package net.sf.latexdraw.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.Map.Entry;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.DisplayCanvas;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.VersionChecker;

/**
 * Define the dialogue box describing the latexdraw information.<br>
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
 * 11/20/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class AboutDialogueBox extends JFrame {
	private static final long serialVersionUID = 1L;
	// TODO Strings to remove(?): getStringLaTeXDrawFrame("LFrame2.25"), getString16("LaTeXDrawFrame.18"), getString16("LaTeXDrawFrame.19"), getString18("LaTeXDrawFrame.25")
	/**
	 * Creates the dialogue box.
	 * @since 3.0
	 */
	public AboutDialogueBox() {
		super();
		initialiseDialogueBox();
	}


	protected void initialiseDialogueBox() {
		final int width			= 490;
		final int height		= 430;
		final Dimension dim 	= Toolkit.getDefaultToolkit().getScreenSize();
		JTabbedPane tabbedPane 	= new JTabbedPane();

		setTitle(LResources.LABEL_ABOUT);
		setLocation((dim.width-width)/2, (dim.height-height)/2);

		createMainPanel(tabbedPane);
		createReleaseNotePanel(tabbedPane);
		createTranslatorsPanel(tabbedPane);
		createSystemPanel(tabbedPane);
		createLicensePanel(tabbedPane);

		tabbedPane.setPreferredSize(new Dimension(510, 290));
 		setIconImage(LResources.ABOUT_ICON.getImage());
 		getContentPane().add(tabbedPane);

 		setSize(width, height);
	}

	protected void createSystemPanel(final JTabbedPane tabbedPane) {
		JEditorPane editorPane = new JEditorPane();
		StringBuilder builder = new StringBuilder();
		editorPane.setEditable(false);

		for(final Entry<Object, Object> entry : System.getProperties().entrySet())
			builder.append(entry.getKey().toString()).append(':').append(' ').append(entry.getValue().toString()).append(LResources.EOL);

		editorPane.setText(builder.toString());
		tabbedPane.add("System", new JScrollPane(editorPane));
	}


	protected void createLicensePanel(final JTabbedPane tabbedPane) {
		try {
			JEditorPane editorPane = new JEditorPane(getClass().getResource("/res/license.txt"));//$NON-NLS-1$
			initEditorPane(editorPane);
			tabbedPane.add(LangTool.LANG.getString18("LaTeXDrawFrame.28"), new JScrollPane(editorPane)); //$NON-NLS-1$
		}catch(Exception e) { BadaboomCollector.INSTANCE.add(e); }
	}


	protected void createReleaseNotePanel(final JTabbedPane tabbedPane) {
		try {
			JEditorPane editorPane = new JEditorPane(getClass().getResource("/res/release_note.txt"));//$NON-NLS-1$
			initEditorPane(editorPane);
			tabbedPane.add(LangTool.LANG.getString18("LaTeXDrawFrame.27"), new JScrollPane(editorPane)); //$NON-NLS-1$
		}catch(Exception e) { BadaboomCollector.INSTANCE.add(e); }
	}


	protected void createTranslatorsPanel(final JTabbedPane tabbedPane) {
		try {
			JEditorPane editorPane = new JEditorPane(getClass().getResource("/res/translators.txt"));//$NON-NLS-1$
			initEditorPane(editorPane);
			tabbedPane.add(LangTool.LANG.getString18("LaTeXDrawFrame.26"), new JScrollPane(editorPane)); //$NON-NLS-1$
		}catch(Exception e) { BadaboomCollector.INSTANCE.add(e); }
	}


	protected void createMainPanel(final JTabbedPane tabbedPane) {
		try {
			Image i = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("res/LaTeXDrawSmall.png"));//$NON-NLS-1$
			MediaTracker tracker = new MediaTracker(this);
		    tracker.addImage(i,0);
		    try { tracker.waitForID(0); }
		    catch(InterruptedException e) { BadaboomCollector.INSTANCE.add(e); }

			JPanel panel 			= new JPanel();
			JEditorPane editorPane 	= new JEditorPane();
			initEditorPane(editorPane);
			panel.setLayout(new BorderLayout());
			editorPane.setContentType("text/html");//$NON-NLS-1$
			editorPane.setBackground(tabbedPane.getBackground());
			editorPane.setText("<html><body><div style=\"text-align: center; \"><font size=\"-1\"><br>"+ //$NON-NLS-1$
					LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.219")+ " " + //$NON-NLS-1$ //$NON-NLS-2$
					VersionChecker.VERSION + (VersionChecker.VERSION_STABILITY.length()==0 ? "" : " " +VersionChecker.VERSION_STABILITY) + //$NON-NLS-1$ //$NON-NLS-2$
					LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.220") + " " + VersionChecker.ID_BUILD+"<br><br>"+//$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
					LResources.LABEL_APP+LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.221")+"<br>"+//$NON-NLS-1$//$NON-NLS-2$
					"Copyright(c) 2005-2011 - Arnaud BLOUIN - arno.b.dev@gmail.com<br><br>"+//$NON-NLS-1$
					"http://latexdraw.sourceforge.net/<br></div></body></html>");//$NON-NLS-1$

			panel.add(new DisplayCanvas(i), BorderLayout.NORTH);
			panel.add(new JScrollPane(editorPane), BorderLayout.SOUTH);
			tabbedPane.add(LangTool.LANG.getStringDialogFrame("PreferencesFrame.general"), panel); //$NON-NLS-1$
		}catch(Exception e) { BadaboomCollector.INSTANCE.add(e); }
	}


	protected static void initEditorPane(final JEditorPane editorPane) {
		editorPane.setEditable(false);
		editorPane.setDragEnabled(true);
	}
}
