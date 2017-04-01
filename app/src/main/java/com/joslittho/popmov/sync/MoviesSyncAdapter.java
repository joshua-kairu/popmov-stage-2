package com.joslittho.popmov.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.joslittho.popmov.BuildConfig;
import com.joslittho.popmov.R;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.data.database.MovieTableColumns;
import com.joslittho.popmov.data.database.MoviesProvider;
import com.joslittho.popmov.data.model.Movie;
import com.joslittho.popmov.data.model.trailers.Result;
import com.joslittho.popmov.data.model.trailers.TrailersResponse;
import com.joslittho.popmov.rest.ApiClient;
import com.joslittho.popmov.rest.ApiInterface;
import com.tbruyelle.rxpermissions.RxPermissions;

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
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

import static android.Manifest.permission.GET_ACCOUNTS;

/**
 * The sync adapter we will use for fetching movie data.
 */
// begin class MoviesSyncAdapter
public class MoviesSyncAdapter extends AbstractThreadedSyncAdapter {

    /* CONSTANTS */
    
    /* Integers */

    /** Interval in seconds at which to sync with the weather. */
    private static final int SYNC_INTERVAL = 60 * 180;

    /** Flex time for weather sync. */
    private static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    /* Strings */

    /** The logger. */
    private static final String LOG_TAG = MoviesSyncAdapter.class.getSimpleName();

    /* VARIABLES */

    /*
     A Context member (such as mContext) is not defined since in MoviesSyncService we declare a
     static MoviesSyncAdapter. Declaring context members in static variables not only has a chance
     of creating memory leaks but also breaks Instant Run.
     */

    /* CONSTRUCTOR */

    /**
     * Creates an {@link AbstractThreadedSyncAdapter}.
     *
     * @param context        the {@link Context} that this is running within.
     * @param autoInitialize if true then sync requests that have
     *                       {@link ContentResolver#SYNC_EXTRAS_INITIALIZE} set will be internally
     *                       handled by {@link AbstractThreadedSyncAdapter} by calling
     *                       {@link ContentResolver#setIsSyncable(Account, String, int)} with 1 if
     *                       it is currently set to 0.
     */
    // begin constructor
    public MoviesSyncAdapter( Context context, boolean autoInitialize ) {

        // 0. super stuff

        // 0. super stuff

        super( context, autoInitialize );

    } // end constructor
    
    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */

    @Override
    // begin onPerformSync
    public void onPerformSync( Account account, Bundle bundle, String s,
                               ContentProviderClient contentProviderClient, SyncResult syncResult ) {

        Log.e( LOG_TAG, "onPerformSync: called" );

        // 0. fetch movies

        // 0. fetch movies

        fetchMovies();

        Log.e( LOG_TAG, "onPerformSync: finished" );

    } // end onPerformSync

    /* Other Methods */

    /* statics */

    /**
     * Helper method to have the sync adapter sync immediately.
     *
     * @param activity The {@link Activity} used to access the account service
     * */
    // begin method syncImmediately
    public static void syncImmediately( Activity activity ) {
        Log.d( LOG_TAG, "syncImmediately() called with: activity = [" + activity + "]" );
        // 0. create a bundle
        // 1. put an argument to sync asap
        // 2. put an argument to sync manually
        // 3. start sync

        // 0. create a bundle

        Bundle bundle = new Bundle();

        // 1. put an argument to sync asap

        bundle.putBoolean( ContentResolver.SYNC_EXTRAS_EXPEDITED, true );

        // 2. put an argument to sync manually

        bundle.putBoolean( ContentResolver.SYNC_EXTRAS_MANUAL, true );

        // 3. start sync

        // requestSync - Start an asynchronous sync operation using the movies provider's content
        // authority.
        ContentResolver.requestSync( getSyncAccount( activity ),
                activity.getString( R.string.movies_provider_content_authority ), bundle );
        Log.e( LOG_TAG, "syncImmediately: sync requested" );

    } // end method syncImmediately

    /**
     * Helper method to initialize the sync adapter.
     *
     * @param activity The {@link Activity} we're working in
     * */
    // method initializeSyncAdapter
    public static void initializeSyncAdapter( Activity activity ) {
        Log.d( LOG_TAG, "initializeSyncAdapter() called with: activity = [" + activity + "]" );getSyncAccount( activity ); }

    /**
     * Helper method to get the fake account to be used with the sync adapter, or
     * make one if the fake account doesn't currently exist.
     *
     * If we make a new account, we call onAccountCreated so we can initialize things.
     *
     * @param activity The {@link Activity} used to access the account service.
     *
     * @return A fake account.
     * */
    // begin method getSyncAccount
    public static Account getSyncAccount( final Activity activity ) {
        Log.d( LOG_TAG, "getSyncAccount() called with: activity = [" + activity + "]" );
        // 0. get
        // 0a. the accounts manager
        // 0b. a rx permissions instance
        // 1. create the account type and default account
        // 2. if the account doesn't exist
        // 2a. attempt to add it
        // 2a1. if successful
        // 2a1a. notify the sync adapter that a new account has been created
        // 2a1b. last we will return the account created at 1
        // 2a2. otherwise
        // 2a2a. last we will return a null account
        // 3. otherwise the account exists
        // 3a. last we will return the existing account
        // last. return the account created at 1

        // 0. get

        // 0a. the accounts manager

        final AccountManager accountManager = ( AccountManager ) activity.getSystemService(
                Context.ACCOUNT_SERVICE );

        // 0b. a rx permissions instance

        RxPermissions rxPermissions = new RxPermissions( activity );

        // 1. create the account type and default account

        // use a one-element array so that we can assign values to the account even if it is final
        final Account[] newAccount = { new Account( activity.getString( R.string.app_name ),
                activity.getString( R.string.sync_account_type ) ) };

        rxPermissions
                .request( GET_ACCOUNTS )
                // begin subscribe
                .subscribe(

                        // begin new Action1
                        new Action1< Boolean >() {

                            @Override
                            // begin call
                            public void call( Boolean granted ) {

                                // begin if the get accounts permission was granted
                                if ( granted ) {

                                    // 2. if the account doesn't exist

                                    // if the account doesn't have a password, the account doesn't
                                    // exist

                                    // begin if the account has no password
                                    // getPassword - Gets the saved password associated with the
                                    // account.
                                    //  This is intended for authenticators and related code;
                                    //  applications should get an auth token instead.
                                    if ( accountManager.getPassword( newAccount[ 0 ] ) == null ) {

                                        // 2a. attempt to add it

                                        // 2a1. if successful

                                        // begin if account addition is successful
                                        // addAccountExplicitly - Adds an account directly to the
                                        // AccountManager.
                                        //  Normally used by sign-up wizards associated with
                                        // authenticators, not directly by applications.
                                        if ( accountManager.addAccountExplicitly(
                                                newAccount[ 0 ], "", null ) ) {

                                            // 2a1a. notify the sync adapter that a new account has
                                            // been created
                                            // 2a1b. last we will return the account created at 1

                                            // 2a1a. notify the sync adapter that a new account has
                                            // been created

                                            onAccountCreated( newAccount[ 0 ], activity );

                                            // 2a1b. last we will return the account created at 1

                                        } // end if account addition is successful

                                        // 2a2. otherwise

                                        // 2a2a. last we will return a null account

                                        else { newAccount[ 0 ] = null;
                                            Toast.makeText( activity, "Account not added successfully", Toast.LENGTH_LONG ).show();}

                                    } // end if the account has no password

                                    // 3. otherwise the account exists

                                    // 3. otherwise the account exists
                                    // 3a. last we will return the existing account

                                    // else { return newAccount[ 0 ]; }

                                } // end if the get accounts permission was granted

                                // else the get accounts permission was not granted
                                // so make the new account null
                                else { newAccount[ 0 ] = null; Toast.makeText( activity, "Permission not granted", Toast.LENGTH_LONG ).show(); }

                            } // end call

                        } // end new Action1

                ); // end subscribe

        // last. return the account created at 1
        Log.e( LOG_TAG, "getSyncAccount: Returned account - " + newAccount[ 0 ] );
        return newAccount[ 0 ];

    } // end method getSyncAccount

    /**
     * Handles some things that need to be done after an account has been created.
     *
     * More specifically, after an account is created,
     * a periodic sync should be configured, enabled, and started.
     *
     * @param newAccount The newly-created {@link Account}.
     * @param activity The {@link Activity} where this method is running.
     * */
    // begin method onAccountCreated
    private static void onAccountCreated( Account newAccount, Activity activity ) {
        Log.d( LOG_TAG, "onAccountCreated() called with: newAccount = [" + newAccount + "], activity = [" + activity + "]" );
        // 0. configure the periodic sync
        // 1. enable the periodic sync
        // 2. kick off a sync to get things started

        // 0. configure the periodic sync

        MoviesSyncAdapter.configurePeriodicSync( activity, SYNC_INTERVAL, SYNC_FLEXTIME );

        // 1. enable the periodic sync

        // setSyncAutomatically - Set whether or not the provider is synced
        //  when it receives a network tickle.
        // a tickle tells the app that there is some new data.
        // the app then decides whether or not to fetch that data.
        // http://android-developers.blogspot.co.ke/2010/05/android-cloud-to-device-messaging.html
        ContentResolver.setSyncAutomatically( newAccount,
                activity.getString( R.string.movies_provider_content_authority ), true );

        // 2. kick off a sync to get things started

        MoviesSyncAdapter.syncImmediately( activity );

    } // end method onAccountCreated

    /**
     * Helper method to schedule the sync adapter's periodic execution.
     *
     * @param activity The {@link Activity} we will be working in
     * @param syncInterval The time interval in seconds between successive syncs
     * @param flexTime The amount of flex time in seconds before {@param syncInterval}
     *                 that you permit for the sync to take place. Must be less than pollFrequency.
     * */
    // begin method configurePeriodicSync
    public static void configurePeriodicSync( Activity activity, int syncInterval, int flexTime ) {

        // 0. get the account
        // 1. get the authority
        // 2. for Kitkat and above
        // 2a. build a sync request using inexact timers
        // 2b. sync using that request
        // 3. for below Kitkat
        // 3a. sync using the exact sync intervals

        // 0. get the account

        Account account = getSyncAccount( activity );

        // 1. get the authority

        String authority = activity.getString( R.string.movies_provider_content_authority );

        // 2. for Kitkat and above

        // begin if Kitkat and above
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {

            // 2a. build a sync request using inexact timers

            SyncRequest syncRequest = new SyncRequest.Builder()
                    .setSyncAdapter( account, authority ) // Specify authority and account for this
                    // transfer.
                    .syncPeriodic( syncInterval, flexTime ) // Build a periodic sync with some flex
                    // time
                    .setExtras( new Bundle() )
                    .build();

            // 2b. sync using that request

            ContentResolver.requestSync( syncRequest );

        } // end if Kitkat and above

        // 3. for below Kitkat

        // begin else below Kitkat
        else {

            // 3a. sync using the exact sync intervals

            // addPeriodicSync -
            //  Specifies that a sync should be requested with the specified the account, authority,
            //  and extras at the given frequency. If there is already another periodic sync
            //  scheduled with the account, authority and extras then a new periodic sync won't be
            //  added, instead the frequency of the previous one will be updated.
            ContentResolver.addPeriodicSync( account, authority, new Bundle(), syncInterval );

        } // end else below Kitkat

    } // end method configurePeriodicSync

    /* others */
    
    /** Fetches movies from the net and stores them in the db. */
    // begin method fetchMovies
    private void fetchMovies() {

        // 0. if there is no sort order or the sort order is favorites, just stop
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
        // 8. get the movies form the JSON
        // e0. io|json, log, stop
        // 9. finally
        // 9a. disconnect the url
        // 9b. close the buffered reader
        // e0. io, log

        // 0. if there is no sort order or the sort order is favorites, just stop

        String sortOrder = Utility.getPreferredSortOrder( getContext() );

        if ( sortOrder == null || Utility.isSortOrderFavorites( getContext() ) ) { return; }

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
            final String KEY_PARAMETER = "api_key";

            Uri builtUri = Uri.parse( MOVIE_BASE_URL ).buildUpon()
                    .appendEncodedPath( sortOrder )
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
                return;
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

            // 8. get the movies form the JSON

            movieJSONString = stringBuilder.toString();

            getMovieDataFromJSON( movieJSONString );

        }  // end try to finish network work

        // e0. io|json, log, stop

        catch ( IOException | JSONException e ) { Log.e( LOG_TAG, "doInBackground: ", e ); }

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

    } // end method fetchMovies

    /**
     * Helper method to get movie data from a JSON string.
     *
     * @param movieJSONString The string having the movie JSON
     * */
    // begin method getMovieDataFromJSON
    private void getMovieDataFromJSON( String movieJSONString ) throws JSONException, IOException {

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
        // 0h. popularity
        // 0i. and the retrofit instance
        // 1. extract data from JSON
        // 1a. get the list of movies from the JSON
        // 1b. have an array list of movies matching the JSON list
        // 1b1. get a movie JSON item
        // 1b2. create a movie object from it
        // 1b3. handle trailers asynchronously using retrofit
        // 1b3a. fetch trailers using movie id
        // 1b3b. convert fetched trailers into JSON
        // 1b3c. insert converted JSON to db
        // 1b-last. add that object to the movie array
        // 2. initialize the ContentValues vectors where we will put the movie data
        // 3. for each fetched movie
        // 3a. if it is already in the db, skip it
        // 3b. put it in a ContentValues for movies
        // 3c. put the ContentValues in the vector made earlier
        // 4. if the vector has something,
        // 4a. bulk insert to add the movie entries in the vector to movie table

        // 0. initialize the names of JSON objects to extract

        // 0a. movie list
        // 0b. poster path
        // 0c. overview
        // 0d. release date
        // 0e. id
        // 0f. title
        // 0g. vote average
        // 0h. popularity

        final String MOVIE_LIST = "results";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_ID = "id";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_VOTE_AVERAGE = "vote_average";
        final String MOVIE_POPULARITY = "popularity";

        // 0i. and the retrofit instance

        ApiInterface apiService = ApiClient.getClient().create( ApiInterface.class );

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
            // 0h. popularity

            String moviePosterPath = currentMovieJsonObject.getString( MOVIE_POSTER_PATH );

            String movieSynopsis = currentMovieJsonObject.getString( MOVIE_OVERVIEW );

            String movieReleaseDate = currentMovieJsonObject.getString( MOVIE_RELEASE_DATE );

            long movieId = currentMovieJsonObject.getLong( MOVIE_ID );

            String movieTitle = currentMovieJsonObject.getString( MOVIE_TITLE );

            double movieUserRating = currentMovieJsonObject.getDouble( MOVIE_VOTE_AVERAGE );

            double moviePopularity = currentMovieJsonObject.getDouble( MOVIE_POPULARITY );

            final Movie aMovie = new Movie( movieId, movieTitle, movieReleaseDate, movieSynopsis,
                    movieUserRating, moviePopularity, moviePosterPath, false /* Not a favorite */,
                    null );

            // 1b3. handle trailers asynchronously using retrofit

            // 1b3a. fetch trailers using movie id

            Call< TrailersResponse > call = apiService.getMovieTrailersResult(
                    Integer.parseInt( String.valueOf( movieId ) ),
                    BuildConfig.THE_MOVIE_DB_API_KEY );

            // begin call.enqueue
            call.enqueue( new Callback< TrailersResponse >() {

                @Override
                // begin onResponse
                public void onResponse( Call< TrailersResponse > call, Response< TrailersResponse > response ) {

                    // 1b3b. convert fetched trailers into JSON

                    List< Result > trailerResults = response.body().getResults();

                    String resultsString = new Gson().toJson( trailerResults );

                    // 1b3c. insert converted JSON to db

                    long movieId  = response.body().getId();

                    ContentValues trailerContentValues = new ContentValues();

                    trailerContentValues.put( MovieTableColumns.TRAILERS, resultsString );

                    getContext().getContentResolver().update(
                            MoviesProvider.MoviesUriHolder.withMovieId( movieId ),
                            trailerContentValues, null, null );

                } // end onResponse

                @Override
                // begin onFailure
                // tells the user
                public void onFailure( Call< TrailersResponse > call, Throwable t ) {
                    Toast.makeText( getContext(), R.string.message_error_fetching_trailers,
                            Toast.LENGTH_SHORT ).show();
                } // end onFailure

            } ); // end call.enqueue

            // 1b-last. add that object to the movie array

            movies.add( aMovie );

        } // end for to initialize a movie from the JSON array

        // 2. initialize the ContentValues vectors where we will put the movie data

        Vector< ContentValues > moviesVector = new Vector<>( movies.size() );

        // 3. for each fetched movie

        // begin for through each fetched movie
        for ( Movie movie : movies ) {

            // 3a. if it is already in the db, skip it

            if ( Utility.isMovieInDatabase( movie.getID(), getContext().getContentResolver() ) ) {
                continue;
            }

            // 3b. put it in a ContentValues for movies

            ContentValues movieContentValues = new ContentValues();

            movieContentValues.put( MovieTableColumns.MOVIE_ID, movie.getID() );
            movieContentValues.put( MovieTableColumns.POSTER_PATH, movie.getPosterPath() );
            movieContentValues.put( MovieTableColumns.OVERVIEW, movie.getSynopsis() );
            movieContentValues.put( MovieTableColumns.RELEASE_DATE, movie.getReleaseDate() );
            movieContentValues.put( MovieTableColumns.TITLE, movie.getTitle() );
            movieContentValues.put( MovieTableColumns.VOTE_AVERAGE, movie.getUserRating() );
            movieContentValues.put( MovieTableColumns.POPULARITY, movie.getPopularity() );

            // 3c. put the ContentValues in the vector made earlier

            moviesVector.add( movieContentValues );

        } // end for through each fetched movie

        // 4. if the vector has something,
        // 4a. bulk insert to add the movie entries in the vector to movie table

        int numberOfMovieInserts = 0;

        // begin if the movies vector has something
        if ( !moviesVector.isEmpty() ) {

            ContentValues movieContentValuesArray[] = new ContentValues[ moviesVector.size() ];

            // stores the vector contents in the content values array
            moviesVector.toArray( movieContentValuesArray );

            numberOfMovieInserts = getContext().getContentResolver().bulkInsert(
                    MoviesProvider.MoviesUriHolder.MOVIES_URI, movieContentValuesArray );

        } // end if the movies vector has something

        Log.e( LOG_TAG, "onFetchedMoviesEvent: number of movies inserted: " + numberOfMovieInserts );

    } // end method getMovieDataFromJSON

} // end class MoviesSyncAdapter
