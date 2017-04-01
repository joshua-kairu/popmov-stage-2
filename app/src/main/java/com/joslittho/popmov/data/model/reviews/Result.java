package com.joslittho.popmov.data.model.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * A result of searching for reviews. This contains the details for a single review.
 */
// begin class Result
public class Result {

    /* CONSTANTS */
    
    /* Integers */
        
    /* Strings */
    
    /* VARIABLES */

    @SerializedName( "id" )
    @Expose
    private String id;
    @SerializedName( "author" )
    @Expose
    private String author;
    @SerializedName( "content" )
    @Expose
    private String content;
    @SerializedName( "url" )
    @Expose
    private String url;
    
    /* CONSTRUCTOR */

    /**
     * No args constructor for use in serialization
     */
    public Result() {
    }

    /**
     * @param content
     * @param id
     * @param author
     * @param url
     */
    public Result( String id, String author, String content, String url ) {
        super();
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    /* METHODS */
        
    /* Getters and Setters */

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor( String author ) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }
        
    /* Overrides */
        
    /* Other Methods */

} // end class Result
