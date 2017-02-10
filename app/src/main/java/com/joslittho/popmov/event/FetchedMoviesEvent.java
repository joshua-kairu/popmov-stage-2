package com.joslittho.popmov.event;

import com.joslittho.popmov.data.model.Movie;

import java.util.List;

/**
 * Event for when movies have been fetched from the net.
 */
// begin class FetchedMoviesEvent
public class FetchedMoviesEvent {

    /* CONSTANTS */
    
    /* Integers */
    
    /* Strings */

    /* VARIABLES */

    /* Lists */

    private List< Movie > mFetchedMovies; // ditto
    
    /* CONSTRUCTOR */

    /**
     * Default constructor
     *
     * Initializes the fetched movies
     * */
    public FetchedMoviesEvent( List< Movie > fetchedMovies ) { this.mFetchedMovies = fetchedMovies; }

    /* METHODS */
    
    /* Getters and Setters */

    public List< Movie > getFetchedMovies() {
        return mFetchedMovies;
    }

    /* Overrides */
    
    /* Other Methods */

} // end class FetchedMoviesEvent
