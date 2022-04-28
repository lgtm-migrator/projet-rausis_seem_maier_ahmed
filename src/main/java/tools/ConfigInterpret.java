package tools;

import org.json.*;

import java.io.ObjectInputFilter;
import java.util.HashMap;
import java.util.Iterator;

public class ConfigInterpret{
    private HashMap<String, String> itemMap = new HashMap<>();
    private static ConfigInterpret permanentConfig = null;

    private ConfigInterpret(){}

    public static ConfigInterpret getInstance()
    {
        initInstance();

        return permanentConfig;
    }

    private static void initInstance(){
        if (permanentConfig == null) {
            permanentConfig = new ConfigInterpret();
        }
    }

    public static void config(String Json) {
        JSONObject jsonObject = new JSONObject(Json);
        initInstance();
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

        String value = itemMap.get(key);

        if(value != null){
            return value;
        }else{
            return key;
        }

    }
}
