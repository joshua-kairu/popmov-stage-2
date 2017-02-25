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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.joslittho.popmov.R;
import com.joslittho.popmov.activity.DetailActivity;
import com.joslittho.popmov.adapter.PosterAdapter;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.data.database.FavoritesTableColumns;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.data.database.MoviesProvider;
import com.joslittho.popmov.data.database.generated.MoviesDatabase;
import com.joslittho.popmov.data.model.Movie;
import com.joslittho.popmov.data.remote.CheckConnectivityAsyncTask;
import com.joslittho.popmov.data.remote.FetchMovieTask;
import com.joslittho.popmov.databinding.FragmentPostersBinding;
import com.joslittho.popmov.event.ConnectivityEvent;
import com.joslittho.popmov.event.FetchedMoviesEvent;
import com.joslittho.popmov.sync.MoviesSyncAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Vector;

import static com.joslittho.popmov.data.database.MoviesProvider.*;

/**
 * {@link Fragment} to show the movie posters.
 * */
// begin fragment PostersFragment
public class PostersFragment extends Fragment implements LoaderManager.LoaderCallbacks< Cursor > {

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

    /* Cursors */

    private Cursor mAllMoviesCursor; // ditto

    /* Fragment Posters Bindings */

    private FragmentPostersBinding binding; // ditto

    /* Poster Adapters */

    private PosterAdapter mPosterAdapter; // ditto
    
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

        // 0. super stuff

        super.onCreate( savedInstanceState );

        // 1. register for the menu

        setHasOptionsMenu( true );


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
        // 1. get the grid
        // 2. use the poster adapter
        // 2a. get a cursor pointing to the movies
        // 2a0. if the user wants favorites
        // 2a0a. get the favorites without sorting
        // 2a1. otherwise
        // 2a1a. sort the movies based on the user's preference
        // 2b. initialize the poster adapter with this cursor
        // 3. when an movie poster is clicked
        // 3a. go to details of the movie
        // 4. set the empty view for the grid
        // 4a. update the empty view
        // last. return the inflated view NOT THE INFLATED GRID VIEW SINCE
        // THE INFLATED GRID VIEW *WILL* HAVE A PARENT

        // 0. use the posters fragment layout

        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_posters, container, false );

        // 1. get the grid

        GridView postersGridView = binding.fpGvPosters;

        // 2. use the poster adapter

        // 2a. get a cursor pointing to the movies

        Context context = getActivity();

        String sortOrder = Utility.getPreferredSortOrder( context );

        // 2a0. if the user wants favorites

        // begin if it's favorites
        if ( sortOrder.equals( getString( R.string.pref_sort_order_favorites_entry_value ) ) ) {

            // 2a0a. get the favorites without sorting

            mAllMoviesCursor = context.getContentResolver().query(
                    FavoritesUriHolder.FAVORITES_URI,
                    FavoritesTableColumns.POSTERS_FRAGMENT_FAVORITES_COLUMNS, null, null, null );

        } // end if it's favorites

        // 2a1. otherwise

        // begin else it's not favorites
        else {

            // 2a1a. sort the movies based on the user's preference

            String sortOrderForDatabase = Utility.getSortOrderForDatabase( context );

            mAllMoviesCursor = context.getContentResolver().query(
                    MoviesUriHolder.MOVIES_URI,
                    MovieTableColumns.POSTERS_FRAGMENT_COLUMNS, null, null, sortOrderForDatabase );

        } // end else it's not favorites

        // 2b. initialize the poster adapter with this cursor

        mPosterAdapter = new PosterAdapter( context, mAllMoviesCursor, 0 );

        postersGridView.setAdapter( mPosterAdapter );

        // 3a. go to details of the movie

        // begin postersGridView.setOnItemClickListener
        postersGridView.setOnItemClickListener(

                // 3a. go to details of the movie

                // begin new AdapterView.OnItemClickListener
                new AdapterView.OnItemClickListener() {

                    @Override
                    // begin onItemClick
                    public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {

                        // 0. get the cursor at the given position
                        // 1. if there is a cursor there
                        // 1a. use a uri based on whether this is a regular or a favorite movie
                        // 1b. start the details activity using the gotten uri

                        // 0. get the cursor at the given position

                        Cursor cursor = ( Cursor ) parent.getItemAtPosition( position );

                        // 1. if there is a cursor there

                        // begin if there is a cursor
                        if ( cursor != null ) {

                            // 1a. use a uri based on whether this is a regular or a favorite movie

                            long movieId = cursor.getLong( MovieTableColumns.COLUMN_MOVIE_ID );

                            // by default use the regular movie uri
                            Uri uri = MoviesUriHolder.withMovieId( movieId );

                            // if we are doing favorites, edit the uri appropriately
                            if ( Utility.isSortOrderFavorites( getActivity() ) ) {
                                uri = FavoritesUriHolder.withFavoriteMovieId( movieId );
                            }

                            // 1b. start the details activity using the gotten uri

                            Intent detailsIntent = new Intent( getActivity(), DetailActivity.class )
                                    .setData( uri );

                            startActivity( detailsIntent );

                        } // end if there is a cursor

                    } // end onItemClick

                } // end new AdapterView.OnItemClickListener

        ); // end postersGridView.setOnItemClickListener

        // 4. set the empty view for the grid

        postersGridView.setEmptyView( binding.fpTvEmpty );

        // 4a. update the empty view

        updateEmptyView();

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
    // begin onStart
    public void onStart() {

        // 0. super stuff

        // 0. super stuff

        super.onStart();

    } // end onStart

    @Override
    // begin onPause
    public void onPause() {

        // 0. super stuff
        // 1. close the movies cursor

        // 0. super stuff

        super.onPause();

        // 1. close the movies cursor

        if ( mAllMoviesCursor != null ) { mAllMoviesCursor.close(); }

    }// end onPause

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

        TextView emptyTextView = binding.fpTvEmpty;

        // 1. if the posters adapter has nothing

        // begin if adapter count is zero
        if ( mPosterAdapter.getCount() == 0 ) {

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