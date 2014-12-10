package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umn.d.grenoble.mhcs.bus.AreaUpdateEvent;
import edu.umn.d.grenoble.mhcs.bus.Bus;
import edu.umn.d.grenoble.mhcs.modules.Area;

public class ImportPanel extends Tab {
    FlowPanel panel = new FlowPanel();
    
    public ImportPanel(){
        Button importGps = new Button("Load Via GPS");
        importGps.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                for(int curCase = 0; curCase < 100; curCase += 1){
                    String url= URL.encode("http://d.umn.edu/~redi0068/mhcs/proxy.php?case=" + curCase);
                    
                    JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
                    jsonp.setCallbackParam("callback");
                    
                    final Integer thisCase = curCase;
                    jsonp.requestString(url,  new AsyncCallback<String>() {
                        public void onFailure(final Throwable caught){
                            Window.alert("Json onFailure" + caught.getMessage());
                        }
                      
                        @Override          
                        public void onSuccess(final String result) {
                            if (result.equals("")){
                                return;
                            }
                            Area area = new Area(result); 
                            AreaHolder.saveArea("Imported Number " + thisCase, area);
                            Bus.bus.fireEvent( new AreaUpdateEvent( area ));
                        }     
                    });   
                }
            }
        });
        
        panel.add(importGps);
    }
    
    @Override
    Widget getPanel() {
        return panel;
    }

    @Override
    void switchedTo() {
        
    }

    @Override
    String getTabName() {
        return "Import Maps";
    }

}
