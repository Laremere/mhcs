package edu.umn.d.grenoble.mhcs.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * The panel that displays the current weather status from wunderground
 *
 * @author Justin Pieper
 *
 */
public class WeatherPanel {
    
 /* Initialization */
    
    private final JsonpRequestBuilder jsonp;
    private final JsonpRequestBuilder jsonp1;
    private FlowPanel weatherTable;
    private String visString;
    private String tempString;
    private Image wunderLogo;
    private Label weatherHeader;
    private String stringTemp;
    private String stringVis;
    private String stringHour;
    private String stringMinute;
  
    /**
     * The WeatherPanel Constructor.
     * Obtains a JSON Object containing the current weather conditions
     * and creates a panel to display those conditions.
     */
    public WeatherPanel(){
        
        String url= URL.encode("http://api.wunderground.com/api/8fd09796b6c9895c/conditions/q/55812.json?");
        String url2 = URL.encode("http://api.wunderground.com/api/8fd09796b6c9895c/astronomy/q/55812.json?");
        
        
        //Next two sections are performing JSON request to wunderground servers and calling update methods.
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
    
        this.jsonp1 = new JsonpRequestBuilder();
        this.jsonp1.setCallbackParam("callback");        
        this.jsonp1.requestObject(url2,  new AsyncCallback<JavaScriptObject>() {
            public void onFailure(final Throwable caught){
                Window.alert("Json onFailure");
            }
          
            @Override          
            public void onSuccess(final JavaScriptObject s) {
                JSONObject obj = new JSONObject(s);
                update2( obj.toString() );              
            }     
        });
        
        this.weatherTable = new FlowPanel();
        this.weatherTable.setStyleName("weatherPanel");
        this.visString = new String("Visibility: ");
        this.tempString = new String("Temperature:  ");
        
        this.wunderLogo = new Image();
        this.wunderLogo.setUrl("images/WunderGroundLogo.jpg");
        
        this.weatherHeader = new Label("Weather Conditions:");
        
        

    }
    
 /* Methods */
  
    /**
     * Getter (Accessor) for the weather panel.
     */
    public FlowPanel getWeatherPanel() {
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
        
        this.stringTemp = conditions.get("temp_c").toString();
        this.stringVis = conditions.get("visibility_km").toString();
        
        
        

        this.weatherTable.add(this.weatherHeader);
        this.weatherTable.add(new Label(this.tempString + "  " + this.stringTemp + " Degrees C"));
        this.weatherTable.add(new Label(this.visString + " " + this.stringVis + " Km"));
        this.weatherTable.add(new Label("Sunset time: " + this.stringHour + ":" + this.stringMinute));
        this.weatherTable.add(this.wunderLogo);
        
  
    }
    
    
    /**
     * Parses a JSONObject for relevant values then 
     * adds the values to the weatherTable.
     * @param conditions_ - The current weather conditions
     */
    public void update2(final String astronomy_) {
        JSONObject astronomy = (JSONObject) JSONParser.parseLenient(astronomy_);
        String current = astronomy.get("sun_phase").toString();
        astronomy = (JSONObject) JSONParser.parseLenient(current);
        
        current = astronomy.get("sunset").toString();
        astronomy = (JSONObject) JSONParser.parseLenient(current);
        
        this.stringHour = astronomy.get("hour").toString();
        this.stringMinute = astronomy.get("minute").toString();

    }
    
}
