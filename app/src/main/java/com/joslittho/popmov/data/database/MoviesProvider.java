package com.joslittho.popmov.data.database;

import android.net.Uri;

import com.joslittho.popmov.data.database.MoviesDatabase.MovieTablesHolder;
import com.joslittho.popmov.data.model.Movie;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

import static com.joslittho.popmov.data.database.MoviesDatabase.MovieTablesHolder.*;

/**
 * {@link android.content.ContentProvider} to serve {@link com.joslittho.popmov.data.model.Movie}s
 * from the db.
 *
 * Credits:
 * https://valeriodg.com/2016/06/09/contentprovider-example-15-minutes/
 * https://github.com/SimonVT/schematic/blob/master/README.md
 */
@ContentProvider( authority = MoviesProvider.AUTHORITY, database = MoviesDatabase.class )
// begin class MoviesProvider
public class MoviesProvider {

    /* CONSTANTS */
    
    /* Integers */
    
    /* Strings */

    public static final String AUTHORITY = "com.joslittho.popmov.data.database.MoviesProvider";

    // idea from https://valeriodg.com/2016/06/09/contentprovider-example-15-minutes/
    static final Uri BASE_CONTENT_URI = Uri.parse( "content://" + AUTHORITY ); // ditto

    /* VARIABLES */
    
    /* CONSTRUCTOR */

    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */
    
    /* Other Methods */

    /**
     * Helper method to assist with content Uri generation.
     *
     * @param paths List of paths to append to the Uri
     *
     * @return A Uri having the BASE_CONTENT_URI with the paths appended to it
     * */
    // begin method buildUri
    private static Uri buildUri( String... paths ) {

        // 0. start with the base uri
        // 1. append all paths to the base uri
        // 2. return the new uri

        // 0. start with the base uri

        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();

        // 1. append all paths to the base uri

        for ( String path : paths ) { builder.appendPath( path ); }

        // 2. return the new uri

        return builder.build();

    } // end method buildUri

    /* INNER CLASSES */

    /**
     * Inner class of the {@link MoviesProvider} that contains Uri's which can be queried for
     * {@link Movie}
     * data.
     * */
    @TableEndpoint( table = MOVIES_TABLE_NAME )
    // begin inner class MoviesUriHolder
    public static class MoviesUriHolder {

        /** Uri pointing to the movies table. */
        @ContentUri(
                path = MOVIES_TABLE_NAME, // the movies table in general
                type = "vnd.android.cursor.dir/" + MOVIES_TABLE_NAME // this Uri looks for all
                // contents inside the movies table
        )
        public static final Uri MOVIES_URI = buildUri( MOVIES_TABLE_NAME );

        /**
         * Uri pointing to a particular movie in the movies table.
         *
         * @param movieId The movie's unique id
         *
         * @return Uri pointing to the movie with the given id.
         * */
        @InexactContentUri(
                path = MOVIES_TABLE_NAME + "/#", // a number in the movies table
                name = "PARTICULAR_MOVIE_FROM_MOVIES_LIST", // name of this inexact URI, I think
                type = "vnd.android.cursor.item/" + MOVIES_TABLE_NAME, // this Uri looks for an item
                // inside the movies table
                whereColumn = MovieTableColumns.MOVIE_ID, // the column which we will use to choose
                // a specific item
                pathSegment = 1 // how many paths the Uri will have, I think. Uri a/b has one path -
                // "b". Uri a/b/c has two paths - "b and c", I think
        )
        public static Uri withMovieId( long movieId ) {
            return buildUri( MOVIES_TABLE_NAME, String.valueOf( movieId ) );
        }

    } // end inner class MoviesUriHolder

    /**
     * Inner class of the {@link MoviesProvider} that contains Uris which can be queried for
     * {@link com.joslittho.popmov.data.model.Movie} favorites data.
     * */
    @TableEndpoint( table = FAVORITES_TABLE_NAME )
    // begin inner class FavoritesUriHolder
    public static class FavoritesUriHolder {

        /**
         * Inner join the movie table with the favorites table where the movie id in both tables match.
         *
         * Thanks to http://stackoverflow.com/questions/4957009/how-do-i-join-two-sqlite-tables-in-my-android-application
         * here is how a join should look like
         *
         * "SELECT desired-cols-list FROM T1 INNER JOIN T2 on T1.questionid =T2.questionid AND
         * T1.categoryid = T2.categoryid WHERE T1.categoryid = {the desired category value}"
         *
         * so our join should look like
         * SELECT favorites._id, movie_id FROM favorites JOIN movies ON
         * favorites.movie_id = movies.movie_id
         *
         */
        private static final String JOIN_MOVIES_WITH_FAVORITES_STRING =
                "JOIN " + MOVIES_TABLE_NAME + " ON "
                        + FAVORITES_TABLE_NAME + "." + FavoritesTableColumns.MOVIE_ID + " = "
                        + MOVIES_TABLE_NAME + "." + MovieTableColumns.MOVIE_ID;

        /** Uri pointing to the favorites table. */
        @ContentUri(
                join = JOIN_MOVIES_WITH_FAVORITES_STRING,
                path = FAVORITES_TABLE_NAME, // the favorites table in general
                type = "vnd.android.cursor.dir/" + FAVORITES_TABLE_NAME // this Uri looks for all
                // contents inside the favorites table
        )
        public static final Uri FAVORITES_URI = buildUri( FAVORITES_TABLE_NAME );

        /**
         * Uri pointing to a particular favorite in the favorites table.
         *
         * @param favoriteMovieId The favorite movie's unique id
         *
         * @return Uri pointing to the favorite with the given id.
         * */
        @InexactContentUri(
                join = JOIN_MOVIES_WITH_FAVORITES_STRING,
                path = FAVORITES_TABLE_NAME + "/#", // a row in the movies table
                name = "PARTICULAR_FAVORITE_FROM_FAVORITE_LIST", // name of this inexact URI
                type = "vnd.android.cursor.item/" + FAVORITES_TABLE_NAME, // this Uri looks for an
                // item inside the favorites table
                whereColumn = FAVORITES_TABLE_NAME + "." + FavoritesTableColumns.MOVIE_ID, // the
                // column which we will use to choose a specific favorite
                pathSegment = 1 // how many paths the Uri will have, I think. Uri a/b has one path
                // - "b". Uri a/b/c has two paths - "b and c", I think
        )
        public static Uri withFavoriteMovieId( long favoriteMovieId ) {
            return buildUri( FAVORITES_TABLE_NAME, String.valueOf( favoriteMovieId ) );
        }

    } // end inner class FavoritesUriHolder

} // end class MoviesProvider
