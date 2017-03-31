package com.joslittho.popmov.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.joslittho.popmov.R;
import com.joslittho.popmov.data.model.Result;
import com.joslittho.popmov.databinding.TrailerItemBinding;
import com.joslittho.popmov.databinding.TrailersExpandableGroupHeaderBinding;

import java.util.HashMap;
import java.util.List;

/**
 * Adapter to populate the {@link TrailersGroup} expandable
 *
 * Source: http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */
// begin class TrailersAdapter
public class TrailersAdapter extends BaseExpandableListAdapter {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */

    /* Contexts */

    private Context mContext; // ditto

    // child data in format of <header trailer title, child trailer(s)>
    private HashMap< String, List< Result > > mChildTrailers;

    /* Lists */

    private List< String > mHeaderTitles; // ditto

    /* TrailersAdapterOnClickHandlers */

    public TrailersAdapterOnClickHandler mTrailersAdapterOnClickHandler; // ditto
    
    /* CONSTRUCTOR */

    // begin default constructor
    public TrailersAdapter( Context context, List< String > headerTitles,
                            HashMap< String, List< Result > > childTrailers,
                            TrailersAdapterOnClickHandler handler ) {

        // 0. initialize members

        // 0. initialize members

        mContext = context;

        mHeaderTitles = headerTitles;

        mChildTrailers = childTrailers;

        mTrailersAdapterOnClickHandler = handler;

    } // end default constructor
    
    /* METHODS */
        
    /* Getters and Setters */
        
    /* Overrides */

    /** Gets the data associated with the given child within the given group. */
    @Override
    // begin getChild
    public Object getChild( int groupPosition, int childPosition ) {

        // 0. get the child in child position childPosition of
        // the group in group position groupPosition
        // 0a. get the group title at position groupPosition
        // 0b. get the child group of the group title gotten above
        // 0c. return the child in position childPosition of the child group gotten above

        // 0. get the child in child position childPosition of
        // the group in group position groupPosition

        // 0a. get the group title at position groupPosition

        String currentGroupTitle = mHeaderTitles.get( groupPosition );

        // 0b. get the child group of the group title gotten above

        List< Result > currentChildGroup = mChildTrailers.get( currentGroupTitle );

        // 0c. return the child in position childPosition of the child group gotten above

        return currentChildGroup.get( childPosition );

    } // end getChild

    /**
     * Gets the ID for the given child within the given group. This ID must be unique across all
     * children within the group.
     * */
    @Override
    // getChildId
    // we'll just use the child's position as the child Id
    public long getChildId( int groupPosition, int childPosition ) {
        return childPosition;
    }

    /** Gets a View that displays the data for the given child within the given group. */
    @Override
    // begin getChildView
    public View getChildView( int groupPosition, final int childPosition,
                              boolean isLastChild, View convertView, ViewGroup parent ) {

        // 0. get the title of this child trailer
        // 1. if the child view is not inflated
        // 1a. set it up
        // 2. get reference to the text view which will hold the child's title
        // 3. put the child's title in the text view
        // last. return the child view

        // 0. get the title of this child trailer

        Result childTrailer = ( Result ) getChild( groupPosition, childPosition );
        String childTitle = childTrailer.getName();

        // 1. if the child view is not inflated
        // 1a. set it up

        ConstraintLayout constraintLayout;

        // begin if the converted view does not exist
        if ( convertView == null ) {

            TrailerItemBinding binding = DataBindingUtil.inflate( LayoutInflater.from( mContext ),
                    R.layout.trailer_item, parent, false );

             constraintLayout = binding.trailerItemClParent;

        } // end if the converted view does not exist

        // else the converted view exists and is the constraint layout
        else {
            constraintLayout = ( ConstraintLayout ) convertView;
        }

        // 2. get reference to the text view which will hold the child's title

        TextView childTitleTextView = ( TextView ) constraintLayout.findViewById(
                R.id.trailer_item_tv_name );

        // 3. put the child's title in the text view

        childTitleTextView.setText( childTitle );

        // last. return the child view

        return constraintLayout;

    } // end getChildView

    /** Gets the number of children in a specified group. */
    @Override
    // begin getChildrenCount
    public int getChildrenCount( int groupPosition ) {

        // 0. get the group at groupPosition
        // 1. return the number of children in that group

        // 0. get the group at groupPosition

        String groupTitle = mHeaderTitles.get( groupPosition );

        // 1. return the number of children in that group

        return mChildTrailers.get( groupTitle ).size();

    } // end getChildrenCount

    /** Gets the data associated with the given group. */
    @Override
    // getGroup
    // we return the title of the group at groupPosition
    public Object getGroup( int groupPosition ) {
        return mHeaderTitles.get( groupPosition );
    }

    @Override
    // getGroupCount
    public int getGroupCount() {
        return mHeaderTitles.size();
    }

    @Override
    // getGroupId
    // we'll use the group's position here
    public long getGroupId( int groupPosition ) {
        return groupPosition;
    }

    /** Gets a View that displays the data for the given child within the given group. */
    @Override
    // begin getGroupView
    public View getGroupView( int groupPosition, boolean isExpanded, View convertView,
                              ViewGroup parent ) {

        // 0. get the title of this group
        // 1. if the group view is not inflated
        // 1a. set it up
        // 2. show the group title text
        // last. return the group view

        // 0. get the title of this group

        String groupTitle = ( String ) getGroup( groupPosition );

        // 1. if the child view is not inflated
        // 1a. set it up

        TextView trailerGroupTitleTextView;

        // begin if the converted view does not exist
        if ( convertView == null ) {

            TrailersExpandableGroupHeaderBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from( mContext ),
                    R.layout.trailers_expandable_group_header, parent, false );

            trailerGroupTitleTextView = binding.trailersHeaderTvTitle;

        } // end if the converted view does not exist

        // else the converted view exists and is the text view
        else {
            trailerGroupTitleTextView = ( TextView ) convertView;
        }

        // 2. show the group title text

        trailerGroupTitleTextView.setText( groupTitle );

        // last. return the group view
        return trailerGroupTitleTextView;

    } // end getGroupView

    /**
     * Indicates whether the child and group IDs are stable across changes to the underlying data.
     *
     * @return boolean whether or not the same ID always refers to the same object
     * */
    @Override
    // hasStableIds
    // not sure here. left as false by default
    public boolean hasStableIds() {
        return false;
    }

    /** Whether the child at the specified position is selectable. */
    @Override
    // isChildSelectable
    // for us, yes. we want to respond to trailers being tapped
    public boolean isChildSelectable( int groupPosition, int childPosition ) {
        return true;
    }

    /* Other Methods */

} // end class TrailersAdapter
