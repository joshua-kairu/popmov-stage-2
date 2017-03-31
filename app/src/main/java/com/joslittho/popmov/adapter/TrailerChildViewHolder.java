package com.joslittho.popmov.adapter;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.joslittho.popmov.data.database.MovieTableColumns;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * {@link ChildViewHolder} to hold individual trailer items in the {@link TrailersGroup} expandable
 */
// begin class TrailerChildViewHolder
public class TrailerChildViewHolder extends ChildViewHolder implements View.OnClickListener {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */

    /* TextViews */

    public TextView mNameTextView; // ditto

    /* TrailersAdapters */

    private final TrailersAdapter mHostTrailersAdapter; // ditto

    /* CONSTRUCTOR */

    // begin constructor
    public TrailerChildViewHolder( TextView nameTextView, TrailersAdapter trailersAdapter ) {

        // 0. super stuff
        // 1. initialize members

        // 0. super stuff

        super( nameTextView );

        // 1. initialize members

        mNameTextView = nameTextView;

        mHostTrailersAdapter = trailersAdapter;

    } // end constructor
    
    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */

    @Override
    // begin onClick
    public void onClick( View view ) {

        // 0. get the adapter position since this is the position of the trailer
        // 1. call the handler with the adapter position

        // 0. get the adapter position since this is the position of the trailer

        int position = getAdapterPosition();

        // 1. call the handler with the adapter position

        mHostTrailersAdapter.mTrailersAdapterOnClickHandler.onClick( position );

    } // end onClick

    /* Other Methods */

} // end class TrailerChildViewHolder
