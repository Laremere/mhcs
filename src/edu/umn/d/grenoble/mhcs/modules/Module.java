package edu.umn.d.grenoble.mhcs.modules;

/**
 * The type of module.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public class Module {
    
 /* Initialization */
    
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
    
    /**
     * Default Constructor.
     */
    public Module() { }
    
    /**
     * Complete Constructor.
     * @param idNumber - The ID number of the module
     * @param xCoordinate - The module's X-Coordinate
     * @param yCoordinate - The module's Y-Coordinate
     * @param orientation - The orientation of the module
     * @param status - The status of the module
     */
    public Module(final int idNumber_, final int xCoordinate_, final int yCoordinate_, 
                  final Orientation orientation_, final Status status_) {
        this.idNumber = idNumber_; 
        this.xCoordinate = xCoordinate_;
        this.yCoordinate = yCoordinate_;
        this.orientation = orientation_;
        this.status = status_;      
    }
    
    public Module(final Module other) {
        this.idNumber = other.idNumber;
        this.orientation = other.orientation;
        this.status = other.status;
        this.xCoordinate = other.xCoordinate;
        this.yCoordinate = other.xCoordinate;
    }
    
    
 /* Methods */
    
    /**
     * Getter (Accessor) for the module's ID number.
     * @return The ID number of the module
     */ 
    public int getId() {
        return this.idNumber;
    }
    
    /**
     * Getter (Accessor) for the module's X-Coordinate.
     * @return The X-Coordinate of the module
     */ 
    public int getX() {
        return this.xCoordinate;
    }
   
   /**
    * Getter (Accessor) for the module's Y-Coordinate.
    * @return The Y-Coordinate of the module
    */ 
    public int getY() {
        return this.yCoordinate;
    }
   
   /**
    * Getter (Accessor) for the module's orientation.
    * @return The orientation of the module
    */ 
    public Orientation getOrientation() {
        return this.orientation;
    }
   
    /**
     * Getter (Accessor) for the module's type.
     * @return The type of module
     */ 
    public Type getType() {
        return Type.getFromId(this.idNumber);
    }
    
    /**
     * Getter (Accessor) for the module's status.
     * @return The current status of module
     */ 
    public Status getStatus() {
        return this.status;
    }
    
    /**
     * Setter (Mutator) for the module's ID number. The module type and image URL also
     * get updated reflecting the new ID number. The new ID can only be set
     * if the module's ID has not yet been set and the new ID is valid.
     * @param newId - The ID number of the module
     */ 
    public void setId(final int newId) {
        this.idNumber = newId;
    }
   
    /**
     * Setter (Mutator) for the module's X-Coordinate.
     * Only input between 1 and 100 is accepted. Invalid data will
     * result in no change to the X-Coordinate of the module.
     * @param newX - The new X-Coordinate of the module
     */ 
    public void setX(final int newX) {
        this.xCoordinate = newX;
    }
  
   /**
    * Setter (Mutator) for the module's Y-Coordinate.
    * Only input between 1 and 50 is accepted. Invalid data will
    * result in no change to the Y-Coordinate of the module.
    * @param newY - The new Y-Coordinate of the module
    */ 
    public void setY(final int newY) {
        this.yCoordinate = newY;
    }
  
   /**
    * Setter (Mutator) for the module's orientation.
    * @param newOrientation - The new orientation of the module
    */ 
    public void setOrientation(final Orientation newOrientation) {
        this.orientation = newOrientation;
    }
   
    /**
     * Setter (Mutator) for the module's status.
     * @param newStatus - The new status of module
     */ 
    public void setStatus(final Status newStatus) {
        this.status = newStatus;
    }
    
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
