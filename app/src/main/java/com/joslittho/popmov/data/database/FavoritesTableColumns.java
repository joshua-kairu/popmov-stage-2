package com.joslittho.popmov.data.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;

/**
 * Interface containing the columns of the favorites table in the db
 */
// begin interface FavoritesTableColumns
public interface FavoritesTableColumns {

    /* VARIABLES */

    @DataType( INTEGER )
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id"; // the unique row id

    @DataType( INTEGER )
    @Unique
    @NotNull
    String MOVIE_ID = "movie_id"; // the unique movie ID - int

    @DataType( INTEGER )
    @NotNull
    String IS_FAVORITE = "is_favorite"; // whether or not this movie is a favorite - int 0 or 1
                                        // depending on whether favorite or not

    /** Integer value meaning that a movie is a favorite. */
    int FAVORITE_TRUE = 0;

    /** Integer value meaning that a movie is not a favorite. */
    int FAVORITE_FALSE = 1;

    /* METHODS */

} // end interface FavoritesTableColumns
