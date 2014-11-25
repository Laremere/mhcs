package edu.umn.d.grenoble.mhcs.bus;

import com.google.gwt.event.shared.GwtEvent;

public class AreaClickEvent extends GwtEvent<AreaClickEventHandler>{
    public static Type<AreaClickEventHandler> TYPE =
            new Type<AreaClickEventHandler>();
    
    private int xcoordinate;
    private int ycoordinate;
    
    public AreaClickEvent(final int x, final int y){
        this.xcoordinate = x;
        this.ycoordinate = y;
    }
    
    public int getX() {
        return this.xcoordinate;
    }
    
    public int getY() {
        return this.ycoordinate;
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
