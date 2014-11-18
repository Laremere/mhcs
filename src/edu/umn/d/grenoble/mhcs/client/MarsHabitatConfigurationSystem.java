package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MarsHabitatConfigurationSystem implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        AddModulesPanel thisPanel = new AddModulesPanel();
        
        RootPanel.get().add(thisPanel.getAddModulesPanel());
        
        
    }
}