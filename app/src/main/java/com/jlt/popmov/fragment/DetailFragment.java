/*
 * 
 * com.jlt.popmov.fragment
 * 
 * <one line to give the program's name and a brief idea of what it does.>
 * 
 * Copyright (C) 2016 Kairu Joshua Wambugu
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 */

package com.jlt.popmov.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jlt.popmov.R;
import com.jlt.popmov.data.Utility;
import com.jlt.popmov.data.model.Movie;
import com.jlt.popmov.databinding.FragmentDetailBinding;
import com.squareup.picasso.Picasso;

/**
 * {@link Fragment} to show the details of a selected movie
 * */
// begin fragment DetailFragment
public class DetailFragment extends Fragment {
    
    /* CONSTANTS */
    
    /* Integers */
    
    /* Strings */

    /** Key for the {@link Movie} bundle item. */
    public static final String ARGUMENT_MOVIE = "ARGUMENT_MOVIE";

    /* VARIABLES */

    /* Movies */

    private Movie mMovie; // ditto

    /* CONSTRUCTOR */

    // empty constructor for fragment subclasses
    public DetailFragment() {
    }
    
    /* METHODS */
    
    /* Getters and Setters */
    
    /* Overrides */

    @Override
    // begin onCreateView
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        // 0. get the movie from the arguments
        // 1. use the detail fragment layout
        // 2. show the movie details
        // last. return the inflated view

        // 0. get the movie from the arguments

        if ( getArguments() != null ) { mMovie = getArguments().getParcelable( ARGUMENT_MOVIE ); }

        // 1. use the detail fragment layout

        FragmentDetailBinding binding = DataBindingUtil.inflate( LayoutInflater.from( getActivity() ),
                R.layout.fragment_detail, container, false );

        // 2. show the movie details

        binding.detailTvTitle.setText( mMovie.getTitle() );

        Picasso.with( getActivity() ).load( Utility.getPosterUri( mMovie.getPosterPath() ) )
                .placeholder( R.color.primary_dark ).into( binding.detailIvPoster );

        binding.detailTvDate.setText( Utility.getFormattedReleaseDate( mMovie.getReleaseDate() ) );

        binding.detailTvVoteAverage.setText(
                Utility.getFormattedUserRating( getActivity(), mMovie.getUserRating()  ) );

        binding.detailTvSynopsis.setText( mMovie.getSynopsis() );

        // last. return the inflated view

        return binding.getRoot();

    } // end onCreateView
    
    /* Other Methods */

    /* statics */

    // begin method newInstance
    public static DetailFragment newInstance( Movie movie ) {

        // 0. put the movie in a bundle
        // 1. create a fragment with that bundle

        // 0. put the movie in a bundle

        Bundle bundle = new Bundle();

        bundle.putParcelable( ARGUMENT_MOVIE, movie );

        // 1. create a fragment with that bundle

        DetailFragment detailFragment = new DetailFragment();

        detailFragment.setArguments( bundle );

        return detailFragment;

    } // end method newInstance
    
    /* INNER CLASSES */

} // end fragment DetailFragment