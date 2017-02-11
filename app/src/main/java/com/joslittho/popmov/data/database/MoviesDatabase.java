package com.joslittho.popmov.data.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * A representation of the db holding all movie data
 */
@Database( version = MoviesDatabase.VERSION )
// begin class MoviesDatabase
public class MoviesDatabase {

    /* CONSTANTS */

    /* Integers */
    
    /* Strings */

    /** The db's current version. */
    public static final int VERSION = 1;

    /** The movies table */
    @Table( MovieTableColumns.class )
    public static final String MOVIES_TABLE_NAME = "movies";

    /* VARIABLES */
    
    /* CONSTRUCTOR */
    
    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */
    
    /* Other Methods */

} // end class MoviesDatabase
