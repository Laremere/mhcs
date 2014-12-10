package edu.umn.d.grenoble.mhcs.bus;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Window;

import edu.umn.d.grenoble.mhcs.cfinder.Layout;
import edu.umn.d.grenoble.mhcs.cfinder.Shape;
import edu.umn.d.grenoble.mhcs.modules.Area;

public class AreaUpdateEvent extends GwtEvent<AreaUpdateEventHandler>{
    public static Type<AreaUpdateEventHandler> TYPE =
            new Type<AreaUpdateEventHandler>();
    
    public enum MoveType {
        MoveUp,
        MoveDown,
        MoveLeft,
        MoveRight,
        ZoomIn,
        ZoomOut;    
    }
    
    private Area area;
    private Area sideArea;
    private MoveType moveType;
    private Layout layout;
    
    public AreaUpdateEvent(){
        
    }
    
    public AreaUpdateEvent(final Area area_){
        this.area = area_;
    }
    
    public AreaUpdateEvent(final Area area_, final Area sideArea_){
        this.area = area_;
        this.sideArea = sideArea_;
    }
    
    public AreaUpdateEvent(final MoveType moveType_){
        this.moveType = moveType_;
    }
    
    public AreaUpdateEvent(final Layout layout_){
        this.layout = layout_;
    }
    
    public Area getArea() {
        return this.area;
    }
    
    public Area getSideArea() {
        return this.sideArea;
    }
    
    public MoveType getMoveType() {
        return this.moveType;
    }
    
    public Layout getLayout() {
        return this.layout;
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
