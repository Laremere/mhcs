package edu.umn.d.grenoble.mhcs.client;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;

import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.bus.SoundEvent;
import edu.umn.d.grenoble.mhcs.bus.SoundEventHandler;

/**
 * Class that creates and plays sound output objects.
 * @author Justin Pieper
 *
 */
public class SoundOutput {
    private static SoundController controller = new SoundController();
    
    private ToggleButton muteButton;
    private Image muteIcon;

    
    /**
     * Constructor for sound output object
     */
    public SoundOutput() {
        this.muteIcon = new Image();
        this.muteIcon.setUrl("images/MuteImage.jpg");
        
        this.muteButton = new ToggleButton(this.muteIcon);
        this.muteButton.addStyleName("muteButton");
        this.muteButton.setPixelSize(45, 35);
        
        final SoundOutput soundOutput = this;
        Bus.bus.addHandler(SoundEvent.TYPE, new SoundEventHandler(){
            @Override
            public void onEvent(final SoundEvent event) {
                if(soundOutput.muteButton.isDown()){
                    
                } else {
                    event.getSound().play();
                }
            }            
        });
    }
    
    public ToggleButton getMuteButton() {
        return this.muteButton;
    }
    
    public enum Sounds{
        ModuleAdded("audio/ModulesAdded.mp3");
     
        private Sound sound;
        
        private Sounds(final String url){
            this.sound = SoundOutput.controller.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3, url);
        }
        
        void play(){
            this.sound.play();
        }
        
    }
}
