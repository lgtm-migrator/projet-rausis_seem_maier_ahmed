package tools;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PageCompiler {
    private HashMap<String, String> pageParameter = new HashMap<>();
    private final String HEADER_CONTENT_SEPARATOR = "---";
    private final String SITE_PREFIXE = "site";
    private final String PAGE_PREFIXE = "page";
    private final String CONTENT = "{{ content }}";
    private final String INCLUDE = "{* include";
    private final String PARAMETER_START = "{{";
    private final String PARAMETER_END = "}}";

    /**
     * Permet d'extraire les paramètres d'une page
     * (le contenu passé en paramètre doit être uniquement
     * le header de la page qui contient les paramètres)
     * @param pageHeader le header de la page qui contient les paramètres
     */
    private void extractPageParameter(String pageHeader){
        String[] rowsHeader = pageHeader.split("\n");
        for (String row : rowsHeader) {
            String[] keyValue = row.split(":");
            pageParameter.put(keyValue[0], keyValue[1].replace(" ", ""));
        }
    }

    /**
     * Retourne la valeur d'un paramètre en fonction de sa clé.
     * En prenant en compte les préfixes (site. ou page.)
     * @param parameterKey la valeur de la clé du paramètre
     * @return
     */
    private String getParameter(String parameterKey){
        String[] splitPrefixeKey = parameterKey.split(".");
        if(splitPrefixeKey.length <= 1) return splitPrefixeKey[0];
        if(splitPrefixeKey[0].equals(PAGE_PREFIXE)){
            return pageParameter.get(parameterKey);
        }
        if(splitPrefixeKey[1].equals(SITE_PREFIXE)) {
            //Retourne les paramtères globaux au site
            return "";
        }
        return parameterKey;
    }


    /**
     * Compile une page en utilisant un layout.
     * S'il n'y a pas de fichier layout.html à la racine, retourne uniquement le
     * contenu html correspondant au code markdown
     * @param pageContent
     * @return
     */
    public String compilePage(String pageContent){

        //Split le header du content
        String[] splitHeaderContent = pageContent.split(HEADER_CONTENT_SEPARATOR);
        String header = splitHeaderContent[0];
        String content = splitHeaderContent[1];

        extractPageParameter(header);

        //Récupérer le contenu du layout
        String layoutPath = System.getProperty("user.dir") + "\\layout.html";
        if(!FileManager.fileExists(layoutPath)){
            return MarkdownToHtml.convertToHtml(content);
        }
        try {
            String layout = FileManager.getContent(layoutPath);
            String[] rowsLayout = layout.split("\n");
            StringBuilder finalPage = new StringBuilder();
            for(String row : rowsLayout){
                if(row.contains(CONTENT)){
                    //Remplacer le content

                } else if (row.contains(INCLUDE)){
                    //Remplacer les includes

                } else if (row.contains(PARAMETER_END) && row.contains(PARAMETER_START)){
                    //Remplacer les paramètres

                } else {
                    finalPage.append(row);
                }
            }
            return finalPage.toString();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return MarkdownToHtml.convertToHtml(content);
    }
}
