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
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.joslittho.popmov.R;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.data.model.Movie;
import com.joslittho.popmov.databinding.GridItemPosterBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A {@link android.widget.BaseAdapter} to populate the posters.
 * */
// begin class PosterAdapter
public class PosterAdapter extends BaseAdapter {

    /* CONSTANTS */

    /* Integers */
    
    /* Strings */
        
    /* VARIABLES */

    /* Contexts */

    private Context mContext; // ditto

    /* Lists */

    private List< Movie > mMovies; // ditto

    /* CONSTRUCTOR */

    // begin constructor
    public PosterAdapter( Context context, List< Movie > movies ) {

        // 0. initialize members

        // 0. initialize members

        mMovies = movies;

        mContext = context;

    } // end constructor

    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */

    @Override
    public int getCount() { return mMovies.size(); }

    @Override
    public Object getItem( int position ) {
        return mMovies.get( position );
    }

    @Override
    public long getItemId( int position ) {
        return 0;
    }

    @Override
    /**
     * Get a View that displays the data at the specified position in the data set.
     * You can either create a View manually or inflate it from an XML layout file.
     * When the View is inflated, the parent View (GridView, ListView...) will apply
     * default layout parameters unless you use
     * android.view.LayoutInflater.inflate(int, ViewGroup, boolean) to specify a root view and
     * to prevent attachment to the root.
     * */
    // begin getView
    public View getView( int position, View convertView, ViewGroup parent ) {

        // 0. have an image view
        // 1. if the image view is not recycled
        // 1a. inflate it from XML
        // 2. otherwise it is recycled
        // 2a. so the converted view is an image view
        // 3. put the correct image into the image view
        // 4. put the correct content description
        // last. return the image view

        // 0. have an image view

        ImageView imageView;

        // 1. if the image view is not recycled

        // the image view is not recycled if the converted view is null

        // begin if the converted view is null
        if ( convertView == null ) {

            // 1a. inflate it from XML

            GridItemPosterBinding binding = DataBindingUtil.inflate( LayoutInflater.from( mContext ),
                    R.layout.grid_item_poster, parent, false );

            imageView = binding.gridIvPoster;

        } // end if the converted view is null

        // 2. otherwise it is recycled
        // 2a. so the converted view is an image view

        else {
            imageView = ( ImageView ) convertView;
        }

        // 3. put the correct image into the image view

        Picasso.with( mContext )
                .load( Utility.getPosterUri( mMovies.get(  position ).getPosterPath() ) )
                .placeholder( R.color.primary_dark )
                .into( imageView );

        // 4. put the correct content description

        imageView.setContentDescription(
                mContext.getString( R.string.a11y_grid_poster, mMovies.get( position ).getTitle() )
        );

        // last. return the image view

        return imageView;

    } // end getView

    /* Other Methods */

    /**
     * (Comment copied from {@link android.widget.ArrayAdapter#addAll(Object[])})
     * Adds the specified movies at the end of the movies array.
     *
     * @param movies The movies to add at the end of the movies array.
     */
    // begin method addAll
    public void addAll( List< Movie > movies ) {

        // 0. add the parameter movies to the array
        // 1. notify that the data has changed

        // 0. add the parameter movies to the array

        mMovies.addAll( movies );

        // 1. notify that the data has changed

        notifyDataSetChanged();

    } // end method addAll

    /**
     * (Copied from {@link ArrayAdapter#clear()}).
     *
     * Remove all elements from the list.
     * */
    // begin method clear
    public void clear() {

        // 0. empty the movies list
        // 1. notify of data change

        // 0. empty the movies list

        mMovies.clear();

        // 1. notify of data change

        notifyDataSetChanged();

    } // end method clear

    /* INNER CLASSES */

} // end class PosterAdapter