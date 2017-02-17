package com.joslittho.popmov.data.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.IfNotExists;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * A representation of the db holding the movie data
 */
@Database( version = MoviesDatabase.VERSION )
// begin class MoviesDatabase
public class MoviesDatabase {

    /* CONSTANTS */

    /* Integers */
    
    /* Strings */

    /** The db's current version. */
    static final int VERSION = 1;

    /* VARIABLES */
    
    /* CONSTRUCTOR */
    
    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */
    
    /* Other Methods */

    /* INNER CLASSES */

    /** Holder for the tables. */
    // begin inner class MovieTablesHolder
    static class MovieTablesHolder {

        /** The movies table */
        @Table( MovieTableColumns.class )
        @IfNotExists
        static final String MOVIES_TABLE_NAME = "movies";

        /** The favorites table. */
        @Table( FavoritesTableColumns.class )
        @IfNotExists
        static final String FAVORITES_TABLE_NAME = "favorites";

    } // end inner class MovieTablesHolder

} // end class MoviesDatabase
