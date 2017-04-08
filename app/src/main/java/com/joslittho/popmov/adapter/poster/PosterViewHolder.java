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

package com.joslittho.popmov.adapter.poster;

import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.joslittho.popmov.adapter.ItemChoiceManager;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.databinding.GridItemPosterBinding;

/**
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} for the poster
 */
// begin class PosterViewHolder
class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /* CONSTANTS */

    /* Integers */

    /* Strings */

    /* VARIABLES */

    /* ImageViews */

    final ImageView posterImageView; // ditto

   /* ItemChoiceManagers */

    private ItemChoiceManager mICM; // ditto

    /* PosterAdapters */

    private final PosterAdapter mHostPosterAdapter; // ditto

    /* CONSTRUCTOR */

    // begin constructor
    PosterViewHolder( GridItemPosterBinding binding, PosterAdapter posterAdapter,
                      ItemChoiceManager itemChoiceManager ) {

        // 0. super stuff
        // 1. initialize
        // 1a. local image view
        // 1b. the host adapter
        // 1c. the item choice manager
        // 2. this view holder should listen to clicks from the parameter view

        // 0. super stuff

        super( binding.gridFlContainer );

        // 1. initialize

        // 1a. local image view

        posterImageView = binding.gridIvPoster;

        // 1b. the host adapter

        mHostPosterAdapter = posterAdapter;

        // 1c. the item choice manager

        mICM = itemChoiceManager;

        // 2. this view holder should listen to clicks from the parameter view

        itemView.setOnClickListener( this );

    } // end constructor

    /* METHODS */

    /* Getters and Setters */

    /* Overrides */

    @Override
    // begin onClick
    public void onClick( View view ) {

        // 0. get the movie id from the adapter's current position using a cursor
        // 1. call the handler with the movie id
        // 2. send the click to the item choice manager

        // 0. get the movie id from the adapter's current position using a cursor

        Cursor cursor = mHostPosterAdapter.getCursor();

        cursor.moveToPosition( getAdapterPosition() );

        long movieId = cursor.getLong( MovieTableColumns.COLUMN_MOVIE_ID );

        // TODO: 4/5/17 what about in phone mode? And persist the selection too
        // 1. call the handler with the movie id

        mHostPosterAdapter.mPosterAdapterOnClickHandler.onClick( movieId );

        // 2. send the click to the item choice manager

        mICM.onClick( this );

    } // end onClick
    
    /* Other Methods */

} // end class PosterViewHolder