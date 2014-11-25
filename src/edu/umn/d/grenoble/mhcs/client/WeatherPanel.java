package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class WeatherPanel {
    private String url;
    private String result;
    private final JsonpRequestBuilder jsonp;
    private FlexTable weatherTable;
    private Label tempLabel;
    private Label visLabel;

    
    /**
     * Constructor for WeatherPanel 
     * Obtains JSON Object for current conditions
     * Creates Panel to display conditions.
     */
    public WeatherPanel(){
        this.url = "http://api.wunderground.com/api/8fd09796b6c9895c/conditions/q/55812.json?";
        
        this.url= URL.encode(this.url);
        
        this.jsonp = new JsonpRequestBuilder();

        this.jsonp.setCallbackParam("callback");

        this.jsonp.requestObject(this.url,  new AsyncCallback<JavaScriptObject>() {
            public void onFailure(final Throwable caught){
                Window.alert("Json onFailure");
            }
            

            @Override
            
            public void onSuccess(final JavaScriptObject s) {

                JSONObject obj = new JSONObject(s);
                result = obj.toString();
                update(result);

                
            }
            
        });
        
        this.weatherTable = new FlexTable();
        this.visLabel = new Label("Visibility (Km): ");
        this.tempLabel = new Label("Temperature (C): ");
        this.weatherTable.setWidget(0,0,this.visLabel);
        this.weatherTable.setWidget(1, 0, this.tempLabel);
    }
    
    /**
     * Update function parses JSONObject for relevant values
     * Adds values to panel
     * @param conditions
     */
    public void update(final String conditions){
        String stringAll = conditions; 
        JSONObject jsonA = 
                (JSONObject) JSONParser.parseLenient(stringAll);
        JSONValue jsonTry = jsonA.get("current_observation");
        
        JSONObject jsonB = 
                (JSONObject)JSONParser.parseLenient(jsonTry.toString());
        JSONValue temp = jsonB.get("temp_c");
        JSONValue visibility = jsonB.get("visibility_km");
        
        String stringTemp = temp.toString();
        String stringVis = visibility.toString();
        
        this.weatherTable.setWidget(0, 1, new Label(stringTemp));
        this.weatherTable.setWidget(1, 1, new Label(stringVis));
        
    }
    


    public FlexTable getWeatherPanel() {
        return this.weatherTable;
    }
}
