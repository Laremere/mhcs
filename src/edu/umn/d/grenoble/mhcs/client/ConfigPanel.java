package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfigPanel extends Tab {
    private SimpleLayoutPanel holder = new SimpleLayoutPanel();
    
    public ConfigPanel(){
        this.holder.setWidget(new Label("hi"));
        this.holder.setHeight("100px");
        this.holder.setWidth("800px");
    }

    @Override
    Widget getPanel() {
        return this.holder;
    }

    @Override
    void switchedTo() {
        // TODO Auto-generated method stub
        
    }

    @Override
    String getTabName() {
        return "Make New Configuration";
    }
}
