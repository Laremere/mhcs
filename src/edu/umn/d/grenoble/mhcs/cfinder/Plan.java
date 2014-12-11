package edu.umn.d.grenoble.mhcs.cfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

import edu.umn.d.grenoble.mhcs.modules.Type;

public class Plan {
    private int width;
    private int height;
    private Type[] plan;
    private Wing[] wings;
    
    public Plan(final Layout layout, Map<Type, Integer> counts){
        this.height = layout.getHeight() + 2;
        this.width = layout.getWidth() + 2;
        this.plan = new Type[this.height * this.width];
        this.wings = new Wing[this.height * this.width];
        {
            Map<Type, Integer> newCounts = new HashMap<Type, Integer>();
            for(Type t : counts.keySet()){
                newCounts.put(t, counts.get(t));
            }
            counts = newCounts;
        }
        
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

//            boolean a = false;
//            for(XY xy : spots){
//                if(a)
//                this.setWing(xy.x, xy.y, Wing.Food);
//                a = !a;
//            }
            
            this.setWing(spots.get(0).x, spots.get(0).y, Wing.Airlock);
            this.set(spots.get(0).x, spots.get(0).y, Type.AIRLOCK);
            counts.put(Type.AIRLOCK, counts.get(Type.AIRLOCK) - 1);

            if(counts.get(Type.AIRLOCK) > 0){
                List<Integer> open = new ArrayList<Integer>();
                for(int i = this.height - 1; i >= 0; i-= 1){
                    if(this.getWing(0, i) == Wing.Unset){
                        open.add(i);
                    }
                }
                this.setWing(0, open.get(open.size() / 2), Wing.Airlock);
                this.set(0, open.get(open.size() / 2), Type.AIRLOCK);
                counts.put(Type.AIRLOCK, counts.get(Type.AIRLOCK) - 1);
            }
            if(counts.get(Type.AIRLOCK) > 0){
                List<Integer> open = new ArrayList<Integer>();
                for(int i = 0; i < this.width; i+= 1){
                    if(this.getWing(i, 0) == Wing.Unset){
                        open.add(i);
                    }
                }
                int ax = open.get(open.size() / 2);
                this.setWing(ax, 0, Wing.Airlock);
                this.set(ax, 0, Type.AIRLOCK);
                counts.put(Type.AIRLOCK, counts.get(Type.AIRLOCK) - 1);
            }
            if(counts.get(Type.AIRLOCK) > 0){
                List<Integer> open = new ArrayList<Integer>();
                int ay = this.height - 1;
                for(int i = 0; i < this.width; i+= 1){
                    if(this.getWing(i, ay) == Wing.Unset){
                        open.add(i);
                    }
                }
                int ax = open.get(open.size() / 2);
                this.setWing(ax, ay, Wing.Airlock);
                this.set(ax, ay, Type.AIRLOCK);
                counts.put(Type.AIRLOCK, counts.get(Type.AIRLOCK) - 1);
            }
            {
                boolean lastWasAirlock = false;
                for(XY xy: spots){
                    if(lastWasAirlock && this.get(xy.x, xy.y) == null){
                        if(counts.get(Type.MEDICAL) > 0){
                            this.set(xy.x, xy.y, Type.MEDICAL);
                            counts.put(Type.MEDICAL, counts.get(Type.MEDICAL) - 1);
                        }
                    }
                    lastWasAirlock = this.get(xy.x, xy.y) == Type.AIRLOCK;
                }
            }
            
            
            //DONE WITH AIRLOCK, AIRLOCK MEDICAL
            
            {//Canteen and food
                int foodPerCanteen = counts.get(Type.FOOD) / counts.get(Type.CANTEEN);
                int extraFood = counts.get(Type.FOOD) % counts.get(Type.CANTEEN);
                int totalCanteenCount = counts.get(Type.CANTEEN);
                for(int i = 0; i < totalCanteenCount; i++){
                    int food = foodPerCanteen;
                    if(extraFood > 0){
                        extraFood -= 1;
                        food += 1;
                    }
                    int start = 0;
                    int end = 0;
                    for(int j = 0; j < spots.size(); j++){
                        if(this.get(spots.get(j)) != null){
                            start = j + 1;
                        }
                        end = j + 1;
                        if (end - start >= food + 1){
                            break;
                        }
                    }
                    int halfFood = (food - 1) / 2;
                    for(int j = start; j < end; j += 1){
                        this.setWing(spots.get(j), Wing.Food);
                        this.set(spots.get(j), Type.FOOD);
                        if(j - start== halfFood + 1 ){
                            this.set(spots.get(j), Type.CANTEEN);    
                        }
                    }
                    counts.put(Type.CANTEEN, counts.get(Type.CANTEEN) - 1);
                    counts.put(Type.FOOD, counts.get(Type.FOOD) - food);
                }
            } //Done with food
            
            { //Housing
                int end = spots.size();
                placementLoop:
                while(end > 0 && counts.get(Type.DORMITORY) > 0){
                    int start = end;
                    int gyms = 0;
                    int dorms = 1;
                    int baths = 0;
                    if (counts.get(Type.SANITATION) > 0){
                        baths = 1;
                    }
                    if (counts.get(Type.DORMITORY) > 1){
                        dorms = 2;
                    }
                    if (counts.get(Type.GYM) > 0){
                        gyms += 1;
                    }
                    if (baths < 1){
                        gyms = 0;
                        dorms = 1;
                    }
                    if (dorms < 2){
                        gyms = 0;
                    }
                    
                    while (this.get(spots.get(start - 1)) == null && end - start < dorms + baths + gyms){
                        start -= 1;
                    }
                    
                    
                    if(dorms == 1 && gyms + baths == 0){
                        if(this.canFitBedroom(spots.get(start))){
                            this.set(spots.get(start), Type.DORMITORY);   
                        } else {
                            end -= 1;
                            continue placementLoop;
                        }
                    }
                    if(dorms == 1 && baths == 1 && gyms == 0){
                        if(this.canFitBedroom(spots.get(start)) && this.canFitBath(spots.get(start + 1))){
                            this.set(spots.get(start), Type.DORMITORY);
                            this.set(spots.get(start + 1), Type.SANITATION);
                        } else if(this.canFitBedroom(spots.get(start + 1)) && this.canFitBath(spots.get(start))){
                            this.set(spots.get(start + 1), Type.DORMITORY);
                            this.set(spots.get(start), Type.SANITATION);                            
                        } else {
                            end -= 1;
                            continue placementLoop;
                        }
                    }
                    if(dorms == 2 && baths == 1 && gyms == 0){
                        if(this.canFitBedroom(spots.get(start)) && this.canFitBath(spots.get(start + 1)) && this.canFitBedroom(spots.get(start + 2))){
                            this.set(spots.get(start), Type.DORMITORY);
                            this.set(spots.get(start + 1), Type.SANITATION);
                            this.set(spots.get(start + 2), Type.DORMITORY);
                        } else if (this.canFitBedroom(spots.get(start + 1)) && this.canFitBath(spots.get(start)) && this.canFitBedroom(spots.get(start + 2))){
                            this.set(spots.get(start + 1), Type.DORMITORY);
                            this.set(spots.get(start), Type.SANITATION);
                            this.set(spots.get(start + 2), Type.DORMITORY);                            
                        } else if (this.canFitBedroom(spots.get(start + 1)) && this.canFitBath(spots.get(start + 2)) && this.canFitBedroom(spots.get(start))){
                            this.set(spots.get(start), Type.DORMITORY);
                            this.set(spots.get(start + 2), Type.SANITATION);
                            this.set(spots.get(start + 1), Type.DORMITORY);                                                        
                        } else {
                            end -= 1;
                            continue placementLoop;
                        }
                    }
                    if(dorms == 2 && baths == 1 && gyms == 1){
                        if(this.canFitBedroom(spots.get(start)) && this.canFitBath(spots.get(start + 1)) && this.canFitBedroom(spots.get(start + 3))){
                            this.set(spots.get(start), Type.DORMITORY);
                            this.set(spots.get(start + 1), Type.SANITATION);
                            this.set(spots.get(start + 2), Type.GYM);
                            this.set(spots.get(start + 3), Type.DORMITORY);                                                        
                        } else if (this.canFitBedroom(spots.get(start)) && this.canFitBath(spots.get(start + 2)) && this.canFitBedroom(spots.get(start + 3))){
                            this.set(spots.get(start), Type.DORMITORY);
                            this.set(spots.get(start + 1), Type.GYM);
                            this.set(spots.get(start + 2), Type.SANITATION);
                            this.set(spots.get(start + 3), Type.DORMITORY);                                                        
                        } else if (this.canFitBedroom(spots.get(start + 2)) && this.canFitBath(spots.get(start + 1)) && this.canFitBedroom(spots.get(start + 3))){
                            this.set(spots.get(start + 0), Type.GYM);
                            this.set(spots.get(start + 1), Type.SANITATION);
                            this.set(spots.get(start + 2), Type.DORMITORY);
                            this.set(spots.get(start + 3), Type.DORMITORY);                                                        
                        } else if (this.canFitBedroom(spots.get(start + 0)) && this.canFitBath(spots.get(start + 2)) && this.canFitBedroom(spots.get(start + 1))){
                            this.set(spots.get(start + 0), Type.DORMITORY);
                            this.set(spots.get(start + 1), Type.DORMITORY);                                                        
                            this.set(spots.get(start + 2), Type.SANITATION);
                            this.set(spots.get(start + 3), Type.GYM);
                        } else {
                            end -= 1;
                            continue placementLoop;
                        }
                        
                    }

                    for(int i = start; i < end; i++){
                        this.setWing(spots.get(i), Wing.Sleep);
                    }
                        
                    counts.put(Type.DORMITORY, counts.get(Type.DORMITORY) - dorms);
                    counts.put(Type.SANITATION, counts.get(Type.SANITATION) - baths);
                    counts.put(Type.GYM, counts.get(Type.GYM) - gyms);
                    
                    end = start;
                }
            } //Dormitory wings
            { //Place remaining randomly
                int remaining = 0;
                for (int count :counts.values()){
                    remaining += count;
                }
                int seed = 123456789;
                while(remaining > 0){
                    seed = (1103515245 * seed + 12345);
                    int next = seed % remaining;
                    Type tnext = null;
                    for(Type t: counts.keySet()){
                        next -= counts.get(t);
                        if (next < 0){
                            tnext = t;
                            break;
                        }
                    }
                    counts.put(tnext, counts.get(tnext) - 1);
                    
                    int spotCount = 0;
                    for(XY xy : spots){
                        if(this.get(xy) == null && this.getWing(xy) == Wing.Unset){
                            spotCount += 1;
                        }
                    }
                    seed = (1103515245 * seed + 12345);
                    int targetSpot = seed % spotCount;
                    
                    for(XY xy : spots){
                        if(this.get(xy) == null && this.getWing(xy) == Wing.Unset){
                            targetSpot -= 1;
                            if (targetSpot < 0){
                                this.set(xy, tnext);
                                break;   
                            }
                        }
                    }
                    
                    remaining = 0;
                    for (int count :counts.values()){
                        remaining += count;
                    }
                }
            }//placing modules randomly
            
        }
        
    }

    private boolean canFitBedroom(XY xy){
        int x = xy.x;
        int y = xy.y;
        if (
                this.get(x + 1, y) == Type.AIRLOCK ||
                this.get(x - 1, y) == Type.AIRLOCK ||
                this.get(x, y + 1) == Type.AIRLOCK ||
                this.get(x, y - 1) == Type.AIRLOCK ||
                this.get(x + 1, y + 1) == Type.AIRLOCK ||
                this.get(x + 1, y - 1) == Type.AIRLOCK ||
                this.get(x - 1, y + 1) == Type.AIRLOCK ||
                this.get(x - 1, y - 1) == Type.AIRLOCK
                ){
            return false;
        }
        
        return true;
    }
    
    private boolean canFitBath(XY xy){
        int x = xy.x;
        int y = xy.y;
        if (
                this.get(x + 1, y) == Type.FOOD ||
                this.get(x - 1, y) == Type.FOOD ||
                this.get(x, y + 1) == Type.FOOD ||
                this.get(x, y - 1) == Type.FOOD ||
                this.get(x + 1, y + 1) == Type.FOOD ||
                this.get(x + 1, y - 1) == Type.FOOD ||
                this.get(x - 1, y + 1) == Type.FOOD ||
                this.get(x - 1, y - 1) == Type.FOOD ||
                this.get(x + 1, y) == Type.CANTEEN ||
                this.get(x - 1, y) == Type.CANTEEN ||
                this.get(x, y + 1) == Type.CANTEEN ||
                this.get(x, y - 1) == Type.CANTEEN ||
                this.get(x + 1, y + 1) == Type.CANTEEN ||
                this.get(x + 1, y - 1) == Type.CANTEEN ||
                this.get(x - 1, y + 1) == Type.CANTEEN ||
                this.get(x - 1, y - 1) == Type.CANTEEN
                ){
            return false;
        }
        
        return true;
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
    
    public Type get(final XY xy){
        return this.get(xy.x, xy.y);
    }
    
    private void set(final int x, final int y, final Type t){
        this.plan[x + y * this.width] = t;
    }
    
    private void set(final XY xy, final Type t){
        this.set(xy.x, xy.y, t);
    }
    
    public Wing getWing(final int x, final int y){
        if (x < 0 || y < 0 || x >= this.width || y >= this.height){
            return null;
        }
        return this.wings[x + y * this.width];
    }
    
    private Wing getWing(final XY xy){
        return this.getWing(xy.x, xy.y);
    }
    
    private void setWing(final int x, final int y, final Wing w){
        this.wings[x + y * this.width] = w;
    }
    
    private void setWing(final XY xy, final Wing w){
        this.setWing(xy. x, xy.y, w);
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
