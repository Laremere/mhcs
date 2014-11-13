package edu.umn.d.grenoble.modules;

import java.util.ArrayList;
import java.util.List;

public class Area {
    List<Module> modules = new ArrayList<Module>();
    
    public Area(Area other) {
        for (Module m : other.modules) {
            modules.add(new Module(m));
        }
    }
    
    public void addModule(Module module) {
        modules.add(module);
    }
    
    public boolean occupied(int xcoord, int ycoord) {
        for (Module module : modules) {
            if (module.xposition == xcoord && module.yposition == ycoord) {
                return true;
            }
        }
        return false;
    }
    
    public List<Module> getModules() {
        return modules;
    }
}
