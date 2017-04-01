package com.joslittho.popmov.data.model.trailers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The trailers response gotten from TMDB JSON. This contains the details of all trailers for a
 * given movie.
 */
// begin class TrailersResponse
public class TrailersResponse {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */

    @SerializedName( "id" )
    @Expose
    private int id;
    @SerializedName( "results" )
    @Expose
    private List< Result > results = null;

    /* CONSTRUCTOR */

    /**
     * No args constructor for use in serialization
     */
    public TrailersResponse() {
    }

    /**
     * @param id
     * @param results
     */
    public TrailersResponse( int id, List< Result > results ) {
        super();
        this.id = id;
        this.results = results;
    }
    
    /* METHODS */
        
    /* Getters and Setters */

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public TrailersResponse withId( int id ) {
        this.id = id;
        return this;
    }

    public List< Result > getResults() {
        return results;
    }

    public void setResults( List< Result > results ) {
        this.results = results;
    }

    public TrailersResponse withResults( List< Result > results ) {
        this.results = results;
        return this;
    }

    /* Overrides */
        
    /* Other Methods */

} // end class TrailersResponse