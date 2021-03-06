package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.user.client.ui.Widget;

public abstract class Tab {
    boolean isCurrent = false;
    
    abstract Widget getPanel();
    abstract void switchedTo();
    abstract String getTabName();
}
