package tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {
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

    public static boolean fileExists(String path){
        File file = new File(path);
        return file.exists();
    }

    public static String getContent(String path) throws IOException {
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

    private String getRelativePath(String absolutePath, String initPath){
        return absolutePath.substring(initPath.length(), absolutePath.length());
    }

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
