package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

/**
 * The panel that displays the current weather status from wunderground
 * @author Scott Redig
 * @author Justin Pieper
 * @author Paul Rodysill
 */
public class WeatherPanel {
    
 /* Initialization */
    
    private final JsonpRequestBuilder jsonp;
    private FlexTable weatherTable;
  
    /**
     * The WeatherPanel Constructor.
     * Obtains a JSON Object containing the current weather conditions
     * and creates a panel to display those conditions.
     */
    public WeatherPanel(){
        
        String url= URL.encode("http://api.wunderground.com/api/8fd09796b6c9895c/conditions/q/55812.json?");
        
        this.jsonp = new JsonpRequestBuilder();
        this.jsonp.setCallbackParam("callback");
        this.jsonp.requestObject(url,  new AsyncCallback<JavaScriptObject>() {
            public void onFailure(final Throwable caught){
                Window.alert("Json onFailure");
            }
          
            @Override          
            public void onSuccess(final JavaScriptObject s) {
                JSONObject obj = new JSONObject(s);
                update( obj.toString() );              
            }     
        });
        
        this.weatherTable = new FlexTable();
        this.weatherTable.setWidget(0, 0, new Label("Visibility (Km): ") );
        this.weatherTable.setWidget(1, 0, new Label("Temperature (C): ") );
    }
    
 /* Methods */
  
    /**
     * Getter (Accessor) for the weather panel.
     */
    public FlexTable getWeatherPanel() {
        return this.weatherTable;
    }
    
    /**
     * Parses a JSONObject for relevant values then 
     * adds the values to the weatherTable.
     * @param conditions_ - The current weather conditions
     */
    public void update(final String conditions_){
        
        JSONObject conditions = (JSONObject) JSONParser.parseLenient(conditions_);
        String current = conditions.get("current_observation").toString();
        conditions = (JSONObject) JSONParser.parseLenient( current );
        
        String stringTemp = conditions.get("temp_c").toString();
        String stringVis = conditions.get("visibility_km").toString();
        
        this.weatherTable.setWidget(0, 1, new Label(stringTemp));
        this.weatherTable.setWidget(1, 1, new Label(stringVis));     
    }
    
}
