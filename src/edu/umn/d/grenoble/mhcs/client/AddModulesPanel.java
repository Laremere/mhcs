package edu.umn.d.grenoble.mhcs.client;

import java.util.Iterator;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;





public class AddModulesPanel{

    private Area moduleList;
    private FlexTable thisPanel;
    private Button submitButton, cancelButton;
    private TextBox coorX, coorY, moduleNumber;
    private Label coorXLabel, coorYLabel, moduleNumberLabel;
    private ListBox orientation, condition;
    private Label orientationLabel, conditionLabel, spaceLabel;
    
    public AddModulesPanel() {
        moduleList = new Area();
        thisPanel = new FlexTable();
        
        submitButton = new Button("Submit");
        cancelButton = new Button("Cancel/Clear");
        
        coorXLabel = new Label("X Position:");
        coorXLabel.setVisible(true);
        coorYLabel = new Label("Y Position:");
        moduleNumberLabel = new Label("ID Number:");
        conditionLabel = new Label("Module Condition:");
        orientationLabel = new Label("Orientation:");
        spaceLabel = new Label("   ");

        
        coorX = new TextBox();
        coorY = new TextBox();
        moduleNumber = new TextBox();
        
        orientation = new ListBox();
        orientation.addItem("0");
        orientation.addItem("1");
        orientation.addItem("2");
        condition = new ListBox();
        condition.addItem("Usable");
        condition.addItem("Usable after repair");
        condition.addItem("Beyond Repair");
        
        thisPanel.setWidget(0, 0, coorXLabel);  
        thisPanel.setWidget(0, 1, coorX);
        thisPanel.setWidget(1, 0, coorYLabel);
        thisPanel.setWidget(1, 1, coorY);
        thisPanel.setWidget(0, 3, moduleNumberLabel);
        thisPanel.setWidget(0, 4, moduleNumber);
        thisPanel.setWidget(0, 6, conditionLabel);
        thisPanel.setWidget(0, 7, condition);
        thisPanel.setWidget(0, 9, submitButton);
        thisPanel.setWidget(1, 3, orientationLabel);
        thisPanel.setWidget(1, 4, orientation);
        thisPanel.setWidget(1, 9, cancelButton);
        
        submitButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Module currentModule = new Module();
                currentModule.id = Integer.parseInt(moduleNumber.getText());
                currentModule.xposition = Integer.parseInt(coorX.getText());
                currentModule.yposition = Integer.parseInt(coorY.getText());

                //currentModule.orientation = 
                //currentModule.condition = 
                if(currentModule.isValid()){                    
                    moduleList.addModule(currentModule);
                    Window.alert("Module added" + "\n" + moduleList.getModules().size() 
                            + " module(s) have been logged");
                    clearPanel();
                }
                else{
                    Window.alert("Invalid entry");
                    clearPanel();
                    
                }

                
                

         }});
        
        
        
        cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                clearPanel();
            }
        });
        
        
        
    }
    
    public void clearPanel(){
        
        coorX.setText(null);
        coorY.setText(null);
        moduleNumber.setText(null);
        orientation.setSelectedIndex(0);
        condition.setSelectedIndex(0);
        
    }
    
    
    public FlexTable getAddModulesPanel() {
        return thisPanel;
    }
    

}
