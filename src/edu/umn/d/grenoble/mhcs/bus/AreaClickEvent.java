package edu.umn.d.grenoble.mhcs.bus;

import com.google.gwt.event.shared.GwtEvent;

public class AreaClickEvent extends GwtEvent<AreaClickEventHandler>{
    public static Type<AreaClickEventHandler> TYPE =
            new Type<AreaClickEventHandler>();
    
    private int xcoordinate;
    private int ycoordinate;
    private MouseType type;
    
    public enum MouseType {
        Pressed,
        Released,
        Dragged;
    }
    
    public AreaClickEvent(final int x, final int y, MouseType type){
        this.xcoordinate = x;
        this.ycoordinate = y;
        this.type = type;
        
    }
    

    
    public int getX() {
        return this.xcoordinate;
    }
    
    public int getY() {
        return this.ycoordinate;
    }
    
    public MouseType getMouseType() {
        return this.type;
    }

    @Override
    public Type<AreaClickEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final AreaClickEventHandler handler) {
        handler.onEvent(this);
    }
}
