package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

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
    
    public final int TIMER = 864000000;
    
    AreaRenderer areaRenderer;
    WeatherPanel weather;
    SoundOutput soundOutput;
    TabPanel tabPanel;
    DockPanel dockPanel;
    FlowPanel leftPanel;
    Label header;
    
    public MarsHabitatConfigurationSystem(){
    }
    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        this.areaRenderer = new AreaRenderer(this);
        this.weather = new WeatherPanel();
        this.soundOutput = new SoundOutput();
        tabPanel = new TabPanel();
        dockPanel = new DockPanel();
        leftPanel = new FlowPanel();
        header = new Label("Mars Habitat Configuration System");
        header.setStyleName("headerLabel");
        dockPanel.add(header, DockPanel.NORTH);
        
        
        //RootPanel.get().add( this.soundOutput.getMuteButton() );
        
        //Trigger initialization of sounds, so they load before
        //they are used.
        SoundOutput.Sounds.values();
        this.startTimer();
    }
    
    //Called by areaRenderer, when the images are preloaded.
    public void Begin() {
        //RootPanel.get().add(this.areaRenderer.GetCanvas());
        Area area = new Area();
        Bus.bus.fireEvent(new AreaUpdateEvent(area));
        
        AddModulesPanel thisPanel = new AddModulesPanel();               
        this.tabPanel.add(thisPanel.getAddModulesPanel(), new String("Add modules"));
        this.tabPanel.add(new FlowPanel(), "Minimum Configurations");
        //RootPanel.get().add(tabPanel);
        //RootPanel.get().add( this.weather.getWeatherPanel() );
        tabPanel.selectTab(0);
        this.dockPanel.add(this.tabPanel, DockPanel.SOUTH);
        dockPanel.add(this.areaRenderer.GetCanvas(), DockPanel.CENTER);
        leftPanel.add(this.weather.getWeatherPanel());
        leftPanel.add(this.soundOutput.getMuteButton());
        dockPanel.add(leftPanel, DockPanel.EAST);
        RootPanel.get().add(dockPanel);
    }
    
    public void startTimer() {
        
        Timer t = new Timer() {
                
            @Override
            public void run() {
                Window.alert( "                         WARNING! \n" + 
                              " 10 days have passed since the milometer \n" +
                              "device on the lift rover has been calibrated. \n\n" +
                              "   (2 minutes for demonstration purposes)" );
            }
        };

        t.schedule(this.TIMER);
    }
}
