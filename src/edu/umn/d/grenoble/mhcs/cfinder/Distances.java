package edu.umn.d.grenoble.mhcs.cfinder;

import java.util.LinkedList;
import java.util.List;

import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;
import edu.umn.d.grenoble.mhcs.modules.Status;

public class Distances {
    private int[] distances;
    
    private class XYD {
        public int x;
        public int y;
        public int d;
        public XYD(int x_, int y_, int d_){
            x = x_;
            y = y_;
            d = d_;
        }
    }
    
    public Distances(Area area, int fromX, int fromY){
        distances = new int[Area.Width * Area.Height];
        
        
        for (Module m : area.getModules()){
            if(m.getStatus() != Status.GOOD){
                set(m.getX(), m.getY(), -1);
            }
        }
        
        LinkedList<XYD> todo = new LinkedList<XYD>();
        todo.add(new XYD(fromX, fromY, 1));
        set(fromX, fromY, 1);
        while(!todo.isEmpty()){
            XYD cur = todo.removeFirst();
            if (get(cur.x + 1, cur.y) == 0){
                todo.addLast(new XYD(cur.x + 1, cur.y, cur.d + 1));
                set(cur.x + 1, cur.y, cur.d + 1);
            }

            if (get(cur.x - 1, cur.y) == 0){
                todo.addLast(new XYD(cur.x + 1, cur.y, cur.d + 1));
                set(cur.x - 1, cur.y, cur.d - 1);
            }

            if (get(cur.x, cur.y + 1) == 0){
                todo.addLast(new XYD(cur.x, cur.y + 1, cur.d + 1));
                set(cur.x + 1, cur.y + 1, cur.d);
            }

            if (get(cur.x, cur.y - 1) == 0){
                todo.addLast(new XYD(cur.x, cur.y - 1, cur.d + 1));
                set(cur.x + 1, cur.y, cur.d + 1);
            }
        }
    }
    
    public int get(int x, int y){
        if(x >= 1 && y >= 1 && x <= Area.Width && y <= Area.Height){
            return distances[(x - 1) + (y - 1) * Area.Width];
        }
        return -1;
    }
    
    private void set(int x, int y, int value){
        if(x >= 1 && y >= 1 && x <= Area.Width && y <= Area.Height){
            distances[(x - 1) + (y - 1) * Area.Width] = value;   
        }
    }
}
