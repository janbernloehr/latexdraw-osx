package net.sf.latexdraw.actions;

import org.malai.undo.Undoable;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.util.LNumber;

/**
 * This action translates shapes.<br>
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
 * 12/05/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class TranslateShape extends DrawingAction implements Undoable, Modifying {
	/** The x vector translation. */
	protected double tx;

	/** The y vector translation. */
	protected double ty;

	/** The x vector translation that has been already performed. This attribute is needed since
	 * this action can be executed several times. */
	private double performedTx;

	/** The y vector translation that has been already performed. This attribute is needed since
	 * this action can be executed several times. */
	private double performedTy;

	private IShape shape;


	/**
	 * Initialises the action.
	 * @since 3.0
	 */
	public TranslateShape() {
		super();

		tx = 0.;
		ty = 0.;
		performedTx = 0.;
		performedTy = 0.;
	}


	@Override
	public boolean isRegisterable() {
		return hadEffect();
	}


	@Override
	protected void doActionBody() {
		shape.translate(tx-performedTx, ty-performedTy);
		shape.setModified(true);
		drawing.setModified(true);
		performedTx = tx;
		performedTy = ty;
	}


	@Override
	public boolean canDo() {
		final boolean okShape = super.canDo() && shape instanceof IGroup ? !((IGroup)shape).isEmpty() : true;
		return okShape && (!LNumber.INSTANCE.equals(tx, 0.) || !LNumber.INSTANCE.equals(ty, 0.)) && GLibUtilities.INSTANCE.isValidPoint(tx, ty);
	}


	@Override
	public void undo() {
		shape.translate(-tx, -ty);
		shape.setModified(true);
		drawing.setModified(true);
	}


	@Override
	public void redo() {
		shape.translate(tx, ty);
		shape.setModified(true);
		drawing.setModified(true);
	}


	@Override
	public String getUndoName() {
		return "Translation";
	}


	@Override
	public void setDrawing(final IDrawing drawing) {
		super.setDrawing(drawing);

		if(drawing!=null)
			shape = drawing.getSelection().duplicate();
	}


	/**
	 * @param tx The x vector translation.
	 * @since 3.0
	 */
	public void setTx(final double tx) {
		this.tx = tx;
	}


	/**
	 * @param ty The y vector translation.
	 * @since 3.0
	 */
	public void setTy(final double ty) {
		this.ty = ty;
	}
}
