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
    
    private final JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
    private final String url = URL.encode("http://api.wunderground.com/api/8fd09796b6c9895c/conditions/q/55812.json?");
    private final String url2 = URL.encode("http://api.wunderground.com/api/8fd09796b6c9895c/astronomy/q/55812.json?");
    private FlowPanel weatherPanel  = new FlowPanel();
    private Image wunderLogo = new Image();
    private String temperature;
    private String visibility;
    private int hoursUntilSunset;
    private int minutesUntilSunset;
    private boolean ready;
  
    /**
     * The WeatherPanel Constructor.
     * Obtains a JSON Object containing the current weather conditions
     * and creates a panel to display those conditions.
     */
    public WeatherPanel() {     
        
        this.weatherPanel.setStyleName("weatherPanel");
        this.wunderLogo.setUrl("images/WunderGroundLogo.jpg");
        
        // Gets the astronomical data from wunderground
        this.jsonp.requestObject(this.url2 ,  new AsyncCallback<JavaScriptObject>() {
            
            /**
             * If data retrieval is successful, the data gets processed in the updateSunset method.
             * NOTE: This class overrides AsyncCallback's onSuccess method
             */
            @Override          
            public void onSuccess(final JavaScriptObject s) {
                updateSunset( ( new JSONObject(s) ).toString() );
                if (ready) { create(); } else { ready = true; }
            }
            
            /**
             * If data retrieval is fails, an error message is displayed.
             * NOTE: This class overrides AsyncCallback's onFailure method
             */
            @Override
            public void onFailure(final Throwable caught){
                Window.alert("Error: Problem recieving JSON for sunset from wunderground");
            }     
        });
            
        // Gets the weather data from wunderground
        this.jsonp.requestObject(this.url ,  new AsyncCallback<JavaScriptObject>() {
          
            /**
             * If data retrieval is successful, the data gets processed in the updateWeather method.
             * NOTE: This class overrides AsyncCallback's onSuccess method
             */
            @Override          
            public void onSuccess(final JavaScriptObject s) {
                updateWeather( ( new JSONObject(s) ).toString() );
                if (ready) { create(); } else { ready = true; }
            }     
            
            /**
             * If data retrieval is fails, an error message is displayed.
             * NOTE: This class overrides AsyncCallback's onFailure method
             */
            @Override
            public void onFailure(final Throwable caught){
                Window.alert("Error: Problem recieving JSON for weather conditions from wunderground");
            }
        });       
    }
    
 /* Getter */
  
    public FlowPanel getWeatherPanel() { return this.weatherPanel; }
    
 /* Private Methods */
    
    /**
     * Parses a JSONObject for relevant values then 
     * adds the values to the weatherTable.
     * @param conditions - The current weather conditions
     */
    private void updateWeather(final String conditions) {
        
        JSONObject currentObject = (JSONObject) JSONParser.parseLenient(conditions);
        currentObject = this.fetchObject(currentObject, "current_observation");
        
        this.temperature = currentObject.get("temp_c").toString();
        this.visibility = currentObject.get("visibility_km").toString().replace("\"", "");

    }
        
    /**
     * Parses a JSONObject for the sunset time.
     * @param astronomy - The current astronomical conditions
     */
    private void updateSunset(final String astronomy) {
        
        JSONObject currentObject = (JSONObject) JSONParser.parseLenient(astronomy);
        
        currentObject = this.fetchObject(currentObject, "sun_phase");
        currentObject = this.fetchObject(currentObject, "sunset");
        
        this.hoursUntilSunset = this.fetchInt(currentObject, "hour") - 12;
        this.minutesUntilSunset = this.fetchInt(currentObject, "minute");       

    }  
    
    /**
     * Creates the weather panel once all data is loaded.
     */
    private void create() {      
        this.weatherPanel.add( new Label("Weather Conditions:") );
        this.weatherPanel.add( new Label("Temperature: " + this.temperature + " \u00b0C") );
        this.weatherPanel.add( new Label("Visibility: " + this.visibility + " Km") );
        this.weatherPanel.add( new Label("Sunset time: " + this.hoursUntilSunset + ":" + this.minutesUntilSunset + " PM ") );
        this.weatherPanel.add(this.wunderLogo); 
    }
    
    /**
     * Parses a JSONObject for the key and returns the integer value stored at that key.
     * @param obj - The JSON Object that is being parsed
     * @param key - The key that is being searched for
     * @return The integer that was associated with the key
     */
    private int fetchInt( final JSONObject currentObject, final String key) {
        return Integer.parseInt( currentObject.get("hour").toString().replace("\"", "") );
    }
    
    /**
     * Parses a JSONObject for the key and returns the object stored at that key.
     * @param obj - The JSON Object that is being parsed
     * @param key - The key that is being searched for
     * @return The new JSON Object that was associated with the key
     */
    private JSONObject fetchObject(final JSONObject obj, final String key) {
        return (JSONObject) JSONParser.parseLenient( obj.get(key).toString() );
    }
}
