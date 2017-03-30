package com.joslittho.popmov.adapter;

import com.joslittho.popmov.data.model.Result;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * The {@link com.thoughtbot.expandablerecyclerview.models.ExpandableGroup} that will hold
 * {@link com.joslittho.popmov.data.model.Result}s
 */
// begin class TrailersGroup
public class TrailersGroup extends ExpandableGroup< Result > {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */
    
    /* CONSTRUCTOR */

    // default constructor
    public TrailersGroup( String title, List< Result > items ) {
        super( title, items );
    }
    
    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */
        
    /* Other Methods */

} // begin end TrailersGroup
