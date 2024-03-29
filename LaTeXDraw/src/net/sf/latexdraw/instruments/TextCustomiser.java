package net.sf.latexdraw.instruments;

import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import net.sf.latexdraw.actions.ModifyLatexProperties;
import net.sf.latexdraw.actions.ModifyLatexProperties.LatexProperties;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.ModifyShapeProperty;
import net.sf.latexdraw.actions.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.util.LResources;

import org.malai.instrument.Link;
import org.malai.interaction.library.KeysTyped;
import org.malai.widget.MTextArea;
import org.malai.widget.MToggleButton;

/**
 * This instrument modifies texts.<br>
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
 * 12/27/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class TextCustomiser extends ShapePropertyCustomiser {
	/** The button that selects the bottom-left text position. */
	protected MToggleButton blButton;

	/** The button that selects the bottom text position. */
	protected MToggleButton bButton;

	/** The button that selects the bottom-right text position. */
	protected MToggleButton brButton;

	/** The button that selects the top-left text position. */
	protected MToggleButton tlButton;

	/** The button that selects the top text position. */
	protected MToggleButton tButton;

	/** The button that selects the top-right text position. */
	protected MToggleButton trButton;

	/** The label used to explain the goal of the package text field. */
	protected JLabel packagesLabel;

	/** This text field permits to add latex packages that will be used during compilation. */
	protected MTextArea packagesField;

	/** The widget used to group the widgets dedicated to the support of packages. */
	protected JComponent mainPackageWidget;


	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public TextCustomiser(final Hand hand, final Pencil pencil) {
		super(hand, pencil);

		initWidgets();
		initialiseLinks();
	}



	@Override
	protected void initWidgets() {
		packagesLabel = new JLabel("Packages:");
		packagesField = new MTextArea(true, true);
		Font font = packagesField.getFont();
		packagesField.setToolTipText("Contains the LaTeX packages that will be used to compile the text.");
		packagesField.setFont(new Font(font.getName(), font.getStyle(), Math.max(10, font.getSize()-4)));
		packagesField.setColumns(20);
		packagesField.setRows(5);

		blButton = new MToggleButton(LResources.TEXTPOS_BL);
		blButton.setMargin(LResources.INSET_BUTTON);
		blButton.setToolTipText("The position point is the bottom-left point.");
		bButton = new MToggleButton(LResources.TEXTPOS_B);
		bButton.setMargin(LResources.INSET_BUTTON);
		bButton.setToolTipText("The position point is the bottom-middle point.");
		brButton = new MToggleButton(LResources.TEXTPOS_BR);
		brButton.setMargin(LResources.INSET_BUTTON);
		brButton.setToolTipText("The position point is the bottom-right point.");
		tlButton = new MToggleButton(LResources.TEXTPOS_TL);
		tlButton.setMargin(LResources.INSET_BUTTON);
		tlButton.setToolTipText("The position point is the top-left point.");
		tButton = new MToggleButton(LResources.TEXTPOS_T);
		tButton.setMargin(LResources.INSET_BUTTON);
		tButton.setToolTipText("The position point is the top-middle point.");
		trButton = new MToggleButton(LResources.TEXTPOS_TR);
		trButton.setMargin(LResources.INSET_BUTTON);
		trButton.setToolTipText("The position point is the top-right point.");
	}



	@Override
	protected void update(final IShape shape) {
		final boolean isText = shape instanceof IText;
		final boolean isGroup = shape instanceof IGroup;
		final boolean visible = isText && (!isGroup || (isGroup && ((IGroup)shape).containsTexts()));

		if(widgetContainer!=null)
			widgetContainer.setVisible(visible);

		if(mainPackageWidget!=null)
			mainPackageWidget.setVisible(visible);

		if(isText) {
			final TextPosition tp = ((IText)shape).getTextPosition();

			bButton.setSelected(tp==TextPosition.BOT);
			brButton.setSelected(tp==TextPosition.BOT_RIGHT);
			blButton.setSelected(tp==TextPosition.BOT_LEFT);
			tButton.setSelected(tp==TextPosition.TOP);
			trButton.setSelected(tp==TextPosition.TOP_RIGHT);
			tlButton.setSelected(tp==TextPosition.TOP_LEFT);
			packagesField.setText(LaTeXGenerator.getPackages());
		}
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new KeysTyped2ChangePackages(this));
			links.add(new ButtonPressed2ChangeTextPosition(this));
			links.add(new ButtonPressed2ChangePencil(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The button that selects the bottom-left text position.
	 * @since 3.0
	 */
	public MToggleButton getBlButton() {
		return blButton;
	}

	/**
	 * @return The button that selects the bottom text position.
	 * @since 3.0
	 */
	public MToggleButton getBButton() {
		return bButton;
	}

	/**
	 * @return The button that selects the bottom-right text position.
	 * @since 3.0
	 */
	public MToggleButton getBrButton() {
		return brButton;
	}

	/**
	 * @return The button that selects the top-left text position.
	 * @since 3.0
	 */
	public MToggleButton getTlButton() {
		return tlButton;
	}

	/**
	 * @return The button that selects the top text position.
	 * @since 3.0
	 */
	public MToggleButton getTButton() {
		return tButton;
	}

	/**
	 * @return The button that selects the top-right text position.
	 * @since 3.0
	 */
	public MToggleButton getTrButton() {
		return trButton;
	}

	/**
	 * @return the packagesLabel.
	 * @since 3.0
	 */
	public JLabel getPackagesLabel() {
		return packagesLabel;
	}

	/**
	 * @return the packagesField.
	 * @since 3.0
	 */
	public MTextArea getPackagesField() {
		return packagesField;
	}

	/**
	 * @param mainPackageWidget the mainPackageWidget to set.
	 * @since 3.0
	 */
	public void setMainPackageWidget(final JComponent mainPackageWidget) {
		this.mainPackageWidget = mainPackageWidget;
	}
}



class KeysTyped2ChangePackages extends Link<ModifyLatexProperties, KeysTyped, TextCustomiser> {
	protected KeysTyped2ChangePackages(final TextCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ModifyLatexProperties.class, KeysTyped.class);
	}

	@Override
	public void initAction() {
		action.setProperty(LatexProperties.PACKAGES);
	}

	@Override
	public void updateAction() {
		action.setValue(instrument.getPackagesField().getText());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getObject()==instrument.packagesField;
	}
}



/**
 * Links a button interaction to an action that modifies the pencil.
 */
class ButtonPressed2ChangePencil extends ButtonPressedForCustomiser<ModifyPencilParameter, TextCustomiser> {
	/**
	 * Creates the link.
	 */
	public ButtonPressed2ChangePencil(final TextCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		final AbstractButton ab = interaction.getButton();

		action.setProperty(ShapeProperties.TEXT_POSITION);
		action.setPencil(instrument.pencil);

		if(instrument.blButton==ab) action.setValue(IText.TextPosition.BOT_LEFT);
		else if(instrument.brButton==ab) action.setValue(IText.TextPosition.BOT_RIGHT);
		else if(instrument.tButton==ab) action.setValue(IText.TextPosition.TOP);
		else if(instrument.bButton==ab) action.setValue(IText.TextPosition.BOT);
		else if(instrument.tlButton==ab) action.setValue(IText.TextPosition.TOP_LEFT);
		else action.setValue(IText.TextPosition.TOP_RIGHT);
	}

	@Override
	public boolean isConditionRespected() {
		final AbstractButton ab = interaction.getButton();
		return instrument.pencil.isActivated() && (instrument.tButton==ab || instrument.tlButton==ab ||
				instrument.trButton==ab || instrument.bButton==ab || instrument.blButton==ab || instrument.brButton==ab);
	}
}


/**
 * Links a button interaction to an action that modifies the selected shapes.
 */
class ButtonPressed2ChangeTextPosition extends ButtonPressedForCustomiser<ModifyShapeProperty, TextCustomiser> {
	/**
	 * Creates the link.
	 */
	public ButtonPressed2ChangeTextPosition(final TextCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		final AbstractButton ab = interaction.getButton();

		action.setShape(instrument.drawing.getSelection().duplicate());
		action.setProperty(ShapeProperties.TEXT_POSITION);

		if(instrument.bButton==ab) action.setValue(IText.TextPosition.BOT);
		else if(instrument.blButton==ab) action.setValue(IText.TextPosition.BOT_LEFT);
		else if(instrument.brButton==ab) action.setValue(IText.TextPosition.BOT_RIGHT);
		else if(instrument.tButton==ab) action.setValue(IText.TextPosition.TOP);
		else if(instrument.tlButton==ab) action.setValue(IText.TextPosition.TOP_LEFT);
		else action.setValue(IText.TextPosition.TOP_RIGHT);
	}

	@Override
	public boolean isConditionRespected() {
		final AbstractButton ab = interaction.getButton();
		return instrument.hand.isActivated() && ( instrument.bButton==ab || instrument.blButton==ab ||
				instrument.brButton==ab || instrument.tButton==ab || instrument.tlButton==ab || instrument.trButton==ab);
	}
}
