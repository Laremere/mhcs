package edu.umn.d.grenoble.mhcs.cfinder;

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
