package edu.umn.d.grenoble.mhcs.bus;

import com.google.gwt.event.shared.EventHandler;

public interface SoundEventHandler extends EventHandler{
    
    void onEvent(SoundEvent event);
}
