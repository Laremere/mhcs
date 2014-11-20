package edu.umn.d.grenoble.mhcs.bus;

import com.google.gwt.event.shared.GwtEvent;

import edu.umn.d.grenoble.mhcs.modules.Area;

public class AreaUpdateEvent extends GwtEvent<AreaUpdateEventHandler>{
    public static Type<AreaUpdateEventHandler> TYPE =
            new Type<AreaUpdateEventHandler>();
    
    private Area area;
    
    public AreaUpdateEvent(final Area area_){
        this.area = area_;
    }
    
    public Area getArea() {
        return this.area;
    }

    @Override
    public Type<AreaUpdateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final AreaUpdateEventHandler handler) {
        handler.onEvent(this);
    }
}
