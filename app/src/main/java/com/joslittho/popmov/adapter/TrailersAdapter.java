package com.joslittho.popmov.adapter;

import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Adapter to populate the {@link TrailersGroup} expandable
 */
// begin class TrailersAdapter
public class TrailersAdapter
        extends ExpandableRecyclerViewAdapter< TrailersGroupViewHolder, TrailerChildViewHolder > {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */
    
    /* CONSTRUCTOR */

    // default constructor
    public TrailersAdapter( List< ? extends ExpandableGroup > groups ) {
        super( groups );
    }
    
    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */

    @Override
    // onCreateGroupViewHolder
    public TrailersGroupViewHolder onCreateGroupViewHolder( ViewGroup parent, int viewType ) {
        return null;
    }

    @Override
    public TrailerChildViewHolder onCreateChildViewHolder( ViewGroup parent, int viewType ) {
        return null;
    }

    @Override
    public void onBindChildViewHolder( TrailerChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex ) {

    }

    @Override
    public void onBindGroupViewHolder( TrailersGroupViewHolder holder, int flatPosition, ExpandableGroup group ) {

    }

        
    /* Other Methods */

} // end class TrailersAdapter
