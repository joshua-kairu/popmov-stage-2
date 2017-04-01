/*
 *
 * PopMov
 *
 * An Android app to show the latest movies from https://www.themoviedb.org.
 *
 * Copyright (C) 2016 Kairu Joshua Wambugu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 */

package com.joslittho.popmov.data.model;

import com.joslittho.popmov.data.model.trailers.Result;

import java.util.List;

/**
 * A movie, as defined by the TMDB JSON.
 * */
// begin class Movie
public class Movie {

    /* CONSTANTS */

    /* Integers */
    
    /* Strings */
        
    /* VARIABLES */

    /** The trailers for this movie */
    private List< Result > mTrailers; // ditto

    /* Primitives */

    /** The movie's user rating, out of 10. */
    private double mUserRating;

    /** if this movie is a user favorite. */
    private boolean mFavorite; // ditto

    /** The movie's unique ID in TMDB */
    private long mID;

    /** The movie's popularity */
    private double mPopularity;

    /* Strings */

    private String mTitle; // ditto

    /** The movie's release date, in the form 2016-12-31. */
    private String mReleaseDate;

    /**
     * The path to the movie's poster.
     * To use this path, build a Url that looks like this:
     * http://image.tmdb.org/t/p/w185/poster_path
     * */
    private String mPosterPath;

    /** The movie's synopsis. */
    private String mSynopsis;

    /* CONSTRUCTOR */

    // begin default constructor
    public Movie( long id, String title, String releaseDate, String synopsis, double userRating,
                  double popularity, String posterPath, boolean favorite, List trailers ) {

        // 0. initialize members

        // 0. initialize members

        setID( id );
        setTitle( title );
        setReleaseDate( releaseDate );
        setSynopsis( synopsis );
        setUserRating( userRating );
        setPopularity( popularity );
        setPosterPath( posterPath );
        setFavorite( favorite );

    } // end default constructor

    /* METHODS */
    
    /* Getters and Setters */

    // getter for the mSynopsis
    public String getSynopsis() {
        return mSynopsis;
    }

    // setter for the mSynopsis
    public void setSynopsis( String synopsis ) {
        mSynopsis = synopsis;
    }

    // getter for the poster path
    public String getPosterPath() {
        return mPosterPath;
    }

    // setter for the poster path
    public void setPosterPath( String posterPath ) {
        mPosterPath = posterPath;
    }

    // getter for the popularity
    public double getPopularity() { return mPopularity; }

    // setter for the popularity
    public void setPopularity( double popularity ) { mPopularity = popularity; }

    // getter for the release date
    public String getReleaseDate() {
        return mReleaseDate;
    }

    // setter for the release date
    public void setReleaseDate( String releaseDate ) {
        mReleaseDate = releaseDate;
    }

    // getter for the title
    public String getTitle() {
        return mTitle;
    }

    // setter for the title
    public void setTitle( String title ) {
        mTitle = title;
    }

    // getter for the vote average
    public double getUserRating() {
        return mUserRating;
    }

    // setter for the vote average
    public void setUserRating( double userRating ) {
        mUserRating = userRating;
    }

    // getter for the id
    public long getID() { return mID; }

    // setter for the id
    public void setID( long id ) { mID = id; }

    // getter for favorite
    public boolean isFavorite() { return mFavorite; }

    // setter for favorite
    public void setFavorite( boolean favorite ) { this.mFavorite = favorite; }

    // getter for trailers
    public List< Result > getTrailers() {
        return mTrailers;
    }

    // setter for trailers
    public void setTrailers( List< Result > trailers ) {
        this.mTrailers = trailers;
    }

    /* Overrides */

    /* Other Methods */

    /* INNER CLASSES */

} // end class Movie