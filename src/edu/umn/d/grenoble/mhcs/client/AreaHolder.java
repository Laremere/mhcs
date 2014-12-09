package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;

import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.bus.SoundEvent;
import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.ConfigurationBuilder;

public class AreaHolder {

    private static Storage moduleStore = Storage.getLocalStorageIfSupported();
    private static final String loadingNamePrefix = "grenoble_area_";
    private static final String areaListName = "grenoble_area_list";
    public static final String asLandedName = "Modules as landed";
    
    private AreaHolder() {
   
    }
    
    public static void saveArea(String name, Area area) {       
        if(moduleStore == null) {
            Window.alert("Local Storage not supported!");
        }
        
        moduleStore.setItem(loadingNamePrefix + name, area.toJsonString());   
        for(String other : getAreas()){
            if(other.equals(name)){
                return;
            }
        }
        String newList = moduleStore.getItem(areaListName);
        newList += "," + name;
        moduleStore.setItem(areaListName, newList);
    }
    
    public static String[] getAreas(){
        if(moduleStore == null) {
            Window.alert("Local Storage not supported");
        }
        return moduleStore.getItem(areaListName).split(",");
    }
    
    public static Area getArea(String name) {
        
        if(moduleStore == null) {
            Window.alert("Local Storage not supported");
        }
        
        String sConfigOne = moduleStore.getItem(loadingNamePrefix + name);
        return new Area(sConfigOne);


    }
    
    
    
}
