package net.sf.latexdraw.glib.views.Java2D;

import net.sf.latexdraw.glib.models.interfaces.IPolyline;

/**
 * Defines a view of the IPolyline model.<br>
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
 * 03/18/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class LPolylineView extends LPolygonView {
	/**
	 * Creates an initialises the Java view of a LLines.
	 * @param model The model to view.
	 * @since 3.0
	 */
	public LPolylineView(final IPolyline model) {
		super(model);
	}


	@Override
	protected void updateGeneralPathMiddle() {
		setPath(false);
	}
}
