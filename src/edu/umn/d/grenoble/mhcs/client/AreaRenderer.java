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
    
    final private int tileSizeMin = 10;
    final private int tileSizeMax = 70;
    final private int tileSizeStep = 10;
    private int tileSize = this.tileSizeMin;
    
    private float viewX = 0;
    private float viewY = 1;
    
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
                    areaRenderer.currentView = new Area(event.getArea());
                    if (event.getSideArea() != null){
                        areaRenderer.splitView = new Area(event.getSideArea());
                    } else {
                        areaRenderer.splitView = null;
                    }
                } else if (event.getMoveType() == AreaUpdateEvent.MoveType.ZoomIn){
                    areaRenderer.tileSize += areaRenderer.tileSizeStep;
                    if (areaRenderer.tileSize > areaRenderer.tileSizeMax){
                        areaRenderer.tileSize = areaRenderer.tileSizeMax;
                    }
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.ZoomOut){
                    areaRenderer.tileSize -= areaRenderer.tileSizeStep;
                    if (areaRenderer.tileSize < areaRenderer.tileSizeMin){
                        areaRenderer.tileSize = areaRenderer.tileSizeMin;
                    }                    
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.MoveUp){
                    areaRenderer.viewY -= 1.0f / areaRenderer.tileSize;
                    if (areaRenderer.viewY < 0){
                        areaRenderer.viewY = 0;
                    }
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.MoveDown){
                    areaRenderer.viewY += 1.0f / areaRenderer.tileSize;
                    if (areaRenderer.viewY > 1){
                        areaRenderer.viewY = 1;
                    }                    
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.MoveLeft){
                    areaRenderer.viewX -= 1.0f / areaRenderer.tileSize;
                    if (areaRenderer.viewX < 0){
                        areaRenderer.viewX = 0;
                    }
                }else if (event.getMoveType() == AreaUpdateEvent.MoveType.MoveRight){
                    areaRenderer.viewX += 1.0f / areaRenderer.tileSize;
                    if (areaRenderer.viewX > 1){
                        areaRenderer.viewX = 1;
                    }
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
            this.RenderModules(0, canvasWidth, 0, canvasHeight, this.currentView, ctx);    
        } else {
            this.RenderModules(0, canvasWidth / 2, 0, canvasHeight, this.currentView, ctx);
            this.RenderModules(canvasWidth / 2, canvasWidth, 0, canvasHeight, this.splitView, ctx);
        }
        
        
    }
    
    private void RenderModules(final int canvasXmin, final int canvasXmax, 
            final int canvasYmin, final int canvasYmax,
            final Area area, final Context2d ctx){
        ctx.save();
        ctx.beginPath();
        ctx.moveTo(canvasXmin, canvasYmin);
        ctx.lineTo(canvasXmin, canvasYmax);
        ctx.lineTo(canvasXmax, canvasYmax);
        ctx.lineTo(canvasXmax, canvasYmin);
        ctx.moveTo(canvasXmin, canvasYmin);
        ctx.clip();

        int viewWidth = canvasXmax - canvasXmin;
        int viewHeight = canvasYmax - canvasYmin;

        int mapWidth = this.tileSize * Area.Width;
        int mapHeight = this.tileSize * Area.Height;

        int xMin = Math.round((mapWidth - viewWidth) * this.viewX);
        int yMin = Math.round((mapHeight - viewHeight) * this.viewY);
        
        ctx.drawImage(this.images.get(this.background), canvasXmin, canvasYmin, mapWidth, mapHeight);
        for (Module module : this.currentView.getModules()) {
            ctx.drawImage(
                    this.images.get(module.getType().getImageUrl()),
                    (module.getX() - 1) * this.tileSize + canvasXmin - xMin, 
                    (Area.Height - module.getY()) * this.tileSize + canvasYmin - yMin,
                    this.tileSize, this.tileSize);
        }
        
        ctx.restore();
    }
}
