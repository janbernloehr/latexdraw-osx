package net.sf.latexdraw.actions;

import org.malai.action.Action;

import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * This abstract action allows the definition of actions related with spaes.<br>
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
 * 11/21/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public abstract class ShapeAction extends Action {
	/** The shape to add. */
	protected IShape shape;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public ShapeAction() {
		super();
	}


	/**
	 * Sets the shape to add.
	 * @param shape The shape to add.
	 * @since 3.0
	 */
	public void setShape(final IShape shape) {
		this.shape = shape;
	}


	@Override
	public void flush() {
		super.flush();
		shape = null;
	}


	/**
	 * @return The shape to modify.
	 * @since 3.0
	 */
	public IShape getShape() {
		return shape;
	}


	@Override
	public boolean canDo() {
		return shape!=null;
	}
}
