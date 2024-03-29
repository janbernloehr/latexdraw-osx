package net.sf.latexdraw.glib.views.Java2D;

import java.awt.geom.Path2D;

import net.sf.latexdraw.glib.models.interfaces.ITriangle;

/**
 * Defines a view of the ITriangle model.<br>
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
 * 03/19/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class LTriangleView extends LRectangularView<ITriangle> {
	/**
	 * Creates an initialises the Java view of a LTriangle.
	 * @param model The model to view.
	 * @since 3.0
	 */
	public LTriangleView(final ITriangle model) {
		super(model);
		update();
	}


	@Override
	protected void setRectangularShape(final Path2D path, final double tlx, final double tly, final double width, final double height) {
		path.moveTo(tlx+width/2., tly);
		path.lineTo(tlx+width, tly+height);
		path.lineTo(tlx, tly+height);
		path.closePath();
	}
}

