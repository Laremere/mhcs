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
    
    private List<Module> unusableModules = new ArrayList<Module>();
    
    private Area minConfigA = new Area();
    private Area minConfigB = new Area();
    //private Area config1 = new Area();
    //private Area config2 = new Area();
    
    /**
     * Default (Empty) Constructor
     */
    public ConfigurationBuilder() {}
    
    /**
     * Copy Constructor
     * @param moduleList - The list of modules to add to the configuration builder
     */
    public ConfigurationBuilder(final List<Module> moduleList) {
        
        for ( int i = 0; i < moduleList.size(); i += 1){
            this.storeModule( moduleList.get(i) );
        }
        this.checkConfigs();
       
    }
    
 /* Getter */
    
    public Area getMinConfigA() { return this.minConfigA; } 
    public Area getMinConfigB() { return this.minConfigB; }
    //public Area getConfig1() { return this.config1; }
    //public Area getConfig2() { return this.config2; }
    
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
     * @param oldModule - The old module data to be removed
     * @param newModule - The new module data to be added
     */
    public void editModule(final Module oldModule, final Module newModule) {
        this.removeModule(oldModule); 
        this.addModule(newModule);
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
            typeList != null ) { typeList.add(module); 
        } else { this.unusableModules.add(module); }
    }
    
    /**
     * Removed the selected module from the associated list.
     * @param typeList - the list of modules of a given type
     * @param module - The module to remove
     */
    private void removeModule( final Module oldModule) {  
        
        List<Module> typeList = this.getTypeList(oldModule);
        
        if ( !typeList.isEmpty() ) {
            for (int i = 0; i < typeList.size(); i += 1) {
                if ( typeList.get(i) == oldModule ) {
                    typeList.remove(i);
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
     * Checks to see if configurations are available and if so 
     * handles the configuration accordingly.
     */
    private void checkConfigs() {
        
        // For now, just displays a message indicating that 
        // minimum configurations are available
        if ( this.minConfigsFound() ) {                     
            Window.alert("Minimum configurations are now available!");
        }
    }
}
