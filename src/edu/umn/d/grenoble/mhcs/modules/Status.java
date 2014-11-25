package edu.umn.d.grenoble.mhcs.modules;

/**
 * The status of the module.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public enum Status {
    
 /* Initialization */
    
    /**
     * This indicates that the module's status is unknown.
     */
    UNKNOWN ("The module's status is currently unknown."),
    
    /**
     * This indicates that the module's status is good.
     */
    GOOD ("The module's status is good."),
    
    /**
     * This indicates that the module is damaged but can still be repaired.
     */
    NEEDS_REPAIR ("The module is damaged and needs repair."),
    
    /**
     * This indicates that the module is damaged and cannot be repaired.
     */
    BEYOND_REPAIR ("The module is damaged beyond repair.");
       
    /**
     * Contains a brief description of the module's current status.
     */
    private final String description; 
    
    /**
     * Constructor - Associates the module's status with its description.
     * @param description_ - The description of the module's status 
     */
    private Status(final String description_) {
        this.description = description_;
    }
 
    
    
 /* Methods */
    
    /**
     * Getter (Accessor) for the description of the module's status.
     * @return the description of the module's status
     */ 
    public String getDescription() {
        return this.description;
    }
    
}
