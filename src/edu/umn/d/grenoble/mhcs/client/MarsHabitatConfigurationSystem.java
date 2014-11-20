package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

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
        final int NumberOfTestModules = 10;
        for (int i = 1; i <= NumberOfTestModules; i+=1) {
            Module module = new Module();
            module.setX(i);
            module.setY(i);
            module.setId(i);
            module.setOrientation(Orientation.UPRIGHT);
            module.setStatus(Status.GOOD);
            area.addModule(module);
        }
        this.areaRenderer.RenderArea(area);
        
        AddModulesPanel thisPanel = new AddModulesPanel();        
        RootPanel.get().add(thisPanel.getAddModulesPanel());
    }
}
