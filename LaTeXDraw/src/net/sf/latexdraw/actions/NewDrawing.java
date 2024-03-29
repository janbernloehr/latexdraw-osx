package net.sf.latexdraw.actions;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.malai.action.library.IOAction;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.instruments.PreferencesSetter;

/**
 * This action permits to create a new drawing and initialises the application as required.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 08/09/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class NewDrawing extends IOAction implements Modifying {
	/** The file chooser that will be used to select the location to save. */
	protected JFileChooser fileChooser;

	/** The instrument used that manage the preferences. */
	protected PreferencesSetter prefSetter;


	@Override
	protected void doActionBody() {
		if(ui.isModified()) {
			switch(SaveDrawing.showAskModificationsDialog(ui)) {
				case JOptionPane.NO_OPTION: //new
					newDrawing();
					break;
				case JOptionPane.YES_OPTION: // save + load
					File f = SaveDrawing.showDialog(fileChooser, true, ui, file);
					if(f!=null) {
						openSaveManager.save(f.getPath(), ui);
						ui.setModified(false);
						newDrawing();
					}
					break;
				case JOptionPane.CANCEL_OPTION: // nothing
					break;
				default:
					break;
			}
		}
		else newDrawing();
	}


	protected void newDrawing() {
		ui.reinit();
		try{ prefSetter.readXMLPreferences(); }
		catch(Exception exception){ BadaboomCollector.INSTANCE.add(exception); }
	}


	@Override
	public boolean canDo() {
		return fileChooser!=null && ui!=null && openSaveManager!=null && prefSetter!=null;
	}


	@Override
	public void flush() {
		super.flush();
		fileChooser = null;
	}


	/**
	 * @param fileChooser The file chooser that will be used to select the location to save.
	 * @since 3.0
	 */
	public void setFileChooser(final JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}


	/**
	 * @param prefSetter The instrument used that manage the preferences.
	 * @since 3.0
	 */
	public void setPrefSetter(final PreferencesSetter prefSetter) {
		this.prefSetter = prefSetter;
	}
}
