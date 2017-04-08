/*
 *
 * PopMov
 *
 * An Android app to show the latest movies from https://www.themoviedb.org.
 *
 * Copyright (C) 2016 Kairu Joshua Wambugu
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

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joslittho.popmov.R;
import com.joslittho.popmov.adapter.ItemChoiceManager;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.databinding.GridItemPosterBinding;
import com.squareup.picasso.Picasso;

/**
 * {@link PosterAdapter} exposes movie posters from a {@link Cursor} to a {@link RecyclerView}
 * */
// begin class PosterAdapter
public class PosterAdapter extends RecyclerView.Adapter< PosterViewHolder > {

    /* CONSTANTS */

    /* Integers */

    /* Strings */

    /* VARIABLES */

    /* Contexts */

    private Context mContext; // ditto

    /* Cursors */

    private Cursor mCursor; // ditto

    /* ItemChoiceManagers */

    private ItemChoiceManager mICM; // ditto

    /* PosterAdapterOnClickHandlers */

    public final PosterAdapterOnClickHandler mPosterAdapterOnClickHandler; // ditto

    /* Views */

    private View mEmptyView; // ditto

    /* CONSTRUCTOR */

    /**
     * Default constructor.
     *
     * @param context Android {@link Context}
     * @param emptyView The empty view
     * @param handler A {@link PosterAdapterOnClickHandler} to handle item clicks
     * @param choiceMode The choice mode for the {@link RecyclerView}. One of
     *                   {@link android.widget.AbsListView#CHOICE_MODE_NONE},
     *                   {@link android.widget.AbsListView#CHOICE_MODE_SINGLE}, and
     *                   {@link android.widget.AbsListView#CHOICE_MODE_MULTIPLE}.
     * */
    // begin constructor
    public PosterAdapter( Context context, View emptyView, PosterAdapterOnClickHandler handler,
                          int choiceMode ) {

        // 0. initialize context
        // 1. initialize empty view
        // 2. initialize click handler
        // 3. initialize item choice manager and set choice mode

        // 0. initialize context

        mContext = context;

        // 1. initialize empty view

        mEmptyView = emptyView;

        // 2. initialize click handler

        mPosterAdapterOnClickHandler = handler;

        // 3. initialize item choice manager and set choice mode

        mICM = new ItemChoiceManager( this );
        mICM.setChoiceMode( choiceMode );

    } // end constructor

    /* METHODS */

    /* Getters and Setters */

    // getter for the cursor
    public Cursor getCursor() { return mCursor; }

    /* Overrides */

    @Override
    // begin onCreateViewHolder
    public PosterViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        // 0. if the parent view group is recycler
        // 0a. inflate the correct layout
        // 0b. make the inflated view focusable
        // 0b-last. return the inflated layout in a view holder
        // 1. else exception!

        // begin if the parent view group is a recycler
        if ( parent instanceof RecyclerView ) {

            // 0a. inflate the correct layout

            GridItemPosterBinding binding = DataBindingUtil.inflate( LayoutInflater.from( mContext ),
                    R.layout.grid_item_poster, parent, false );

            // 0b. make the inflated view focusable

            // done in grid_item_poster XML

            // 0b-last. return the inflated layout in a view holder

            return new PosterViewHolder( binding, this, mICM );

        } // end if the parent view group is a recycler

        // 1. else exception!

        else {
            throw new RuntimeException( "Not bound to RecyclerView." );
        }

    } // end onCreateViewHolder

    @Override
    // begin onBindViewHolder
    public void onBindViewHolder( PosterViewHolder holder, int position ) {

        // 0. if the cursor exists and has something
        // 0a. first move it to the given position
        // 0b. read the poster path from the cursor
        // 0c. put the correct image into the image view
        // 0d. put the correct content description
        // 0e. tell the item choice manager to bind view too

        // 0. if the cursor exists and has something

        // begin if there is non-empty cursor
        if ( mCursor != null && mCursor.moveToFirst() ) {

            // 0a. first move it to the given position

            mCursor.moveToPosition( position );

            // 0b. read the poster path from the cursor

            String posterPath = mCursor.getString( MovieTableColumns.COLUMN_POSTER_PATH );

            // 0c. put the correct image into the image view

            Picasso.with( mContext )
                    .load( Utility.getPosterUri( posterPath ) )
                    .placeholder( R.color.primary_dark )
                    .into( holder.posterImageView );

            // 0d. put the correct content description

            String title = mCursor.getString( MovieTableColumns.COLUMN_TITLE );

            holder.posterImageView
                    .setContentDescription( mContext.getString( R.string.a11y_grid_poster, title ) );

            // 0e. tell the item choice manager to bind view too

            mICM.onBindViewHolder( holder, position );

        } // end if there is non-empty cursor

    } // end onBindViewHolder

    /**
     * Return the number of items in the cursor, or zero if cursor is not there.
     * */
    @Override
    // getItemCount
    public int getItemCount() { return ( mCursor == null ) ? 0 : mCursor.getCount(); }

    /* Other Methods */

    /**
     * Replaces the member {@link Cursor} with the one passed in and refreshes the data.
     *
     * @param newCursor The {@link Cursor} to replace the member cursor with.
     * */
    // begin method swapCursor
    public void swapCursor( Cursor newCursor ) {

        // 0. swap cursors
        // 1. tell of data change
        // 2. set empty view visibility based on item count

        // 0. swap cursors

        mCursor = newCursor;

        // 1. tell of data change

        notifyDataSetChanged();

        // 2. set empty view visibility based on item count

        mEmptyView.setVisibility( getItemCount() == 0 ? View.VISIBLE : View.GONE );

    } // end method swapCursor

    // onRestoreInstanceState
    public void onRestoreInstanceState( Bundle savedInstanceState ) {
        mICM.onRestoreInstanceState( savedInstanceState );
    }

    // onSaveInstanceState
    public void onSaveInstanceState( Bundle outState ) {
        mICM.onSaveInstanceState( outState );
    }

    /* INNER CLASSES */

} // end class PosterAdapter