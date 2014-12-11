package edu.umn.d.grenoble.mhcs.cfinder;

import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;
import edu.umn.d.grenoble.mhcs.modules.Status;

public class BadAreas {
    boolean[] open;
    
    public BadAreas(Area area){
        open = new boolean[Area.Width * Area.Height];
        
        //Set all to true
        for (int x = 1; x <= Area.Width; x++){
            for (int y = 1; y <= Area.Height; y++){
                set(x,y,true);
            }
        }
        
        //Remove the bad area in the map
        for (int x = 40; x <= 50; x++){
            for (int y = 40; y <= 50; y++){
                set(x,y,false);
            }
        }
        
        for (Module m: area.modules){
            if(m.getStatus() != Status.GOOD){
                for (int i = -1; i <= 1; i++){
                    for (int j = -1; j <= 1; j++){
                        set(m.getX() + i, m.getY() + j, false);
                    }
                }
            }
        }
    }
    
    public boolean get(int x, int y){
        if(x >= 1 && y >= 1 && x <= Area.Width && y <= Area.Height){
            return open[(x - 1) + (y - 1) * Area.Width];
        }
        return false;
    }
    
    private void set(int x, int y, boolean value){
        if(x >= 1 && y >= 1 && x <= Area.Width && y <= Area.Height){
            open[(x - 1) + (y - 1) * Area.Width] = value;   
        }
    }
}
