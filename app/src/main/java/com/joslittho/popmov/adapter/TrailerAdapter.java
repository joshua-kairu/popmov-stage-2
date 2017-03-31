package com.joslittho.popmov.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.joslittho.popmov.R;
import com.joslittho.popmov.data.model.Result;
import com.joslittho.popmov.databinding.TrailerItemBinding;

import java.util.List;

/**
 * Adapter to populate the trailers {@link RecyclerView}
 */
// begin class TrailerAdapter
public class TrailerAdapter extends RecyclerView.Adapter< TrailerViewHolder > {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */

    /* Contexts */

    private Context mContext; // ditto

    /* Lists */

    private List< Result > mTrailers; // ditto

    /* TrailerAdapterOnClickHandlers */

    public TrailerAdapterOnClickHandler mTrailerAdapterOnClickHandler; // ditto

    /* CONSTRUCTOR */

    // begin default constructor
    public TrailerAdapter( Context context, List< Result > trailers,
                           TrailerAdapterOnClickHandler handler ) {

        // 0. initialize members

        // 0. initialize members

        mContext = context;

        mTrailers = trailers;

        mTrailerAdapterOnClickHandler = handler;

    } // end default constructor
    
    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */

    @Override
    // begin onCreateViewHolder
    public TrailerViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        // 0. inflate the correct layout
        // last. return the inflated layout in a view holder

        // 0. inflate the correct layout

        TrailerItemBinding binding = DataBindingUtil.inflate( LayoutInflater.from( mContext ),
                R.layout.trailer_item, parent, false );

        // last. return the inflated layout in a view holder

        return new TrailerViewHolder( binding, this );

    } // end onCreateViewHolder

    @Override
    // begin onBindViewHolder
    public void onBindViewHolder( TrailerViewHolder holder, int position ) {

        // 0. read the trailer title from the trailers list
        // 1. display the trailer title

        // 0. read the trailer title from the trailers list

        String currentTrailerTitle = mTrailers.get( position ).getName();

        // 1. display the trailer title

        holder.mTrailerNameTextView.setText( currentTrailerTitle );

    } // end onBindViewHolder

    @Override
    // getItemCount
    public int getItemCount() {
        return mTrailers.size();
    }

    /* Other Methods */

} // end class TrailerAdapter
