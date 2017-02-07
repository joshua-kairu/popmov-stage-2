package com.joslittho.popmov.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

/**
 * An {@link android.app.Application} instance to assist with {@link com.facebook.stetho.Stetho}.
 *
 * Thanks to these two sites for explanations:
 * a) https://code.tutsplus.com/tutorials/debugging-android-apps-with-facebooks-stetho--cms-24205
 * b) http://facebook.github.io/stetho/
 * c) https://developer.android.com/studio/build/multidex.html#mdex-gradle - Multi-dex usage
 */
// begin class StethoApplication
public class StethoApplication extends MultiDexApplication {

    /* CONSTANTS */
    
    /* Integers */
    
    /* Strings */

    /* VARIABLES */
    
    /* CONSTRUCTOR */
    
    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */

//    @Override
//    // begin attachBaseContext
//    protected void attachBaseContext( Context base ) {
//
//        // 0. super stuff
//        // 1. set up multidex
//
//        // 0. super stuff
//
//        super.attachBaseContext( base );
//
//        // 1. set up multidex
//
//        MultiDex.install( this );
//
//    } // end attachBaseContext

    @Override
    // begin onCreate
    public void onCreate() {

        // 0. super stuff
        // 1. initialize stetho with defaults

        // 0. super stuff

        super.onCreate();

        // 1. initialize stetho with defaults

        Stetho.initializeWithDefaults( this );

    } // end onCreate

    /* Other Methods */

} // end class StethoApplication
