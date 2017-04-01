/*
 *
 * PopMov
 *
 * An Android app to show the latest movies from https://www.themoviedb.org.
 *
 * Copyright (C) 2016-present Kairu Joshua Wambugu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 */

package com.joslittho.popmov.adapter.trailer;

/**
 * Handler for click events in the {@link android.support.v7.widget.RecyclerView}
 * populated by the {@link TrailerAdapter }.
 */
// begin interface TrailerAdapterOnClickHandler
public interface TrailerAdapterOnClickHandler {

    /* VARIABLES */
    
    /* METHODS */

    /**
     * Click handler for the {@link TrailerViewHolder}.
     *
     * @param trailerPosition The position of the selected trailer.
     * */
    // onClick
    void onClick( int trailerPosition );

} // end interface TrailerAdapterOnClickHandler
