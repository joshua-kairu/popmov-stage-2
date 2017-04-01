package com.joslittho.popmov.adapter.review;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.joslittho.popmov.databinding.ReviewItemBinding;

/**
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} for a review
 */
// begin class ReviewViewHolder
public class ReviewViewHolder extends RecyclerView.ViewHolder {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */

    /* TextViews */

    public TextView mReviewContentTextView; // ditto
    
    /* CONSTRUCTOR */

    // begin constructor
    public ReviewViewHolder( ReviewItemBinding binding ) {

        // 0. super stuff
        // 1. initialize members

        // 0. super stuff

        super( binding.reviewItemClParent );

        // 1. initialize members

        mReviewContentTextView = binding.reviewItemTvContent;

    } // end constructor

    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */
        
    /* Other Methods */

} // end class ReviewViewHolder
