package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public class MarsHabitatConfigurationSystem implements EntryPoint {
    
    public final int TIMER = 864000000;
    
    AreaRenderer areaRenderer;

    public MarsHabitatConfigurationSystem(){
    }
    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        this.areaRenderer = new AreaRenderer(this); 
//        
//        
//        
        
        //RootPanel.get().add( this.soundOutput.getMuteButton() );
        
        //Trigger initialization of sounds, so they load before
        //they are used.
        SoundOutput.Sounds.values();
        this.startTimer();
    }
    
    //Called by areaRenderer, when the images are preloaded.
    public void Begin() {
        DockPanel dockPanel = new DockPanel();

        { 
            //North
            Label header = new Label("Mars Habitat Configuration System");
            header.setStyleName("headerLabel");
            dockPanel.add(header, DockPanel.NORTH);
        }
        { 
            //East
            FlowPanel eastPanel = new FlowPanel(); 
            eastPanel.add(new WeatherPanel().getWeatherPanel());
            eastPanel.add(new SoundOutput().getMuteButton());
            dockPanel.add(eastPanel, DockPanel.EAST);
        }
        { 
            //South
            final Tab[] tabs = new Tab[] {
                new AddModulesPanel(),
            };
            TabPanel tabPanel = new TabPanel();
            tabPanel.addSelectionHandler(new SelectionHandler<Integer>(){
                @Override
                public void onSelection(final SelectionEvent<Integer> event) {
                    tabs[event.getSelectedItem()].switchedTo();
                }
            });
            for (Tab tab : tabs){
                tabPanel.add(tab.getPanel(), tab.getTabName());
            }
            tabPanel.selectTab(0);
            dockPanel.add(tabPanel, DockPanel.SOUTH);

        }   

        dockPanel.add(this.areaRenderer.GetCanvas(), DockPanel.CENTER);
        RootPanel.get().add(dockPanel);
    }
    
    public void startTimer() {
        Timer t = new Timer() {
            @Override
            public void run() {
                Window.alert( "WARNING! \n" + " 10 days have passed since the milometer \n" +
                              "device on the lift rover has been calibrated." );
            }
        };
        t.schedule(this.TIMER);
    }
}
