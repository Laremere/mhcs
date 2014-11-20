package edu.umn.d.grenoble.mhcs.modules;

/**
 * The type of module.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public enum Type {
    
 /* Initialization */
    
    /**
     * This indicates that the module's ID is invalid.
     */
    INVALID ("INVALID", -1, -1, null),
    
    /**
     * This indicates that the module type is <b>Plain</b>.
     */
    PLAIN ("Plain", 1, 40, "Plain.jpg"),
    
    /**
     * This indicates that the module type is <b>Dormitory</b>.
     */
    DORMITORY ("Dormitory", 61, 80, "Dormitory.jpg"),
    
    /**
     * This indicates that the module type is <b>Sanitation</b>.
     */
    SANITATION ("Sanitation", 91, 100, "Sanitation.jpg"),
    
    /**
     * This indicates that the module type is <b>Food</b>.
     */
    FOOD ("Food & Water", 111, 120, "Food.jpg"),
    
    /**
     * This indicates that the module type is <b>Gym</b>.
     */
    GYM ("Gym & Relaxation", 131, 134, "Gym.jpg"),
    
    /**
     * This indicates that the module type is <b>Canteen</b>.
     */
    CANTEEN ("Canteen", 141, 144, "Canteen.jpg"),
    
    /**
     * This indicates that the module type is <b>Power</b>.
     */
    POWER ("Power", 151, 154, "Power.jpg"),
    
    /**
     * This indicates that the module type is <b>Control</b>.
     */
    CONTROL ("Control", 161, 164, "Control.jpg"),
    
    /**
     * This indicates that the module type is <b>Airlock</b>.
     */
    AIRLOCK ("Airlock", 171, 174, "Airlock.jpg"),
    
    /**
     * This indicates that the module type is <b>Medical</b>.
     */
    MEDICAL ("Medical", 181, 184, "Medical.jpg");
    
    /**
     * The name of the module type.
     */
    private final String typeName;
    
    /**
     * The lowest ID number a module of this type can have.
     */
    private final int startingId;
    
    /**
     * The highest ID number a module of this type can have.
     */
    private final int endingId;
    
    /**
     * The URL of the image associated with this module type.
     */
    private final String imageUrl;
    
    /**
     * Constructor - Associates the module's type with it's starting ID, 
     * ending ID and the URL of the image associated with that module's type.
     * @param typeName - The type of module
     * @param startingId - The lowest ID number a module of this type can have
     * @param endingId - The highest ID number a module of this type can have
     * @param filename - The name of the image file associated with this module type
     */
    Type(final String typeName_, final int startingId_, final int endingId_, final String filename) {
        this.typeName = typeName_;
        this.startingId = startingId_;
        this.endingId = endingId_;
        if (filename != null) {
            this.imageUrl = "images/modules/" + filename;
        } else {
            this.imageUrl = null;
        }
    }
    
    
    
 /* Methods */
    
    /**
     * Getter (Accessor) for name of the module type.
     * @return The name of the type associated with this module
     */
    public String getTypeName() {
        return this.typeName;
    }
    
    /**
     * Getter (Accessor) for the URL of the image associated with this module type.
     * @return The string containing the URL of the image file
     */
    public String getImageUrl() {
        return this.imageUrl;
    }
    
    /**
     * Determines the type of module based on its ID number.
     * @param idNumber - The ID number of the module
     * @return The type of module
     */
    public static Type getFromId(final int idNumber) {
        for (Type moduleType : Type.values()) {
            if (idNumber >= moduleType.startingId && idNumber <= moduleType.endingId) {
                return moduleType;
            }
        }
        return INVALID;
    }
    
}
