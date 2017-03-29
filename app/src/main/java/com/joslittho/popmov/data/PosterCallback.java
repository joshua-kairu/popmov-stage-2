package com.joslittho.popmov.data;

import android.net.Uri;

/**
 * A callback interface that all activities containing the {@link com.joslittho.popmov.fragment.PostersFragment}
 * should have. This mechanism allows activities to be informed of item selections
 */
// begin interface PosterCallback
public interface PosterCallback {
    
    /* METHODS */

    /**
     * {@link com.joslittho.popmov.fragment.DetailFragment} callback for when an item has been selected.
     *
     * @param movieUri {@link Uri} for the movie.
     * */
    void onPosterItemSelected( Uri movieUri );

} // end interface PosterCallback
