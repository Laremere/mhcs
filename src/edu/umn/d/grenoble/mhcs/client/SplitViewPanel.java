package edu.umn.d.grenoble.mhcs.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;

public class SplitViewPanel extends Tab {
    
    ListBox listBox1;
    ListBox listBox2;
    Button submitButton;
    FlowPanel splitViewPanel;
    Label label;
    
    /**
     * Constructor for SplitViewPanel
     */
    public SplitViewPanel(){
        this.listBox1 = new ListBox();
        this.listBox2 = new ListBox();
        this.submitButton = new Button("Submit");
        this.label = new Label(" ");
        
        this.splitViewPanel = new FlowPanel();

        
        this.splitViewPanel.add(this.listBox1);
        this.splitViewPanel.add(this.listBox2);
        this.splitViewPanel.add(this.submitButton);
        this.splitViewPanel.add(this.label);
        
        final SplitViewPanel splitViewPanel = this;
        
        /**
         * Adds listener to submit button
         * Fires area update event to area renderer
         */
        this.submitButton.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                
                String[] areas = AreaHolder.getAreas();
                //Gets appropriate areas from list and fires event.
                Area area1 = AreaHolder.getArea(areas[splitViewPanel.listBox1.getSelectedIndex()]);
                Area area2 = AreaHolder.getArea(areas[splitViewPanel.listBox2.getSelectedIndex()]);
                label.setText( "Size of moving task: " + ( compare(area1, area2) ) * 5 + " meters" );
                Bus.bus.fireEvent( new AreaUpdateEvent( area1, area2 ));
            }
        });
        
        
    }

    @Override
    Widget getPanel() {
        return this.splitViewPanel;
    }

    @Override
    /**
     * Event fired when tab is switched to in UI
     */
    void switchedTo() {
        this.listBox1.clear();
        this.listBox2.clear();
        
        
        String[] areas = AreaHolder.getAreas();
        if(areas.length == 0){
            this.listBox1.addItem("Nothing");
            this.listBox2.addItem("Nothing");
        }
        //Cycles through list of areas and adds to list boxes
        for(String name : areas){
            if(name == null || name.equals("")){}
            else{
                this.listBox1.addItem(name);
                this.listBox2.addItem(name);
            }
        }
        
    }

    @Override
    String getTabName() {        
        return "Split View";
    }
    
    private int compare(Area area1, Area area2) {
        List<Module> list1 = area1.getModules();
        List<Module> list2 = area2.getModules();
        int distance = 0;
        
        for ( Module m1 : list1 ) {
            for ( Module m2 : list2 ) {
                if ( m1.getId() == m2.getId() ) {
                    distance += Math.abs( m1.getX() - m2.getX() );
                    distance += Math.abs( m1.getY() - m2.getY() );
                }
            }
        }
        
        return distance;    
    }
    
}
