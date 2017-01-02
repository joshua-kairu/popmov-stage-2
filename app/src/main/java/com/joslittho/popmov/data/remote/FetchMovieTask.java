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

package com.joslittho.popmov.data.remote;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.joslittho.popmov.BuildConfig;
import com.joslittho.popmov.adapter.PosterAdapter;
import com.joslittho.popmov.data.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link android.os.AsyncTask} to fetch the movies from TMDB.
 *
 * The parameters should include a string containing the user's preferred sort order.
 * This order should be either "popular" or "top_rated".
 *
 * Returns an array of movies gotten from TMDB.
 * */
// begin class FetchMovieTask
public class FetchMovieTask extends AsyncTask< String, Void, List< Movie > > {

    /* CONSTANTS */
    
    /* Integers */
    
    /* Strings */

    /**
     * The logger.
     */
    private static final String LOG_TAG = FetchMovieTask.class.getSimpleName();
        
    /* VARIABLES */

    /* Poster Adapters */

    /** Adapter that controls the posters in the host fragment. */
    private PosterAdapter mPosterAdapter;

    /* CONSTRUCTOR */

    /**
     * Default constructor
     *
     * @param posterAdapter The {@link PosterAdapter} to hold the fetched movies
     * */
    // begin constructor
    public FetchMovieTask( PosterAdapter posterAdapter ) {

        // 0. initialize members

        mPosterAdapter = posterAdapter;

    } // end constructor
    
    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */

    @Override
    // begin doInBackground
    protected List< Movie > doInBackground( String... params ) {

        // 0. if there is no sort order, just stop
        // 1. initialize
        // 1a. the http connection
        // 1b. the buffered reader
        // 1c. the string that will store the received JSON
        // 2. construct the url to fetch
        // 3. create a GET request to the url
        // 4. connect to the url
        // 5. buffer read from the url to a string
        // 5a. if there is nothing to read, stop
        // 6. sanitize the read string into a string builder for logging
        // 7. if the string builder is empty, stop
        // 8. return an array of the gotten movies
        // e0. io, log, stop
        // e1. json, log, stop
        // 9. finally
        // 9a. disconnect the url
        // 9b. close the buffered reader
        // e0. io, log

        // 0. if there is no sort order, just stop

        if ( params.length == 0 ) { return null; }

        // 1. initialize

        // 1a. the http connection

        HttpURLConnection httpURLConnection = null;

        // 1b. the buffered reader

        // Wraps an existing Reader and buffers the input. Expensive interaction with the
        // underlying reader is minimized, since most (smaller) requests can be satisfied by
        // accessing the buffer alone
        BufferedReader bufferedReader = null;

        // 1c. the string that will store the received JSON

        String movieJSONString;

        // begin try to finish network work
        try {

            // 2. construct the url to fetch

            // http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]

            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
            final String SORT_ORDER_PARAMETER = params[ 0 ];
            final String KEY_PARAMETER = "api_key";

            Uri builtUri = Uri.parse( MOVIE_BASE_URL ).buildUpon()
                    .appendEncodedPath( SORT_ORDER_PARAMETER )
                    .appendQueryParameter( KEY_PARAMETER, BuildConfig.THE_MOVIE_DB_API_KEY )
                    .build();

            URL movieUrl = new URL( builtUri.toString() );

            // 3. create a GET request to the url

            // openConnection - Returns a new connection to the resource referred to by this URL.
            httpURLConnection = ( HttpURLConnection ) movieUrl.openConnection();

            httpURLConnection.setRequestMethod( "GET" );

            // 4. connect to the url

            httpURLConnection.connect();

            // 5. buffer read from the url to a string

            InputStream inputStream = httpURLConnection.getInputStream();

            // 5a. if there is nothing to read, stop

            if ( inputStream == null ) {
                Log.e( LOG_TAG, "doInBackground: InputStream is null" );
                return null;
            }

            // InputStreamReader - Constructs a new InputStreamReader on the InputStream in.
            bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );

            // 6. sanitize the read string into a string builder for logging

            // StringBuilder - A modifiable sequence of characters for use in creating strings.
            //  This class is intended as a direct replacement of StringBuffer for
            //  non-concurrent use; unlike StringBuffer this class is not synchronized.
            StringBuilder stringBuilder = new StringBuilder();

            String lineRead;

            // while to read lines from the buffered reader
            // append a newline for logging
            while ( ( lineRead = bufferedReader.readLine() ) != null ) {
                stringBuilder.append( lineRead ).append( "\n" );
            }

            // 7. if the string builder is empty, stop

            if ( stringBuilder.length() == 0 ){
                Log.e( LOG_TAG, "doInBackground: stringBuilder is empty." );
            }

            // 8. return an array of the gotten movies

            movieJSONString = stringBuilder.toString();

            return getMovieDataFromJSON( movieJSONString );

        }  // end try to finish network work

        // e0. io, log, stop

        catch ( IOException e ) { Log.e( LOG_TAG, "doInBackground: ", e ); return null; }

        // e1. json, log, stop

        catch ( JSONException e ) { Log.e( LOG_TAG, "doInBackground: ", e ); return null; }

        // 9. finally

        // begin finally
        finally {

            // 9a. disconnect the url

            if ( httpURLConnection != null ) { httpURLConnection.disconnect(); }

            // 9b. close the buffered reader

            // begin if there is a buffered reader
            if ( bufferedReader != null ) {

                try {
                    bufferedReader.close();
                }

                // e0. io, log

                catch ( IOException e ) {
                    Log.e( LOG_TAG, "doInBackground: Error closing stream", e );
                }

            } // end if there is a buffered reader

        } // end finally

    } // end doInBackground

    @Override
    // begin onPostExecute
    protected void onPostExecute( List< Movie > fetchedMovies ) {

        // 0. super stuff
        // 1. remove old movies from adapter
        // 2. put fetched movies into adapter

        // 0. super stuff

        super.onPostExecute( fetchedMovies );

        // 1. remove old movies from adapter

        mPosterAdapter.clear();

        // 2. put fetched movies into adapter

        mPosterAdapter.addAll( fetchedMovies );

    } // end onPostExecute

    /* Other Methods */

    /**
     * Helper method to get movie data from a JSON string.
     *
     * @param movieJSONString The string having the movie JSON
     *
     * @return An {@link List} of {@link Movie} objects contained in the JSON
     * */
    // begin method getMovieDataFromJSON
    private List< Movie > getMovieDataFromJSON( String movieJSONString ) throws JSONException {

        /*

        TMDB JSON looks like this:

        {
           "page":1,
           "results":[
              {
                 "poster_path":"\/5N20rQURev5CNDcMjHVUZhpoCNC.jpg",
                 "adult":false,
                 "overview":"Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.",
                 "release_date":"2016-04-27",
                 "genre_ids":[
                    28,
                    53,
                    878
                 ],
                 "id":271110,
                 "original_title":"Captain America: Civil War",
                 "original_language":"en",
                 "title":"Captain America: Civil War",
                 "backdrop_path":"\/rqAHkvXldb9tHlnbQDwOzRi0yVD.jpg",
                 "popularity":49.388343,
                 "vote_count":2767,
                 "video":false,
                 "vote_average":6.93
              },

        */

        // 0. initialize the names of JSON objects to extract
        // 0a. movie list
        // 0b. poster path
        // 0c. overview
        // 0d. release date
        // 0e. id
        // 0f. title
        // 0g. vote average
        // 1. extract data from JSON
        // 1a. get the list of movies from the JSON
        // 1b. have an array list of movies matching the JSON list
        // 1b1. get a movie JSON item
        // 1b2. create a movie object from it
        // 1b3. add that object to the movie array
        // 2. return the gotten movies

        // 0. initialize the names of JSON objects to extract

        // 0a. movie list
        // 0b. poster path
        // 0c. overview
        // 0d. release date
        // 0e. id
        // 0f. title
        // 0g. vote average

        final String MOVIE_LIST = "results";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_ID = "id";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_VOTE_AVERAGE = "vote_average";

        // 1. extract data from JSON

        // 1a. get the list of movies from the JSON

        JSONObject movieJsonObject = new JSONObject( movieJSONString );

        JSONArray movieListJsonArray = movieJsonObject.getJSONArray( MOVIE_LIST );

        // 1b. have an array list of movies matching the JSON list

        List< Movie > movies = new ArrayList< Movie >( movieListJsonArray.length() );

        // 1b. get the list of movies from the JSON

        // begin for to initialize a movie from the JSON array
        for ( int i = 0; i < movieListJsonArray.length(); i++ ) {

            // 1b1. get a movie JSON item

            JSONObject currentMovieJsonObject = movieListJsonArray.getJSONObject( i );

            // 1b2. create a movie object from it

            // the order is:
            // 0b. poster path
            // 0c. overview
            // 0d. release date
            // 0e. id
            // 0f. title
            // 0g. vote average

            String moviePosterPath = currentMovieJsonObject.getString( MOVIE_POSTER_PATH );

            String movieSynopsis = currentMovieJsonObject.getString( MOVIE_OVERVIEW );

            String movieReleaseDate = currentMovieJsonObject.getString( MOVIE_RELEASE_DATE );

            long movieId = currentMovieJsonObject.getLong( MOVIE_ID );

            String movieTitle = currentMovieJsonObject.getString( MOVIE_TITLE );

            double movieUserRating = currentMovieJsonObject.getDouble( MOVIE_VOTE_AVERAGE );

            Movie aMovie = new Movie( movieId, movieTitle, movieReleaseDate, movieSynopsis,
                    movieUserRating, moviePosterPath );

            // 1b3. add that object to the movie array

            movies.add( aMovie );

        } // end for to initialize a movie from the JSON array

        // 2. return the gotten movies

        return movies;

    } // end method getMovieDataFromJSON

    /* INNER CLASSES */

} // end class FetchMovieTask