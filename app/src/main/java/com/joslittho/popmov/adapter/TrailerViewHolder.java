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

package com.joslittho.popmov.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joslittho.popmov.R;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.databinding.TrailerItemBinding;

/**
 * {@link RecyclerView.ViewHolder} for a trailer
 */
// begin class TrailerViewHolder
class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /* CONSTANTS */

    /* Integers */

    /* Strings */

    /* VARIABLES */

    /* ImageViews */

    final TextView mTrailerNameTextView; // ditto

    /* TrailerAdapters */

    private final TrailerAdapter mHostTrailerAdapter; // ditto

    /* CONSTRUCTOR */

    // begin constructor
    TrailerViewHolder( TrailerItemBinding binding, TrailerAdapter trailerAdapter ) {

        // 0. super stuff
        // 1. initialize members
        // 2. this view holder should listen to clicks from the parameter view

        // 0. super stuff

        super( binding.trailerItemClParent );

        // 1. initialize members

        mTrailerNameTextView = binding.trailerItemTvName;

        mHostTrailerAdapter = trailerAdapter;

        // 3. this view holder should listen to clicks from the parameter view

        itemView.setOnClickListener( this );

    } // end constructor

    /* METHODS */

    /* Getters and Setters */

    /* Overrides */

    @Override
    // begin onClick
    public void onClick( View view ) {

        // 0. call the handler with the view holder's position

        // 0. call the handler with the view holder's position

        mHostTrailerAdapter.mTrailerAdapterOnClickHandler.onClick( getAdapterPosition() );

    } // end onClick
    
    /* Other Methods */

} // end class PosterViewHolder