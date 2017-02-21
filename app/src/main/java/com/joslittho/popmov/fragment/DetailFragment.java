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

import android.content.ContentValues;
import android.content.Context;
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
import com.joslittho.popmov.data.database.FavoritesTableColumns;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.data.database.MoviesProvider.FavoritesUriHolder;
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

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {

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

        // 0. if there is no intent, there is no particular movie's uri, so don't load
        // this can happen in tablet mode
        // 1. get this particular movie's uri from intent
        // 2. use a projection based on whether this is a regular or a favorite movie
        // 3. return a loader that fetches the movie's uri from the db using the gotten projection

        // 0. if there is no intent, there is no particular movie's uri, so don't load
        // this can happen in tablet mode

        Intent intent = getActivity().getIntent();

        if ( intent == null ) { return null; }

        // 1. get this particular movie's uri from intent

        Uri movieUri = intent.getData();

        // 2. use a projection based on whether this is a regular or a favorite movie

        // by default use the regular movie's projection
        String[] projection = DETAIL_FRAGMENT_COLUMNS;

        // if we are showing favorites, use the favorite movie's projection
        if ( Utility.isSortOrderFavorites( getActivity() ) ) {
            projection = FavoritesTableColumns.DETAIL_FRAGMENT_FAVORITE_COLUMNS;
        }

        // 3. return a loader that fetches the movie's uri from the db using the gotten projection

        return new CursorLoader( getActivity(), movieUri, projection, null, null, null );
        
    } // end onCreateLoader

    @Override
    // begin onLoadFinished
    public void onLoadFinished( Loader< Cursor > cursorLoader, final Cursor cursor ) {

        // 0. bind the needed details to their views
        // 0a. if the mark as favorite button is touched
        // 0a0. get the context for later content provider use
        // 0a1. get the movie id for this movie that we are to make favorite
        // 0a2. if the movie is a favorite
        // 0a2a. display "Mark As Favorite" since by touching this button
        // the user has unmarked this movie as favorite
        // 0a2b. remove this movie from the favorites table
        // 0a3. otherwise the movie is not a favorite
        // 0a3a. display "Unmark As Favorite" since by touching this button
        // the user has marked this movie as favorite
        // 0a3b. add this movie to the favorites table
        // 0a3b0. create a content values
        // 0a3b1. add needed values to the content values
        // 0a3b2. add content values to the db
        
        // 0. bind the needed details to their views

        // begin if there is a cursor and it has something
        if ( cursor != null && cursor.moveToFirst() ) {

            mBinding.detailTvTitle.setText( cursor.getString( COLUMN_TITLE ) );

            Picasso.with( getActivity() )
                    .load( Utility.getPosterUri( cursor.getString( COLUMN_POSTER_PATH ) ) )
                    .placeholder( R.color.primary_dark ).into( mBinding.detailIvPoster );

            mBinding.detailTvDate.setText(
                    Utility.getFormattedReleaseDate( cursor.getString( COLUMN_RELEASE_DATE ) ) );

            mBinding.detailTvVoteAverage.setText(
                    Utility.getFormattedUserRating( getActivity(),
                            cursor.getDouble( COLUMN_VOTE_AVERAGE ) ) );

            mBinding.detailTvSynopsis.setText( cursor.getString( COLUMN_DETAIL_OVERVIEW ) );

            final boolean favorite = Utility.getFavoriteForDetailFromDatabase( cursor );

            // if this movie is favorite, put the "Unmark As Favorite" text in the button
            // if this movie is not favorite, put the "Mark As Favorite" text in the button
            mBinding.detailBFavorite.setText(
                    favorite ? R.string.unmark_as_favorite : R.string.mark_as_favorite );

            // 0a. if the mark as favorite button is touched

            // begin mBinding.detailBFavorite.setOnClickListener
            mBinding.detailBFavorite.setOnClickListener(

                    // begin new View.OnClickListener
                    new View.OnClickListener() {

                        @Override
                        // begin onClick
                        public void onClick( View view ) {

                            // 0a0. get the context for later content provider use
                            // 0a1. get the movie id for this movie that we are to make favorite

                            // 0a0. get the context for later content provider use

                            Context context = getActivity();

                            // 0a1. get the movie id for this movie that we are to make favorite

                            long movieId = cursor.getLong( MovieTableColumns.COLUMN_MOVIE_ID );

                            // 0a2. if the movie is a favorite

                            // begin if favorite
                            if ( favorite ) {

                                // 0a2a. display "Mark As Favorite" since by touching this button
                                // the user has unmarked this movie as favorite

                                mBinding.detailBFavorite.setText( R.string.mark_as_favorite );

                                // 0a2b. remove this movie from the favorites table

                                context.getContentResolver().delete(
                                        FavoritesUriHolder.withFavoriteMovieId( movieId ),
                                        null, null );

                            } // end if favorite

                            // 0a3. otherwise the movie is not a favorite

                            // begin else not favorite
                            else {

                                // 0a3a. display "Unmark As Favorite" since by touching this button
                                // the user has marked this movie as favorite

                                mBinding.detailBFavorite.setText( R.string.unmark_as_favorite );

                                // 0a3b. add this movie to the favorites table

                                // 0a3b0. create a content values

                                ContentValues favoriteContentValues = new ContentValues();

                                // 0a3b1. add needed values to the content values

                                favoriteContentValues.put(
                                        FavoritesTableColumns.MOVIE_ID, movieId );
                                favoriteContentValues.put(
                                        FavoritesTableColumns.IS_FAVORITE,
                                        Utility.getFavoriteForDatabase(
                                                true /*favorite is always true here since the user
                                                wants to add the movie to favorites*/ ) );

                                // 0a3b2. add content values to the db

                                context.getContentResolver().insert(
                                        FavoritesUriHolder.FAVORITES_URI, favoriteContentValues );

                            } // end else not favorite

                        } // end onClick

                    } // end new View.OnClickListener

            ); // end mBinding.detailBFavorite.setOnClickListener

        } // end if there is a cursor and it has something
        
    } // end onLoadFinished

    @Override
    // nothing here
    public void onLoaderReset( Loader< Cursor > loader ) { }

    /* Other Methods */

    /* statics */

    /* INNER CLASSES */

} // end fragment DetailFragment