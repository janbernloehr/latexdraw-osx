package net.sf.latexdraw.glib.views.pst;

import java.util.List;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;


/**
 * Defines a PSTricks view of the LFreeHand model.<br>
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
 * 04/18/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class PSTFreeHandView extends PSTClassicalView<IFreehand> {
	/**
	 * Creates and initialises a LFreeHand PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	public PSTFreeHandView(final IFreehand model) {
		super(model);

		update();
	}



	@Override
	public void updateCache(final IPoint origin, final float ppc) {
		if(!GLibUtilities.INSTANCE.isValidPoint(origin) || ppc<1)
			return ;

		emptyCache();

		int i, size 		= shape.getNbPoints();
		List<IPoint> pts 	= shape.getPoints();
		StringBuilder coord = new StringBuilder();
		StringBuilder rot   = getRotationHeaderCode(ppc, origin);
		int interval 		= shape.getInterval();
		double originx 		= origin.getX();
		double originy 		= origin.getY();

		if(size<2)
			return;

		switch(shape.getType()) {
			case CURVES:
				float prevx=0;
				float prevy=0;
				float curx = (float)pts.get(0).getX();
				float cury = (float)pts.get(0).getY();
		        float midx=0;
		        float midy=0;

		        coord.append("\\moveto(").append(LNumber.INSTANCE.getCutNumber((float)((curx-originx)/ppc)));//$NON-NLS-1$
		        coord.append(',').append(LNumber.INSTANCE.getCutNumber((float)((originy-cury)/ppc))).append(')').append('\n');

		        if(pts.size()>interval) {
		            prevx = curx;
		            prevy = cury;
		            curx = (float)pts.get(interval).getX();
		            cury = (float)pts.get(interval).getY();
		            midx = (curx + prevx) / 2.0f;
		            midy = (cury + prevy) / 2.0f;

		            coord.append("\\lineto(").append(LNumber.INSTANCE.getCutNumber((float)((midx-originx)/ppc)));//$NON-NLS-1$
		            coord.append(',').append(LNumber.INSTANCE.getCutNumber((float)((originy-midy)/ppc))).append(')').append('\n');
		        }

		        for(i=interval*2; i<size; i+=interval) {
					float x1 	= (midx + curx) / 2.0f;
					float y1 	= (midy + cury) / 2.0f;
					prevx 		= curx;
					prevy 		= cury;
					curx 		= (float)pts.get(i).getX();
					cury 		= (float)pts.get(i).getY();
					midx 		= (curx + prevx) / 2.0f;
					midy 		= (cury + prevy) / 2.0f;
					float x2 	= (prevx + midx) / 2.0f;
					float y2 	= (prevy + midy) / 2.0f;

		            coord.append("\\curveto(").append(LNumber.INSTANCE.getCutNumber((float)((x1-originx)/ppc)));//$NON-NLS-1$
		            coord.append(',').append(LNumber.INSTANCE.getCutNumber((float)((originy-y1)/ppc))).append(')').append('(');
		            coord.append(LNumber.INSTANCE.getCutNumber((float)((x2-originx)/ppc))).append(',');
		            coord.append(LNumber.INSTANCE.getCutNumber((float)((originy-y2)/ppc))).append(')').append('(');
		            coord.append(LNumber.INSTANCE.getCutNumber((float)((midx-originx)/ppc))).append(',');
		            coord.append(LNumber.INSTANCE.getCutNumber((float)((originy-midy)/ppc))).append(')').append('\n');
		        }

		        if((i-interval+1)<size) {
		        	float x1 	= (midx + curx) / 2.0f;
		        	float y1 	= (midy + cury) / 2.0f;
		            prevx 		= curx;
		            prevy 		= cury;
		            curx 		= (float)pts.get(pts.size()-1).getX();
		            cury 		= (float)pts.get(pts.size()-1).getY();
		            midx 		= (curx + prevx) / 2.0f;
		            midy 		= (cury + prevy) / 2.0f;
		            float x2 	= (prevx + midx) / 2.0f;
		            float y2 	= (prevy + midy) / 2.0f;

		            coord.append("\\curveto("); //$NON-NLS-1$
            		coord.append(LNumber.INSTANCE.getCutNumber((float)((x1-originx)/ppc))).append(',');
    				coord.append(LNumber.INSTANCE.getCutNumber((float)((originy-y1)/ppc))).append(')').append('(');
					coord.append(LNumber.INSTANCE.getCutNumber((float)((x2-originx)/ppc))).append(',');
					coord.append(LNumber.INSTANCE.getCutNumber((float)((originy-y2)/ppc))).append(')').append('(');
					coord.append(LNumber.INSTANCE.getCutNumber((float)((pts.get(pts.size()-1).getX()-originx)/ppc))).append(',');
					coord.append(LNumber.INSTANCE.getCutNumber((float)((originy-pts.get(pts.size()-1).getY())/ppc))).append(')').append('\n');
		        }

				break;

			case LINES:
				IPoint p = pts.get(0);

				coord.append("\\moveto(").append(LNumber.INSTANCE.getCutNumber((float)((p.getX()-originx)/ppc)));//$NON-NLS-1$
				coord.append(',').append(LNumber.INSTANCE.getCutNumber((float)((originy-p.getY())/ppc))).append(')').append('\n');

				for(i=interval; i<size; i+=interval) {
					p = pts.get(i);
					coord.append("\\lineto(").append(LNumber.INSTANCE.getCutNumber((float)((p.getX()-originx)/ppc)));//$NON-NLS-1$
					coord.append(',').append(LNumber.INSTANCE.getCutNumber((float)((originy-p.getY())/ppc))).append(')').append('\n');
				}

				if((i-interval)<size)
					coord.append("\\lineto(").append(LNumber.INSTANCE.getCutNumber((float)((pts.get(pts.size()-1).getX()-originx)/ppc))).append(//$NON-NLS-1$
						',').append(LNumber.INSTANCE.getCutNumber((float)((originy-pts.get(pts.size()-1).getY())/ppc))).append(')').append('\n');

				break;

			default:
				BadaboomCollector.INSTANCE.add(new IllegalArgumentException());
				break;
		}

		if(rot!=null)
			cache.append(rot);

		cache.append("\\pscustom[");//$NON-NLS-1$
		cache.append("]\n{\n\\newpath\n");//$NON-NLS-1$
		cache.append(coord);
		cache.append(shape.isOpen() ? "" : "\\closepath");//$NON-NLS-1$//$NON-NLS-2$
		cache.append(shape.hasShadow() ? "\\openshadow\n" : "");//$NON-NLS-1$//$NON-NLS-2$
		cache.append('}');

		if(rot!=null)
			cache.append('}');
	}
}
