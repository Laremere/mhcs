package edu.umn.d.grenoble.mhcs.client;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;

import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEventHandler;
import edu.umn.d.grenoble.mhcs.bus.Bus;

/**
 * Class that creates and plays sound output objects.
 * @author Justin Pieper
 *
 */
public class SoundOutput {
    private ToggleButton muteButton;
    private SoundController controller;
    private Sound moduleAddedSound;
    private Image muteIcon;

    
    /**
     * Constructor for sound output object
     */
    public SoundOutput() {
        this.muteIcon = new Image();
        this.muteIcon.setUrl("images/MuteImage.jpg");
        
        this.muteButton = new ToggleButton(muteIcon);
        this.muteButton.addStyleName("muteButton");
        this.muteButton.setPixelSize(45, 35);
        

        
        this.controller = new SoundController();
        this.moduleAddedSound = this.controller.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3, "audio/ModulesAdded.mp3");            
        
        final SoundOutput soundOutput = this;
        Bus.bus.addHandler(AreaUpdateEvent.TYPE, new AreaUpdateEventHandler(){
            @Override
            public void onEvent(final AreaUpdateEvent event) {
                if(soundOutput.muteButton.isDown()){
                    
                } else {
                soundOutput.moduleAddedSound.play();
                }
            }            
        });
    }
    
    public ToggleButton getMuteButton() {
        return this.muteButton;
    }
}
