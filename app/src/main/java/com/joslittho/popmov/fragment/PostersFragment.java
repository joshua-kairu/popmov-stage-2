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
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.data.database.MoviesProvider;
import com.joslittho.popmov.data.model.Movie;
import com.joslittho.popmov.data.remote.CheckConnectivityAsyncTask;
import com.joslittho.popmov.data.remote.FetchMovieTask;
import com.joslittho.popmov.databinding.FragmentPostersBinding;
import com.joslittho.popmov.event.ConnectivityEvent;
import com.joslittho.popmov.event.FetchedMoviesEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Vector;

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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        // 0. use the posters fragment layout
        // 1. get the grid
        // 2. use the poster adapter
        // 2a. get a cursor pointing to all the movies sorted by the user's preference
        // 2b. initialize the poster adapter with this cursor
        // 3. when an movie poster is clicked
        // 3a. go to details of the movie
        // 4. set the empty view for the grid
        // last. return the inflated view NOT THE INFLATED GRID VIEW SINCE
        // THE INFLATED GRID VIEW *WILL* HAVE A PARENT

        // 0. use the posters fragment layout

        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_posters, container, false );

        // 1. get the grid

        GridView postersGridView = binding.fpGvPosters;

        // 2. use the poster adapter

        // 2a. get a cursor pointing to all the movies sorted by the user's preference

        Context context = getActivity();

        String sortOrder = Utility.getSortOrderForDatabase( context );

        mAllMoviesCursor = context.getContentResolver().query(
                MoviesProvider.MoviesUriHolder.MOVIES_URI, MovieTableColumns.POSTERS_FRAGMENT_COLUMNS,
                null, null, sortOrder );

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
                        // 1a. start the details activity using the uri from the cursor at this position

                        // 0. get the cursor at the given position

                        Cursor cursor = ( Cursor ) parent.getItemAtPosition( position );

                        // 1. if there is a cursor there

                        // begin if there is a cursor
                        if ( cursor != null ) {

                            // 1a. start the details activity using the uri from the cursor at this position

                            long movieId = cursor.getLong( MovieTableColumns.COLUMN_MOVIE_ID );

                            Intent detailsIntent = new Intent( getActivity(), DetailActivity.class )
                                    .setData( MoviesProvider.MoviesUriHolder.withMovieId( movieId ) );

                            startActivity( detailsIntent );

                        } // end if there is a cursor

                    } // end onItemClick

                } // end new AdapterView.OnItemClickListener

        ); // end postersGridView.setOnItemClickListener

        // 4. set the empty view for the grid

        postersGridView.setEmptyView( binding.fpTvEmpty );

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
        // 1. register for the event bus
        // 2. check for connectivity

        // 0. super stuff

        super.onStart();

        // 1. register for the event bus

        EventBus.getDefault().register( this );

        // 2. check for connectivity

        new CheckConnectivityAsyncTask().execute();

    } // end onStart

    @Override
    // begin onPause
    public void onPause() {

        // 0. super stuff
        // 1. unregister from the event bus
        // 2. close the movies cursor

        // 0. super stuff

        super.onPause();

        // 1. unregister from the event bus

        EventBus.getDefault().unregister( this );

        // 2. close the movies cursor

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

        // 0. load movie data from db using a cursor loader

        Context context = getActivity();

        String sortOrder = Utility.getSortOrderForDatabase( context );

        Uri moviesUri = MoviesProvider.MoviesUriHolder.MOVIES_URI;

        return new CursorLoader( context, moviesUri, MovieTableColumns.POSTERS_FRAGMENT_COLUMNS, null, null,
                sortOrder );

    } // end onCreateLoader

    @Override
    // begin onLoadFinished
    public void onLoadFinished( Loader< Cursor > cursorLoader, Cursor newCursor ) {

        // 0. hide the empty view
        // 1. refresh the grid view

        // 0. hide the empty view

        binding.fpTvEmpty.setVisibility( View.INVISIBLE );

        // 1. refresh the grid view

        mPosterAdapter.swapCursor( newCursor );

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

        // 0. get the preferred sort order
        // 1. fetch movie info using the preferred sort order

        // 0. get the preferred sort order

        String preferredSortOrder = Utility.getPreferredSortOrder( getActivity() );

        // 1. fetch movie info using the preferred sort order

        FetchMovieTask fetchMovieTask = new FetchMovieTask( mPosterAdapter );

        fetchMovieTask.execute( preferredSortOrder );

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

        // 0. if the movie adapter has nothing
        // 0a. get the empty view
        // 0a0. if there is the empty view
        // 0a0a. tell the user why the list is empty - possibly because
        // 0a0a0. there is no internet

        // 0. if the movie adapter has nothing

        // begin if adapter count is zero
        if ( mPosterAdapter.getCount() == 0 ) {

            // 0a. get the empty view

            TextView emptyTextView = binding.fpTvEmpty;

            // 0a0. if there is the empty view

            // begin if there is an empty view
            if ( emptyTextView != null ) {

                // 0a0a. tell the user why the list is empty - possibly because

                int message = R.string.message_error_no_movie_info;

                // 0a0a0. there is no internet

                if ( ! Utility.isInternetUp() ) { message = R.string.message_error_no_movie_info_no_connectivity; }

                emptyTextView.setText( message );

            } // end if there is an empty view

        } // end if adapter count is zero

    } // end method updateEmptyView

    /**
     * Responds to receiving a {@link com.joslittho.popmov.event.ConnectivityEvent}.
     *
     * @param connectivityEvent The received {@link ConnectivityEvent}
     * */
    @Subscribe( threadMode = ThreadMode.MAIN )
    // begin method onConnectivityEvent
    public void onConnectivityEvent( ConnectivityEvent connectivityEvent ) {

        // 0. if we're connected to net,
        // 0a. inform user we are fetching the movies
        // 0b. update the movies
        // 1. if not connected
        // 1a. inform user

        // 0. if we're connected to net,

        // begin if connected
        if ( connectivityEvent.isConnected() ) {

            // 0a. inform user we are fetching the movies

            binding.fpTvEmpty.setText( R.string.message_info_fetching_movies );

            // 0b. update the movies

            updateMovies();

        } // end if connected

        // 1. if not connected

        // 1a. inform user

        else {
            binding.fpTvEmpty.setText( R.string.message_error_no_movie_info_no_connectivity );
        }

    } // end method onConnectivityEvent

    /**
     * Receives a {@link com.joslittho.popmov.event.FetchedMoviesEvent}
     *
     * @param fetchedMoviesEvent The received {@link FetchedMoviesEvent}
     * */
    @Subscribe( threadMode = ThreadMode.MAIN )
    // begin method onFetchedMoviesEvent
    public void onFetchedMoviesEvent( FetchedMoviesEvent fetchedMoviesEvent ) {

        // 0. initialize the ContentValues vector where we will put the movie data
        // 1. for each fetched movie
        // 1a. put it in a ContentValues
        // 1b. put the ContentValues in the vector made earlier
        // 2. if the vector has something,
        // 2a. bulk insert to add the weather entries in the vector to the db
        // 3. else,
        // 3a. log

        // 0. initialize the ContentValues vector where we will put the movie data

        List< Movie > fetchedMovies = fetchedMoviesEvent.getFetchedMovies();

        Vector< ContentValues > moviesVector = new Vector<>( fetchedMovies.size() );

        // 1. for each fetched movie

        // begin for through each fetched movie
        for ( Movie movie : fetchedMovies ) {

            // 1a. put it in a ContentValues

            ContentValues movieContentValues = new ContentValues();

            movieContentValues.put( MovieTableColumns.MOVIE_ID, movie.getID() );
            movieContentValues.put( MovieTableColumns.POSTER_PATH, movie.getPosterPath() );
            movieContentValues.put( MovieTableColumns.OVERVIEW, movie.getSynopsis() );
            movieContentValues.put( MovieTableColumns.RELEASE_DATE, movie.getReleaseDate() );
            movieContentValues.put( MovieTableColumns.TITLE, movie.getTitle() );
            movieContentValues.put( MovieTableColumns.VOTE_AVERAGE, movie.getUserRating() );
            movieContentValues.put( MovieTableColumns.POPULARITY, movie.getPopularity() );

            // 1b. put the ContentValues in the vector made earlier

            moviesVector.add( movieContentValues );

        } // end for through each fetched movie

        // 2. if the vector has something,
        // 2a. bulk insert to add the weather entries in the vector to the db

        int numberOfInserts = 0;

        // begin if the movies vector has something
        if ( !moviesVector.isEmpty() ) {

            ContentValues movieContentValuesArray[] = new ContentValues[ moviesVector.size() ];

            // stores the vector contents in the content values array
            moviesVector.toArray( movieContentValuesArray );

            Context context = getActivity();

            numberOfInserts = context.getContentResolver().bulkInsert(
                    MoviesProvider.MoviesUriHolder.MOVIES_URI, movieContentValuesArray );

        } // end if the movies vector has something

        Log.e( LOG_TAG, "onFetchedMoviesEvent: number of movies inserted: " + numberOfInserts );

    } // end method onFetchedMoviesEvent

    /* INNER CLASSES */

} // end fragment PostersFragment