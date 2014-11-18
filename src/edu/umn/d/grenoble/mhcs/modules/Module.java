package edu.umn.d.grenoble.mhcs.modules;

public class Module {
    public int id;
    public Orientation orientation;
    public Status status;
    public int xposition = 0;
    public int yposition = 0;

    public Module(){
        
    }
    
    public Type getType() {
        return Type.getFromId(id);
    }
    
    public boolean isValid() {
        return getType() != Type.Invalid
                && orientation != Orientation.Unknown
                && status != Status.Unknown
                && xposition >= 1
                && xposition <= 100
                && yposition >= 1
                && yposition <= 50;
    }
    
    public Module(Module other) {
        this.id = other.id;
        this.orientation = other.orientation;
        this.status = other.status;
        this.xposition = other.xposition;
        this.yposition = other.yposition;
    }
}
