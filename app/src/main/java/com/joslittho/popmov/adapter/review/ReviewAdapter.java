package com.joslittho.popmov.adapter.review;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.joslittho.popmov.R;
import com.joslittho.popmov.data.model.reviews.Result;
import com.joslittho.popmov.databinding.ReviewItemBinding;

import java.util.List;

/**
 * Adapter to populate the trailers {@link RecyclerView}
 */
// begin class ReviewAdapter
public class ReviewAdapter extends RecyclerView.Adapter< ReviewViewHolder > {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */

    /* Lists */

    private List< Result > mReviews; // ditto
    
    /* CONSTRUCTOR */

    // begin constructor
    public ReviewAdapter( List< Result > reviews ) {

        // 0. initialize members

        // 0. initialize members

        this.mReviews = reviews;

    } // end constructor

    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */

    @Override
    // begin onCreateViewHolder
    public ReviewViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        // 0. inflate the correct layout

        ReviewItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from( parent.getContext() ), R.layout.review_item, parent, false );

        // last. return the inflated layout in a view holder

        return new ReviewViewHolder( binding );

    } // end onCreateViewHolder

    @Override
    // begin onBindViewHolder
    public void onBindViewHolder( ReviewViewHolder holder, int position ) {

        // 0. read the review content from the reviews list
        // 1. display the review content

        // 0. read the review content from the reviews list

        String reviewContent = mReviews.get( position ).getContent();

        // 1. display the review content

        holder.mReviewContentTextView.setText( reviewContent );

    } // end onBindViewHolder

    @Override
    // getItemCount
    public int getItemCount() {
        return mReviews != null ? mReviews.size() : 0;
    }

    /* Other Methods */

} // end class ReviewAdapter
