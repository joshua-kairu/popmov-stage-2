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

package com.joslittho.popmov.adapter;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.joslittho.popmov.R;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.databinding.GridItemPosterBinding;
import com.squareup.picasso.Picasso;

/**
 * A {@link android.widget.CursorAdapter} to populate the posters.
 * */
// begin class PosterAdapter
public class PosterAdapter extends CursorAdapter {

    /* CONSTANTS */

    /* Integers */
    
    /* Strings */
        
    /* VARIABLES */

    /* Contexts */

    private Context mContext; // ditto

    /* CONSTRUCTOR */

    // begin default recommended constructor
    public PosterAdapter( Context context, Cursor c, int flags ) {

        // 0. super stuff
        // 1. initialize context

        // 0. super stuff

        super( context, c, flags );

        // 1. initialize context

        mContext = context;

    } // end default recommended constructor

    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */



    @Override
    /** Makes a new view to hold the data pointed to by cursor. */
    // begin newView
    public View newView( Context context, Cursor cursor, ViewGroup parent ) {

        // 0. inflate the image view from XML
        // 1. get the poster path from the cursor
        // 2. put the correct image into the image view
        // 3. put the correct content description
        // 4. put the inflated image view inside a view holder
        // last. return the image view

        // 0. inflate the image view from XML

        GridItemPosterBinding binding = DataBindingUtil.inflate( LayoutInflater.from( mContext ),
                R.layout.grid_item_poster, parent, false );

        ImageView imageView = binding.gridIvPoster;

        // 1. get the poster path from the cursor

        String posterPath = cursor.getString( MovieTableColumns.COLUMN_POSTER_PATH );

        // 2. put the correct image into the image view

        Picasso.with( mContext )
                .load( Utility.getPosterUri( posterPath ) )
                .placeholder( R.color.primary_dark )
                .into( imageView );

        // 3. put the correct content description

        String title = cursor.getString( MovieTableColumns.COLUMN_TITLE );

        imageView.setContentDescription( mContext.getString( R.string.a11y_grid_poster, title ) );

        // 4. put the inflated image view inside a view holder

        imageView.setTag( R.id.view_holder_tag, new PosterViewHolder( imageView ) ); // rather pointless, eh?

        // last. return the image view

        return imageView;

    } // end newView

    @Override
    /** Bind an existing view to the data pointed to by cursor. */
    // begin bindView
    public void bindView( View view, Context context, Cursor cursor ) {

        // 0. get the view holder from the tag
        // 1. read the poster path from the cursor
        // 2. display the poster on the image view gotten from the view holder

        // 0. get the view holder from the tag

        PosterViewHolder posterViewHolder = ( PosterViewHolder ) view.getTag( R.id.view_holder_tag );

        // 1. read the poster path from the cursor

        String posterPath = cursor.getString( MovieTableColumns.COLUMN_POSTER_PATH );

        // 2. display the poster on the image view gotten from the view holder

        Picasso.with( mContext )
                .load( Utility.getPosterUri( posterPath ) )
                .placeholder( R.color.primary_dark )
                .into( posterViewHolder.posterImageView );

    } // end bindView

    /* Other Methods */

    /* INNER CLASSES */

} // end class PosterAdapter