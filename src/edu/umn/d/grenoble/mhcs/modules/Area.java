package edu.umn.d.grenoble.mhcs.modules;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

/**
 * The area where modules land and are arranged into habitat configurations.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public class Area {
    
 /* Class Constants */
 
    /**
     * The width of the landing area.
     */
    public final static int Width = 100;
    
    /**
     * The height of the landing area.
     */
    public final static int Height = 50;
    
 /* Class Variables */
    
    /**
     * The list of Modules.
     */
    public List<Module> modules = new ArrayList<Module>();

  /* Constructors */
    
    /**
     * Default constructor.
     */
    public Area() {}
    
    /**
     * Clone constructor.
     * Makes a copy of the list of modules.
     * @param other - The list of modules that is to be copied
     */
    public Area(final Area other){
        for (Module m : other.modules) {
            this.modules.add(new Module(m));
        }
    }
    
    public Area(final String jsonSource){
        JSONArray jsonArray = (JSONArray) JSONParser.parseLenient( jsonSource );
        
        for (int i = 0; i < jsonArray.size(); i += 1) {
            Module loadedModule = new Module();
            JSONObject jsonModule = (JSONObject) jsonArray.get(i);
            loadedModule.setId((int) ((JSONNumber) jsonModule.get("code")).doubleValue());
            loadedModule.setStatus(Status.getFromString(
                    ((JSONString) jsonModule.get("status")).stringValue()));
            loadedModule.setOrientation(Orientation.getFromFlips(
                    (int) ((JSONNumber) jsonModule.get("turns")).doubleValue()));
            loadedModule.setX((int) ((JSONNumber) jsonModule.get("X")).doubleValue());
            loadedModule.setY((int) ((JSONNumber) jsonModule.get("Y")).doubleValue());
            
            this.addModule(loadedModule);
        }
    }
    
      
 /* Getters */
    
    public List<Module> getModules() { return this.modules; }
  
 /* Methods */  
  
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
    public Module occupied(final int xCoordinate, final int yCoordinate) {
        for (Module module : this.modules) {
            if (module.getX() == xCoordinate && module.getY() == yCoordinate) {
                return module;
            }
        }
        return null;
    }
    
    public String toJsonString(){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Module m : this.getModules()){
            builder.append("{");
            builder.append("\"code\":");
            builder.append(m.getId());
            builder.append(",\"status\":\"");
            builder.append(m.getStatus().getJsonName());
            builder.append("\",\"turns\":");
            builder.append(m.getOrientation().getFlipsNeeded());
            builder.append(",\"X\":");
            builder.append(m.getX());
            builder.append(",\"Y\":");
            builder.append(m.getY());
            builder.append("}");
        }
        builder.append("]");
        return builder.toString();
    }
}
