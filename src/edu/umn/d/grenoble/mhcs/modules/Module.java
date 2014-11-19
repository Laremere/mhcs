package edu.umn.d.grenoble.modules;

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
    private int xCoordinate = 0;
    
    /**
     * The <b>Y-Coordinate</b> of the module.
     */
    private int yCoordinate = 0;
    
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
    public Module(int idNumber, int xCoordinate, int yCoordinate, 
                  Orientation orientation, Status status) {
        this.idNumber = idNumber; 
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.orientation = orientation;
        this.status = status;      
	}
    
    
    
 /* Methods */
    
    /**
     * Getter (Accessor) for the module's ID number.
     * @return The ID number of the module
     */ 
    public int getId() {
        return idNumber;
    }
    
    /**
     * Getter (Accessor) for the module's X-Coordinate.
     * @return The X-Coordinate of the module
     */ 
    public int getX() {
        return xCoordinate;
    }
   
   /**
    * Getter (Accessor) for the module's Y-Coordinate.
    * @return The Y-Coordinate of the module
    */ 
    public int getY() {
        return yCoordinate;
    }
   
   /**
    * Getter (Accessor) for the module's orientation.
    * @return The orientation of the module
    */ 
    public Orientation getOrientation() {
        return orientation;
    }
   
    /**
     * Getter (Accessor) for the module's type.
     * @return The type of module
     */ 
    public Type getType() {
        return Type.getFromId(this.id);
    }
    
    /**
     * Getter (Accessor) for the module's status.
     * @return The current status of module
     */ 
    public Status getStatus() {
        return status;
    }
    
   /**
    * Setter (Mutator) for the module's ID number. The module type and image URL also
    * get updated reflecting the new ID number. The new ID can only be set
    * if the module's ID has not yet been set and the new ID is valid.
    * @param newId - The ID number of the module
    */ 
   public void setId(int newId) {
       this.idNumber = newId;
   }
   
   /**
    * Setter (Mutator) for the module's X-Coordinate.
    * Only input between 1 and 100 is accepted. Invalid data will
    * result in no change to the X-Coordinate of the module.
    * @param newX - The new X-Coordinate of the module
    */ 
   public void setX(int newX) {
       xCoordinate = newX;
   }
  
  /**
   * Setter (Mutator) for the module's Y-Coordinate.
   * Only input between 1 and 50 is accepted. Invalid data will
   * result in no change to the Y-Coordinate of the module.
   * @param newY - The new Y-Coordinate of the module
   */ 
   public void setY(int newY) {
       this.yCoordinate = newY;
   }
  
  /**
   * Setter (Mutator) for the module's orientation.
   * @param newOrientation - The new orientation of the module
   */ 
   public void setOrientation(Orientation newOrientation) {
       orientation = newOrientation;
   }
   
   /**
    * Setter (Mutator) for the module's status.
    * @param newStatus - The new status of module
    */ 
   public void setStatus(Status newStatus) {
       status = newStatus;
   }
    
    /**
     * Checks if the module is in the landing zone and has information logged
     * for module type, status and orientation.
     * @return True or False depending on if all the module's data is valid or not.
     */
    public boolean isValid() {
        return this.getType() != Type.INVALID
                && orientation != Orientation.UNKNOWN
                && status != Status.UNKNOWN
                && xCoordinate >= 1
                && xCoordinate <= 100
                && yCoordinate >= 1
                && yCoordinate <= 50;
    }
}
