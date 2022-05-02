package tools;

import java.io.File;
import java.util.HashMap;

public class PageCompiler {
    private HashMap<String, String> pageParameter = new HashMap<>();
    public static final String LAYOUT_NAME = "layout.html";
    private final String SPLIT_PARAMETER = ": ";
    private final String HEADER_CONTENT_SEPARATOR = "---";
    private final String SITE_PREFIXE = "site";
    private final String PAGE_PREFIXE = "page";
    private final String CONTENT = "{{ content }}";
    private final String INCLUDE_START_REGEX = "\\{\\% include";
    private final String INCLUDE_START = "{% include";
    private final String INCLUDE_END = "}";
    private final String PARAMETER_START = "{{";
    private final String PARAMETER_START_REGEX = "\\{\\{";
    private final String PARAMETER_END = "}}";
    private String homeDir;

    public PageCompiler(String homeDir){
        this.homeDir = homeDir;
    }

    /**
     * Permet d'extraire les paramètres d'une page
     * (le contenu passé en paramètre doit être uniquement
     * le header de la page qui contient les paramètres)
     * @param pageHeader le header de la page qui contient les paramètres
     */
    private void extractPageParameter(String pageHeader){
        String[] rowsHeader = pageHeader.split("\n");
        for (String row : rowsHeader) {
            String[] keyValue = row.split(SPLIT_PARAMETER);
            pageParameter.put(keyValue[0], keyValue[1]);
        }
    }

    /**
     * Retourne la valeur d'un paramètre en fonction de sa clé.
     * En prenant en compte les préfixes (site. ou page.)
     * @param parameterKey la valeur de la clé du paramètre
     * @return
     */
    private String getParameter(String parameterKey){
        String[] splitPrefixeKey = parameterKey.split("\\.");
        if(splitPrefixeKey.length <= 1) return splitPrefixeKey[0];
        String prefix = splitPrefixeKey[0];
        String key = splitPrefixeKey[1];
        if(prefix.equals(PAGE_PREFIXE)){
            return pageParameter.getOrDefault(key, parameterKey);
        }
        if(prefix.equals(SITE_PREFIXE)) {
            //Retourne les paramtères globaux au site
            ConfigInterpret ci = ConfigInterpret.getInstance();
            return ci.getConfig(key);
        }
        return parameterKey;
    }

    private String addIndentation(String content, String indentation){
        StringBuilder str = new StringBuilder();
        String[] rows = content.split("\n");
        for(String row : rows){
            str.append(indentation).append(row).append("\n");
        }
        return str.toString();
    }


    /**
     * Compile une page en utilisant un layout.
     * S'il n'y a pas de fichier LAYOUT_NAME à la racine, retourne uniquement le
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
        String layoutPath = this.homeDir + File.separator + LAYOUT_NAME;
        if(!FileManager.fileExists(layoutPath)){
            return MarkdownToHtml.convertToHtml(content);
        }
        try {
            String layout = FileManager.getContent(layoutPath);
            String[] rowsLayout = layout.split("\n");
            StringBuilder finalPage = new StringBuilder();
            //Check ligne par ligne du layout s'il y a des remplacements à faire
            for(String row : rowsLayout){
                if(row.contains(CONTENT)){
                    //Remplacer le content
                    String indentation = row.replace(CONTENT, "");
                    finalPage.append(addIndentation(MarkdownToHtml.convertToHtml(content), indentation));
                } else if (row.contains(INCLUDE_START) && row.contains(INCLUDE_END)){
                    //Remplacer les includes
                    String[] temp = row.split(INCLUDE_START_REGEX);
                    int idInclude = (temp.length > 1) ? 1 : 0;
                    String include = temp[idInclude].split(INCLUDE_END)[0];
                    String fileRelativePath = include.replace(" ", "");
                    String fileAbsolutePath = this.homeDir + File.separator + fileRelativePath;
                    if(FileManager.fileExists(fileAbsolutePath)) {
                        String includeContent = FileManager.getContent(fileAbsolutePath);
                        String indentation = row.replace(INCLUDE_START + include + INCLUDE_END, "");
                        finalPage.append(addIndentation(includeContent, indentation));
                    } else {
                        finalPage.append(row);
                    }
                } else if (row.contains(PARAMETER_END) && row.contains(PARAMETER_START)){
                    //Remplacer les paramètres
                    String[] temp = row.split(PARAMETER_START_REGEX);
                    String finalRow = row;
                    for(String part : temp){
                        if(part.contains(PARAMETER_END)){
                            String key = part.split(PARAMETER_END)[0];
                            String value = getParameter(key.replace(" ", ""));
                            String strReplace = PARAMETER_START + key + PARAMETER_END;
                            finalRow = finalRow.replace(strReplace, value);
                        }
                    }
                    finalPage.append(finalRow).append("\n");
                } else {
                    finalPage.append(row).append("\n");
                }
            }
            //Enlève le \n en trop
            return finalPage.substring(0, finalPage.length() - 1);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return MarkdownToHtml.convertToHtml(content);
        }
    }
}
