package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;
import edu.umn.d.grenoble.mhcs.modules.Type;
import edu.umn.edu.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.edu.d.grenoble.mhcs.bus.AreaUpdateEventHandler;
import edu.umn.edu.d.grenoble.mhcs.bus.Bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaRenderer {
    private static final int tileSize = 10;
    private Canvas canvas;
    private int imagesRemaining;
    private Map<String, ImageElement> images = new HashMap<String, ImageElement>();
    
    public AreaRenderer(final MarsHabitatConfigurationSystem mhcs) {
        //Preload images
        List<String> toLoad = new ArrayList<String>();
        for (Type t : Type.values()) {
            String url = t.getImageUrl();
            if (url != null) {
                toLoad.add(url);
            }
        }
        
        this.imagesRemaining = toLoad.size();
        
        for (final String filePath : toLoad) {
            final Image img = new Image(filePath);
            final ImageElement imgHandler = ImageElement.as(img.getElement());
            this.images.put(filePath, imgHandler);
            img.addErrorHandler(new ErrorHandler(){
                @Override
                public void onError(final ErrorEvent event) {
                    throw new Error("Image failed to load: " + filePath);
                }
            });
            final AreaRenderer areaRenderer = this;
            img.addLoadHandler(new LoadHandler(){
                public void onLoad(final LoadEvent event) {
                    areaRenderer.imagesRemaining -= 1;
                    if (areaRenderer.imagesRemaining <= 0) {
                        mhcs.Begin();
                    }
                }
            });
            img.setVisible(false);
            RootPanel.get().add(img);
        }
        
        this.canvas = Canvas.createIfSupported();
        if (this.canvas == null) {
            return;
        }
        
        final String px = "px";
        this.canvas.setSize(tileSize * Area.Width + px, tileSize * Area.Height + px);
        this.canvas.setCoordinateSpaceWidth(tileSize * Area.Width);
        this.canvas.setCoordinateSpaceHeight(tileSize * Area.Height);
        
        final AreaRenderer areaRenderer = this;
        Bus.bus.addHandler(AreaUpdateEvent.TYPE, new AreaUpdateEventHandler(){
            @Override
            public void onEvent(final AreaUpdateEvent event) {
                areaRenderer.RenderArea(event.getArea());
            }
            
        });
    }
    
    private void RenderArea(final Area area) {
        Context2d context = this.canvas.getContext2d();
        
        context.setFillStyle("#444444");
        context.fillRect(0, 0, tileSize * Area.Width, tileSize * Area.Height);
        
        for (Module module : area.getModules()) {
            context.drawImage(
                    this.images.get(module.getType().getImageUrl()),
                    (module.getX() - 1) * tileSize, 
                    (Area.Height - module.getY()) * tileSize,
                    tileSize, tileSize);
        }
        
    }
    
    public Canvas GetCanvas() {
        return this.canvas;
    }
    
}
