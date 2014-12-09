package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import edu.umn.d.grenoble.mhcs.bus.AreaClickEvent;
import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEventHandler;
import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;
import edu.umn.d.grenoble.mhcs.modules.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Renders the landing area and the locations of the modules.
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public class AreaRenderer {
    private static final String units = "px";
    private static final int canvasWidth = 1000;
    private static final int canvasHeight = 500;
 /* Initialization */
    
    private Canvas canvas;
    private int imagesRemaining;
    private Map<String, ImageElement> images = new HashMap<String, ImageElement>();
    private String background = "images/MarsModuleLandingArea.jpg";
    private Area currentView;
    private Area splitView;
    
    private int tileSize = 10;
    private float viewX;
    private float viewY;
    
    public AreaRenderer(final MarsHabitatConfigurationSystem mhcs) {
        // Preload images
        List<String> toLoad = new ArrayList<String>();
        for ( Type t : Type.values() ) {
            String url = t.getImageUrl();
            if (url != null) {
                toLoad.add(url);
            }
        }
        toLoad.add(this.background);
        
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
        
        this.canvas.setSize(canvasWidth + units, canvasHeight + units);
        this.canvas.setCoordinateSpaceWidth(canvasWidth);
        this.canvas.setCoordinateSpaceHeight(canvasHeight);
        
        final AreaRenderer areaRenderer = this;
        
        Bus.bus.addHandler(AreaUpdateEvent.TYPE, new AreaUpdateEventHandler(){
            @Override
            public void onEvent(final AreaUpdateEvent event) {
                if (event.getMoveType() == null){
                    Area view1 = event.getArea();
                    Area view2 = event.getSideArea();
                    if (view1 != null){
                        areaRenderer.currentView = new Area(view1);
                        if (view2 != null){
                            areaRenderer.splitView = new Area(view2);
                        }
                    }    
                } else if (event.getMoveType() == AreaUpdateEvent.MoveType.ZoomIn){
                    
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.ZoomOut){
                    
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.MoveUp){
                    
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.MoveDown){
                    
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.MoveLeft){
                    
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.MoveRight){
                    
                }
                
                areaRenderer.RenderArea();
            }            
        });
        
        this.canvas.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(final ClickEvent event) {
                int x = event.getRelativeX(areaRenderer.canvas.getElement()) / areaRenderer.tileSize + 1;
                int y = Area.Height - event.getRelativeY(areaRenderer.canvas.getElement()) / areaRenderer.tileSize;
                Bus.bus.fireEvent(new AreaClickEvent(x, y));
            }            
        });
        
    }
    
 /* Methods */
    
    /**
     * Getter (Accessor) for the canvas representation of the landing area.
     */
    public Canvas GetCanvas() {
        return this.canvas;
    }
    
    /**
     * Adds the modules to the canvas.
     * @param area - The canvas that the modules are to be added to
     */
    private void RenderArea() {
        Context2d ctx = this.canvas.getContext2d();
        
        ctx.setFillStyle("#444444");
        ctx.fillRect(0, 0, canvasWidth, canvasHeight);
        
        if (this.currentView == null){
            ctx.drawImage(this.images.get(this.background), 0, 0, Area.Width * this.tileSize, Area.Height * this.tileSize);
        } else if (this.splitView == null){
            this.RenderModules(0, canvasWidth, 0, canvasHeight, this.currentView, 0, 0, ctx);    
        } else {
            this.RenderModules(0, canvasWidth / 2, 0, canvasHeight, this.currentView, 0, 0, ctx);
            this.RenderModules(canvasWidth / 2, canvasWidth, 0, canvasHeight, this.splitView, 0, 0, ctx);
        }
        
        
    }
    
    private void RenderModules(final int canvasXmin, final int canvasXmax, 
            final int canvasYmin, final int canvasYmax,
            final Area area, final float xMin, final float yMin, final Context2d ctx){
        ctx.save();
        ctx.beginPath();
        ctx.moveTo(canvasXmin, canvasYmin);
        ctx.lineTo(canvasXmin, canvasYmax);
        ctx.lineTo(canvasXmax, canvasYmax);
        ctx.lineTo(canvasXmax, canvasYmin);
        ctx.moveTo(canvasXmin, canvasYmin);
        ctx.clip();

        ctx.drawImage(this.images.get(this.background), canvasXmin, canvasYmin, Area.Width * tileSize, Area.Height * tileSize);
        for (Module module : this.currentView.getModules()) {
            ctx.drawImage(
                    this.images.get(module.getType().getImageUrl()),
                    (module.getX() - 1) * tileSize + canvasXmin, 
                    (Area.Height - module.getY()) * tileSize + canvasYmin,
                    tileSize, tileSize);
        }
        
        ctx.restore();
    }
}
