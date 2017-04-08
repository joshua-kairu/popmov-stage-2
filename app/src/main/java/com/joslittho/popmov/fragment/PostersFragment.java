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

package com.joslittho.popmov.fragment;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.joslittho.popmov.R;
import com.joslittho.popmov.adapter.ItemChoiceManager;
import com.joslittho.popmov.adapter.poster.PosterAdapter;
import com.joslittho.popmov.adapter.poster.PosterAdapterOnClickHandler;
import com.joslittho.popmov.data.PosterCallback;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.data.database.FavoritesTableColumns;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.data.database.MoviesProvider;
import com.joslittho.popmov.databinding.FragmentPostersBinding;
import com.joslittho.popmov.sync.MoviesSyncAdapter;

import static com.joslittho.popmov.data.database.MoviesProvider.*;

/**
 * {@link Fragment} to show the movie posters.
 * */
// begin fragment PostersFragment
public class PostersFragment extends Fragment implements LoaderManager.LoaderCallbacks< Cursor >,
        PosterAdapterOnClickHandler {

    /* CONSTANTS */
    
    /* Integers */

    /** Movies loader ID */
    public static final int MOVIES_LOADER_ID = 0;

    /* Strings */

    /**
     * The logger.
     */
    private static final String LOG_TAG = PostersFragment.class.getSimpleName();

    /** Fragment tag. */
    public static final String POSTER_FRAGMENT_TAG = "POSTER_FRAGMENT_TAG";

    /* VARIABLES */

    /* Fragment Posters Bindings */

    private FragmentPostersBinding binding; // ditto

    /* Poster Adapters */

    private PosterAdapter mPosterAdapter; // ditto

    /* PosterCallbacks */

    private PosterCallback posterCallbackListener; // ditto

    /* CONSTRUCTOR */

    // empty constructor for fragment subclasses
    public PostersFragment() {
    }
    
    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */

    @Override
    // begin onCreate
    public void onCreate( @Nullable Bundle savedInstanceState ) {

        // 0. super stuff
        // 1. register for the menu
        // 2. initialize the poster callback listener

        // 0. super stuff

        super.onCreate( savedInstanceState );

        // 1. register for the menu

        setHasOptionsMenu( true );

        // 2. initialize the poster callback listener

        try {
            posterCallbackListener = ( PosterCallback ) getActivity();
        }
        catch ( ClassCastException e ) {
            Log.e( LOG_TAG, "The parent activity must implement PosterCallback" );
        }

    } // end onCreate

    @Override
    // begin onCreateOptionsMenu
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

        // 0. super stuff
        // 1. inflate the correct menu for this fragment

        // 0. super stuff

        super.onCreateOptionsMenu( menu, inflater );

        // 1. inflate the correct menu for this fragment

        inflater.inflate( R.menu.menu_fragment_posters, menu );

    } // end onCreateOptionsMenu

    @Override
    // begin onCreateView
    public View onCreateView( LayoutInflater inflater, final ViewGroup container,
                              Bundle savedInstanceState ) {

        // 0. use the posters fragment layout
        // 1. initialize the adapter
        // 2. the recycler
        // 2a. find reference to it
        // 2b. use a grid layout manager with screen-size-dependent columns
        // 2c. has fixed size
        // 2d. use the poster adapter
        // 3. if there is saved state
        // 3a. restore the adapter's saved state so that the tapped item persists
        // last. return the inflated view NOT THE INFLATED GRID VIEW SINCE
        // THE INFLATED GRID VIEW *WILL* HAVE A PARENT

        // 0. use the posters fragment layout

        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_posters, container, false );

        // 1. initialize the adapter

        mPosterAdapter = new PosterAdapter( getActivity(), binding.mainTvEmpty, this,
                AbsListView.CHOICE_MODE_SINGLE );

        // 2. the recycler

        // 2a. find reference to it

        RecyclerView mPostersRecyclerView = binding.mainRvPosters;

        // 2b. use a grid layout manager with screen-size-dependent columns

        int columnCount = getResources().getInteger( R.integer.posters_grid_columns );

        mPostersRecyclerView.setLayoutManager( new GridLayoutManager( getActivity(), columnCount ) );

        // 2c. has fixed size

        mPostersRecyclerView.setHasFixedSize( true );

        // 2d. use the poster adapter

        mPostersRecyclerView.setAdapter( mPosterAdapter );

        // 3a. restore the adapter's saved state so that the tapped item persists

        if ( savedInstanceState != null ) {
            mPosterAdapter.onRestoreInstanceState( savedInstanceState );
            // TODO: 4/8/17 scroll to selected position
        }

        // last. return the inflated view NOT THE INFLATED GRID VIEW SINCE
        // THE INFLATED GRID VIEW *WILL* HAVE A PARENT

        return binding.getRoot();

    } // end onCreateView

    @Override
    // begin onActivityCreated
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {

        // 0. super stuff
        // 1. initialize loader

        // 0. super stuff

        super.onActivityCreated( savedInstanceState );

        // 1. initialize loader

        getLoaderManager().initLoader( MOVIES_LOADER_ID, null, this );

    } // end onActivityCreated

    @Override
    // begin onOptionsItemSelected
    public boolean onOptionsItemSelected( MenuItem item ) {

        // 0. if it's the refresh item selected
        // 0a. update the movies
        // 1. otherwise
        // 1a. do defaults

        // begin switching the item id
        switch ( item.getItemId() ) {

            // 0. if it's the refresh item selected
            // 0a. update the movies

            case R.id.action_refresh:
                updateMovies(); return true;

            // 1. otherwise
            // 1a. do defaults
            default:
                return super.onOptionsItemSelected( item );

        } // end switching the item id

    } // end onOptionsItemSelected

    @Override
    // begin onCreateLoader
    public Loader< Cursor > onCreateLoader( int id, Bundle args ) {

        // 0. load movie data from db using a cursor loader
        // 0a. if the user wants favorites
        // 0a0. use the favorites uri
        // 0a1. use the favorites projection
        // 0a2. use a null sort order
        // 0b. otherwise
        // 0a0. use the movies uri
        // 0a1. use the posters projection
        // 0a2. use the user-preferred sort order

        // 0. load movie data from db using a cursor loader

        Context context = getActivity();

        String sortOrderForDatabase;

        Uri uri;

        String[] projection;

        // 0a. if the user wants favorites

        String sortOrder = Utility.getPreferredSortOrder( context );

        // begin if it's favorites
        if ( sortOrder.equals( getString( R.string.pref_sort_order_favorites_entry_value ) ) ) {

            // 0a0. use the favorites uri

            uri = FavoritesUriHolder.FAVORITES_URI;

            // 0a1. use the favorites projection

            projection = FavoritesTableColumns.POSTERS_FRAGMENT_FAVORITES_COLUMNS;

            // 0a2. use a null sort order

            sortOrderForDatabase = null;

        } // end if it's favorites

        // 0b. otherwise

        // begin else it's not favorites
        else {

            // 0a0. use the movies uri

            uri = MoviesUriHolder.MOVIES_URI;

            // 0a1. use the posters projection

            projection = MovieTableColumns.POSTERS_FRAGMENT_COLUMNS;

            // 0a2. use the user-preferred sort order

            sortOrderForDatabase = Utility.getSortOrderForDatabase( context );

        } // end else it's not favorites

        return new CursorLoader( context, uri, projection, null, null, sortOrderForDatabase );

    } // end onCreateLoader

    @Override
    // begin onLoadFinished
    public void onLoadFinished( Loader< Cursor > cursorLoader, Cursor newCursor ) {

        // 0. refresh the grid view
        // 1. update the empty view

        // 0. refresh the grid view

        mPosterAdapter.swapCursor( newCursor );

        // 1. update the empty view

        updateEmptyView();

    } // end onLoadFinished

    @Override
    // begin onLoaderReset
    public void onLoaderReset( Loader< Cursor > loader ) {

        // 0. remove any cursors being used

        // 0. remove any cursors being used

        mPosterAdapter.swapCursor( null );

    } // end onLoaderReset

    @Override
    // begin onClick
    public void onClick( long movieId ) {

        // 0. get the uri for this movie
        // 1. pass the gotten movie uri to the callback listener

        // 0. get the uri for this movie

        Uri movieUri = MoviesProvider.MoviesUriHolder.withMovieId( movieId );

        // 1. pass the gotten movie uri to the callback listener

        posterCallbackListener.onPosterItemSelected( movieUri );

    } // end onClick

    @Override
    // begin onSaveInstanceState
    public void onSaveInstanceState( Bundle outState ) {

        // 0. tell adapter to save state of selected item
        // 1. super stuff

        // 0. tell adapter to save state of selected item

        mPosterAdapter.onSaveInstanceState( outState );

        // 1. super stuff

        super.onSaveInstanceState( outState );

    } // end onSaveInstanceState

    /* Other Methods */

    /**
     * Helper method to update the movies displayed onscreen.
     * */
    // begin method updateMovies
    private void updateMovies() {

        // 0. sync up

        // 0. sync up

        MoviesSyncAdapter.syncImmediately( getActivity() );

    } // end method updateMovies

    /** Helper method to respond to change in the sort order. */
    // begin method onSortOrderChanged
    public void onSortOrderChanged() {

        // 0. update the movies

        updateMovies();

    } // end method onSortOrderChanged

    /** Helper method to update the view showing that the movie list is empty. */
    // begin method updateEmptyView
    private void updateEmptyView() {

        // 0. get the empty view
        // 1. if the posters adapter has nothing
        // 1a0. if there is the empty view
        // 1a0a. tell the user why the list is empty - possibly because
        // 1a0a0. there is no internet
        // 1a0a1. the user wants to see favorites but hasn't selected any
        // 1a0b. show the empty view
        // 2. otherwise the posters adapter has something
        // 2a. hide the empty view

        // 0. get the empty view

        TextView emptyTextView = binding.mainTvEmpty;

        // 1. if the posters adapter has nothing

        // begin if adapter count is zero
        if ( mPosterAdapter.getCursor().getCount() == 0 ) {

            // 1a0. if there is the empty view

            // begin if there is an empty view
            if ( emptyTextView != null ) {

                // 1a0a. tell the user why the list is empty - possibly because

                int message = R.string.message_error_no_movie_info;

                // 1a0a0. there is no internet

                if ( ! Utility.isInternetUp() ) {
                    message = R.string.message_error_no_movie_info_no_connectivity;
                }

                // 1a0a1. the user wants to see favorites but hasn't selected any

                if ( Utility.isSortOrderFavorites( getActivity() ) ) {
                    message = R.string.message_error_no_movie_info_no_favorites;
                }

                // 1a0b. show the empty view

                emptyTextView.setText( message );

                emptyTextView.setVisibility( View.VISIBLE );

            } // end if there is an empty view

        } // end if adapter count is zero

        // 2. otherwise the posters adapter has something
        // 2a. hide the empty view

        else { if ( emptyTextView != null ) { emptyTextView.setVisibility( View.INVISIBLE ); } }

    } // end method updateEmptyView

    /* INNER CLASSES */

} // end fragment PostersFragment