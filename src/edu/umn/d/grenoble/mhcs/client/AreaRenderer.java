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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaRenderer {
    private static final int tileSize = 10;
    private Canvas canvas;
    private int imagesRemaining = 0;
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
        
        imagesRemaining = toLoad.size();
        
        for (final String filePath : toLoad) {
            final Image img = new Image(filePath);
            final ImageElement imgHandler = ImageElement.as(img.getElement());;
            images.put(filePath, imgHandler);
            img.addErrorHandler(new ErrorHandler(){
                @Override
                public void onError(ErrorEvent event) {
                    throw new Error("Image failed to load: " + filePath);
                }
            });
            img.addLoadHandler(new LoadHandler(){
                public void onLoad(LoadEvent event) {
                    imagesRemaining --;
                    if (imagesRemaining <= 0) {
                        mhcs.Begin();
                    }
                }
            });
            img.setVisible(false);
            RootPanel.get().add(img);
        }
        
        canvas = Canvas.createIfSupported();
        if (canvas == null) {
            return;
        }
        
        canvas.setSize(tileSize * 100 + "px", tileSize * 50 + "px");
        canvas.setCoordinateSpaceWidth(tileSize * 100);
        canvas.setCoordinateSpaceHeight(tileSize * 50);
    }
    
    public void RenderArea(Area area) {
        Context2d context = canvas.getContext2d();
        
        context.setFillStyle("#444444");
        context.fillRect(0, 0, tileSize * 100, tileSize * 50);
        
        for (Module module : area.getModules()) {
            context.drawImage(
                    images.get(module.getType().getImageUrl()),
                    (module.getX() - 1) * tileSize, 
                    (50 - module.getY()) * tileSize,
                    tileSize, tileSize);
        }
        
    }
    
    public Canvas GetCanvas() {
        return canvas;
    }
    
}
