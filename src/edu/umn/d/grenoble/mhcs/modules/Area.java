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
                    ((JSONString) jsonModule.get("status")).toString()));
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
    
    /**
     * Takes previously saved data from client-side storage and then parses the
     * JSONString for key-value pairs in order to create an arrayList of modules
     * and the associated module data.
     */
    /*
    public void loadModules() {

        Storage testStorage = Storage.getLocalStorageIfSupported();
        if (testStorage != null) {
            testStorage.setItem(this.test, "[" +
                 "{idNumber:001,xCoordinate:5,yCoordinate:5,status:GOOD,orientation:0}," +
                 "{idNumber:002,xCoordinate:5,yCoordinate:6,status:GOOD,turns:0}," +
                 "{idNumber:003,xCoordinate:5,yCoordinate:7,status:GOOD,turns:0}" + "]");
        }

        String testJsonString = testStorage.getItem(this.test);
        JSONArray jsonArray = (JSONArray) JSONParser.parseLenient( testJsonString );
        Module loadedModule = new Module();
        
        for (int i = 0; i < jsonArray.size(); i += 1) {
            JSONObject savedModule = (JSONObject) jsonArray.get(i);
            loadedModule.setId( (JSONNumber) savedModule.get("idNumber") );
            loadedModule.setX( (JSONNumber) savedModule.get("xCoordinate") );
            loadedModule.setY( (JSONNumber) savedModule.get("yCoordinate") );
            loadedModule.setStatus( (JSONString) savedModule.get("status") );
            loadedModule.setOrientation( (JSONString) savedModule.get("orientation") );
            
        }
    }*/
}
