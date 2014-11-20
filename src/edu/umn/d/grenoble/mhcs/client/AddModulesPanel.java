package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;





public class AddModulesPanel{

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
    
    public AddModulesPanel() {
        this.moduleList = new Area();
        this.thisPanel = new FlexTable();
        
        this.submitButton = new Button("Submit");
        this.cancelButton = new Button("Cancel/Clear");
        
        this.coorXLabel = new Label("X Position:");
        this.coorXLabel.setVisible(true);
        this.coorYLabel = new Label("Y Position:");
        this.moduleNumberLabel = new Label("ID Number:");
        this.conditionLabel = new Label("Module Condition:");
        this.orientationLabel = new Label("Orientation:");
        new Label("   ");

        
        this.coorX = new TextBox();
        this.coorY = new TextBox();
        this.moduleNumber = new TextBox();
        
        this.orientation = new ListBox();
        this.orientation.addItem("0");
        this.orientation.addItem("1");
        this.orientation.addItem("2");
        this.condition = new ListBox();
        this.condition.addItem("Usable");
        this.condition.addItem("Usable after repair");
        this.condition.addItem("Beyond Repair");
        
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
                Module currentModule = new Module();
                currentModule.setId(Integer.parseInt(addModulesPanel.moduleNumber.getText()));
                currentModule.setX(Integer.parseInt(addModulesPanel.coorX.getText()));
                currentModule.setY(Integer.parseInt(addModulesPanel.coorY.getText()));

                //currentModule.orientation = 
                //currentModule.condition = 
                if(currentModule.isValid()){                    
                    addModulesPanel.moduleList.addModule(currentModule);
                    Window.alert("Module added" + "\n" + addModulesPanel.moduleList.getModules().size() 
                            + " module(s) have been logged");
                    addModulesPanel.clearPanel();
                }
                else{
                    Window.alert("Invalid entry");
                    addModulesPanel.clearPanel();
                    
                }
            }
        });
        
        
        
        this.cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                addModulesPanel.clearPanel();
            }
        });
        
        
        
    }
    
    public void clearPanel(){
        
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
