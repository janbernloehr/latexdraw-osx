package net.sf.latexdraw.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.sf.latexdraw.instruments.PreferencesSetter;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.VersionChecker;

import org.malai.widget.MFrame;

/**
 * This frame allows the user to set his preferences concerning the program and its components.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 09/14/06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class PreferencesFrame extends MFrame {
	private static final long serialVersionUID = 1L;

	/** The instrument that modifies the preferences. */
	protected PreferencesSetter prefSetter;


	/**
	 * The constructor using a frame.
	 * @param setter The instrument that modifies the preferences.
	 * @throws IllegalArgumentException If the given parameter is null.
	 */
	public PreferencesFrame(final PreferencesSetter setter) {
		super(true);

		if(setter==null)
			throw new IllegalArgumentException();

		this.prefSetter = setter;
  		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

  		setIconImage(LResources.PROPERTIES_ICON.getImage());

	  	setTitle(LangTool.LANG.getStringDialogFrame("PreferencesFrame.Pref")); //$NON-NLS-1$
  		setLocation(dim.width*1/3, dim.height*1/4);

  		JTabbedPane tabbedPane = new JTabbedPane();
  		JPanel pGeneral = new JPanel(new BorderLayout());

  		tabbedPane.add(LangTool.LANG.getStringDialogFrame("PreferencesFrame.general"), createGeneralPanel()); //$NON-NLS-1$
  		tabbedPane.add("LaTeX", createLatexPanel()); //$NON-NLS-1$
  		tabbedPane.add(LangTool.LANG.getStringDialogFrame("PreferencesFrame.folders"), createFoldersPanel()); //$NON-NLS-1$
  		tabbedPane.add(LangTool.LANG.getStringDialogFrame("PreferencesFrame.quality"), createQualityPanel()); //$NON-NLS-1$
  		tabbedPane.add(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.90"), createDisplayPanel()); //$NON-NLS-1$
  		tabbedPane.add(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.92"), createCodePanel()); //$NON-NLS-1$
  		tabbedPane.add(LangTool.LANG.getStringDialogFrame("PreferencesFrame.grid"), createGridPanel()); //$NON-NLS-1$

		pGeneral.add(tabbedPane, BorderLayout.CENTER);

		getContentPane().add(pGeneral);
  		setSize(500, 340);//FIXME
		setVisible(false);
	}



	private JPanel createLatexPanel() {
		JPanel pLatex 		 = new JPanel();
		JPanel pLatexDistrib = new JPanel();

		pLatex.setLayout(new BoxLayout(pLatex, BoxLayout.Y_AXIS));
		pLatexDistrib.setLayout(new BoxLayout(pLatexDistrib, BoxLayout.X_AXIS));

  		JButton bChooseLatex = new JButton(LResources.OPEN_ICON);

  		pLatexDistrib.add(prefSetter.getPathLatexDistribField());
  		pLatexDistrib.add(bChooseLatex);
  		pLatex.add(new JLabel("The path of the latex binaires:"));
  		pLatex.add(pLatexDistrib);
  		pLatex.add(new JLabel("Packages included during latex compilations:"));
  		pLatex.add(prefSetter.getLatexIncludes().getScrollpane());

		return pLatex;
	}



	/**
	 * Creates the panel which allows the user to set the general preferences of latexdraw.
	 * @return The created panel.
	 */
	private JPanel createGeneralPanel() {
		JPanel pGeneral = new JPanel();
		JPanel pLang 	= new JPanel();
		JPanel pEditor 	= new JPanel();
  		JPanel pRecent 	= new JPanel();
  		JPanel pTheme	= new JPanel();

  		pTheme.setLayout(new BoxLayout(pTheme, BoxLayout.X_AXIS));
		pEditor.setLayout(new BoxLayout(pEditor, BoxLayout.X_AXIS));
		pLang.setLayout(new BoxLayout(pLang, BoxLayout.X_AXIS));
  		pGeneral.setLayout(new GridLayout(6, 1));
  		pRecent.setLayout(new FlowLayout(FlowLayout.LEFT));

  		JButton bChooseEditor = new JButton(LResources.OPEN_ICON);

  		pRecent.add(prefSetter.getNbRecentFilesField().getLabel());
  		pRecent.add(prefSetter.getNbRecentFilesField());

  		pTheme.add(new JLabel(LangTool.LANG.getString19("PreferencesFrame.1"))); //$NON-NLS-1$
  		pTheme.add(prefSetter.getThemeList());

  		pLang.add(new JLabel(LangTool.LANG.getStringDialogFrame("PreferencesFrame.lge"))); //$NON-NLS-1$
  		pLang.add(prefSetter.getLangList());
  		pEditor.add(prefSetter.getPathTexEditorField());
  		pEditor.add(bChooseEditor);

  		pGeneral.add(pLang);
  		pGeneral.add(pTheme);
  		if(VersionChecker.WITH_UPDATE)
	  		pGeneral.add(prefSetter.getCheckNewVersion());
  		pGeneral.add(new JLabel(LangTool.LANG.getString18("PreferencesFrame.3"))); //$NON-NLS-1$
  		pGeneral.add(pEditor);
  		pGeneral.add(pRecent);

		return pGeneral;
	}



	/**
	 * Creates the panel which allows the user to set the code panel preferences.
	 * @return The created panel.
	 */
	private JPanel createCodePanel() {
		JPanel pCode = new JPanel(new GridLayout(6, 1));
  		pCode.add(prefSetter.getCodeAutoUpdateCB());
		return pCode;
	}



	/**
	 * Creates a JPanel containing elements allowing the set of the grid parameters.
	 * @return The created JPanel.
	 */
	private JPanel createGridPanel() {
		final JPanel pGrid = new JPanel(new GridLayout(5, 1));
		final JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(prefSetter.getPersoGridGapField().getLabel());
		panel.add(prefSetter.getPersoGridGapField());

  		pGrid.add(prefSetter.getDisplayGridCB());
  		pGrid.add(prefSetter.getClassicGridRB());
  		pGrid.add(prefSetter.getPersoGridRB());
  		pGrid.add(prefSetter.getMagneticGridCB());
  		pGrid.add(panel);

		return pGrid;
	}



	/**
	 * Creates the panel which allows the user to set the preferences about displayable elements.
	 * @return The created panel.
	 */
	private JPanel createDisplayPanel() {
  		final JPanel pDisplay = new JPanel(new GridLayout(5, 1));
		final JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(prefSetter.getUnitChoice());

  		pDisplay.add(prefSetter.getDisplayCodePanelCB());
  		pDisplay.add(prefSetter.getDisplayXScaleCB());
  		pDisplay.add(prefSetter.getDisplayYScaleCB());
  		pDisplay.add(prefSetter.getDisplayBordersCB());
  		pDisplay.add(panel);

  		return pDisplay;
	}



	/**
	 * Creates the panel which allows the user to set preferences about paths.
	 * @return The created panel.
	 */
	private JPanel createFoldersPanel() {
  		JPanel pFolders  = new JPanel(new GridBagLayout());
  		JButton bExport  = new JButton(LResources.OPEN_ICON);
  		JButton bOpen    = new JButton(LResources.OPEN_ICON);
  		GridBagConstraints constraint = new GridBagConstraints();

    	constraint.gridx = 0;
     	constraint.gridy = 0;
     	constraint.gridwidth = 6;
     	constraint.gridheight = 1;
     	constraint.weightx = 0.1;
     	constraint.weighty = 0.1;
     	constraint.fill = GridBagConstraints.HORIZONTAL;
     	constraint.anchor = GridBagConstraints.EAST;
     	pFolders.add(new JLabel(LangTool.LANG.getStringDialogFrame("PreferencesFrame.defOpenSave")), constraint); //$NON-NLS-1$

     	constraint.gridy = 1;
     	constraint.weightx = 10;
     	pFolders.add(prefSetter.getPathOpenField(), constraint);

    	constraint.gridx = 6;
     	constraint.gridwidth = 1;
     	constraint.weightx = 0.1;
     	constraint.fill = GridBagConstraints.NONE;
     	pFolders.add(bOpen, constraint);

    	constraint.gridx = 0;
     	constraint.gridy = 2;
     	constraint.gridwidth = 6;
     	constraint.fill = GridBagConstraints.HORIZONTAL;
     	pFolders.add(new JLabel(LangTool.LANG.getStringDialogFrame("PreferencesFrame.defFold")), constraint); //$NON-NLS-1$

     	constraint.gridy = 3;
     	constraint.weightx = 10;
     	pFolders.add(prefSetter.getPathExportField(), constraint);

    	constraint.gridx = 6;
     	constraint.gridwidth = 1;
     	constraint.weightx = 0.1;
     	constraint.fill = GridBagConstraints.NONE;
     	pFolders.add(bExport, constraint);

  		return pFolders;
	}


	/**
	 * Creates the panel which allows the user to set preferences about the rendering.
	 * @return The created panel.
	 */
	private JPanel createQualityPanel() {
  		final JPanel pQuality  = new JPanel(new GridLayout(4, 1));

  		pQuality.add(prefSetter.getAntialiasingCheckBox());
  		pQuality.add(prefSetter.getRenderingCheckBox());
  		pQuality.add(prefSetter.getColorRenderCheckBox());
  		pQuality.add(prefSetter.getAlpaInterCheckBox());

  		return pQuality;
	}
}
