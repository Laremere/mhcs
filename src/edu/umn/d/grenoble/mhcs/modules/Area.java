package edu.umn.d.grenoble.mhcs.modules;

import java.util.ArrayList;
import java.util.List;

/**
 * The area where modules land and are arranged into habitat configurations.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public class Area {
    public final static int Width = 100;
    public final static int Height = 50;
    
 /* Initialization */
    
    /**
     * The list of Modules.
     */
    public List<Module> modules = new ArrayList<Module>();

    public Area(){
    }
    
    public Area(final Area other){
        for (Module m : other.modules) {
            this.modules.add(new Module(m));
        }
    }
    
    
 /* Methods */
    
    /**
     * getter (Accessor) for the module list.
     * @return the list of modules
     */
    public List<Module> getModules() {
        return this.modules;
    }
    
    /**
     * Adds the module to the list of modules.
     * @param module - The new module to add to the list of modules.
     */
    public void addModule(final Module module) {
        this.modules.add(module);
    }
    
    /**
     * Checks to see if the space is occupied by another module.
     * @param xCoordinate - The x-Coordinate of the space in question
     * @param yCoordinate - The Y-Coordinate of the space in question
     * @return whether or not the space is occupied (True = Occupied, False = Vacant)
     */
    public boolean occupied(final int xCoordinate, final int yCoordinate) {
        for (Module module : this.modules) {
            if (module.getX() == xCoordinate && module.getY() == yCoordinate) {
                return true;
            }
        }
        return false;
    }

}
