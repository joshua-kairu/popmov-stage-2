package com.joslittho.popmov.data.database;

import android.net.Uri;

import com.joslittho.popmov.data.database.MoviesDatabase.MovieTablesHolder;
import com.joslittho.popmov.data.model.Movie;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

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

    @TableEndpoint( table = MovieTablesHolder.MOVIES_TABLE_NAME )
    /**
     * Inner class of the {@link MoviesProvider} that contains Uri's which can be queried for {@link Movie}
     * data.
     * */
    // begin inner class MoviesUriHolder
    public static class MoviesUriHolder {

        @ContentUri(
                path = MovieTablesHolder.MOVIES_TABLE_NAME, // the movies table in general
                type = "vnd.android.cursor.dir/" + MovieTablesHolder.MOVIES_TABLE_NAME // this Uri looks for all contents inside the movies table
        )
        /** Uri pointing to the movies table. */
        public static final Uri MOVIES_URI = buildUri( MovieTablesHolder.MOVIES_TABLE_NAME );

        /**
         * Inner join the movie table with the favorites table where the movie id in both tables match
         */
        //private static final String JOIN_MOVIES_WITH_FAVORITES_STRING =
        //        "JOIN " + MovieTablesHolder.MOVIES_TABLE_NAME + " ON " +
        //                MovieTableColumns.MOVIE_ID + " = " + FavoritesTableColumns.MOVIE_ID;

        @InexactContentUri(
                // TODO: 2/14/17 How to do a join between the movies and favorites tables
                // join = JOIN_MOVIES_WITH_FAVORITES_STRING,
                path = MovieTablesHolder.MOVIES_TABLE_NAME + "/#", // a number in the movies table
                name = "PARTICULAR_MOVIE_FROM_MOVIES_LIST", // name of this inexact URI, I think
                type = "vnd.android.cursor.item/" + MovieTablesHolder.MOVIES_TABLE_NAME, // this Uri looks for an item inside the movies table
                whereColumn = MovieTableColumns.MOVIE_ID, // the column which we will use to choose a specific item
                pathSegment = 1 // how many paths the Uri will have, I think. Uri a/b has one path - "b". Uri a/b/c has two paths - "b and c", I think
        )
        /**
         * Uri pointing to a particular movie in the movies table.
         *
         * @param movieId The movie's unique id
         *
         * @return Uri pointing to the movie with the given id.
         * */
        public static Uri withMovieId( long movieId ) {
            return buildUri( MovieTablesHolder.MOVIES_TABLE_NAME, String.valueOf( movieId ) );
        }

    } // end inner class MoviesUriHolder

    @TableEndpoint( table = MovieTablesHolder.FAVORITES_TABLE_NAME )
    /**
     * Inner class of the {@link MoviesProvider} that contains Uris which can be queried for
     * {@link com.joslittho.popmov.data.model.Movie} favorites data.
     * */
    // begin inner class FavoritesUriHolder
    public static class FavoritesUriHolder {

        @ContentUri(
                path = MovieTablesHolder.FAVORITES_TABLE_NAME, // the favorites table in general
                type = "vnd.android.cursor.dir/" + MovieTablesHolder.FAVORITES_TABLE_NAME // this Uri looks for all contents inside the favorites table
        )
        /** Uri pointing to the favorites table. */
        public static final Uri FAVORITES_URI = buildUri( MovieTablesHolder.FAVORITES_TABLE_NAME );

    } // end inner class FavoritesUriHolder

} // end class MoviesProvider
