package edu.umn.d.grenoble.mhcs.client;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
import edu.umn.d.grenoble.mhcs.cfinder.BadAreas;
import edu.umn.d.grenoble.mhcs.cfinder.Distances;
import edu.umn.d.grenoble.mhcs.cfinder.Layout;
import edu.umn.d.grenoble.mhcs.cfinder.Plan;
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
        Widget wpanel = panel;
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
                wpanel = new StepStart().getPanel();
                return;
            }
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
            {
                spotCount = new Label();
                panel.add(spotCount);
            }
            {
                final Button next = new Button("Next");
                next.addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        configPanel.holder.setWidget(new CountOfEachStep(stepLayout.area, stepLayout.layout).getPanel());
                    }
                });
                panel.add(next);
            }
            
            updateLayout();
        }
        
        void updateLayout(){
            layout = shape.getLayout(plainCountUse, ratio);
            spotCount.setText("Number of spots for modules: " + layout.SpotCount());
            Bus.bus.fireEvent( new AreaUpdateEvent( layout ));
        }
        
        Widget getPanel(){
            return wpanel;
        }
    }
    
    class CountOfEachStep{
        Area area;
        Layout layout;
        FlowPanel panel = new FlowPanel();
        Widget wpanel = panel;
        Map<Type, Integer> counts = new HashMap<Type, Integer>();
        Label countLabel = new Label();
        Button next = new Button("next");
        final CountOfEachStep coes = this;
        
        public CountOfEachStep(Area area_, Layout layout_){
            area = area_;
            layout = layout_;
            panel.setStyleName("flowPanel_inline");
            
            for (Type t: Type.values()){
                if (t != Type.PLAIN && t != Type.INVALID){
                    int count = 0;
                    for (Module m: area.getModules()){
                        if(m.getType() == t && m.getStatus() == Status.GOOD){
                            count += 1;
                        }
                    }
                    if (count < t.getMinCount()){
                        Window.alert("Not enough " + t.getTypeName() + " modules for a configuration!");
                        wpanel = new StepStart().getPanel();
                        return;
                    }
                    
                    counts.put(t, count);
                
                    Label l = new Label(t.getTypeName());
                
                    final ListBox numOf = new ListBox();
                    for (int i = 0; count - i >=  t.getMinCount(); i+= 1){
                        numOf.addItem("" + (count - i));
                    }
                    final Type ft = t;
                    final int TotalCount = count;
                    numOf.addChangeHandler(new ChangeHandler(){
                        @Override
                        public void onChange(ChangeEvent event) {
                            counts.put(ft, TotalCount - numOf.getSelectedIndex());
                            updateCount();
                        }
                    });
                    
                    panel.add(l);
                    panel.add(numOf);
                }
            }
            
            {
                next.addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        configPanel.holder.setWidget(new ViewPlanStep(coes.area, coes.layout, coes.counts).getPanel());
                    }
                });
            }
            
            panel.add(countLabel);
            panel.add(next);
            updateCount();
        }
        
        void updateCount(){
            int sumOfModules = 0;
            for (Type t: Type.values()){
                if (counts.containsKey(t)){
                    sumOfModules += counts.get(t);
                }
            }
            int out_of = layout.SpotCount();
            next.setEnabled(sumOfModules <= out_of);
            countLabel.setText("Using " + sumOfModules + " out of " + out_of + " module spots.");
            
            Plan plan = new Plan(this.layout, this.counts);
            Bus.bus.fireEvent( new AreaUpdateEvent( plan ));

        }
        
        Widget getPanel(){
            return wpanel;
        }
    }
    
    class ViewPlanStep{
        FlowPanel panel = new FlowPanel();
        
        Area area;
        Layout layout;
        Plan plan;
        Area outArea;
        BadAreas badAreas;
        
        int baseX;
        int baseY;
        final ViewPlanStep viewPlanStep = this;
        
        public ViewPlanStep(final Area area_, final Layout layout_, final Map<Type, Integer> counts_){
            this.area = area_;
            this.layout = layout_;
            panel.setStyleName("flowPanel_inline");
            
            plan = new Plan(this.layout, counts_);
            Bus.bus.fireEvent( new AreaUpdateEvent( plan ));
            badAreas = new BadAreas(area);
            
            int AverageX = 0;
            int AverageY = 0;
            int AverageCount = 0;
            for (Module m : area.modules){
                if(m.getStatus() == Status.GOOD){
                    AverageX += m.getX();
                    AverageY += m.getY();
                    AverageCount += 1;
                }
            }
            AverageX /= AverageCount;
            AverageY /= AverageCount;
            baseX = AverageX - plan.getWidth() / 2;
            baseY = AverageY - plan.getHeight() / 2;

            {
                final ListBox listX = new ListBox();
                for (int i = 1; i <= Area.Width; i+= 1){
                    listX.addItem("" + i);
                }
                listX.addChangeHandler(new ChangeHandler(){
                    @Override
                    public void onChange(ChangeEvent event) {
                        viewPlanStep.baseX = listX.getSelectedIndex() + 1;
                        viewPlanStep.plan();
                    }
                });
                panel.add(new Label("X"));
                panel.add(listX);
                listX.setSelectedIndex(baseX - 1);
            }
            {
                final ListBox listY = new ListBox();
                for (int i = 1; i <= Area.Height; i+= 1){
                    listY.addItem("" + i);
                }
                listY.addChangeHandler(new ChangeHandler(){
                    @Override
                    public void onChange(ChangeEvent event) {
                        viewPlanStep.baseY = listY.getSelectedIndex() + 1;
                        viewPlanStep.plan();
                    }
                });
                panel.add(new Label("Y"));
                panel.add(listY);
                listY.setSelectedIndex(baseY - 1);
            }
            plan();
        }
        
        void plan(){
            int finalX = baseX;
            int finalY = baseY;
            if(!test(finalX, finalY)){
                outerLoop:
                for (int distance = 1; distance < Area.Width; distance ++){
                    for (int i = 0; i < distance * 2; i++){
                        finalX = baseX - distance + i;
                        finalY = baseY + distance;
                        if(test(finalX, finalY)){
                            break outerLoop;
                        }
                        finalX = baseX + distance;
                        finalY = baseY + distance - i;
                        if(test(finalX, finalY)){
                            break outerLoop;
                        }
                        finalX = baseX + distance - i;
                        finalY = baseY - distance;
                        if(test(finalX, finalY)){
                            break outerLoop;
                        }
                        finalX = baseX - distance;
                        finalY = baseY - distance + i;
                        if(test(finalX, finalY)){
                            break outerLoop;
                        }
                    }
                }
            }
            
            outArea = new Area();
            
            List<Module> toUse = new ArrayList<Module>(); 
            for(Module m: area.modules){
                Module copy = new Module(m);
                if(copy.getStatus() == Status.GOOD){
                    toUse.add(copy);
                }
                outArea.addModule(copy);
            }
            
            moveModulesLoop:
            for(int relX = 0; relX < plan.getWidth(); relX++){
                for(int relY = 0; relY < plan.getHeight(); relY++){
                    if(plan.get(relX, relY) != null){
                        Type toGet = plan.get(relX, relY);

                        Distances dist = new Distances(outArea, finalX + relX, finalY + relY);

                        int BestDistance = Area.Width * Area.Height;
                        Module toMove = null;
                        for(Module m: toUse){
                            if (m.getType() == toGet){
                                int thisDist = dist.get(m.getX(), m.getY());
                                if (thisDist < BestDistance){
                                    toMove = m;
                                    BestDistance = thisDist;
                                }
                            }
                        }

                        if (toMove == null){
                            Window.alert("Can't reach module of type " + toGet.getTypeName() + " restart config process and use fewer of them to build in this location");
                            break moveModulesLoop;
                        }
                        toUse.remove(toMove);
                        toMove.setX(finalX + relX);
                        toMove.setY(finalY + relY);
                    }
                }
            }
            
            for(Module m: toUse){
                int x = m.getX();
                int y = m.getY();
                int dj = 1;
                int j = 1;
                int dx = 2;
                int dy = 0;
                for( int i = 0; i < 100000 && 
                        ( 
                                (outArea.occupied(x, y) != m && outArea.occupied(x, y) != null ) ||
                            (
                                x >= finalX - 1 && y >= finalY - 1 &&
                                x <= finalX + plan.getWidth() &&
                                y <= finalY + plan.getHeight()
                            ) ||
                            x <= 0 || y <= 0 ||   
                            x > Area.Width || y > Area.Height);
                        i += 1){
                    x += dx;
                    y += dy;
                    j -= 1;
                    if (j <= 0){
                        j = dj;
                        dj += 1;
                        int ty = dy;
                        dy = dx;
                        dx = -1 * ty;
                    }
                }
                m.setX(x);
                m.setY(y);
            }
            
            Bus.bus.fireEvent( new AreaUpdateEvent( outArea ));
        }
        
        boolean test(int baseX_, int baseY_){
            if(baseX_ < 1 || baseY_ < 1 || baseX_ + plan.getWidth() - 1 > Area.Width
                    || baseY_ + plan.getHeight() - 1 > Area.Height){
                return false;
            }
            
            for (int relX = 0; relX < plan.getWidth(); relX ++){
                for (int relY = 0; relY < plan.getHeight(); relY ++){
                    if(plan.get(relX, relY) != null){
                        if(! badAreas.get(baseX_ + relX, baseY_ + relY)){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        
        Widget getPanel(){
            return panel;
        }
    }
}
