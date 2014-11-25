package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import edu.umn.d.grenoble.mhcs.bus.AreaClickEvent;
import edu.umn.d.grenoble.mhcs.bus.AreaClickEventHandler;
import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEventHandler;
import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;
import edu.umn.d.grenoble.mhcs.modules.Orientation;
import edu.umn.d.grenoble.mhcs.modules.Status;

public class AddModulesPanel {

    private Area moduleList;
    private FlexTable thisPanel;
    private Button submitButton;
    private Button cancelButton;
    private TextBox coorX;
    private TextBox coorY; 
    private TextBox moduleNumber;
    private Label coorXLabel; 
    private Label coorYLabel;
    private Label moduleNumberLabel;
    private ListBox orientation;
    private ListBox condition;
    private Label orientationLabel;
    private Label conditionLabel;
    private Module moduleToEdit;
    
    public AddModulesPanel() {
        
        this.moduleList = new Area();
        this.thisPanel = new FlexTable();
        
        this.submitButton = new Button("Submit");
        this.cancelButton = new Button("Cancel/Clear");
        
        this.coorXLabel = new Label("X Position:");
        this.coorYLabel = new Label("Y Position:");
        this.moduleNumberLabel = new Label("ID Number:");
        this.conditionLabel = new Label("Module Condition:");
        this.orientationLabel = new Label("Orientation:");
        
        this.coorX = new TextBox();
        this.coorY = new TextBox();
        this.moduleNumber = new TextBox();
        
        this.orientation = new ListBox();
        for(Orientation o : Orientation.values()){
            this.orientation.addItem(o.name());
        }

        this.condition = new ListBox();
        for(Status s : Status.values()){
            this.condition.addItem(s.name());
        }
        
        this.thisPanel.setWidget(0, 0, this.coorXLabel);  
        this.thisPanel.setWidget(0, 1, this.coorX);
        this.thisPanel.setWidget(1, 0, this.coorYLabel);
        this.thisPanel.setWidget(1, 1, this.coorY);
        this.thisPanel.setWidget(0, 3, this.moduleNumberLabel);
        this.thisPanel.setWidget(0, 4, this.moduleNumber);
        this.thisPanel.setWidget(0, 6, this.conditionLabel);
        this.thisPanel.setWidget(0, 7, this.condition);
        this.thisPanel.setWidget(0, 9, this.submitButton);
        this.thisPanel.setWidget(1, 3, this.orientationLabel);
        this.thisPanel.setWidget(1, 4, this.orientation);
        this.thisPanel.setWidget(1, 9, this.cancelButton);
        
        final AddModulesPanel addModulesPanel = this;
        
        this.submitButton.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                
                final String INVALID = "Invalid entry";
                final String MODULE_ADDED = "Module added \n";
                final String MODULES_LOGGED = " module(s) have been logged";
                
                if ( moduleToEdit == null ) {
                    Module currentModule = new Module();
                    currentModule.setId( Integer.parseInt( addModulesPanel.moduleNumber.getText() ) );
                    currentModule.setX( Integer.parseInt( addModulesPanel.coorX.getText() ) );
                    currentModule.setY( Integer.parseInt( addModulesPanel.coorY.getText() ) );
                    currentModule.setOrientation( Orientation.values()[ orientation.getSelectedIndex() ] );
                    currentModule.setStatus( Status.values()[ condition.getSelectedIndex() ] );
                    if ( currentModule.isValid() ) {                    
                        addModulesPanel.moduleList.addModule(currentModule);
                        Bus.bus.fireEvent( new AreaUpdateEvent(addModulesPanel.moduleList) );
                        Window.alert( MODULE_ADDED + addModulesPanel.moduleList.getModules().size() 
                                + MODULES_LOGGED );
                        addModulesPanel.clearPanel();
                    }
                    else {
                        Window.alert(INVALID);
                    }                    
                }
                else {
                    moduleToEdit.setId( Integer.parseInt( addModulesPanel.moduleNumber.getText() ) );
                    moduleToEdit.setX( Integer.parseInt( addModulesPanel.coorX.getText() ) );
                    moduleToEdit.setY( Integer.parseInt( addModulesPanel.coorY.getText() ) );
                    moduleToEdit.setOrientation( Orientation.values()[ orientation.getSelectedIndex() ] );
                    moduleToEdit.setStatus( Status.values()[ condition.getSelectedIndex() ] );
                    if ( moduleToEdit.isValid() ) {                        
                        Bus.bus.fireEvent( new AreaUpdateEvent(addModulesPanel.moduleList) );
                        Window.alert( MODULE_ADDED + addModulesPanel.moduleList.getModules().size() 
                                + MODULES_LOGGED );
                        addModulesPanel.clearPanel();
                    }
                    else {
                        Window.alert(INVALID);
                    }                         
                }
            }
        });
          
        this.cancelButton.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                addModulesPanel.clearPanel();
            }
        });
        
        Bus.bus.addHandler(AreaClickEvent.TYPE, new AreaClickEventHandler() {
            
            @Override
            public void onEvent(final AreaClickEvent event) {
                
                moduleToEdit = moduleList.occupied( event.getX(), event.getY() );
                coorX.setText( Integer.toString( moduleToEdit.getX() ) );
                coorY.setText( Integer.toString( moduleToEdit.getY() ) );
                moduleNumber.setText( Integer.toString( moduleToEdit.getId() ) );
                
                for (int i = 0; i < Orientation.values().length; i += 1) {
                    if ( Orientation.values()[i].equals( moduleToEdit.getOrientation() ) ) {
                        orientation.setSelectedIndex(i);
                        break;
                    }
                }
                for ( int i = 0; i < Status.values().length; i += 1 ) {
                    if ( Status.values()[i].equals( moduleToEdit.getStatus() ) ) {
                        condition.setSelectedIndex(i);
                        break;
                    }
                }
            }     
        });                    
    }
    
    public void clearPanel() {       
        this.coorX.setText(null);
        this.coorY.setText(null);
        this.moduleNumber.setText(null);
        this.orientation.setSelectedIndex(0);
        this.condition.setSelectedIndex(0);  
    }  
    
    public FlexTable getAddModulesPanel() {
        return this.thisPanel;
    }
}
