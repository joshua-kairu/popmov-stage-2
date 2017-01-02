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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A movie, as defined by the TMDB JSON.
 * */
// begin class Movie
public class Movie implements Parcelable {

    /* CONSTANTS */

    /* Creators */

    // begin new Parcelable.Creator< Movie >
    public static final Parcelable.Creator< Movie > CREATOR
            = new Parcelable.Creator< Movie >() {

        @Override
        /**
         * Create a new instance of the Parcelable class, instantiating it from
         * the given Parcel whose data had previously been written by
         * Parcelable.writeToParcel()
         * */
        // createFromParcel
        public Movie createFromParcel( Parcel source ) { return new Movie( source ); }

        @Override
        /** Create a new array of the Parcelable class. */
        // newArray
        public Movie[] newArray( int size ) { return new Movie[ size ]; }

    }; // end new Parcelable.Creator< Movie >

    /* Integers */
    
    /* Strings */
        
    /* VARIABLES */

    /* Primitives */

    /** The movie's user rating, out of 10. */
    private double mUserRating;

    /** The movie's unique ID in TMDB */
    private long mID;

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
                  String posterPath ) {

        // 0. initialize members

        // 0. initialize members

        setID( id );
        setTitle( title );
        setReleaseDate( releaseDate );
        setSynopsis( synopsis );
        setUserRating( userRating );
        setPosterPath( posterPath );

    } // end default constructor

    // begin parcel constructor
    public Movie( Parcel inParcel ) {

        // 0. initialize members from parcel

        // 0. initialize members from parcel

//        destParcel.writeString( getPosterPath() );
        // Parcel reads need to be in the same order as Parcel writes
        setID( inParcel.readLong() );
        setTitle( inParcel.readString() );
        setReleaseDate( inParcel.readString() );
        setSynopsis( inParcel.readString() );
        setUserRating( inParcel.readDouble() );
        setPosterPath( inParcel.readString() );

    } // end parcel constructor

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

    /* Overrides */

    @Override
    /** Flatten this object in to a Parcel. */
    // begin writeToParcel
    public void writeToParcel( Parcel destParcel, int flags ) {

        // 0. write members to the parcel

        // 0. write members to the parcel

        // Parcel writes need to be in the same order as Parcel reads
        destParcel.writeLong( getID() );
        destParcel.writeString( getTitle() );
        destParcel.writeString( getReleaseDate() );
        destParcel.writeString( getSynopsis() );
        destParcel.writeDouble( getUserRating() );
        destParcel.writeString( getPosterPath() );

    } // end writeToParcel

    @Override
    /**
     * Describe the kinds of special objects
     * contained in this Parcelable instance's marshaled representation.
     * */
    // describeContents
    public int describeContents() {
        return 0;
    }

    /* Other Methods */

    /* INNER CLASSES */

} // end class Movie