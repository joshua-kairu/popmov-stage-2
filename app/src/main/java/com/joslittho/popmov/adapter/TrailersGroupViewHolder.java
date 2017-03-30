package com.joslittho.popmov.adapter;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * {@link com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder} to hold the group of
 * trailers viewed in the trailers expandable list
 */
// begin class TrailersGroupViewHolder
public class TrailersGroupViewHolder extends GroupViewHolder {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */

    /* TextViews */

    public TextView mTitleTextView; // ditto
    
    /* CONSTRUCTOR */

    /** Default constructor. */
    // begin constructor
    public TrailersGroupViewHolder( TextView titleTextView ) {

        // 0. super stuff
        // 1. initialize members

        // 0. super stuff

        super( titleTextView );

        // 1. initialize members

        mTitleTextView = titleTextView;

    } // end constructor
    
    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */
        
    /* Other Methods */

} // end class TrailersGroupViewHolder