package net.sf.latexdraw.glib.views.Java2D;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid;
import net.sf.latexdraw.util.LNumber;
import sun.font.FontDesignMetrics;

/**
 * Defines an abstract view of the LAbstractGrid model.<br>
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
 * 04/12/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public abstract class LStandardGridView<S extends IStandardGrid> extends LShapeView<S> {
	/** The current font metrics of the text */
	protected FontMetrics fontMetrics;

	/** The path containing the labels. */
	protected Path2D pathLabels;


	/**
	 * Creates and initialises an abstract grid view
	 * @param grid The abstract grid to view.
	 * @since 3.0
	 */
	public LStandardGridView(final S grid) {
		super(grid);
		pathLabels  = new Path2D.Double();
	}



	@Override
	public void updateBorder() {
		final double angle = shape.getRotationAngle();

		if(LNumber.INSTANCE.equals(angle, 0.))
			border.setFrame(path.getBounds2D().createUnion(pathLabels.getBounds2D()));
		else {
			BadaboomCollector.INSTANCE.add(new IllegalAccessException());
			//TODO
		}
	}


	@Override
	public void update() {
		updateFonts();
		updatePath();
		updateBorder();
	}


	@Override
	public boolean intersects(final Rectangle2D r) {
		if(r==null)
			return false;

		final double rotationAngle = shape.getRotationAngle();
		boolean intersects;

		if(LNumber.INSTANCE.equals(rotationAngle, 0.)) {
			intersects = path.intersects(r) || pathLabels.intersects(r);
		}
		else {
			final IPoint tl 	= shape.getTopLeftPoint();
			final IPoint br 	= shape.getBottomRightPoint();
			final double cx 	= (tl.getX()+br.getX())/2.;
			final double cy		= (tl.getY()+br.getY())/2.;
			final double c2x 	= cos(rotationAngle)*cx - sin(rotationAngle)*cy;
			final double c2y 	= sin(rotationAngle)*cx + cos(rotationAngle)*cy;
			final AffineTransform at = AffineTransform.getTranslateInstance(cx - c2x, cy - c2y);

			at.rotate(rotationAngle);
			intersects = at.createTransformedShape(path).intersects(r) || at.createTransformedShape(pathLabels).intersects(r);
		}

		return intersects;
	}



	@Override
	public boolean contains(final IPoint p) {
		return p==null ? false : border.contains(p.getX(), p.getY());
	}



	/**
	 * Updates the font and the fontMetrics.
	 */
	protected void updateFonts() {
		fontMetrics = FontDesignMetrics.getMetrics(new Font(null, Font.PLAIN, shape.getLabelsSize()));
	}

	
	@Override
	protected void updateGeneralPathInside() {
		// Nothing to do.
	}

	@Override
	protected void updateGeneralPathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathMiddle() {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathOutside() {
		// Nothing to do.
	}
	
	@Override
	protected void updateGeneralPathOutside() {
		// Nothing to do.
	}
	
	@Override
	protected void updateDblePathInside() {
		// Nothing to do.
	}
}
