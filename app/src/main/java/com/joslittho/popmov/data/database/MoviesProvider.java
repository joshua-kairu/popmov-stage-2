package com.joslittho.popmov.data.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * {@link android.content.ContentProvider} to serve {@link com.joslittho.popmov.data.model.Movie}s
 * from the db.
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
     * Credits: https://valeriodg.com/2016/06/09/contentprovider-example-15-minutes/
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

    @TableEndpoint( table = MoviesDatabase.MOVIES_TABLE_NAME )
    /**
     * Inner class of the {@link MoviesProvider} that contains Uri's which can be queried for {@link com.joslittho.popmov.data.model.Movie}
     * data.
     * */
    // begin inner class MoviesUriHolder
    public static class MoviesUriHolder {

        @ContentUri(
                path = MoviesDatabase.MOVIES_TABLE_NAME,
                type = "vnd.android.cursor.dir/" + MoviesDatabase.MOVIES_TABLE_NAME
                // TODO: 2/8/17 Add sort order
        )
        /** Uri pointing to the movies table. */
        public static final Uri MOVIES_URI = buildUri( MoviesDatabase.MOVIES_TABLE_NAME );

    } // end inner class MoviesUriHolder

} // end class MoviesProvider
