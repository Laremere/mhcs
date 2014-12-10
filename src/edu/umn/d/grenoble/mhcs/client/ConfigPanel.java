package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.cfinder.Shape;

public class ConfigPanel extends Tab {
    private SimpleLayoutPanel holder = new SimpleLayoutPanel();
    private Label debug = new Label("hi");
    
    public ConfigPanel(){
        this.holder.setWidget(debug);
        this.holder.setHeight("100px");
        this.holder.setWidth("800px");
    }

    @Override
    Widget getPanel() {
        return this.holder;
    }

    private int count = 30;
    private float ratio = 0.0f;
    
    @Override
    void switchedTo() {
        Timer t = new Timer() {
            @Override
            public void run() {
                ratio += 0.1f;
                if (ratio > 1.0f){
                    ratio = 0;
                    count += 1;
                }
                Bus.bus.fireEvent( new AreaUpdateEvent( Shape.shapes[1].getLayout(count, ratio) ));
                debug.setText("count: " + count + " ratio: " + ratio);
            }
        };
        t.scheduleRepeating(100);
    }

    @Override
    String getTabName() {
        return "Make New Configuration";
    }
}
