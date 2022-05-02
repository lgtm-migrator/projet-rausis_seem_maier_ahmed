package tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {
    /**
     * Créer un fichier à un certain endroit et avec un certain contenu
     * @param path l'endroit ou créer le fichier
     * @param content le contenu du fichier
     * @return Vrai si la création du fichier est un succès
     */
    private boolean createFile(String path, String content) {
        try {
            File f = new File(path);

            if (f.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Créer le répertoire path
     * @param path le répertoire à créer (chemin absolue)
     * @return
     */
    private boolean createDirectory(String path) {
        try {

            Path p = Paths.get(path);

            Files.createDirectories(p);

        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Vérifie si un fichier existe
     * @param path le chemin du fichier à vérifier
     * @return vrai s'il existe
     */
    private boolean fileExists(String path){
        File file = new File(path);
        return file.exists();
    }

    /**
     * Permet de récupére le contenu d'un fichier
     * @param path le chemin du fichier à récupérer le contenu
     * @return le contenu du fichier
     * @throws IOException
     */
    private String getContent(String path) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(path));
        StringBuilder content = new StringBuilder();


        byte[] buffer = new byte[1024];
        int lengthRead;
        while ((lengthRead = in.read(buffer)) > 0) {
            String append = new String(buffer, 0, lengthRead, StandardCharsets.UTF_8);
            content.append(append);
        }
        in.close();
        return content.toString();
    }

    /**
     * Permet de copier un fichier (from) à un autre endroit (to)
     * @param from le fichier source
     * @param to le nouveau fichier
     * @return
     */
    private boolean copyFile(String from, String to) {
        try {
            File f = new File(to);
            if (f.createNewFile()) {
                InputStream in = new BufferedInputStream(new FileInputStream(from));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(f));

                byte[] buffer = new byte[1024];
                int lengthRead;
                while ((lengthRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, lengthRead);
                    out.flush();
                }
                out.close();
                in.close();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Permet de récupérer un lien relatif depuis un lien absolue et la référence
     * @param absolutePath le lien absolue
     * @param initPath la référence
     * @return
     */
    private String getRelativePath(String absolutePath, String initPath){
        return absolutePath.substring(initPath.length(), absolutePath.length());
    }

    /**
     * Gère le processus du build (de manière récursive s'il y a des sous dossiers)
     * @param directory le répertoire à traiter (peut être un sous-répertoire)
     * @param initPath le path initiale (ou le build à été lancé)
     * @param initPathBuild le path du build (en générale initPath + "/build")
     * @return Vrai si le build est réussi
     */
    private boolean buildRecursive(File directory, String initPath, String initPathBuild){
        for (final File fileEntry : directory.listFiles()) {
            if (fileEntry.isDirectory()) {
                if(!fileEntry.getName().equals("build")) {
                    createDirectory(initPathBuild + getRelativePath(fileEntry.getPath(), initPath));
                    if(!buildRecursive(fileEntry, initPath, initPathBuild)){
                        return false;
                    }
                }
            } else {
                if(fileEntry.getName().contains("yaml")){
                    //Ignorer les fichiers yaml
                } else if(fileEntry.getName().contains("md")){
                    String temppath = initPathBuild + getRelativePath(fileEntry.getPath(), initPath);
                    String content = "";
                    try {
                        content = getContent(fileEntry.getPath());
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                        return false;
                    }
                    MarkdownToHtml m = new MarkdownToHtml();
                    createFile(temppath.replace("md", "html"), m.convertToHtml(content));
                } else {
                    copyFile(fileEntry.getPath(), initPathBuild + getRelativePath(fileEntry.getPath(), initPath));
                }
            }
        }
        return true;
    }

    /**
     * Permet de faire les premières actions du build
     * (vérification des fichiers obligatoires, TODO: load du fichier de config
     * @param path le dossier à "build"
     * @return vrai si le build est un succès
     */
    public boolean build(String path){
        if(!fileExists(path + File.separator + "index.md")){
            System.out.println("Le fichier index.md est manquant");
            return false;
        }

        if(!fileExists(path + File.separator + "config.yaml")){
            System.out.println("Le fichier config.yaml est manquant");
            return false;
        }

        createDirectory(path + File.separator + "build");

        return buildRecursive(new File(path), path, path + File.separator + "build");
    }
}
