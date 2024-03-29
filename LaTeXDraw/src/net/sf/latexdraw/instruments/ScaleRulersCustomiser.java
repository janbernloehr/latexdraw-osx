package net.sf.latexdraw.instruments;

import javax.swing.JMenuItem;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.MenuItemPressed;
import org.malai.undo.Undoable;
import org.malai.widget.MCheckBoxMenuItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.latexdraw.actions.SetUnit;
import net.sf.latexdraw.actions.ShowHideScaleRuler;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.ui.ScaleRuler.Unit;
import net.sf.latexdraw.ui.XScaleRuler;
import net.sf.latexdraw.ui.YScaleRuler;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LResources;

/**
 * This instrument activates X and Y scale rulers.<br>
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
 * 11/12/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class ScaleRulersCustomiser extends Instrument {
	/** The label of the centimetre menu */
	public static final String LABEL_CM = LangTool.LANG.getStringOthers("XScale.cm"); //$NON-NLS-1$

	/** The label of the inch menu */
	public static final String LABEL_INCH = LangTool.LANG.getStringOthers("XScale.inch"); //$NON-NLS-1$

	/** The x ruler of the system. */
	protected XScaleRuler xRuler;

	/** The Y ruler of the system. */
	protected YScaleRuler yRuler;

	/** The menu item that (des-)activate the x-scale ruler. */
	protected MCheckBoxMenuItem xRulerItem;

	/** The menu item that (des-)activate the y-scale ruler. */
	protected MCheckBoxMenuItem yRulerItem;

	/** The menu for the centimetre unit. */
	protected MCheckBoxMenuItem unitCmItem;

	/** The menu for the inch unit. */
	protected MCheckBoxMenuItem unitInchItem;


	/**
	 * Creates the instrument.
	 * @param xRuler The x ruler of the system.
	 * @param yRuler The Y ruler of the system.
	 * @throws IllegalArgumentException If one of the given rulers is null.
	 * @since 3.0
	 */
	public ScaleRulersCustomiser(final XScaleRuler xRuler, final YScaleRuler yRuler) {
		super();

		if(xRuler==null || yRuler==null)
			throw new IllegalArgumentException();

		this.xRuler = xRuler;
		this.yRuler = yRuler;

		xRulerItem = new MCheckBoxMenuItem(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.38"), LResources.EMPTY_ICON); //$NON-NLS-1$
		yRulerItem = new MCheckBoxMenuItem(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.39"), LResources.EMPTY_ICON); //$NON-NLS-1$

		unitCmItem		= new MCheckBoxMenuItem(LABEL_CM);
		unitInchItem	= new MCheckBoxMenuItem(LABEL_INCH);

		initialiseLinks();

		// Mapping the instrument to the widgets that produce interactions that concerns its links.
		addEventable(this.xRuler);
		addEventable(this.yRuler);
	}


	@Override
	public void interimFeedback() {
		update();
	}


	@Override
	public void onUndoableUndo(final Undoable undoable) {
		super.onUndoableUndo(undoable);
		update();
	}


	@Override
	public void onUndoableRedo(final Undoable undoable) {
		super.onUndoableRedo(undoable);
		update();
	}


	protected void update() {
		unitCmItem.setSelected(ScaleRuler.getUnit()==Unit.CM);
		unitInchItem.setSelected(!unitCmItem.isSelected());
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new MenuItem2ShowHideCodeScaleRuler(this));
			links.add(new MenuItem2SetUnit(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element root) {
		super.load(generalPreferences, nsURI, root);

		final String name = root.getNodeName();

		if(name.endsWith(LNamespace.XML_DISPLAY_X)) {
			xRuler.setVisible(Boolean.valueOf(root.getTextContent()));
			xRulerItem.setSelected(xRuler.isVisible());
		} else if(name.endsWith(LNamespace.XML_DISPLAY_Y)) {
			yRuler.setVisible(Boolean.valueOf(root.getTextContent()));
			yRulerItem.setSelected(yRuler.isVisible());
		}
	}


	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(document==null || root==null)
			return ;

		Element elt;
		final String ns = generalPreferences ? "" : LPath.INSTANCE.getNormaliseNamespaceURI(nsURI); //$NON-NLS-1$
		elt = document.createElement(ns + LNamespace.XML_DISPLAY_X);
        elt.setTextContent(String.valueOf(xRuler.isVisible()));
        root.appendChild(elt);
		elt = document.createElement(ns + LNamespace.XML_DISPLAY_Y);
        elt.setTextContent(String.valueOf(yRuler.isVisible()));
        root.appendChild(elt);
	}


	/**
	 * @return The menu item that (des-)activate the x-scale ruler.
	 * @since 3.0
	 */
	public MCheckBoxMenuItem getxRulerItem() {
		return xRulerItem;
	}


	/**
	 * @return The menu item that (des-)activate the y-scale ruler.
	 * @since 3.0
	 */
	public MCheckBoxMenuItem getyRulerItem() {
		return yRulerItem;
	}


	/**
	 * @return The menu for the centimetre unit.
	 * @since 3.0
	 */
	public MCheckBoxMenuItem getUnitCmItem() {
		return unitCmItem;
	}


	/**
	 * @return The menu for the inch unit.
	 * @since 3.0
	 */
	public MCheckBoxMenuItem getUnitInchItem() {
		return unitInchItem;
	}
}



/**
 * This link maps a menu item to an action that sets the unit of the rulers.
 */
class MenuItem2SetUnit extends Link<SetUnit, MenuItemPressed, ScaleRulersCustomiser> {
	/**
	 * Initialises the link.
	 * @param ins The rulers activator.
	 */
	public MenuItem2SetUnit(final ScaleRulersCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, SetUnit.class, MenuItemPressed.class);
	}

	@Override
	public void initAction() {
		action.setUnit(interaction.getMenuItem()==instrument.unitCmItem ? Unit.CM : Unit.INCH);
	}

	@Override
	public boolean isConditionRespected() {
		final JMenuItem item = interaction.getMenuItem();
		return item==instrument.unitCmItem || item==instrument.unitInchItem;
	}
}


/**
 * This link maps a menu item to an action that shows/hides scale rulers.
 */
class MenuItem2ShowHideCodeScaleRuler extends Link<ShowHideScaleRuler, MenuItemPressed, ScaleRulersCustomiser> {
	/**
	 * Initialises the link.
	 * @param ins The rulers activator.
	 */
	public MenuItem2ShowHideCodeScaleRuler(final ScaleRulersCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ShowHideScaleRuler.class, MenuItemPressed.class);
	}

	@Override
	public void initAction() {
		final JMenuItem item = interaction.getMenuItem();
		action.setRuler(item==instrument.xRulerItem ? instrument.xRuler : instrument.yRuler);
		action.setVisible(item.isSelected());
	}

	@Override
	public boolean isConditionRespected() {
		final JMenuItem item = interaction.getMenuItem();
		return item==instrument.xRulerItem || item==instrument.yRulerItem;
	}
}
