package edu.umn.d.grenoble.modules;

public class Module {
    public int id;
    public Orientation orientation;
    public Status status;
    public int xposition = 0;
    public int yposition = 0;

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
}
