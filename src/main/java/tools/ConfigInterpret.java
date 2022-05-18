package tools;

import org.json.*;

import java.io.ObjectInputFilter;
import java.util.HashMap;
import java.util.Iterator;

public class ConfigInterpret{
    private HashMap<String, String> itemMap = new HashMap<>();
    private static ConfigInterpret permanentConfig = null;

    /**
     * Constructeur privé, configInterpret ne doit pas
     * pouvoir être construit par quelqu'un
     */
    private ConfigInterpret(){}

    /**
     * getInstance: Permet de récupérer une référence sur l'instance contenant la configuration du fichier.
     * Si la configuration n'existe pas encore, crée cette dernière(vide).
     *
     * @return référence sur p
     */
    public static ConfigInterpret getInstance()
    {
        initInstance();

        return permanentConfig;
    }

    /**
     * Construit un objet vide de ConfigInterpret s'il n'existe pas encore
     * afin de contenir la configuration du site.
     */
    private static void initInstance(){
        if (permanentConfig == null) {
            permanentConfig = new ConfigInterpret();
        }
    }

    /**
     * Sauvegarde les configuration du site dans l'objet interne.
     * @param Json Contenu du fichier JSON.
     */
    public static void config(String Json) {
        JSONObject jsonObject = new JSONObject(Json);
        initInstance();
        permanentConfig.config(jsonObject);
    }

    /**
     * Extrait et sauvegarde les informations d'un fichier JSON dans une hashmap.
     * @param jsonObject Fichier JSON dont on veut extraire les données
     */
    private void config(JSONObject jsonObject){
        JSONArray keys = jsonObject.names ();
        for (int i = 0; i < keys.length (); i++) {
            String key = keys.getString (i);
            var value = jsonObject.get(key);

            addHashMap(key, value.toString());
        }
    }

    /**
     * Ajoute "value" dans une hashmap à la clé "key"
     * @param key Clé de value
     * @param value Value qu'on veut stocker.
     */
    private void addHashMap(String key, String value){
        itemMap.put(key, value);
    }

    /**
     * Récupère la valeur dans la hashmap en fonction de la clé passée.
     * @param key Clé dont on veut la valeur.
     * @return Si la clé possède une valeur autre que null dans la hashmap, retourne la valeur en question.
     *         Si la clé ne possède PAS de valeur autre que null, renvoi la clé.
     */
    public String getConfig(String key){

        String value = itemMap.get(key);

        if(value != null){
            return value;
        }else{
            return key;
        }

    }
}
