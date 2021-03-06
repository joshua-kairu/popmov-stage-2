package com.joslittho.popmov.data.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.IfNotExists;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

import static com.joslittho.popmov.data.database.FavoritesTableColumns.*;
import static com.joslittho.popmov.data.database.MovieTableColumns.REVIEWS;
import static com.joslittho.popmov.data.database.MovieTableColumns.TRAILERS;
import static com.joslittho.popmov.data.database.MoviesDatabase.MovieTablesHolder.FAVORITES_TABLE_NAME;
import static com.joslittho.popmov.data.database.MoviesDatabase.MovieTablesHolder.MOVIES_TABLE_NAME;

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
    static final int VERSION = 20;

    /**
     * The logger.
     */
    private static final String LOG_TAG = MoviesDatabase.class.getSimpleName();

    // for database upgrading, please see https://github.com/SimonVT/schematic/issues/44

    // http://stackoverflow.com/questions/8045249/how-do-i-delete-column-from-sqlite-table-in-android
    //
    // Sorry, SQLite doesn't support DROP COLUMN:
    // (11) How do I add or delete columns from an existing table in SQLite.
    //
    // SQLite has limited ALTER TABLE support that you can use to add a column to the end of a
    // table or to change the name of a table. [...]
    //
    // For example, suppose you have a table named "t1" with columns names "a", "b", and "c" and
    // that you want to delete column "c" from this table. The following steps illustrate how this
    // could be done:
    //
    // BEGIN TRANSACTION;
    // CREATE TEMPORARY TABLE t1_backup(a,b);
    // INSERT INTO t1_backup SELECT a,b FROM t1;
    // DROP TABLE t1;
    // CREATE TABLE t1(a,b);
    // INSERT INTO t1 SELECT a,b FROM t1_backup;
    // DROP TABLE t1_backup;
    // COMMIT;
    //
    // So basically, you have to use the "copy, drop table, create new table, copy back" technique
    // to remove a column.
    //
    // So for us with table "favorites" with columns "rowid" and "movie_id" and we
    // want to delete column "rowid" and add column "_id" in its place. Thus our steps will
    // be:
    //
    // t1 = favorites, a = rowid, b = movie_id
    //
    // BEGIN TRANSACTION;
    // CREATE TEMPORARY TABLE favorites_backup(rowid, movie_id);
    // INSERT INTO favorites_backup SELECT rowid, movie_id FROM favorites;
    // DROP TABLE favorites;
    // CREATE TABLE favorites(_id, movie_id);
    // INSERT INTO favorites SELECT rowid, movie_id FROM favorites_backup;
    // DROP TABLE favorites_backup;
    // COMMIT;

    // begin _MIGRATIONS
    public static final String[] _MIGRATIONS = {

            // 0. add trailers column to movie table
            // ALTER TABLE movies ADD trailers TEXT;
            // 1. add reviews column to movie table
            // ALTER TABLE movies ADD reviews TEXT;

            // 0. add trailers column to movie table
            // ALTER TABLE movies ADD trailers TEXT;

            "ALTER TABLE " + MOVIES_TABLE_NAME + " ADD " +  TRAILERS + " TEXT;",

            // 1. add reviews column to movie table
            // ALTER TABLE movies ADD reviews TEXT;

            "ALTER TABLE " + MOVIES_TABLE_NAME + " ADD " +  REVIEWS + " TEXT;"

    }; // end _MIGRATIONS

    /* VARIABLES */
    
    /* CONSTRUCTOR */
    
    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */

    /**
     * Upgrade method copied from https://github.com/SimonVT/schematic/issues/44
     * */
    @OnUpgrade
    // begin onUpgrade
    public static void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion - oldVersion; i++) {
            String migration = _MIGRATIONS[i - oldVersion];
            db.beginTransaction();
            try {
                db.execSQL(migration);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e( LOG_TAG, "Error executing database migration: "+ migration );
                break;
            } finally {
                db.endTransaction();
            }
        }
    } // end onUpgrade
    
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
