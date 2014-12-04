package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import edu.umn.d.grenoble.mhcs.bus.AreaClickEvent;
import edu.umn.d.grenoble.mhcs.bus.AreaClickEventHandler;
import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
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
    private Button loadButton;
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
    private Storage moduleStore;
    
    public AddModulesPanel() {
        
        this.moduleList = new Area();
        this.thisPanel = new FlexTable();
        
        this.submitButton = new Button("Submit");
        this.cancelButton = new Button("Cancel/Clear");
        this.loadButton = new Button("Load");
        
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
        this.thisPanel.setWidget(0, 11, this.loadButton);
        
        final AddModulesPanel addModulesPanel = this;
        
        this.submitButton.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                
                final String INVALID = "Invalid entry";
                final String MODULE_ADDED = "Module added \n";
                final String MODULES_LOGGED = " module(s) have been logged";
                
                if ( addModulesPanel.moduleToEdit == null ) {
                    Module currentModule = new Module();
                    currentModule.setId( Integer.parseInt( addModulesPanel.moduleNumber.getText() ) );
                    currentModule.setX( Integer.parseInt( addModulesPanel.coorX.getText() ) );
                    currentModule.setY( Integer.parseInt( addModulesPanel.coorY.getText() ) );
                    currentModule.setOrientation( Orientation.values()[ 
                        addModulesPanel.orientation.getSelectedIndex() ] );
                    currentModule.setStatus( Status.values()[ 
                        addModulesPanel.condition.getSelectedIndex() ] );
                    if ( currentModule.isValid() ) {                    
                        addModulesPanel.moduleList.addModule(currentModule);
                        Bus.bus.fireEvent( new AreaUpdateEvent(addModulesPanel.moduleList) );
                        Window.alert( MODULE_ADDED + addModulesPanel.moduleList.getModules().size() 
                                + MODULES_LOGGED );
                        addModulesPanel.clearPanel();
                    }
                    else { Window.alert(INVALID); }                    
                } else {
                    addModulesPanel.moduleToEdit.setId( Integer.parseInt( addModulesPanel.moduleNumber.getText() ) );
                    addModulesPanel.moduleToEdit.setX( Integer.parseInt( addModulesPanel.coorX.getText() ) );
                    addModulesPanel.moduleToEdit.setY( Integer.parseInt( addModulesPanel.coorY.getText() ) );
                    addModulesPanel.moduleToEdit.setOrientation( Orientation.values()[ 
                        addModulesPanel.orientation.getSelectedIndex() ] );
                    addModulesPanel.moduleToEdit.setStatus( Status.values()[ 
                        addModulesPanel.condition.getSelectedIndex() ] );
                    if ( addModulesPanel.moduleToEdit.isValid() ) {                        
                        Bus.bus.fireEvent( new AreaUpdateEvent(addModulesPanel.moduleList) );
                        Window.alert( MODULE_ADDED + addModulesPanel.moduleList.getModules().size() 
                                + MODULES_LOGGED );
                        addModulesPanel.clearPanel();
                    } else { Window.alert(INVALID); }                         
                }
            }
        });
          
        this.cancelButton.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                addModulesPanel.clearPanel();
            }
        });
        
        
        
        this.loadButton.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                moduleStore = Storage.getLocalStorageIfSupported();
                
                if(moduleStore == null) {
                    Window.alert("Local Storage not supported");
                }
                
                String sConfigOne = moduleStore.getItem("config1");
                
                moduleList = new Area(sConfigOne);
                
            }
        });
        
        Bus.bus.addHandler(AreaClickEvent.TYPE, new AreaClickEventHandler() {
            
            @Override
            public void onEvent(final AreaClickEvent event) {
                
                addModulesPanel.moduleToEdit = addModulesPanel.moduleList.occupied( event.getX(), event.getY() );
                addModulesPanel.coorX.setText( Integer.toString( addModulesPanel.moduleToEdit.getX() ) );
                addModulesPanel.coorY.setText( Integer.toString( addModulesPanel.moduleToEdit.getY() ) );
                addModulesPanel.moduleNumber.setText( Integer.toString( addModulesPanel.moduleToEdit.getId() ) );
                
                for (int i = 0; i < Orientation.values().length; i += 1) {
                    if ( Orientation.values()[i].equals( addModulesPanel.moduleToEdit.getOrientation() ) ) {
                        addModulesPanel.orientation.setSelectedIndex(i);
                        break;
                    }
                }
                for ( int i = 0; i < Status.values().length; i += 1 ) {
                    if ( Status.values()[i].equals( addModulesPanel.moduleToEdit.getStatus() ) ) {
                        addModulesPanel.condition.setSelectedIndex(i);
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
