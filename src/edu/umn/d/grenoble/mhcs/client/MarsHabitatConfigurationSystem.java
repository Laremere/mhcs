package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
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
    
    public final int TIMER = 30000;
    
    AreaRenderer areaRenderer;
    WeatherPanel weather;
    SoundOutput soundOutput;
    
    public MarsHabitatConfigurationSystem(){
    }
    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        this.areaRenderer = new AreaRenderer(this);
        this.weather = new WeatherPanel();
        this.soundOutput = new SoundOutput();
        
        RootPanel.get().add( this.soundOutput.getMuteButton() );
        
        //Trigger initialization of sounds, so they load before
        //they are used.
        SoundOutput.Sounds.values();
        this.startTimer();
    }
    
    //Called by areaRenderer, when the images are preloaded.
    public void Begin() {
        RootPanel.get().add(this.areaRenderer.GetCanvas());
        Area area = new Area();
        Bus.bus.fireEvent(new AreaUpdateEvent(area));
        
        AddModulesPanel thisPanel = new AddModulesPanel();        
        RootPanel.get().add(thisPanel.getAddModulesPanel());
        RootPanel.get().add( this.weather.getWeatherPanel() );
    }
    
    public void startTimer() {
        
        Timer t = new Timer() {
                
            @Override
            public void run() {
                Window.alert( "                         WARNING! \n" + 
                              " 10 days have passed since the milometer \n" +
                              "device on the lift rover has been calibrated. \n\n" +
                              "  (30 seconds for demonstration purposes)" );
            }
        };

        t.schedule(TIMER);
    }
}
