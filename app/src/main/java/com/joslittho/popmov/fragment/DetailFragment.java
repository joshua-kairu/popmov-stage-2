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
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joslittho.popmov.R;
import com.joslittho.popmov.adapter.TrailersAdapter;
import com.joslittho.popmov.adapter.TrailersAdapterOnClickHandler;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.data.database.FavoritesTableColumns;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.data.database.MoviesProvider.FavoritesUriHolder;
import com.joslittho.popmov.data.model.Result;
import com.joslittho.popmov.databinding.FragmentDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.joslittho.popmov.data.database.MovieTableColumns.*;

/**
 * {@link Fragment} to show the details of a selected movie
 * */
// begin fragment DetailFragment
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks< Cursor >,
        TrailersAdapterOnClickHandler{
    
    /* CONSTANTS */
    
    /* Integers */

    /** Movie detail loader ID */
    public static final int MOVIE_DETAIL_LOADER_ID = 0;

    /* Strings */

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    public static final String KEY_MOVIE_URL = "MOVIE_URL"; // ditto

    /* VARIABLES */

    /* FragmentDetailBindings */

    private FragmentDetailBinding mBinding; // ditto

    /* Uris */

    private Uri mMovieUri; // ditto

    /* CONSTRUCTOR */

    // empty constructor for fragment subclasses
    public DetailFragment() {
    }

    /* statics */

    /**
     * Create a new {@link DetailFragment} with arguments from the given {@link Bundle}.
     *
     * @param bundle The Bundle containing the arguments - in this case the movie Uri
     * @return The instantiated {@link DetailFragment}
     */
    // begin instantiating method newInstance
    public static DetailFragment newInstance( Bundle bundle ) {

        // 0. create a new DetailFragment
        // 1. if the bundle is null, just return the new DetailFragment
        // 2. use the bundle as the arguments for the created DetailFragment
        // 3. return the created DetailFragment

        // 0. create a new DetailFragment
        // 1. if the bundle is null, just return the new DetailFragment
        // 2. use the bundle as the arguments for the created DetailFragment
        // 3. return the created DetailFragment

        // 0. create a new DetailFragment

        DetailFragment fragment = new DetailFragment();

        // 1. if the bundle is null, just return the new DetailFragment

        if ( bundle == null ) {
            return fragment;
        }

        // 2. use the bundle as the arguments for the created DetailFragment

        fragment.setArguments( bundle );

        // 3. return the created DetailFragment

        return fragment;

    } // end instantiating method newInstance

    /* METHODS */
    
    /* Getters and Setters */

    /* Overrides */

    @Override
    // begin onCreateView
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {

        // 0. use the detail fragment layout
        // 1. initialize the data uri, if possible
        // last. return the inflated view

        // 0. use the detail fragment layout

        mBinding = DataBindingUtil.inflate( LayoutInflater.from( getActivity() ),
                R.layout.fragment_detail, container, false );

        // 1. initialize the data uri, if possible

        if ( getArguments() != null ) {
            mMovieUri = getArguments().getParcelable( KEY_MOVIE_URL );
        }

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

        // 0. if there is no particular movie's uri, so don't load
        // this can happen in tablet mode
        // 1. return a loader that fetches the data from movie's uri using the details projection

        // 0. if there is no particular movie's uri, so don't load
        // this can happen in tablet mode

        if ( mMovieUri == null ) { return null; }

        // 1. return a loader that fetches the data from movie's uri using the details projection

        return new CursorLoader( getActivity(), mMovieUri, DETAIL_FRAGMENT_COLUMNS,
                null, null, null );
        
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
        // 0b. the trailers
        // 0b0. get the trailers from db JSON
        // 0b1. instantiate the trailers adapter with the gotten trailers
        // 0b1a. create a list of trailer headers
        // 0b1b. get a hash map to store the trailer titles that will be children of the trailer 
        // header
        // 0b1c. put the titles of the trailers gotten from the db into the map
        // 0b1-last. instantiate the trailers adapter with the needed list and headers
        // 0b2. use the trailers adapter to populate the trailers recycler
        
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

            final boolean favorite = Utility.isMovieAFavorite( cursor.getLong( COLUMN_MOVIE_ID ),
                    getActivity().getContentResolver() );

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

                                // 0a3b2. add content values to the db

                                context.getContentResolver().insert(
                                        FavoritesUriHolder.FAVORITES_URI, favoriteContentValues );

                            } // end else not favorite

                        } // end onClick

                    } // end new View.OnClickListener

            ); // end mBinding.detailBFavorite.setOnClickListener

            // 0b. the trailers

            // 0b0. get the trailers from db JSON

            List< Result > trailerList = Utility.getTrailersFromDB( 
                    cursor.getString( COLUMN_DETAIL_TRAILERS ) );

            // 0b1. instantiate the trailers adapter with the gotten trailers

            // 0b1a. create a list of trailer headers

            String trailersHeaderTitle = getString( R.string.trailers_header_title );

            List< String > trailersHeaderList = new ArrayList<>( 1 );
            trailersHeaderList.add( trailersHeaderTitle );

            // 0b1b. get a hash map to store the trailer titles that will be children of the trailer 
            // header

            HashMap< String, List< Result > > trailersChildMap =
                    new HashMap<>( trailerList.size() );

            // 0b1c. put the titles of the trailers gotten from the db into the map

            trailersChildMap.put( trailersHeaderTitle, trailerList );

            // 0b1-last. instantiate the trailers adapter with the needed list and headers

            TrailersAdapter trailersAdapter = new TrailersAdapter( getActivity(), 
                    trailersHeaderList, trailersChildMap, this );

            // 0b2. use the trailers adapter to populate the trailers recycler

            mBinding.detailElvTrailers.setAdapter( trailersAdapter );

        } // end if there is a cursor and it has something
        
    } // end onLoadFinished

    @Override
    // nothing here
    public void onLoaderReset( Loader< Cursor > loader ) { }

    @Override
    public void onClick( long trailerPosition ) {
        // TODO: 3/30/17 fill this up
    }

    /* Other Methods */

    /* statics */

    /* INNER CLASSES */

} // end fragment DetailFragment