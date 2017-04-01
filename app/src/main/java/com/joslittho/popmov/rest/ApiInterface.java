package com.joslittho.popmov.rest;

import com.joslittho.popmov.data.model.trailers.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface for the {@link retrofit2.Retrofit} endpoints
 */
// begin interface ApiInterface
public interface ApiInterface {

    /* METHODS */

    /**
     * Fetch movie trailers.
     *
     * @param id The movie ID
     * @param apiKey The API key
     * */
    @GET( "movie/{id}/videos" ) // @GET - Make a GET request
    // Call - An invocation of a Retrofit method that sends a request to a web server and returns a
    //  response. Each call yields its own HTTP request and response pair.
    // @Path - Named replacement in a URL path segment.
    // @Query - Query parameter appended to the URL.
    Call< TrailersResponse > getMovieTrailersResult( @Path( "id" ) int id,
                                                     @Query( "api_key" ) String apiKey );

    /**
     * Fetch movie reviews.
     *
     * @param id The movie ID
     * @param apiKey The API key
     * */
    @GET( "movie/{id}/reviews" )
    Call< TrailersResponse > getMovieReviewsResult( @Path( "id" ) int id,
                                                    @Query( "api_key" ) String apiKey );

} // end interface ApiInterface
