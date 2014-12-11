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
                    this.setWing(x + 1, y + 1, Wing.Unseen);
                }
            }
        }
        
        {
            ArrayList<XY> spots = new ArrayList<XY>();
            int x;
            int y;
            {
                List<Integer> open = new ArrayList<Integer>();
                for(int i = 0; i < this.height; i+= 1){
                    if(this.getWing(this.width - 1, i) == Wing.Unseen){
                        open.add(i);
                    }
                }
                x = this.width - 1;
                y = open.get(open.size() / 2);
            }
            {
                findLoop:
                while(true){
                    while(true){
                        spots.add(new XY(x,y));
                        this.setWing(x, y, Wing.Unset);
                        if(this.getWing(x + 1, y) == Wing.Unseen){
                            x += 1;
                            continue;
                        }
                        if(this.getWing(x - 1, y) == Wing.Unseen){
                            x -= 1;
                            continue;
                        }
                        if(this.getWing(x, y + 1) == Wing.Unseen){
                            y += 1;
                            continue;
                        }
                        if(this.getWing(x, y -1) == Wing.Unseen){
                            y -= 1;
                            continue;
                        }
                        if(this.getWing(x + 1, y + 1) == Wing.Unseen){
                            x += 1;
                            y += 1;
                            continue;
                        }
                        if(this.getWing(x - 1, y + 1) == Wing.Unseen){
                            x -= 1;
                            y += 1;
                            continue;
                        }
                        if(this.getWing(x + 1, y - 1) == Wing.Unseen){
                            x += 1;
                            y -= 1;
                            continue;
                        }
                        if(this.getWing(x - 1, y - 1) == Wing.Unseen){
                            x -= 1;
                            y -= 1;
                            continue;
                        }

                        break;
                    }
                    
                    for(x = 0; x < this.width; x++){
                        for(y = 0; y < this.height; y++){
                            if(this.getWing(x, y) == Wing.Unseen){
                                continue findLoop;
                            }
                        }
                    }
                    break;
                }
            }

            boolean a = false;
            for(XY xy : spots){
                if(a)
                this.setWing(xy.x, xy.y, Wing.Food);
                a = !a;
            }
            
            this.setWing(spots.get(0).x, spots.get(0).y, Wing.Airlock);
            this.set(spots.get(0).x, spots.get(0).y, Type.AIRLOCK);
        }
        //Airlock
        /*{
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
        }*/
        
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
        if (x < 0 || y < 0 || x >= this.width || y >= this.height){
            return null;
        }
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
        Unset("#555555"),
        Unseen("#111111");
        
        public String color; 
        
        Wing(String color_){
            color = color_;
        }
    }
    
    private class XY{
        int x;
        int y;
        public XY(int x_, int y_){
            x = x_;
            y = y_;
        }
    
    }
}
