package edu.umn.d.grenoble.mhcs.client;


import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.cfinder.Layout;
import edu.umn.d.grenoble.mhcs.cfinder.Shape;
import edu.umn.d.grenoble.mhcs.modules.Area;
import edu.umn.d.grenoble.mhcs.modules.Module;
import edu.umn.d.grenoble.mhcs.modules.Status;
import edu.umn.d.grenoble.mhcs.modules.Type;

public class ConfigPanel extends Tab {
    private SimpleLayoutPanel holder = new SimpleLayoutPanel();
    private final ConfigPanel configPanel = this;
    
    public ConfigPanel(){
        this.holder.setHeight("100px");
        this.holder.setWidth("800px");
    }

    @Override
    Widget getPanel() {
        return this.holder;
    }

//    private int count = 30;
//    private float ratio = 0.0f;
//    
    @Override
    void switchedTo() {
        this.holder.setWidget(new StepStart().getPanel());
//        Timer t = new Timer() {
//            @Override
//            public void run() {
//                ratio += 0.1f;
//                if (ratio > 1.0f){
//                    ratio = 0;
//                    count += 1;
//                }
//                Bus.bus.fireEvent( new AreaUpdateEvent( Shape.shapes[1].getLayout(count, ratio) ));
//                debug.setText("count: " + count + " ratio: " + ratio);
//            }
//        };
//        t.scheduleRepeating(100);
    }

    @Override
    String getTabName() {
        return "Make New Configuration";
    }
    
    class StepStart{
        FlowPanel panel = new FlowPanel();
        Area area;
        
        StepStart(){
            {
                final ListBox list = new ListBox();
                String[] areas = AreaHolder.getAreas();
                if(areas.length != 0){
                    area = AreaHolder.getArea(AreaHolder.getAreas()[0]);
                    Bus.bus.fireEvent( new AreaUpdateEvent( area ));
                }
                //Cycles through list of areas and adds to list boxes
                for(String name : areas){
                    if(name == null || name.equals("")){}
                    else{
                        list.addItem(name);
                    }
                }
                list.addChangeHandler(new ChangeHandler(){
                    @Override
                    public void onChange(ChangeEvent event) {
                        area = AreaHolder.getArea(AreaHolder.getAreas()[list.getSelectedIndex()]);
                        Bus.bus.fireEvent( new AreaUpdateEvent( area ));
                    }
                });
                panel.add(list);
            }
            {
                Button next = new Button("next");
                next.addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        if (area == null){
                            Window.alert("You must first have a map to configure");
                            return;
                        }
                        configPanel.holder.setWidget(new StepLayout(area).getPanel());
                    }
                });
                
                panel.add(next);
            }
        }
        
        Widget getPanel(){
            return panel;
        }
    }
    
    class StepLayout{
        Area area;
        Layout layout;
        Shape shape;
        float ratio;
        int plainCount;
        int plainCountUse;
        Label spotCount;
        FlowPanel panel = new FlowPanel();
        final StepLayout stepLayout = this;
        StepLayout(Area area_){
            area = area_;
            
            panel.setStyleName("flowPanel_inline");
            
            for(Module m : area.getModules()){
                if (m.getType() == Type.PLAIN && m.getStatus() == Status.GOOD){
                    plainCount += 1;
                }
            }
            if ( plainCount < 3){
                Window.alert("Not enough functional plain modules to make a configuration");
                configPanel.holder.setWidget(new StepStart().getPanel());
                return;
            }
            plainCount = 40;
            plainCountUse = plainCount;
            {
                final ListBox layouts = new ListBox();
                for (Shape shape : Shape.shapes){
                    layouts.addItem(shape.getName());
                }
                layouts.addChangeHandler(new ChangeHandler(){
                    @Override
                    public void onChange(ChangeEvent event) {
                        stepLayout.shape = Shape.shapes[layouts.getSelectedIndex()];
                        stepLayout.updateLayout();
                    }
                });
             
                panel.add(new Label("Layout Type"));
                panel.add(layouts);
                layouts.setSelectedIndex(1);
                shape = Shape.shapes[1];
                
            }
            {
                final ListBox ratios = new ListBox();
                for (int i = 0; i <= 8; i ++){
                    ratios.addItem("" + (i / 8f));
                }
                ratios.addChangeHandler(new ChangeHandler(){
                    @Override
                    public void onChange(ChangeEvent event) {
                        stepLayout.ratio = ratios.getSelectedIndex() / 8f;
                        stepLayout.updateLayout();
                    }
                });
                
                panel.add(new Label("Ratio:"));
                panel.add(ratios);
            }
            {
                final ListBox numPlain = new ListBox();
                for (int i = 0; plainCount - i >= 3; i+= 1){
                    numPlain.addItem("" + (plainCount - i));
                }
                numPlain.addChangeHandler(new ChangeHandler(){
                    @Override
                    public void onChange(ChangeEvent event) {
                        stepLayout.plainCountUse = stepLayout.plainCount - numPlain.getSelectedIndex();
                        stepLayout.updateLayout();
                    }
                });
                panel.add(new Label("Number to Use"));
                panel.add(numPlain);
            }
            spotCount = new Label();
            panel.add(spotCount);
            
            updateLayout();
        }
        
        void updateLayout(){
            layout = shape.getLayout(plainCountUse, ratio);
            spotCount.setText("Number of spots for modules: " + layout.SpotCount());
            Bus.bus.fireEvent( new AreaUpdateEvent( layout ));
        }
        
        Widget getPanel(){
            return panel;
        }
    }
}
