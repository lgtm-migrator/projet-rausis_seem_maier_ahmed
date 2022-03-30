package Subcommands;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "init", description = "init")
public class Init implements Callable<Integer> {
    @Override public Integer call() throws IOException {

        System.out.println("Init");
        String root =  "/mon site";
        Path path = Paths.get(root);

        if(Files.exists(path)){
          System.out.println("The diractory  'mon site' was already created ");
        } else {
            // we creat the  diractory mon site
            File f = new File(root);
            if(f.mkdir() == true){
                // if the diractory "mon site" was succsfuly created, we creat the file contaning site's informations
            File site_information  = new File("site_informations");
            if(site_information.createNewFile() == true){
                // we wirte in the file the site's information
                FileWriter fw  =  new FileWriter(site_information.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                // !!!!!! you have to modifie later !
                bw.write("site information");
                bw.close();
            } else {
                System.out.println("problems appeared while creating the file 'mon_site'");
            }

            } else {
                System.out.println("problems appeared while creating the diractory 'mon site'");
            }

        }


        return 0;
    }


}
