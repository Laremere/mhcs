package edu.umn.d.grenoble.mhcs.cfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.umn.d.grenoble.mhcs.modules.Type;

public class Plan {
    private int width;
    private int height;
    private Type[] plan;
    private Wing[] wings;
    
    public Plan(final Layout layout, final Map<Type, Integer> counts){
        this.height = layout.getHeight() + 2;
        this.width = layout.getWidth() + 2;
        this.plan = new Type[this.height * this.width];
        this.wings = new Wing[this.height * this.width];
        for(int x = -1; x <= layout.getWidth(); x += 1){
            for(int y = -1; y <= layout.getHeight(); y += 1){
                if(layout.get(x, y)){
                    this.set(x + 1, y + 1, Type.PLAIN);
                    this.setWing(x + 1, y + 1, Wing.Plain);
                } else if (layout.isSpot(x, y)){
                    this.setWing(x + 1, y + 1, Wing.Unset);
                }
            }
        }
        
        //Airlock
        {
            {
                List<Integer> open = new ArrayList<Integer>();
                for(int i = 0; i < this.height; i+= 1){
                    if(this.getWing(this.width - 1, i) == Wing.Unset){
                        open.add(i);
                    }
                }
                this.setWing(this.width - 1, open.get(open.size() / 2), Wing.Airlock);
                this.set(this.width - 1, open.get(open.size() / 2), Type.AIRLOCK);
            }
            if(counts.get(Type.AIRLOCK) >= 2){
                List<Integer> open = new ArrayList<Integer>();
                for(int i = this.height - 1; i >= 0; i-= 1){
                    if(this.getWing(0, i) == Wing.Unset){
                        open.add(i);
                    }
                }
                this.setWing(0, open.get(open.size() / 2), Wing.Airlock);
                this.set(0, open.get(open.size() / 2), Type.AIRLOCK);
            }
            if(counts.get(Type.AIRLOCK) >= 3){
                List<Integer> open = new ArrayList<Integer>();
                for(int i = 0; i < this.width; i+= 1){
                    if(this.getWing(i, 0) == Wing.Unset){
                        open.add(i);
                    }
                }
                int x = open.get(open.size() / 2);
                this.setWing(x, 0, Wing.Airlock);
                this.set(x, 0, Type.AIRLOCK);
            }
            if(counts.get(Type.AIRLOCK) >= 4){
                List<Integer> open = new ArrayList<Integer>();
                int y = this.height - 1;
                for(int i = 0; i < this.width; i+= 1){
                    if(this.getWing(i, y) == Wing.Unset){
                        open.add(i);
                    }
                }
                int x = open.get(open.size() / 2);
                this.setWing(x, y, Wing.Airlock);
                this.set(x, y, Type.AIRLOCK);
            }
        }
        
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public Type get(final int x, final int y){
        return this.plan[x + y * this.width];
    }
    
    private void set(final int x, final int y, final Type t){
        this.plan[x + y * this.width] = t;
    }
    
    public Wing getWing(final int x, final int y){
        return this.wings[x + y * this.width];
    }
    
    private void setWing(final int x, final int y, final Wing w){
        this.wings[x + y * this.width] = w;
    }
    
    public enum Wing{
        Plain("#000000"),
        Airlock("#ffff00"),
        Sleep("#0000ff"),
        Food("#00ff00"),
        Unset("#555555");
        
        public String color; 
        
        Wing(String color_){
            color = color_;
        }
    }
}
