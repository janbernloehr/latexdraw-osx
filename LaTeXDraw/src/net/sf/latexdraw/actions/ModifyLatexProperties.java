package net.sf.latexdraw.actions;

import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator.VerticalPosition;

import org.malai.action.Action;
import org.malai.undo.Undoable;

/**
 * This action modifies the latex properties of the current drawing.<br>
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
 * 08/14/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ModifyLatexProperties extends Action implements Undoable, Modifying {
	/**
	 * This enumeration defines the different LaTeX properties that can be modified.
	 */
	public static enum LatexProperties {
		COMMENT {
			@Override
			public boolean isValueSupported(final Object value) {
				return value instanceof String;
			}
		},
		PACKAGES {
			@Override
			public boolean isValueSupported(final Object value) {
				return value instanceof String;
			}
		},
		CAPTION {
			@Override
			public boolean isValueSupported(final Object value) {
				return value instanceof String;
			}
		},
		LABEL {
			@Override
			public boolean isValueSupported(final Object value) {
				return value instanceof String;
			}
		},
		POSITION_VERTICAL {
			@Override
			public boolean isValueSupported(final Object value) {
				return value instanceof VerticalPosition;
			}
		},
		POSITION_HORIZONTAL {
			@Override
			public boolean isValueSupported(final Object value) {
				return value instanceof Boolean;
			}
		};

		/**
		 * @param value The value to test.
		 * @return True: the given value corresponds to the excepted value of the property.
		 * @since 3.0
		 */
		public abstract boolean isValueSupported(final Object value);
	}

	/** The new value to set. */
	protected Object value;

	/** The saved value used for undo/redo. */
	protected Object oldValue;

	/** The property to modify. */
	protected LatexProperties property;

	/** The LaTeX generator to modify. */
	protected LaTeXGenerator generator;


	@Override
	public boolean isRegisterable() {
		return hadEffect();
	}


	@Override
	protected void doActionBody() {
		switch(property) {
			case CAPTION 			: oldValue = generator.getCaption(); break;
			case COMMENT 			: oldValue = generator.getComment(); break;
			case LABEL 				: oldValue = generator.getLabel(); break;
			case PACKAGES 			: oldValue = LaTeXGenerator.getPackages(); break;
			case POSITION_HORIZONTAL: oldValue = generator.isPositionHoriCentre(); break;
			case POSITION_VERTICAL 	: oldValue = generator.getPositionVertToken(); break;
		}

		applyValue(value);
	}


	private void applyValue(final Object object) {
		switch(property) {
			case CAPTION 			: generator.setCaption((String)object); break;
			case COMMENT 			: generator.setComment((String)object); break;
			case LABEL 				: generator.setLabel((String)object); break;
			case PACKAGES 			: LaTeXGenerator.setPackages((String)object); break;
			case POSITION_HORIZONTAL: generator.setPositionVertToken((VerticalPosition)object); break;
			case POSITION_VERTICAL 	: generator.setPositionHoriCentre((Boolean)object); break;
		}
	}


	@Override
	public boolean canDo() {
		// PACKAGES does not require the generator since it is a static attribute.
		return property!=null && property.isValueSupported(value) && (generator!=null || property==LatexProperties.PACKAGES);
	}


	@Override
	public void undo() {
		applyValue(oldValue);
	}


	@Override
	public void redo() {
		applyValue(value);
	}


	@Override
	public String getUndoName() {
		return "Modification of a LaTeX attribute";
	}


	/**
	 * @param value The new value to set.
	 * @since 3.0
	 */
	public void setValue(final Object value) {
		this.value = value;
	}


	/**
	 * @param property The property to modify.
	 * @since 3.0
	 */
	public void setProperty(final LatexProperties property) {
		this.property = property;
	}


	/**
	 * @param generator The LaTeX generator to modify.
	 * @since 3.0
	 */
	public void setGenerator(final LaTeXGenerator generator) {
		this.generator = generator;
	}
}
