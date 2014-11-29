package edu.umn.d.grenoble.mhcs.modules;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;

/**
 * The module and information pertaining to it such as coordinates, 
 * status, type and orientation.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public class Module {
    
 /* Class Variables */
    
    /**
     * The <b>ID number</b> of the module.
     */
    private int idNumber = -1;
    
    /**
     * The <b>X-Coordinate</b> of the module.
     */
    private int xCoordinate;
    
    /**
     * The <b>Y-Coordinate</b> of the module.
     */
    private int yCoordinate;
    
    /**
     * The <b>orientation</b> of the module.
     */
    private Orientation orientation = Orientation.UNKNOWN;
    
    /**
     * The <b>status</b> of the module.
     */
    private Status status = Status.UNKNOWN;
    
 /* CheckStyle Strings */
    
    private final String newLine = "\n";
    private final String unknown = "UNKNOWN";
    private final String defaultUnknown = "Module orientation set to \"Unknown\"";
   
 /* Constructors */
    
    /**
     * Default Constructor.
     */
    public Module() { }
    
    /**
     * Complete Constructor.
     * @param idNumber_ - The ID number of the module
     * @param xCoordinate_ - The module's X-Coordinate
     * @param yCoordinate_ - The module's Y-Coordinate
     * @param orientation_ - The orientation of the module
     * @param status_ - The status of the module
     */
    public Module(final int idNumber_, final int xCoordinate_, final int yCoordinate_, 
                  final Orientation orientation_, final Status status_) {
        this.idNumber = idNumber_; 
        this.xCoordinate = xCoordinate_;
        this.yCoordinate = yCoordinate_;
        this.orientation = orientation_;
        this.status = status_;      
    }
    
    /**
     * Clone constructor.
     * Makes a copy of a module.
     * @param other - The module to make a copy of
     */
    public Module(final Module other) {
        this.idNumber = other.idNumber;
        this.orientation = other.orientation;
        this.status = other.status;
        this.xCoordinate = other.xCoordinate;
        this.yCoordinate = other.xCoordinate;
    }
        
 /* Getters */

    public int getId() { return this.idNumber; }
    public int getX() { return this.xCoordinate; }
    public int getY() { return this.yCoordinate; }
    public Orientation getOrientation() { return this.orientation; }
    public Type getType() { return Type.getFromId(this.idNumber); }
    public Status getStatus() { return this.status; }
    
 /* Setters */
    
    public void setId(final int newId) { this.idNumber = newId; }
    public void setId(final double newId) { this.idNumber = (int) newId; }
    public void setId(final JSONNumber newId) { this.idNumber = (int) newId.doubleValue(); }
    public void setX(final int newX) { this.xCoordinate = newX; }
    public void setX(final double newX) { this.xCoordinate = (int) newX; }
    public void setX(final JSONNumber newX) { this.xCoordinate = (int) newX.doubleValue(); }
    public void setY(final int newY) { this.xCoordinate = newY; }
    public void setY(final double newY) { this.xCoordinate = (int) newY; }
    public void setY(final JSONNumber newY) { this.xCoordinate = (int) newY.doubleValue(); }
    public void setOrientation(final Orientation newOrientation) { this.orientation = newOrientation; }
    public void setOrientation(final int numberOfFlips) { 
        if (numberOfFlips == 0) {  
            this.orientation = Orientation.UPRIGHT; 
        } else if (numberOfFlips == 1) {
            this.orientation = Orientation.ON_SIDE;
        } else if (numberOfFlips == 2) {
            this.orientation = Orientation.UPSIDE_DOWN;
        } else if (numberOfFlips == -1) {
            this.orientation = Orientation.UNKNOWN;
        } else { 
            this.orientation = Orientation.UNKNOWN; 
            Window.alert("Error setting module orientation: Value out of range."
                    + this.newLine + this.defaultUnknown);
        }
    }
    public void setOrientation(final String newOrientation) { 
        String orientationString = this.cleanString(newOrientation);
        if ( "UPRIGHT".equals(orientationString) ) { 
            this.orientation = Orientation.UPRIGHT; 
        } else if ( "ON_SIDE".equals(orientationString) ) {
            this.orientation = Orientation.ON_SIDE;
        } else if ( "UPSIDE_DOWN".equals(orientationString) ) {
            this.orientation = Orientation.UPSIDE_DOWN;
        } else if ( this.unknown.equals(orientationString) ) {
            this.orientation = Orientation.UNKNOWN;
        } else {
            this.orientation = Orientation.UNKNOWN;
            Window.alert("Error setting module orientation: Value must be one of the following:"
                    + this.newLine + "UPRIGHT, ON_SIDE, UPSIDE_DOWN, UNKNOWN." + this.newLine 
                    + this.defaultUnknown );
        }                        
    }
    public void setOrientation(final JSONString newOrientation) { 
        this.setOrientation( newOrientation.stringValue() ); 
    }
    public void setStatus(final Status newStatus) { this.status = newStatus; }
    public void setStatus(final String newStatus) { 
        String statusString = this.cleanString(newStatus);
        if ( "GOOD".equals(statusString) ) { 
            this.status = Status.GOOD; 
        } else if ( "NEEDS_REPAIR".equals(statusString) ) {
            this.status = Status.NEEDS_REPAIR;
        } else if ( "BEYOND_REPAIR".equals(statusString) ) {
            this.status = Status.BEYOND_REPAIR;
        } else if ( this.unknown.equals(statusString) ) {
            this.status = Status.UNKNOWN;
        } else {
            this.status = Status.UNKNOWN;
            Window.alert("Error setting module status: Value must be one of the following:"
                    + this.newLine + "GOOD, NEEDS_REPAIR, BEYOND_REPAIR, UNKNOWN." + this.newLine 
                    + "Module status set to \"Unknown\"");
        }                        
    }
    public void setStatus(final JSONString newString) { this.setStatus( newString.stringValue() ); }
    
 /* Methods */
    
    /**
     * Checks if the module is in the landing zone and has information logged
     * for module type, status and orientation.
     * @return True or False depending on if all the module's data is valid or not.
     */
    public boolean isValid() {
        return this.getType() != Type.INVALID
                && this.orientation != Orientation.UNKNOWN
                && this.status != Status.UNKNOWN
                && this.xCoordinate >= 1
                && this.xCoordinate <= Area.Width
                && this.yCoordinate >= 1
                && this.yCoordinate <= Area.Height;
    }
    
    /**
     * Takes in a string and removes leading and trailing spaces, 
     * converts spaces to underscores and capitalizes each letter.
     * @param dirtyString - The string that needs to be cleaned up
     * @return The correctly formatted string
     */
    private String cleanString(final String dirtyString) {
        String cleanString = dirtyString.trim().replaceAll(" ", "_").toUpperCase();
        return cleanString;
    }
}
