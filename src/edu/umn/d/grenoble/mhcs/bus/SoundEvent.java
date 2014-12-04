package edu.umn.d.grenoble.mhcs.bus;

import com.google.gwt.event.shared.GwtEvent;

import edu.umn.d.grenoble.mhcs.client.SoundOutput;

public class SoundEvent extends GwtEvent<SoundEventHandler>{
    public static Type<SoundEventHandler> TYPE =
            new Type<SoundEventHandler>();
    
    private SoundOutput.Sounds sound;
    
    public SoundEvent(final SoundOutput.Sounds sound_){
        this.sound = sound_;
    }
    
    public SoundOutput.Sounds getSound() {
        return this.sound;
    }

    @Override
    public Type<SoundEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final SoundEventHandler handler) {
        handler.onEvent(this);
    }
}
