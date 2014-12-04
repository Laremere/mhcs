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
    UNKNOWN ("The module's status is currently unknown.", "uncertain"),
    
    /**
     * This indicates that the module's status is good.
     */
    GOOD ("The module's status is good.", "undamaged"),
    
    /**
     * This indicates that the module is damaged but can still be repaired.
     */
    NEEDS_REPAIR ("The module is damaged and needs repair.", "repairable"),
    
    /**
     * This indicates that the module is damaged and cannot be repaired.
     */
    BEYOND_REPAIR ("The module is damaged beyond repair.", "damaged");
       
    /**
     * Contains a brief description of the module's current status.
     */
    private final String description; 
    private final String jsonName;
    
    /**
     * Constructor - Associates the module's status with its description.
     * @param description_ - The description of the module's status 
     */
    private Status(final String description_, final String jsonName_) {
        this.description = description_;
        this.jsonName = jsonName_;
    }
 
    
    
 /* Methods */
    
    /**
     * Getter (Accessor) for the description of the module's status.
     * @return the description of the module's status
     */ 
    public String getDescription() {
        return this.description;
    }
    
    public static Status getFromString(final String statusString){
        for (Status s : Status.values()){
            if(s.jsonName.equals(statusString)){
                return s;
            }
        }
        return Status.UNKNOWN;
    }
    
}
