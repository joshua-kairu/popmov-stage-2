/*
 *
 * PopMov
 *
 * An Android app to show the latest movies from https://www.themoviedb.org.
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

package com.joslittho.popmov.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.joslittho.popmov.R;
import com.joslittho.popmov.data.PosterCallback;
import com.joslittho.popmov.data.Utility;
import com.joslittho.popmov.databinding.ActivityMainBinding;
import com.joslittho.popmov.fragment.PostersFragment;
import com.joslittho.popmov.sync.MoviesSyncAdapter;

/**
 * The landing activity
 * */
// begin activity MainActivity
public class MainActivity extends AppCompatActivity implements PosterCallback {

    /* CONSTANTS */
    
    /* Integers */
    
    /* Strings */
    
    /* VARIABLES */

    /* Primitives */

    private boolean mTwoPane; // ditto

    /* Strings */

    private String mCurrentSortOrder; // ditto

    /* METHODS */

    /* Getters and Setters */

    /* Overrides */

    @Override
    // begin onCreate
    protected void onCreate( Bundle savedInstanceState ) {

        // 0. preliminaries
        // 0a. super things
        // 0b. store current sort order
        // 1. use the main activity layout
        // 2. the posters fragment is added in XML
        // 3. initialize the sync adapter
        // 4. if we are in two pane mode
        // 4a. set the two pane primitive to true
        // 5. else we are not in two pane mode
        // 5a. set the two pane primitive to false

        // 0. preliminaries

        // 0a. super things

        super.onCreate( savedInstanceState );

        // 0b. store current sort order

        mCurrentSortOrder = Utility.getPreferredSortOrder( this );

        // 0c. get main activity binding -> done when initializing layout

        // 1. use the main activity layout

        ActivityMainBinding binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        // 2. the posters fragment is added in XML

        // 3. initialize the sync adapter

        MoviesSyncAdapter.initializeSyncAdapter( this );

        // 4. if we are in two pane mode

        // begin if there is the detail container
        if ( binding.detailFlContainer != null ) {

            // 4a. set the two pane primitive to true

            mTwoPane = true;

        } // end if there is the detail container

        // 5. else we are not in two pane mode

        // begin else there is no detail container
        else {

            // 5a. set the two pane primitive to false

            mTwoPane = false;

        } // end else there is no detail container

    } // end onCreate

    @Override
    // begin onCreateOptionsMenu
    public boolean onCreateOptionsMenu( Menu menu ) {

        // 0. super stuff
        // 1. use the main activity menu

        // 0. super stuff

        super.onCreateOptionsMenu( menu );

        // 1. use the main activity menu

        getMenuInflater().inflate( R.menu.menu_activity_main, menu );

        return true;

    } // end onCreateOptionsMenu

    @Override
    // begin onOptionsItemSelected
    public boolean onOptionsItemSelected( MenuItem item ) {

        // 0. if the settings is selected
        // 0a. switch to the settings activity (for phones for now)
        // 0last. return true
        // last. return super

        // 0. if the settings is selected

        // begin if settings is selected
        if ( item.getItemId() == R.id.action_settings ) {

            // 0a. switch to the settings activity (for phones for now)

            startActivity( new Intent( this, SettingsActivity.class ) );

            // TODO: 10/14/16 do the replacing for a tablet layout

            // 0last. return true

            return true;

        } // end if settings is selected

        // last. return super

        return super.onOptionsItemSelected( item );

    } // end onOptionsItemSelected

    @Override
    // begin onResume
    protected void onResume() {

        // 0. super stuff
        // 1. if the sort order has changed
        // 1a. tell the poster fragment so

        // 0. super stuff

        super.onResume();

        // 1. if the sort order has changed

        // begin if sort order is different
        if ( ! mCurrentSortOrder.equals( Utility.getPreferredSortOrder( this ) ) ) {

            // 1a. tell the poster fragment so

            PostersFragment fragment = ( PostersFragment ) getSupportFragmentManager()
                    .findFragmentByTag( PostersFragment.POSTER_FRAGMENT_TAG );

            if ( fragment != null ) { fragment.onSortOrderChanged(); }

        } // end if sort order is different

    } // end onResume

    @Override
    // begin onPosterItemSelected
    public void onPosterItemSelected( Uri movieUri ) {

        // 0. if we are in two pane
        // 1. otherwise we are in one pane
        // 1a. start the detail activity

        // 0. if we are in two pane

        // begin if two pane
        if ( mTwoPane ) {



        } // end if two pane

        // 1. otherwise we are in one pane

        // begin else one pane
        else {

            // 1a. start the detail activity

            startActivity( new Intent( this, DetailActivity.class ).setData( movieUri ) );

        } // end else one pane

    } // end onPosterItemSelected

    /* Other Methods */
    
    /* INNER CLASSES */

} // end activity MainActivity