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
    public static final int VERSION = 1;


//    /** Migrations for db upgrading. One string per version increment. */
//    // From https://www.tutorialspoint.com/sql/sql-create-table.htm
//    // CREATE TABLE table_name(
//    // column1 datatype,
//    // column2 datatype,
//    // column3 datatype,
//    // .....
//    // columnN datatype,
//    // PRIMARY KEY( one or more columns )
//    // );
//    public static final String[] _MIGRATIONS = {
//            "CREATE TABLE " + FAVORITES_TABLE_NAME + "("
//                    + " column " + FavoritesTableColumns._ID + " INT NOT NULL"
//                    + " column " + FavoritesTableColumns.MOVIE_ID + " INT UNIQUE NOT NULL"
//                    + " column " + FavoritesTableColumns.IS_FAVORITE + " INT NOT NULL"
//                    + " PRIMARY KEY (" + FavoritesTableColumns._ID + ")"
//                    + ");"
//    };

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
