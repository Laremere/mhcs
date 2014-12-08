package edu.umn.d.grenoble.mhcs.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;

/**
 * Stores relevant configuration data, notifies the user when configurations 
 * are available, generates a configuration and finds a valid spot on the map.
 */
public class ConfigurationBuilder {
  
 /* Initialization */
    
    private List<Module> plain = new ArrayList<Module>();
    private List<Module> dormitory = new ArrayList<Module>();
    private List<Module> sanitation = new ArrayList<Module>();
    private List<Module> food = new ArrayList<Module>();
    private List<Module> gym = new ArrayList<Module>();
    private List<Module> canteen = new ArrayList<Module>();
    private List<Module> power = new ArrayList<Module>();
    private List<Module> control = new ArrayList<Module>();
    private List<Module> airlock = new ArrayList<Module>();
    private List<Module> medical = new ArrayList<Module>();
    
    private List<Module> minConfigA = new ArrayList<Module>();
    private List<Module> minConfigB = new ArrayList<Module>();
    //private List<Module> config1 = new ArrayList<Module>();
    //private List<Module> config2 = new ArrayList<Module>();
    
    /**
     * Default (Empty) Constructor
     */
    public ConfigurationBuilder() {}
    
    /**
     * Copy Constructor
     * @param moduleList - The list of modules to add to the configuration builder
     */
    public ConfigurationBuilder(final List<Module> moduleList) {
        
        for (Module currentModule : moduleList) {
            this.storeModule(currentModule);
        }
        this.checkConfigs();
    }
    
 /* Getter */
    
    public List<Module> getMinConfigA() { return this.minConfigA; } 
    public List<Module> getMinConfigB() { return this.minConfigB; }
    //public List<Module> getConfig1() { return this.config1; }
    //public List<Module> getConfig2() { return this.config2; }
    
 /* Public Methods */
    
    /**
     * Adds the module to the configuration lists if and only if the
     * module's status is "Good" and the module type is a valid type.
     * @param newModule - The module to add to the list of valid modules
     */
    public void addModule(final Module newModule) { 
        
        this.storeModule(newModule);                    
                       
        this.checkConfigs();
        
    } 
    
    /**
     * Edits an existing module in the configuration list. Will only
     * @param editedModule - The module that had it's values changed
     */
    public void editModule(final Module editedModule) {
        
        // Removes the module from the list associated with the module's type
        List<Module> typeList = this.getTypeList(editedModule);  
        if (typeList != null) { this.removeModule(typeList, editedModule); }
        
        // Adds the new module reflecting the change
        this.addModule(editedModule);
    }
    
    /**
     * Checks to see if the minimum configurations are available yet.
     * @return - True if minimum configurations are available
     */
    public boolean minConfigsFound() {
        return ( this.plain.size() >= 3 && this.dormitory.size() >= 1 && 
                 this.sanitation.size() >= 1 && this.food.size() >= 1 && 
                 this.canteen.size() >= 1 && this.power.size() >= 1 && 
                 this.control.size() >= 1 && this.airlock.size() >= 1 );      
    }   
    
 /* Private Methods */
    
    /**
     * Determines the type of module and saves it into a list with other
     * modules that are of the same type. Note that this method only accepts
     * valid module types and module that have a status of "GOOD".
     * @param module - The module that is being added / updated
     */
    private void storeModule(final Module module) {
        
        List<Module> typeList = this.getTypeList(module);
        if ( module.getStatus() == Status.GOOD && module.getType() != Type.INVALID &&
             typeList != null ) { typeList.add(module); }    
    }
    
    /**
     * Removed the selected module from the associated list.
     * @param typeList - the list of modules of a given type
     * @param module - The module to remove
     */
    private void removeModule( List<Module> typeList, Module module) {  
        
        if ( !typeList.isEmpty() ) {
            for (Module m : typeList) {
                if ( m.getId() == module.getId() ) {
                    typeList.remove(m);
                }
            }
        }
    }
    
    /**
     * Gets the module's associated type and returns the list of modules 
     * that are of the same type.
     * @param module - The module who's type determines which list is returned
     * @return The list of modules that are of the same type as the parameter "module"
     */
    private List<Module> getTypeList(final Module module) {
                
        Type type = module.getType();
        
        if      ( type == Type.PLAIN )      { return (List<Module>) this.plain; } 
        else if ( type == Type.DORMITORY )  { return (List<Module>) this.dormitory; }
        else if ( type == Type.SANITATION ) { return (List<Module>) this.sanitation; } 
        else if ( type == Type.FOOD )       { return (List<Module>) this.food; }
        else if ( type == Type.GYM )        { return (List<Module>) this.gym; }
        else if ( type == Type.CANTEEN )    { return (List<Module>) this.canteen; }
        else if ( type == Type.POWER )      { return (List<Module>) this.power; }
        else if ( type == Type.CONTROL )    { return (List<Module>) this.control; }
        else if ( type == Type.AIRLOCK )    { return (List<Module>) this.airlock; }
        else if ( type == Type.MEDICAL )    { return (List<Module>) this.medical; }
        else return null;
    }
    
    /**
     * Checks to see if configurations are available and if they are,
     * it calls the appropriate generator function.
     */
    private void checkConfigs() {
        
        // For now, just displays a message indicating that 
        // minimum configurations are available
        if ( this.minConfigsFound() ) {                     
            Window.alert("Minimum configurations are now available!");
        }
    }
}
