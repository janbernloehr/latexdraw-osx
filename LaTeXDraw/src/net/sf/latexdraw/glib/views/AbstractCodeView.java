package net.sf.latexdraw.glib.views;

import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines an abstract view for code-view.<br>
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
public abstract class AbstractCodeView<S extends IShape> extends AbstractView<S> {
	/** The cache of the code. */
	protected StringBuffer cache;


	/**
	 * Creates and initialises an abstract code view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	public AbstractCodeView(final S model) {
		super(model);

		cache = new StringBuffer();
	}



	/**
	 * Empties the cache.
	 * @since 3.0
	 */
	protected void emptyCache() {
		if(cache!=null)
			cache.delete(0, cache.length());
	}



	/**
	 * @return the cache.
	 * @since 3.0
	 */
	public StringBuffer getCache() {
		return cache;
	}
}
