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

package com.joslittho.popmov.data;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.joslittho.popmov.R;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.data.database.MoviesProvider;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.joslittho.popmov.data.database.FavoritesTableColumns.*;

/**
 * A utility class to handle preferences and formatting
 * */
// begin class Utility
public class Utility {

    /* CONSTANTS */
    
    /* Integers */
    
    /* Strings */

    /**
     * The logger.
     */
    private static final String LOG_TAG = Utility.class.getSimpleName();

    /* VARIABLES */
    
    /* CONSTRUCTOR */
    
    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */
    
    /* Other Methods */

    /* statics */

    /**
     * Helper method to get a movie's poster {@link android.net.Uri} using the movie's poster path
     *
     * @param posterPath The movie's poster path
     *
     * @return a {@link android.net.Uri} pointing to the movie's poster path
     * */
    // begin method getPosterUri
    public static Uri getPosterUri( String posterPath ) {

        // 0. initialize build items
        // 0a. the base url
        // 0b. the size path
        // 1. return a built Uri

        // 0. initialize build items

        // 0a. the base url

        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";

        // 0b. the size path

        final String SIZE_PATH = "w185";

        // 1. return a built Uri

        return Uri.parse( POSTER_BASE_URL ).buildUpon()
                .appendPath( SIZE_PATH )
                .appendEncodedPath( posterPath )
                .build();

    } // end method getPosterUri

    /**
     * Helper method to get the preferred sort order from preferences.
     *
     * @param context Android {@link android.content.Context}
     *
     * @return A string having the preferred sort order
     * */
    // begin method getPreferredSortOrder
    public static String getPreferredSortOrder( Context context ) {

        // 0. get the preferences
        // 1. return the preferred sort order, default popular

        // 0. get the preferences

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences( context );

        // 1. return the preferred sort order, default popular

        return sharedPreferences.getString( context.getString( R.string.pref_sort_order_key ),
                context.getString( R.string.pref_sort_order_most_popular_entry_value ) );

    } // end method getPreferredSortOrder


    /**
     * Helper method to determine the sort order for database querying.
     *
     * This sort order is based on the user's preferred sort order and sorts items from highest to
     * lowest, that is, in descending order.
     * */
    // begin method getSortOrderForDatabase
    public static String getSortOrderForDatabase( Context context ) {

        // 0. get the preferred sort order
        // 1. return an SQL sort statement based on the preferred sort order
        // 1a. if the preferred is highest rated, return a vote-average-descending SQL sort
        // 1b. else return a popularity-descending SQL sort

        // 0. get the preferred sort order

        String preferredSortOrder = getPreferredSortOrder( context );

        // 1. return an SQL sort statement based on the preferred sort order

        // 1a. if the preferred is highest rated, return a vote-average-descending SQL sort

        if ( preferredSortOrder.equals( context.getString(
                R.string.pref_sort_order_highest_rated_entry_value ) ) ) {
            return MovieTableColumns.VOTE_AVERAGE + " DESC";
        }

        // 1b. else return a popularity-descending SQL sort

        else {
            return MovieTableColumns.POPULARITY + " DESC";
        }

    } // end method getSortOrderForDatabase

    /**
     * Helper method to format the movie release date so that it appears as required by the
     * screenshots.
     *
     * TMDB JSON gives the movie release date in the form YYYY-MM-DD, for example 2016-06-16.
     * The screenshots need the date to be in the form YYYY, for example 2016.
     *
     * */
    // begin method getFormattedReleaseDate
    public static String getFormattedReleaseDate ( String releaseDate ) {

        // 0. convert the release date to a date object
        // 1. set up a date format with the needed format
        // 2. return a formatted date string

        // begin trying to get the formatted date string
        try {

            // 0. convert the release date to a date object

            Date actualReleaseDate = new SimpleDateFormat( "yyyy-MM-DD", Locale.getDefault() )
                    .parse( releaseDate );

            // 1. set up a date format with the needed format
            // 2. return a formatted date string

            return new SimpleDateFormat( "yyyy", Locale.getDefault() ).format( actualReleaseDate );

        } // end trying to get the formatted date string

        // begin catching parse issues
        catch ( ParseException e ) {

            Log.e( LOG_TAG, "getFormattedReleaseDate: Error parsing date.", e );

            return null;

        } // end catching parse issues

    } // end method getFormattedReleaseDate

    /**
     * Helper method to determine if the Internet is up.
     *
     * It does this by checking the result of a ping.
     *
     * Thanks to http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out/27312494#27312494
     *
     * @return A boolean depending on the success of the ping.
     * */
    // begin method isInternetUp
    public static boolean isInternetUp() {

        // 0. get a runtime
        // 1. start a ping
        // 2. there is net if the ping returns 0
        // last. exceptions and other things mean no net

        // 0. get a runtime

        Runtime runtime = Runtime.getRuntime();

        // begin trying to ping
        try {

            // 1. start a ping

            Process pingProcess = runtime.exec( "/system/bin/ping -c 1 8.8.8.8" );

            // waitFor - Causes the calling thread to wait for the native process
            //  associated with this object to finish executing.
            int exitValue = pingProcess.waitFor();

            // 2. there is net if the ping returns 0

            return ( exitValue == 0 );

        } // end trying to ping

        catch ( InterruptedException | IOException e ) { e.printStackTrace(); }

        // last. exceptions and other things mean no net

        return false;

    } // end method isInternetUp

    /**
     * Helper method to format the movie user rating.
     *
     * Movie rating comes in the form 5.92. We assume that the user needs only tenths of precision
     * so we should display this as 5.9/10.
     *
     * @param context The {@link Context} we're working in
     * @param userRating The user rating as a double
     *
     * @return A string containing the formatted rating.
     * */
    // begin method getFormattedUserRating
    public static String getFormattedUserRating( Context context, double userRating ) {

        // 0. format the user rating

        // 0. format the user rating
        return context.getString( R.string.user_rating_format, userRating );

    } // end method getFormattedUserRating

    /**
     * Helper method to determine the integer value to put in the db as a representation of a
     * movie's favorite status.
     *
     * @param movieFavorite Boolean for if the movie is a favorite
     *
     * @return 0 or 1 based on the favorite status
     * */
    // begin method getFavoriteForDatabase
    public static int getFavoriteForDatabase( boolean movieFavorite ) {

        // 0. return the 0 or 1 based on the favorite

        // 0. return the 0 or 1 based on the favorite
        return movieFavorite ? FAVORITE_TRUE : FAVORITE_FALSE;

    } // end method getFavoriteForDatabase

    /**
     * Helper method to get the favorite status of a movie from the database. This status will be
     * used in the details screen.
     *
     * @param cursor The {@link Cursor} pointing to a movie record in the db
     *
     * @return If the movie is a favorite
     * */
    // begin method getFavoriteForDetailFromDatabase
    public static boolean getFavoriteForDetailFromDatabase( Cursor cursor ) {

        // 0. if the cursor has the favorite column
        // 0a. return a boolean of the favorite column
        // 1. otherwise the cursor has not the favorite column
        // 1a. return false

        // 0. if the cursor has the favorite column
        // 0a. return a boolean of the favorite column
        // 1. otherwise the cursor has not the favorite column
        // 1a. return false

        return cursor.getColumnIndex( IS_FAVORITE ) > -1 &&
                cursor.getInt( COLUMN_DETAIL_IS_FAVORITE ) == FAVORITE_TRUE;

    } // end method getFavoriteForDetailFromDatabase

    /**
     * Helper method to determine if there already is a movie with the given movie id
     * in the movie db.
     *
     * @param movieId The movie's unique id
     * @param contentResolver The {@link ContentResolver} which will be used to query the db for
     *                        the movie
     *
     * @return If the movie is in the db
     * */
    // begin method isMovieInDatabase
    public static boolean isMovieInDatabase ( long movieId, ContentResolver contentResolver ) {

        // 0. query the db for a movie with the given movie id
        // 1. determine if there is such a movie in the db
        // 2. close the cursor
        // 3. return the determined cursor state

        // 0. query the db for a movie with the given movie id

        Cursor movieCursor = contentResolver.query(
                MoviesProvider.MoviesUriHolder.withMovieId( movieId ), null, null, null, null );

        // 1. determine if there is such a movie in the db

        boolean movieInDb = ( movieCursor!= null && movieCursor.moveToFirst() );

        // 2. close the cursor

        if ( movieCursor != null ) { movieCursor.close(); }

        // 3. return the determined cursor state

        return movieInDb;

    } // end method isMovieInDatabase

    /**
     * Helper method to know if the user is currently interested in favorites only.
     *
     * @param context Android context
     *
     * @return If the user has preferred favorites at the moment
     */
    // begin method isSortOrderFavorites
    public static boolean isSortOrderFavorites( Context context ) {

        // 0. return if the sort order is favorites now

        // 0. return if the sort order is favorites now

        return getPreferredSortOrder( context ).equals( context.getString(
                R.string.pref_sort_order_favorites_entry_value ) );

    } // end method isSortOrderFavorites

    /* INNER CLASSES */

} // end class Utility