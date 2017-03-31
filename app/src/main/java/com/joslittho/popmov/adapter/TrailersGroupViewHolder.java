package com.joslittho.popmov.adapter;

import android.widget.ImageView;
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

    /* ImageViews */

    public ImageView mDropArrowImageView; // ditto
    
    /* CONSTRUCTOR */

    /** Default constructor. */
    // begin constructor
    public TrailersGroupViewHolder( ImageView dropArrowImageView ) {

        // 0. super stuff
        // 1. initialize members

        // 0. super stuff

        super( dropArrowImageView );

        // 1. initialize members

        mDropArrowImageView = dropArrowImageView;

    } // end constructor
    
    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */
        
    /* Other Methods */

} // end class TrailersGroupViewHolder