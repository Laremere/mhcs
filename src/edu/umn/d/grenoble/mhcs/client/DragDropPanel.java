package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import edu.umn.d.grenoble.mhcs.bus.AreaClickEvent;
import edu.umn.d.grenoble.mhcs.bus.AreaClickEvent.MouseType;
import edu.umn.d.grenoble.mhcs.bus.AreaClickEventHandler;
import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;
import edu.umn.d.grenoble.mhcs.modules.Orientation;
import edu.umn.d.grenoble.mhcs.modules.Status;

public class DragDropPanel extends Tab {

    ListBox selectionBox;
    Button selectButton;
    Button saveButton;
    TextBox saveArea;
    
    FlowPanel dragDropPanel;
    Area area1;
    
    
    public DragDropPanel() {
        this.selectionBox = new ListBox();
        this.selectButton = new Button("Submit");
        this.saveButton = new Button("Save new config");
        saveArea = new TextBox();
        this.dragDropPanel = new FlowPanel();
        
        this.dragDropPanel.add(this.selectionBox);
        this.dragDropPanel.add(this.selectButton);
        this.dragDropPanel.add(this.saveArea);
        this.dragDropPanel.add(this.saveButton);

        
        this.selectButton.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                String[] areas = AreaHolder.getAreas();
                area1 = AreaHolder.getArea(areas[selectionBox.getSelectedIndex()]);
                Bus.bus.fireEvent( new AreaUpdateEvent(area1) );
            }
        });
        
        this.saveButton.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                AreaHolder.saveArea(saveArea.getText(), area1);
                saveArea.setText("");
            }
        });
        
        final DragDropPanel dragDropPanel = this;
        Bus.bus.addHandler(AreaClickEvent.TYPE, new AreaClickEventHandler() {
            Module currentModule;
            
            @Override
            public void onEvent(final AreaClickEvent event) {
                if(dragDropPanel.isCurrent){
                MouseType type = event.getMouseType();
                
                
                if(type == MouseType.Pressed){
                    currentModule = area1.occupied(event.getX(), event.getY());
                }
                else if(type == MouseType.Dragged){
                    if(currentModule != null){
                    if(area1.occupied(event.getX(), event.getY()) == null){
                        currentModule.setX(event.getX());
                        currentModule.setY(event.getY());
                        Bus.bus.fireEvent( new AreaUpdateEvent(area1) );
                    }
                    }
                    
                }
                else if(type == MouseType.Released){
                    //currentModule.setX(event.getX());
                    //currentModule.setY(event.getY());
                    //Bus.bus.fireEvent(new AreaUpdateEvent(area1));
                }
                
               
            }  
            }
        });        
        
    }
    @Override
    Widget getPanel() {
        
        return dragDropPanel;
    }

    @Override
    void switchedTo() {
        this.selectionBox.clear();
        
        String[] areas = AreaHolder.getAreas();
        if(areas.length == 0){
            this.selectionBox.addItem("Nothing");
        }
        for(String name : areas){
            this.selectionBox.addItem(name);
        }
        
    }

    @Override
    String getTabName() {
        
        return "Drag and Drop: ";
    }

}
