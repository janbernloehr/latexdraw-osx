package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

/**
 * Defines a model of a circle.<br>
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
 * 02/13/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LCircle extends LEllipse implements ICircle {
	/**
	 * Creates a square.
	 * @param isUniqueID isUniqueID True: the model will have a unique ID.
	 * @since 3.0
	 */
    protected LCircle(final boolean isUniqueID) {
		this(new LPoint(), 1., isUniqueID);
	}


	/**
	 * Creates a circle.
	 * @param pt The centre of the circle.
	 * @param radius The radius.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If the radius is not valid.
	 * @throw NullPointerException If the given point pt is null.
	 */
    protected LCircle(final IPoint pt, final double radius, final boolean isUniqueID) {
		super(new LPoint(pt.getX()-radius, pt.getY()-radius), new LPoint(pt.getX()+radius, pt.getY()+radius), isUniqueID);

		update();
	}


	@Override
	public void scale(final double sx, final double sy, final Position pos) {
		if(pos==null || sx<=0 || sy<=0 || !GLibUtilities.INSTANCE.isValidPoint(sx, sy))
			throw new IllegalArgumentException();

		switch(pos) {
			case EAST:
				scaleX(sx, true);
				break;
			case WEST:
				scaleX(sx, false);
				break;
			case NORTH:
				scaleY(sy, false);
				break;
			case SOUTH:
				scaleY(sy, true);
				break;
			case NE:
				scaleY(sx, false);
				scaleX(sx, true);
				break;
			case NW:
				scaleY(sx, false);
				scaleX(sx, false);
				break;
			case SE:
				scaleY(sx, true);
				scaleX(sx, true);
				break;
			case SW:
				scaleY(sx, true);
				scaleX(sx, false);
				break;
		}
	}


	@Override
	public void setRx(final double rx) {
		super.setRx(rx);
		super.setRy(rx);
	}
	
	
	@Override
	public void setRy(final double rx) {
		setRx(rx);
	}
	

	@Override
	public double getRadius() {
		return getWidth()/2.;
	}


	@Override
	public void setRadius(final double radius) {
		if(!GLibUtilities.INSTANCE.isValidCoordinate(radius))
			return ;

		IPoint centre = getGravityCentre();

		setTop(centre.getY()-radius);
		setLeft(centre.getX()-radius);
	}


	@Override
	public double getA() {
		return getRx();
	}


	@Override
	public double getB() {
		return getRx();
	}


	@Override
	public boolean setTop(final double y) {
		boolean ok = super.setTop(y);

		if(ok)
			setWidth(getHeight());

		return ok;
	}


	@Override
	public boolean setBottom(final double y) {
		boolean ok = super.setBottom(y);

		if(ok)
			setWidth(getHeight());

		return ok;
	}


	@Override
	public boolean setLeft(final double x) {
		boolean ok = super.setLeft(x);

		if(ok)
			setHeight(getWidth());

		return ok;
	}


	@Override
	public boolean setRight(final double x) {
		boolean ok = super.setRight(x);

		if(ok)
			setHeight(getWidth());

		return ok;
	}


	@Override
	public void setWidth(final double width) {
		super.setWidth(width);
		super.setHeight(width);
	}



	@Override
	public void setHeight(final double height) {
		super.setHeight(height);
		super.setWidth(height);
	}
}
