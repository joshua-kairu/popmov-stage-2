package com.joslittho.popmov.data.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import static com.joslittho.popmov.data.database.MovieTableColumns.OVERVIEW;
import static com.joslittho.popmov.data.database.MovieTableColumns.POSTER_PATH;
import static com.joslittho.popmov.data.database.MovieTableColumns.RELEASE_DATE;
import static com.joslittho.popmov.data.database.MovieTableColumns.TITLE;
import static com.joslittho.popmov.data.database.MovieTableColumns.VOTE_AVERAGE;
import static com.joslittho.popmov.data.database.MoviesDatabase.MovieTablesHolder.FAVORITES_TABLE_NAME;
import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;

/**
 * Interface containing the columns of the favorites table in the db
 */
// begin interface FavoritesTableColumns
public interface FavoritesTableColumns {

    /* VARIABLES */

    @DataType( INTEGER ) // the data type stored in this column
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id"; // the unique row id

//    @DataType( INTEGER )
//    @AutoIncrement
//    String ROW_ID = "rowid"; // the unique row id

    @DataType( INTEGER )
    @Unique
    @NotNull
    String MOVIE_ID = "movie_id"; // the unique movie ID - int

    /* Arrays */

    /**
     * Array of columns needed to make the {@link com.joslittho.popmov.fragment.PostersFragment}
     * show the favorites.
     *
     * These columns are the _id,
     * the movie id (for linking to the {@link com.joslittho.popmov.fragment.DetailFragment}),
     * title (for content descriptions), poster path, and the favorite status
     *
     * The _id and movie id are explicitly referred to as this table's _id and movie id since
     * the movie table also has _id and movie_id
     * */
    String[] POSTERS_FRAGMENT_FAVORITES_COLUMNS = {
            FAVORITES_TABLE_NAME + "." + _ID, // 0
            FAVORITES_TABLE_NAME + "." + MOVIE_ID, // 1
            POSTER_PATH, // 2
            TITLE }; // 3

    // use column indices from MovieTableColumns

    // int COLUMN_MOVIE_ID = 1; // column for the unique movie id
    // int COLUMN_POSTER_PATH = 2; // column for the poster path
    // int COLUMN_TITLE = MovieTableColumns.COLUMN_TITLE /* which is = 3 */; // column for movie
    // title

    /**
     * Array of columns needed to make the {@link com.joslittho.popmov.fragment.DetailFragment} work
     * with favorites.
     *
     * These columns are the _id,
     * the movie id (for linking to the {@link com.joslittho.popmov.fragment.DetailFragment}),
     * title (for content descriptions), poster path, the favorite status, vote average, release
     * date, and overview
     *
     * The _id and movie id are explicitly referred to as this table's _id and movie id since
     * the movie table also has _id and movie_id
     * */
    String[] DETAIL_FRAGMENT_FAVORITE_COLUMNS = {
            FAVORITES_TABLE_NAME + "." + _ID, // 0
            FAVORITES_TABLE_NAME + "." + MOVIE_ID, // 1
            POSTER_PATH, // 2
            TITLE, // 3
            VOTE_AVERAGE, // 4
            RELEASE_DATE, // 5
            OVERVIEW, // 6
    };

    // use column indices from MovieTableColumns

    // int COLUMN_MOVIE_ID = 1; // column for the unique movie id
    // int COLUMN_POSTER_PATH = 2; // column for the poster path
    // int COLUMN_TITLE = 3; // column for the movie title
    // int COLUMN_VOTE_AVERAGE = 4; // column for the vote average
    // int COLUMN_RELEASE_DATE = 5; // column for the release date - only for the detail
    // int COLUMN_DETAIL_OVERVIEW = 6; // column for the overview - only for the detail

} // end interface FavoritesTableColumns
