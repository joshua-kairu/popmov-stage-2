/*
 * 
 * com.jlt.popmov.fragment
 * 
 * <one line to give the program's name and a brief idea of what it does.>
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

package com.joslittho.popmov.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joslittho.popmov.R;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.databinding.FragmentDetailBinding;
import com.squareup.picasso.Picasso;

import static com.joslittho.popmov.data.database.MovieTableColumns.*;

/**
 * {@link Fragment} to show the details of a selected movie
 * */
// begin fragment DetailFragment
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks< Cursor > {
    
    /* CONSTANTS */
    
    /* Integers */

    /** Movie detail loader ID */
    public static final int MOVIE_DETAIL_LOADER_ID = 0;

    /* Strings */

    /* VARIABLES */

    private FragmentDetailBinding mBinding; // ditto

    /* CONSTRUCTOR */

    // empty constructor for fragment subclasses
    public DetailFragment() {
    }
    
    /* METHODS */
    
    /* Getters and Setters */

    /* Overrides */

    @Override
    // begin onCreateView
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        // 0. use the detail fragment layout
        // last. return the inflated view

        // 0. use the detail fragment layout

        mBinding = DataBindingUtil.inflate( LayoutInflater.from( getActivity() ),
                R.layout.fragment_detail, container, false );

        // last. return the inflated view

        return mBinding.getRoot();

    } // end onCreateView

    @Override
    // begin onActivityCreated
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {

        // 0. super stuff
        // 1. initialize loader

        // 0. super stuff

        super.onActivityCreated( savedInstanceState );

        // 1. initialize loader

        getLoaderManager().initLoader( MOVIE_DETAIL_LOADER_ID, null, this );

    } // end onActivityCreated

    @Override
    // begin onCreateLoader
    public Loader< Cursor > onCreateLoader( int id, Bundle args ) {

        // 0. if there is no intent, there is no particular movie's url, so don't load
        // this can happen in tablet mode
        // 1. get this particular movie's url from intent
        // 2. return a loader that fetches this particular movie's url from the db

        // 0. if there is no intent, there is no particular movie's url, so don't load
        // this can happen in tablet mode

        Intent intent = getActivity().getIntent();

        if ( intent == null ) { return null; }

        // 1. get this particular movie's url from intent

        Uri movieUri = intent.getData();

        // 2. return a loader that fetches this particular movie's url from the db

        return new CursorLoader( getActivity(), movieUri, DETAIL_FRAGMENT_COLUMNS, null, null, null );
        
    } // end onCreateLoader

    @Override
    // begin onLoadFinished
    public void onLoadFinished( Loader< Cursor > cursorLoader, Cursor cursor ) {

        // 0. bind the needed details to their views
        
        // 0. bind the needed details to their views
        
        // begin if there is a cursor and it has something
        if ( cursor != null && cursor.moveToFirst() ) {

            mBinding.detailTvTitle.setText( cursor.getString( COLUMN_TITLE ) );

            Picasso.with( getActivity() )
                    .load( Utility.getPosterUri( cursor.getString( COLUMN_POSTER_PATH ) ) )
                    .placeholder( R.color.primary_dark ).into( mBinding.detailIvPoster );

            mBinding.detailTvDate.setText(
                    Utility.getFormattedReleaseDate( cursor.getString( COLUMN_RELEASE_DATE ) ) );

            mBinding.detailTvUserRating.setText(
                    Utility.getFormattedUserRating( getActivity(),
                            cursor.getDouble( COLUMN_VOTE_AVERAGE ) ) );

            mBinding.detailTvSynopsis.setText( cursor.getString( COLUMN_DETAIL_OVERVIEW ) );

        } // end if there is a cursor and it has something
        
    } // end onLoadFinished

    @Override
    // nothing here
    public void onLoaderReset( Loader< Cursor > loader ) { }

    /* Other Methods */

    /* statics */

    /* INNER CLASSES */

} // end fragment DetailFragment