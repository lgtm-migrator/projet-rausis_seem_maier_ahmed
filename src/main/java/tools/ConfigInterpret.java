package tools;

import org.json.*;
import java.util.HashMap;
import java.util.Iterator;

public class ConfigInterpret{
    private HashMap<String, String> itemMap = new HashMap<>();
    private static final ConfigInterpret permanentConfig = new ConfigInterpret();

    public void config(String Json) {
        JSONObject jsonObject = new JSONObject(Json);
        permanentConfig.config(jsonObject);
    }

    private void config(JSONObject jsonObject){
        JSONArray keys = jsonObject.names ();
        for (int i = 0; i < keys.length (); i++) {
            String key = keys.getString (i);
            var value = jsonObject.get(key);

            addHashMap(key, value.toString());
        }
    }

    private void addHashMap(String key, String value){
        itemMap.put(key, value);
    }

    public String getConfig(String key){

        String value = permanentConfig.itemMap.get(key);

        if(value != null){
            return value;
        }else{
            return key;
        }

    }
}
