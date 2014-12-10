package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;

import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent.MoveType;
import edu.umn.d.grenoble.mhcs.bus.Bus;


/**
 * Creates grid with buttons for map control.
 * Fires appropriate events to AreaRenderer
 * @author Justin Pieper
 *
 */
public class MapControlPanel {

    //Declaration of widgets
    Grid buttonGrid;
    Button upArrow;
    Button downArrow;
    Button leftArrow;
    Button rightArrow;
    Button zoomInButton;
    Button zoomOutButton;
    
    /**
     * Constructor for class
     * Initializes all widgets and adds their listeners.
     */
    public MapControlPanel() {
        this.buttonGrid = new Grid(3,3);
        
        this.upArrow = new Button("^");
        this.downArrow = new Button("v");
        this.leftArrow = new Button("<");
        this.rightArrow = new Button(">");
        this.zoomInButton = new Button("+");
        this.zoomOutButton = new Button("-");
        this.upArrow.addStyleName("controlButton");
        this.downArrow.addStyleName("controlButton");
        this.leftArrow.addStyleName("controlButton");
        this.rightArrow.addStyleName("controlButton");
        this.zoomInButton.addStyleName("controlButton");
        this.zoomOutButton.addStyleName("controlButton");
        
        
        this.buttonGrid.setWidget(0, 0, this.zoomInButton);
        this.buttonGrid.setWidget(0, 1, this.upArrow);
        this.buttonGrid.setWidget(0, 2, this.zoomOutButton);
        this.buttonGrid.setWidget(1, 0, this.leftArrow);
        this.buttonGrid.setWidget(1, 2, this.rightArrow);
        this.buttonGrid.setWidget(2, 1, this.downArrow);
        
        
        // These clickHandlers fire the appropriate event to change the AreaRenderer
        this.upArrow.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                
                Bus.bus.fireEvent( new AreaUpdateEvent(MoveType.MoveUp) );
                
            }
        });
        
        this.downArrow.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                
                Bus.bus.fireEvent( new AreaUpdateEvent(MoveType.MoveDown) );
                
            }
        });
        
        this.leftArrow.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                
                Bus.bus.fireEvent( new AreaUpdateEvent(MoveType.MoveLeft) );
                
            }
        });
        
        this.rightArrow.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                
                Bus.bus.fireEvent( new AreaUpdateEvent(MoveType.MoveRight) );
                
            }
        });
        
        this.zoomInButton.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                
                Bus.bus.fireEvent( new AreaUpdateEvent(MoveType.ZoomIn) );
                
            }
        });
        
        this.zoomOutButton.addClickHandler( new ClickHandler() {
            public void onClick(final ClickEvent event) {
                
                Bus.bus.fireEvent( new AreaUpdateEvent(MoveType.ZoomOut) );
                
            }
        });
        
        
    }
    
    public Grid getMapControlPanel(){
        return this.buttonGrid;
    }
}
