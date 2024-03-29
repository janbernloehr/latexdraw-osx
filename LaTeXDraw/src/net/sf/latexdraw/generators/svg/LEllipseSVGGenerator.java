package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGEllipseElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.util.LNamespace;

/**
 * Defines a SVG generator for an ellipse.<br>
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
public class LEllipseSVGGenerator<S extends IEllipse> extends LShapeSVGGenerator<S> {
	/**
	 * Creates a generator of SVG ellipse.
	 * @param ellipse The ellipse shape used for the generation.
	 * @throws IllegalArgumentException If ellipse is null.
	 * @since 2.0
	 */
	public LEllipseSVGGenerator(final S ellipse) {
		super(ellipse);
	}



	/**
	 * Creates an ellipse from an SVG ellipse element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	public LEllipseSVGGenerator(final SVGEllipseElement elt) {
		this((S)DrawingTK.getFactory().createEllipse(true));

		setEllipseParameters(elt, 0.);
		setSVGParameters(elt);
		applyTransformations(elt);
	}


	/**
	 * Creates an ellipse from a G SVG element.
	 * @param elt The G SVG element used for the creation of an ellipse.
	 * @throws IllegalArgumentException If the given element is null.
	 * @since 2.0
	 */
	public LEllipseSVGGenerator(final SVGGElement elt) {
		this(elt, true);
	}



	/**
	 * Creates an ellipse from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	public LEllipseSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this((S)DrawingTK.getFactory().createEllipse(false));

		setNumber(elt);
		final SVGElement elt2 = getLaTeXDrawElement(elt, null);

		if(elt==null || !(elt2 instanceof SVGEllipseElement))
			throw new IllegalArgumentException();

		setEllipseParameters((SVGEllipseElement)elt2, getPositionGap()/2.);
		setSVGLatexdrawParameters(elt);
		setSVGParameters(elt2);

		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		if(withTransformation)
			applyTransformations(elt);
	}



	/**
	 * Sets the ellipse parameters.
	 * @param ellipseElt The source SVG ellipse element.
	 * @param gap The gap used to define the latexdraw ellipse.
	 * @since 3.0
	 */
	protected void setEllipseParameters(final SVGEllipseElement ellipseElt, final double gap) {
		final double rx = ellipseElt.getRx();
		final double ry	= ellipseElt.getRy();

		shape.setPosition(ellipseElt.getCx()-rx, ellipseElt.getCy()-ry); //TODO to test: gap should be consider.
		shape.setWidth(2.*rx-gap);
		shape.setHeight(2.*ry-gap);
		shape.setBordersPosition(BorderPos.MID);
	}



	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null || doc.getFirstChild().getDefs()==null)
			throw new IllegalArgumentException();

		final IPoint tl = shape.getTopLeftPoint();
		final IPoint br = shape.getBottomRightPoint();
		final double tlx = tl.getX();
		final double tly = tl.getY();
		final double brx = br.getX();
		final double bry = br.getY();
		SVGElement elt;
		SVGElement root = new SVGGElement(doc);
        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_ELLIPSE);
        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
        final double gap 	= getPositionGap();
        final double width  = Math.max(1., (brx-tlx+gap)/2.);
        final double height = Math.max(1., (bry-tly+gap)/2.);
        final double x		= (brx+tlx)/2.;
        final double y		= (bry+tly)/2.;

        if(shape.hasShadow()) {
        	elt = new SVGEllipseElement(x, y, width, height, doc);
        	setSVGShadowAttributes(elt, true);
        	root.appendChild(elt);
        }

        if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE)) {
        	// The background of the borders must be filled is there is a shadow.
        	elt = new SVGEllipseElement(x, y, width, height, doc);
        	setSVGBorderBackground(elt, root);
        }

        elt = new SVGEllipseElement(x, y, width, height, doc);
        setSVGAttributes(doc, elt, true);
        root.appendChild(elt);

        if(shape.hasDbleBord())  {
        	elt = new SVGEllipseElement(x, y, width, height, doc);
        	setSVGDoubleBordersAttributes(elt);
        	root.appendChild(elt);
        }

        setSVGRotationAttribute(root);

		return root;
	}
}
