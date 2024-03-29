package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPicture;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGImageElement;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for an picture.<br>
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
 * 11/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LPictureSVGGenerator extends LShapeSVGGenerator<IPicture>
{
	public LPictureSVGGenerator(final IPicture shape) {
		super(shape);
	}



	/**
	 * Creates a picture from a SVGImage element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	public LPictureSVGGenerator(final SVGImageElement elt) {
		this(DrawingTK.getFactory().createPicture(true, DrawingTK.getFactory().createPoint(), elt.getURI()));

//		IPicture p = (IPicture)getShape();
//
//		p.createEPSImage();
//		p.getPosition().setPoint(elt.getX(), elt.getY());
//		p.getBorders().setFirstPoint(new LPoint(p.getPosition()));
//		p.getBorders().setLastPoint(p.getPosition().getX()+p.getImage().getWidth(null), p.getPosition().getX()+p.getImage().getHeight(null));
//		p.getBorders().setIsFilled(true);
//		p.update();
		applyTransformations(elt);
	}




	public LPictureSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}



	/**
	 * Creates a picture from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	public LPictureSVGGenerator(final SVGGElement elt, final boolean withTransformation)
	{
		this(DrawingTK.getFactory().createPicture(true, DrawingTK.getFactory().createPoint(), "")); //$NON-NLS-1$

		setNumber(elt);
//		SVGElement elt2 = getLaTeXDrawElement(elt, null);
//
//		if(elt==null || elt2==null || !(elt2 instanceof SVGImageElement))
//			throw new IllegalArgumentException();
//
//		SVGImageElement main = (SVGImageElement)elt2;
//
//		IPicture p = (IPicture)getShape();
//		p.setPathSource(main.getURI());
//		p.setPicture(p.getImage());
//
//		if(p.getImage()==null)
//			throw new IllegalArgumentException();
//
//		double x = main.getX();
//		double y = main.getY();
//
//		p.createEPSImage();
//		p.getPosition().setLocation(x, y);
//		p.getBorders().setFirstPoint(new LPoint(x, y));
//		p.getBorders().setLastPoint(x+p.getPicture().getWidth(null), y+p.getPicture().getHeight(null));
//		p.getBorders().setIsFilled(true);
//		p.updateShape();

		if(withTransformation)
			applyTransformations(elt);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc == null)
			return null;

		SVGElement root = new SVGGElement(doc), img;

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_PICTURE);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

		img = new SVGImageElement(doc, shape.getPathSource());
		img.setAttribute(SVGAttributes.SVG_X, String.valueOf(shape.getPosition().getX()));
		img.setAttribute(SVGAttributes.SVG_Y, String.valueOf(shape.getPosition().getY()));
		img.setAttribute(SVGAttributes.SVG_HEIGHT, String.valueOf(shape.getImage().getHeight(null)));
		img.setAttribute(SVGAttributes.SVG_WIDTH, String.valueOf(shape.getImage().getWidth(null)));

		root.appendChild(img);

		return root;
	}
}
