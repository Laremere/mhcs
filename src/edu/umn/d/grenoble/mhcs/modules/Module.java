package edu.umn.d.grenoble.mhcs.modules;

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
        this.yCoordinate = other.yCoordinate;
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
    public void setX(final int newX) { this.xCoordinate = newX; }
    public void setY(final int newY) { this.yCoordinate = newY; }
    public void setOrientation(final Orientation newOrientation) { this.orientation = newOrientation; }
    public void setStatus(final Status newStatus) { this.status = newStatus; }
    
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
}
