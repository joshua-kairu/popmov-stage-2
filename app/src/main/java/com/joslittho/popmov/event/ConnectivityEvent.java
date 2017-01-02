package com.joslittho.popmov.event;

/**
 * Event to check for connectivity. Will be used with {@link org.greenrobot.eventbus.EventBus}.
 */
// begin class ConnectivityEvent
public class ConnectivityEvent {

    /* CONSTANTS */
    
    /* VARIABLES */

    /* Primitives */

    private boolean connected; // ditto

    /* CONSTRUCTOR */

    /* Default constructor.
     *
     * Initialized the connected state.
     * */
    public ConnectivityEvent( boolean connected ) {
        this.connected = connected;
    }

    /* METHODS */
    
    /* Getters and Setters */

    public boolean isConnected() {
        return connected;
    }

    public void setConnected( boolean connected ) {
        this.connected = connected;
    }
    
    /* Overrides */
    
    /* Other Methods */

} // end class ConnectivityEvent
