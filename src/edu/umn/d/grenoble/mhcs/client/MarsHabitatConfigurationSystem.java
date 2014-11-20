package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.modules.Area;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public class MarsHabitatConfigurationSystem implements EntryPoint {
    AreaRenderer areaRenderer;
    
    public MarsHabitatConfigurationSystem(){
    }
    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        this.areaRenderer = new AreaRenderer(this);
     
    }
    
    //Called by areaRenderer, when the images are preloaded.
    public void Begin() {
        RootPanel.get().add(this.areaRenderer.GetCanvas());
        Area area = new Area();
        Bus.bus.fireEvent(new AreaUpdateEvent(area));
        
        AddModulesPanel thisPanel = new AddModulesPanel();        
        RootPanel.get().add(thisPanel.getAddModulesPanel());
    }
}
