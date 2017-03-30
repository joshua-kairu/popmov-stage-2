package com.joslittho.popmov.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client for {@link retrofit2.Retrofit}.
 *
 * Credit: http://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
 */
// begin class ApiClient
public class ApiClient {

    /* CONSTANTS */
    
    /* Retrofits */

    private static Retrofit retrofit = null; // ditto

    /* Strings */

    public static final String BASE_URL = "http://api.themoviedb.org/3/"; // ditto
    
    /* VARIABLES */
    
    /* CONSTRUCTOR */
    
    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */
        
    /* Other Methods */

    /* statics */

    /** Returns a {@link Retrofit} client for the given {@link ApiClient#BASE_URL}. */
    // begin method getClient
    public static Retrofit getClient() {

        // 0. if the retrofit client isn't initialized yet
        // 0a. build it using
        // 0a0. the base url
        // 0a1. a gson converter factory
        // 1. otherwise the retrofit client is initialized
        // 1a. return the retrofit client

        // 0. if the retrofit client isn't initialized yet

        if ( retrofit == null ) {

            // 0a. build it using

            retrofit = new Retrofit.Builder()

            // 0a0. the base url

                    .baseUrl( BASE_URL )

            // 0a1. a gson converter factory

                    .addConverterFactory( GsonConverterFactory.create() )
                    .build();
        }

        // 1. otherwise the retrofit client is initialized
        // 1a. return the retrofit client

        return retrofit;

    } // end method getClient

} // end class ApiClient