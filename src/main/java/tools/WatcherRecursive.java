package tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class WatcherRecursive {

    private String directoryPath;
    private final WatchService watcher = FileSystems.getDefault().newWatchService();

    /**
     * L'interface permet à la boucle while qui attend une modification sur un fichier
     * de signaler cette modification en utilisatn la méthode change
     */
    public interface SignalChange{
        void change(WatchKey key) throws InterruptedException;
    }


    /**
     * Constructeur de la place avec le chemin du dossier à watch
     * @param path le chemin absolue du dossier
     */
    public WatcherRecursive(String path) throws IOException {
        directoryPath = path;
        initWatcher();
    }

    /**
     * Attend jusqu'au prochain event catch
     * @return l'event catch
     */
    public WatchKey watch(SignalChange signal) throws InterruptedException {
        WatchKey key;
        System.out.println("Ecoute un changement de fichier dans " + directoryPath);
        while((key = watcher.take()) != null){
            //TODO: Signal 2 fois chaque modification mais je ne comprends pas pourquoi
            signal.change(key);
            key.reset();
        }
        return key;
    }

    /**
     * Permet d'initialiser tous les watchers sur les dossiers
     * @throws IOException
     */
    private void initWatcher() throws IOException {
        Path dir = Paths.get(directoryPath);
        dir.register(watcher,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        initRecursiveWatcher(new File(directoryPath));
    }

    /**
     * Permet d'initialiser de manière récursive tous les watchers sur les dossiers
     * @param directory Le dossier en cours
     * @throws IOException
     */
    private void initRecursiveWatcher(File directory) throws IOException {
        for(File file : directory.listFiles()){
            if(file.isDirectory() && !file.getName().equals("build")){
                Path dir = Paths.get(file.getPath());
                dir.register(watcher,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);
                initRecursiveWatcher(file);
            }
        }
    }
}
