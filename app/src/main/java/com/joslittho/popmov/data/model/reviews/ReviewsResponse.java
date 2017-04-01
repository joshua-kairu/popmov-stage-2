package com.joslittho.popmov.data.model.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The reviews response gotten from TMDB JSON. This contains the details of all reviews for a
 * given movie.
 */
// begin class ReviewsResponse
public class ReviewsResponse {

    /* CONSTANTS */
    
    /* ints */
        
    /* Strings */
    
    /* VARIABLES */

    @SerializedName( "id" )
    @Expose
    private int id;
    @SerializedName( "page" )
    @Expose
    private int page;
    @SerializedName( "results" )
    @Expose
    private List< Result > results = null;
    @SerializedName( "total_pages" )
    @Expose
    private int totalPages;
    @SerializedName( "total_results" )
    @Expose
    private int totalResults;

    /* CONSTRUCTOR */

    /**
     * No args constructor for use in serialization
     */
    public ReviewsResponse() {
    }

    /**
     * @param id
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public ReviewsResponse( int id, int page, List< Result > results, int totalPages,
                            int totalResults ) {
        super();
        this.id = id;
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }
    
    /* METHODS */
        
    /* Getters and Setters */

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage( int page ) {
        this.page = page;
    }

    public List< Result > getResults() {
        return results;
    }

    public void setResults( List< Result > results ) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages( int totalPages ) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults( int totalResults ) {
        this.totalResults = totalResults;
    }
        
    /* Overrides */
        
    /* Other Methods */

} // end class ReviewsResponse
