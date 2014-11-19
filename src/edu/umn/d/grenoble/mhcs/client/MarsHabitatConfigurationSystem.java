package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;
import edu.umn.d.grenoble.mhcs.modules.Orientation;
import edu.umn.d.grenoble.mhcs.modules.Status;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public class MarsHabitatConfigurationSystem implements EntryPoint {
    AreaRenderer areaRenderer;
    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        areaRenderer = new AreaRenderer(this);
     
    }
    
    //Called by areaRenderer, when the images are preloaded.
    public void Begin() {
        RootPanel.get().add(areaRenderer.GetCanvas());
        Area area = new Area();
        for (int i = 1; i <= 10; i++) {
            Module module = new Module();
            module.xposition = i;
            module.yposition = i;
            module.id = i;
            module.orientation = Orientation.Upright;
            module.status = Status.Good;
            area.addModule(module);
        }
        areaRenderer.RenderArea(area);
        
        AddModulesPanel thisPanel = new AddModulesPanel();        
        RootPanel.get().add(thisPanel.getAddModulesPanel());
    }
}