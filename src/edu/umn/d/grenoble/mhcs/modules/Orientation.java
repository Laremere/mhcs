package edu.umn.d.grenoble.mhcs.modules;

import com.google.gwt.user.client.Window;

/**
 * The orientation of the module.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public enum Orientation {
    
 /* Initialization */
    
    /**
     * This indicates that the module's orientation is unknown.
     */
    UNKNOWN ("The module's orientation is currently unknown.", -1),
    
    /**
     * This indicates that the module is upright.
     */
    UPRIGHT ("This module is upright and therefore requires no flips.", 0),
    
    /**
     * This indicates that the module is on its side.
     */
    ON_SIDE ("This module is on its side and requires 1 flip to be upright.", 1),
    
    /**
     * This indicates that the module is upside down.
     */
    UPSIDE_DOWN ("This module is upside down and requires 2 flips to be upright.", 2);
    
    /**
     * Contains the description of the module's current orientation.
     */
    private final String description;
    
    /**
     * Contains the number of flips needed for the module to be upright.
     */
    private final int flipsNeeded;
    
    /**
     * Constructor - Associates the module's status with its description and
     * the number of flips required to make the module upright.
     * @param description_ - The description of the module's status 
     * @param flipsNeeded_ - The number of flips required for the module to be upright
     */
    private Orientation(final String description_, final int flipsNeeded_) {
        this.description = description_;
        this.flipsNeeded = flipsNeeded_;
    }
   
    
    
 /* Methods */
    
    /**
     * Getter (Accessor) for the description of the module's orientation.
     * @return The description of the module's orientation
     */ 
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Getter (Accessor) for the number of flips needed until the module is upright.
     * @return The number of flips needed to make the module upright
     */ 
    public int getFlipsNeeded() {
        return this.flipsNeeded;
    }
      
    
    public static Orientation getFromFlips(final int flips){
        for(Orientation o : Orientation.values()){
            if(o.flipsNeeded == flips){
                return o;
            }
        }
        return Orientation.UNKNOWN;
    }
}
