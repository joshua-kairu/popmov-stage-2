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

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.joslittho.popmov.R;
import com.joslittho.popmov.adapter.trailer.TrailerAdapter;
import com.joslittho.popmov.adapter.trailer.TrailerAdapterOnClickHandler;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.data.database.FavoritesTableColumns;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.data.database.MoviesProvider.FavoritesUriHolder;
import com.joslittho.popmov.data.model.trailers.Result;
import com.joslittho.popmov.databinding.FragmentDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.joslittho.popmov.data.database.MovieTableColumns.*;

/**
 * {@link Fragment} to show the details of a selected movie
 * */
// begin fragment DetailFragment
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks< Cursor >,
        TrailerAdapterOnClickHandler {
    
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

    /* Lists */

    // a list of individual trailers. Useful when a trailer is tapped
    private List< Result > mTrailersList;

    /* Menus */

    // ditto, needed when we need to change the contents of the share action provider
    private Menu mOptionsMenu;

    /* ShareActionProviders */

    private ShareActionProvider mTrailerShareActionProvider; // ditto

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
    // begin onCreateOptionsMenu
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

        // 0. super stuff
        // 1. inflate the correct menu for this fragment
        // 2. finish setting up the menu (by adding the share action provider)
        // 3. store a reference to the menu

        // 0. super stuff

        super.onCreateOptionsMenu( menu, inflater );

        // 1. inflate the correct menu for this fragment

        inflater.inflate( R.menu.menu_fragment_detail, menu );

        // 2. finish setting up the menu (by adding the share action provider)

        updateShareActionProvider( menu );

        // 3. store a reference to the menu

        mOptionsMenu = menu;

    } // end onCreateOptionsMenu

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
        // 0b1. store the trailers list since we'll use it to handle taps
        // 0b2. instantiate the trailers adapter with the gotten trailers
        // 0b3. the recycler
        // 0b3a. find reference to it
        // 0b3b. use a linear layout manager
        // 0b3c. has fixed size
        // 0b3d. use the trailers adapter
        // 0b4. update the share action provider with the latest trailer

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
            // 0b1. store the trailers list since we'll use it to handle taps

            List< Result > trailersList =
                    Utility.getTrailersFromDB( cursor.getString( COLUMN_DETAIL_TRAILERS ) );

            mTrailersList = trailersList;

            // 0b2. instantiate the trailers adapter with the gotten trailers

            TrailerAdapter trailerAdapter = new TrailerAdapter( getActivity(), trailersList, this );

            // 0b3. the recycler

            // 0b3a. find reference to it

            RecyclerView trailerRecyclerView = mBinding.detailRvTrailers;

            // 0b3b. use a linear layout manager

            trailerRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

            // 0b3c. has fixed size

            trailerRecyclerView.setHasFixedSize( true );

            // 0b3d. use the trailers adapter

            trailerRecyclerView.setAdapter( trailerAdapter );

            // 0b4. update the share action provider with the latest trailer

            if ( mOptionsMenu != null ) {
                updateShareActionProvider( mOptionsMenu );
            }

        } // end if there is a cursor and it has something
        
    } // end onLoadFinished

    @Override
    // nothing here
    public void onLoaderReset( Loader< Cursor > loader ) { }

    @Override
    // begin onClick
    public void onClick( int trailerPosition ) {

        // when a trailer is selected, allow users to view and play trailers
        // ( either in the youtube app or a web browser).

        // 0. get the trailer at the position
        // 1. get the trailer's YouTube url based on its key
        // 2. put the url in an intent
        // 3. if YouTube is installed
        // 3a. launch YouTube with the intent
        // 4. else no YouTube
        // 4a. offer the user a choice of how they'd want to view the trailer

        // 0. get the trailer at the position

        Result trailer = mTrailersList.get( trailerPosition );

        // 1. get the trailer's YouTube url based on its key

        String trailerUrl = Utility.getYouTubeUrlFromKey( getActivity(), trailer.getKey() );

        // 2. put the url in an intent

        String youTubePackageName = "com.google.android.youtube";
        String youTubePlayerActivityName = "com.google.android.youtube.PlayerActivity";

        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( trailerUrl ) )
                .setComponent( new ComponentName( youTubePackageName, youTubePlayerActivityName ) );

        PackageManager packageManager = getActivity().getPackageManager();

        // queryIntentActivities - Retrieve all activities that can be performed for the given
        //  intent.
        // parameters for queryIntentActivities are the intent itself and a flag
        // queryIntentActivities returns a List of ResolveInfo objects containing one entry for
        //  each matching activity, ordered from best to worst.
        // ResolveInfo - Information that is returned from resolving an intent against an
        //  IntentFilter.
        List< ResolveInfo > resolveInfos = packageManager.queryIntentActivities( intent, 0 );

        // 3. if YouTube is installed
        // 3a. launch YouTube with the intent

        // if YouTube is resolved, then the resolveInfos will having something in it
        if ( resolveInfos.size() > 0 ) {
            startActivity( intent );
        }

        // 4. else no YouTube

        // begin else no YouTube
        else {

            // 4a. offer the user a choice of how they'd want to view the trailer

            intent = new Intent( Intent.ACTION_VIEW, Uri.parse( trailerUrl ) );

            startActivity( intent );

        } // end else no YouTube

        // TODO: 3/31/17 test on devices not having YouTube. I cannot uninstall YouTube in mine

    } // end onClick

    /* Other Methods */


    /**
     * Helper method to set up the {@link android.support.v7.widget.ShareActionProvider} that will
     * let users share the url to the first trailer
     *
     * @param menu The menu
     * */
    // begin method updateShareActionProvider
    private void updateShareActionProvider( Menu menu ) {

        // 0. get the share menu item
        // 1. set the share intent

        // 0. get the share menu item

        MenuItem menuItem = menu.findItem( R.id.action_share );

        // 1. set the share intent

        mTrailerShareActionProvider =
                ( ShareActionProvider ) MenuItemCompat.getActionProvider( menuItem );

        mTrailerShareActionProvider.setShareIntent( setTrailerShareIntent() );

    } // end method updateShareActionProvider

    /**
     * Sets the {@link Intent} that will be used to share the url of the first trailer
     *
     * @return The share {@link Intent}
     * */
    // begin method setTrailerShareIntent
    private Intent setTrailerShareIntent() {

        // 0. create the intent for sharing
        // 0a. should hold text
        // 0b. should have the trailer in
        // 0c. called activity should clear when we leave our app
        // 1. return created intent

        // 0. create the intent for sharing

        Intent shareWeatherDetailIntent = new Intent( Intent.ACTION_SEND );

        // 0a. should hold text

        shareWeatherDetailIntent.setType( "text/plain" );

        // 0b. should have the trailer in

        String shareMessage = Utility.getShareMessage( getActivity(),
                mBinding.detailTvTitle.getText().toString(), mTrailersList.get( 0 ).getKey() );

        shareWeatherDetailIntent.putExtra( Intent.EXTRA_TEXT, shareMessage );

        // 0c. called activity should clear when we leave our app

        int necessaryFlag;

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            necessaryFlag = Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else {
            necessaryFlag = Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }

        shareWeatherDetailIntent.addFlags( necessaryFlag );

        // 1. return created intent

        return shareWeatherDetailIntent;

    } // end method setTrailerShareIntent
    /* statics */

    /* INNER CLASSES */

} // end fragment DetailFragment