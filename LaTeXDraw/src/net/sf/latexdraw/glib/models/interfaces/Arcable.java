package net.sf.latexdraw.glib.models.interfaces;

import net.sf.latexdraw.lang.LangTool;

/**
 * Defines a generic interface for shapes that support arc attributes.<br>
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
 * 08/22/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public interface Arcable {
	/** The different styles of arc. */
	public static enum ArcStyle {
		WEDGE {
			@Override
			public boolean supportArrow() { return false; }

			@Override
			public String getLabel() { return LangTool.LANG.getStringOthers("Arc.arc"); }//$NON-NLS-1$
		}, ARC {
			@Override
			public boolean supportArrow() { return true; }

			@Override
			public String getLabel() { return LangTool.LANG.getStringOthers("Arc.wedge"); }//$NON-NLS-1$
		}, CHORD {
			@Override
			public boolean supportArrow() { return false; }

			@Override
			public String getLabel() { return LangTool.LANG.getStringOthers("Arc.chord"); }//$NON-NLS-1$
		};


		/**
		 * @return True if the arc type can have arrows.
		 * @since 3.0.0
		 */
		public abstract boolean supportArrow();


		/**
		 * @return The translated label of the arc type.
		 * @since 3.0
		 */
		public abstract String getLabel();
	}

	/**
	 * @return the style of the arc.
	 * @since 3.0.0
	 */
	ArcStyle getArcStyle();

	/**
	 * @param style the arc style to set.
	 * @since 3.0.0
	 */
	void setArcStyle(final ArcStyle style);

	/**
	 * @return the angleStart.
	 * @since 3.0.0
	 */
	double getAngleStart();

	/**
	 * @param angleStart the angleStart to set.
	 * @since 3.0.0
	 */
	void setAngleStart(final double angleStart);

	/**
	 * @return the angleEnd.
	 * @since 3.0.0
	 */
	double getAngleEnd();

	/**
	 * @param angleEnd the angleEnd to set.
	 * @since 3.0.0
	 */
	void setAngleEnd(final double angleEnd);
}
