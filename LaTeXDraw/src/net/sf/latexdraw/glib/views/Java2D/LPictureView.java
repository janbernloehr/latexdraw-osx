package net.sf.latexdraw.glib.views.Java2D;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.IPicture;


/**
 * Defines an abstract view of the IPicture model.<br>
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
public class LPictureView extends LShapeView<IPicture> {
	/**
	 * Initialises a view of a picture.
	 * @param model The model to view.
	 * @since 3.0
	 */
	public LPictureView(final IPicture model) {
		super(model);

		update();
	}


	@Override
	public boolean intersects(final Rectangle2D r) {
		return border.intersects(r);
	}




	@Override
	public void paint(final Graphics2D g) {
		g.drawImage(shape.getImage(), (int)shape.getX(), (int)shape.getY(), null);

//		if(isSelected)
//			border.paint(g);
	}



	@Override
	public void updateBorder() {
		border.setFrame(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	}


	@Override
	protected void updateDblePathInside() {
		// TODO Auto-generated method stub

	}


	@Override
	protected void updateDblePathMiddle() {
		// TODO Auto-generated method stub

	}


	@Override
	protected void updateDblePathOutside() {
		// TODO Auto-generated method stub

	}


	@Override
	protected void updateGeneralPathInside() {
		// TODO Auto-generated method stub

	}


	@Override
	protected void updateGeneralPathMiddle() {
		// TODO Auto-generated method stub

	}


	@Override
	protected void updateGeneralPathOutside() {
		// TODO Auto-generated method stub

	}
}
