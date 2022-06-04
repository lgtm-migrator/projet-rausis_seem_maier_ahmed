package tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileManager {
    private final static String CONFIG_FILENAME = "config.json";
    private final static String LAYOUT_FILENAME = "layout.html";

    /**
     * Permet de créer un fichier avec un certain contenu
     * @param path Le chemin absolue pour la création du fichier
     * @param content Le contenu du fichier
     * @return true si la création est un succès
     */
    public static boolean createFile(String path, String content) {
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
     * Permet de créer un dossier
     * @param path le chemin absolue du dossier à créer
     * @return true si la création est un succès
     */
    public static boolean createDirectory(String path) {
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
     * Permet de checker si un fichier existe
     * @param path Le chemin absolu du fichier à checker
     * @return true si le fichier existe
     */
    public static boolean fileExists(String path){
        File file = new File(path);
        return file.exists();
    }

    /**
     * Permet de récupérer le contenu d'un fichier
     * @param path le chemin absolu du fichier à récupérer le contenu
     * @return String le contenu du fichier ou null si le fichier n'existe pas ou qu'il y ait eu une erreur
     */
    public static String getContent(String path){
        if(!fileExists(path)) return null;
        try {
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
        } catch (Exception e){
            System.out.println("Une erreur s'est produite lors de FileManager.getContent: " + e.getMessage());
        }
        return null;
    }

    /**
     * Permet de copier un fichier
     * @param from Le chemin absolu du fichier à copier
     * @param to Le chemin absolu de l'endroit ou le copier
     * @return true si la copie est un succès
     */
    private static boolean copyFile(String from, String to) {
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
     * Permet de récupérer le chemin relatif d'un chemin absolu
     * @param absolutePath Le chemin absolu
     * @param initPath La racine
     * @return String le chemin absolu
     */
    private static String getRelativePath(String absolutePath, String initPath){
        return absolutePath.substring(initPath.length(), absolutePath.length());
    }

    /**
     * Permet de build de manière récursive (en allant dans chaque sous-dossier)
     * @param directory File le répertoire à traiter
     * @param initPath Le chemin initiale (le dossier racine du site qui contiendra le dossier build)
     * @param initPathBuild Le chemin initiale du dossier build (souvent initPath/build)
     * @return true si le build est un succès
     */
    private static boolean buildRecursive(File directory, String initPath, String initPathBuild){
        for (final File fileEntry : directory.listFiles()) {
            if (fileEntry.isDirectory()) {
                if(!fileEntry.getName().equals("build")) {
                    createDirectory(initPathBuild + getRelativePath(fileEntry.getPath(), initPath));
                    if(!buildRecursive(fileEntry, initPath, initPathBuild)){
                        return false;
                    }
                }
            } else {
                if(fileEntry.getName().contains("config.json")){
                    //Ignorer le fichier config.json
                } else if(fileEntry.getName().contains("md")){
                    String temppath = initPathBuild + getRelativePath(fileEntry.getPath(), initPath);
                    String content = "";
                    content = getContent(fileEntry.getPath());
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
     * Permet de build un site
     * @param path le chemin au dossier à build
     * @return true si le build est un succès
     */
    public static boolean build(String path){
        // Vérifie que les fichiers index et config existes
        if(!fileExists(path + File.separator + "index.md")){
            System.out.println("Le fichier index.md est manquant");
            return false;
        }

        if(!fileExists(path + File.separator + "config.json")){
            System.out.println("Le fichier config.json est manquant");
            return false;
        }

        String buildPath = path + File.separator + "build";
        //Supprime le dossier build s'il existe
        if(fileExists(buildPath)) deleteRecursive(new File(buildPath));

        createDirectory(buildPath);

        return buildRecursive(new File(path), path, path + File.separator + "build");
    }

    /**
     * Permet d'écouter les changements dans le dossier path
     * et de re-build lorsqu'il y a un changement de fichier
     * @param path le dossier à écouter
     */
    public static void watch(String path){
        try {
            WatcherRecursive wr = new WatcherRecursive(path);
            wr.watch(new WatcherRecursive.SignalChange() {
                @Override
                public void change(WatchKey key) throws InterruptedException {
                    for(WatchEvent<?> event : key.pollEvents()) {
                        String fileName = event.context().toString();
                        if(!fileName.equals("build")) {
                            /*
                            Le rebuild pourrait être optimisé en remplaçant dans le dossier
                            build uniquement les fichiers impactés par la modification du
                            fichier x ou y.
                            */
                            System.out.println("Reconstruction ...");
                            if(build(path)){
                                System.out.println("Fin de la reconstruction");
                            } else {
                                System.out.println("Reconstruction interrompue");
                            }
                        }
                    }
                }
            });
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permet de générer les premiers fichiers à un endroit particuler
     * @param path le chemin ou générer les fichiers
     */
    public static boolean init(String path) {
        //Vérifie que le répertoire fournit existe vraiment
        if(!fileExists(path)) return false;

        //Créer le fichier index.md
        boolean createIndex = createFile(path + File.separator + "index.md",
                "titre: Mon premier article\n" +
                "auteur: Bertil Chapuis\n" +
                "date: 2021-03-10\n" +
                "---\n" +
                "# Mon titre\n" +
                "## Mon sous-titre\n" +
                "Le contenu de mon article.\n" +
                "![Une image](./image.png)"
        );
        if(!createIndex) return false;

        //Créer le fichier config.json
        boolean createConfig = createFile(path + File.separator + "config.json",
                "{" +
                        "   domaine: www.mon-site.com\n" +
                        "   titre: \"Mon site\"" +
                        "}"
        );
        if(!createConfig) return false;
        return true;
    }

    /**
     * Supprime de manière récursive un répertoire
     * @param directoryToBeDeleted le répertoire à supprimer
     */
    public static void deleteRecursive(File directoryToBeDeleted){
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteRecursive(file);
            }
        }
        directoryToBeDeleted.delete();
    }
}
