package com.joslittho.popmov.data.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Interface containing the columns of the movie table in the db
 */
// begin interface MovieTableColumns
public interface MovieTableColumns {

    /* VARIABLES */

    @DataType( INTEGER ) // the data type stored in this column
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id"; // the unique row id

    @DataType( INTEGER )
    @Unique
    @NotNull
    String MOVIE_ID = "movie_id"; // the unique movie ID - int

    @DataType( TEXT )
    @NotNull
    String POSTER_PATH = "poster_path"; // the poster path - string

    @DataType( TEXT )
    @NotNull
    String OVERVIEW = "overview"; // the overview, or synopsis - string

    @DataType( TEXT )
    @NotNull
    String RELEASE_DATE = "release_date"; // the release date - string

    @DataType( TEXT )
    @NotNull
    String TITLE = "title"; // the movie title - string

    @DataType( REAL )
    @NotNull
    String VOTE_AVERAGE = "vote_average"; // the vote average, or user rating - double, hence real

    @DataType( REAL )
    @NotNull
    String POPULARITY = "popularity"; // the popularity - double, hence real

    @DataType( TEXT )
    String TRAILERS = "trailers"; // the trailers (JSON) list - string

    @DataType( TEXT )
    String REVIEWS = "reviews"; // the reviews (JSON) list - string

    /* Arrays */

    /**
     * Array of columns needed to make the {@link com.joslittho.popmov.fragment.PostersFragment}
     * work.
     *
     * These columns are the _id,
     * the movie id (for linking to the {@link com.joslittho.popmov.fragment.DetailFragment}),
     * title (for content descriptions), poster path, movie vote average, and movie popularity
     * */
    String[] POSTERS_FRAGMENT_COLUMNS =
            { _ID, MOVIE_ID, POSTER_PATH, TITLE, VOTE_AVERAGE, POPULARITY };

    int COLUMN_MOVIE_ID = 1; // column for the unique movie id
    int COLUMN_POSTER_PATH = 2; // column for the poster path
    int COLUMN_TITLE = 3; // column for the movie title
    int COLUMN_VOTE_AVERAGE = 4; // column for the vote average
    // int COLUMN_POSTERS_POPULARITY = 5; // column for the popularity - only for the posters, and
                                          // used for sorting not getting the actual popularity for
                                          // display

    /**
     * Array of columns needed to make the {@link com.joslittho.popmov.fragment.DetailFragment}
     * work.
     *
     * These columns are the _id, movie_id, poster path, title, vote average, release date,
     * overview, and trailers
     * */
    String[] DETAIL_FRAGMENT_COLUMNS =
            { _ID, MOVIE_ID, POSTER_PATH, TITLE, VOTE_AVERAGE, RELEASE_DATE, OVERVIEW, TRAILERS };

    int COLUMN_RELEASE_DATE = 5; // column for the release date - only for the detail
    int COLUMN_DETAIL_OVERVIEW = 6; // column for the overview - only for the detail
    int COLUMN_DETAIL_TRAILERS = 7; // column for the trailers - only for the detail

} // end interface MovieTableColumns