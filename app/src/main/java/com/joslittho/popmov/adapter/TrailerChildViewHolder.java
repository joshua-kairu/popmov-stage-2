package com.joslittho.popmov.adapter;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * {@link ChildViewHolder} to hold individual trailer items in the {@link TrailersGroup} expandable
 */
// begin class TrailerChildViewHolder
public class TrailerChildViewHolder extends ChildViewHolder {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */

    public TextView mNameTextView; // ditto

    /* CONSTRUCTOR */

    // begin constructor
    public TrailerChildViewHolder( TextView nameTextView ) {

        // 0. super stuff
        // 1. initialize members

        // 0. super stuff

        super( nameTextView );

        // 1. initialize members

        mNameTextView = nameTextView;

    } // end constructor
    
    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */
        
    /* Other Methods */

} // end class TrailerChildViewHolder
