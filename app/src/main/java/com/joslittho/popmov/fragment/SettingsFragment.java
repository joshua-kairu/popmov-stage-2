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

package com.joslittho.popmov.fragment;

import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.joslittho.popmov.R;

/**
 * The settings fragment
 * */
// begin preference activity SettingsFragment
public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    /* CONSTANTS */
    
    /* Integers */
    
    /* Strings */
    
    /* VARIABLES */

    /* METHODS */

    /* Getters and Setters */

    /* Overrides */

    @Override
    // begin onCreatePreferences
    public void onCreatePreferences( Bundle savedInstanceState, String rootKey ) {

        // 0. use the resource xml
        // 1. display summaries

        // 0. use the resource xml

        addPreferencesFromResource( R.xml.pref_general );

        // 1. display summaries

        bindPreferenceSummaryToValue( findPreference( getString( R.string.pref_sort_order_key ) ) );

    } // end onCreatePreferences

    @Override
    // begin onPreferenceChange
    public boolean onPreferenceChange( Preference changedPreference, Object newValue ) {

        // 0. get the new preference value in string form
        // 1. if the changed preference was a list
        // 1a. check the correct value to display from the list's entries
        // last. terminate

        // 0. get the new preference value in string form

        String preferenceString = newValue.toString();

        // 1. if the changed preference was a list

        // begin if the changed preference was list
        if ( changedPreference instanceof ListPreference ) {

            // 1a. check the correct value to display from the list's entries

            ListPreference listPreference = ( ListPreference ) changedPreference;

            // findIndexOfValue - find the index of passed value in the entry values array
            int preferenceIndex = listPreference.findIndexOfValue( preferenceString );

            if ( preferenceIndex >= 0 ) {
                changedPreference.setSummary( listPreference.getEntries()[ preferenceIndex ] );
            }

        } // end if the changed preference was list

        // last. terminate

        return true;

    } // end onPreferenceChange
    
    /* Other Methods */

    /**
     * Attaches a listener so that the summary is always updated with the preference's value.
     * Also fires the listener once, to initialize the summary
     * (so that the summary shows up before it is changed)
     */
    // begin method bindPreferenceSummaryToValue
    private void bindPreferenceSummaryToValue( Preference preference ) {

        // 0. set the listener to watch for value changes
        // 1. trigger the listener immediately with the preference's current value

        // 0. set the listener to watch for value changes

        preference.setOnPreferenceChangeListener( this );

        // 1. trigger the listener immediately with the preference's current value

        onPreferenceChange(
                preference,
                PreferenceManager
                        .getDefaultSharedPreferences( preference.getContext() )
                        .getString( preference.getKey(), "" )
        );

    } // end method bindPreferenceSummaryToValue    

} // end preference activity SettingsFragment