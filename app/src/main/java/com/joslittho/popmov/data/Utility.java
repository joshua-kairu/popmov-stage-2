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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joslittho.popmov.R;
import com.joslittho.popmov.data.database.FavoritesTableColumns;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.data.database.MoviesProvider;
import com.joslittho.popmov.data.model.trailers.Result;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
     * Helper method to determine if a given movie is a favorite.
     *
     * @param favoriteMovieId The favorite movie's unique id
     * @param contentResolver The {@link ContentResolver} which will be used to query the db for
     *                        the movie
     *
     * @return If the movie is a favorite
     * */
    // begin method isMovieAFavorite
    public static boolean isMovieAFavorite ( long favoriteMovieId,
                                             ContentResolver contentResolver ) {

        // 0. query the favorites db for a movie with the given movie id
        // 1. determine if there is such a movie in the favorites table
        // 2. close the cursor
        // 3. return the determined cursor state

        // 0. query the favorites db for a movie with the given movie id

        Cursor favoriteMovieCursor = contentResolver.query(
                MoviesProvider.FavoritesUriHolder.withFavoriteMovieId( favoriteMovieId ),
                FavoritesTableColumns.DETAIL_FRAGMENT_FAVORITE_COLUMNS, null, null, null );

        // 1. determine if there is such a movie in the favorites table

        boolean movieIsFav = ( favoriteMovieCursor!= null && favoriteMovieCursor.moveToFirst() );

        // 2. close the cursor

        if ( favoriteMovieCursor != null ) { favoriteMovieCursor.close(); }

        // 3. return the determined cursor state

        return movieIsFav;

    } // end method isMovieAFavorite

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

    /**
     * Helper method to extract trailers from the JSON stored in the db.
     *
     * @param trailersJSONString The JSON string - gotten from the db - that contains the trailers
     *
     * @return A {@link List} of trailers as individual {@link Result} objects
     * */
    // begin method getTrailersFromDB
    public static List< Result > getTrailersFromDB ( String trailersJSONString ) {

        // 0. gson convert and return the trailers

        Type type = new TypeToken< List< Result > >(){}.getType();

        return new Gson().fromJson( trailersJSONString, type );

    } // end method getTrailersFromDB

    /**
     * Helper method to generate a YouTube url from a trailer's key.
     *
     * For example, if the key is NQu-153MnGQ, the generated url should be:
     * https://www.youtube.com/watch?v=NQu-153MnGQ
     *
     * @param context Android context
     * @param key The trailer's YouTube key
     *
     * @return A string containing the YouTube url to the trailer
     * */
    // begin method getYouTubeUrlFromKey
    public static String getYouTubeUrlFromKey( Context context, String key ) {

        // 0. return the YouTube url

        // 0. return the YouTube url

        return context.getString( R.string.youtube_url_format, key );

    } // end method getYouTubeUrlFromKey

    /**
     * Helper method to generate a message for sharing out a movie's trailer url to another app.
     *
     * The message should read: Here is <movie name>'s trailer url: <trailer url>
     *
     * For example, for the movie Finding Dory and the trailer key NQu-153MnGQ, the sharing message
     * should be: Here is Finding Dory's trailer url: https://www.youtube.com/watch?v=NQu-153MnGQ
     *
     * @param context Android context
     * @param movieName The movie's name
     * @param key The trailer's YouTube key
     *
     * @return A string containing the share message
     * */
    // begin method getShareMessage
    public static String getShareMessage( Context context, String movieName, String key ) {

        // 0. get the YouTube url
        // 1. return the share message containing the url

        // 0. get the YouTube url

        String url = getYouTubeUrlFromKey( context, key );

        // 1. return the share message containing the url

        return context.getString( R.string.trailer_share_message_format, movieName, url );

    } // end method getShareMessage

    /* INNER CLASSES */

} // end class Utility