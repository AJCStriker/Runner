/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.countercraft.runner.managers;

//Local Imports
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexanderchristie
 */
public class FileManager {
    
    private FileManager() {
    }
    
    public static FileManager getInstance() {
        return FileManagerHolder.INSTANCE;
    }
    
    private static class FileManagerHolder {

        private static final FileManager INSTANCE = new FileManager();
    }
    
    public boolean makeIfNotExistFolder(File file){
        boolean existed;
        
        if(!file.exists()){
            existed = false;
            file.mkdirs();
        }else{
            existed = true;
        }
        
        
        return existed;
    }
    
    public boolean makeIfNotExistFile(File file){
        boolean existed;
        
        if(!file.exists()){
            existed = false;
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            existed = true;
        }
        
        
        return existed;
    }
}
