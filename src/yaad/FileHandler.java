package yaad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {
    String currentPath = System.getProperty("user.dir") + File.separator + "src";
    String decksPath = currentPath + File.separator + "decks";
    String settingsPath = currentPath + File.separator + "settings.json";
    
    File decksFolder = new File(decksPath);
    File settingsFile = new File(settingsPath);
    
    public FileHandler() {
        checkDecksFolder();
        checkSettingsFile();
    }
    
    public boolean decksFolderExists() {
        return decksFolder.exists();
    }
    
    public boolean settingsFileExists() {
        return settingsFile.exists();
    }
    
    public void createDecksFolder() {
        decksFolder.mkdirs();
    }
    
    public void createSettingsFile() {
        settingsFile.getParentFile().mkdirs();
        try {
            settingsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void checkDecksFolder() {
        if (!decksFolderExists()) createDecksFolder();
    }
    
    public void checkSettingsFile() {
        if (!settingsFileExists()) createSettingsFile();
    }
    
    public boolean updateFile(String s, File f) {
        checkSettingsFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
            writer.write(s);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateDeckFile(String s, String deckTitle) {
        File deckFile = new File(decksPath + File.separator + deckTitle + ".json");
        return updateFile(s, deckFile);
    }
    
    public boolean updateSettingsFile(String s) {
        checkSettingsFile();
        return updateFile(s, settingsFile);
    }
    
    public String readFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public String readDeckFile(String deckTitle) {
        return readFile(getDeckFile(deckTitle));
    }
    
    public String readSettingsFile() {
        
    }
    
    public boolean createDeckFile(String deckTitle) {
        
    }
    
    public File getDeckFile(String deckTitle) {
        return new File(decksPath + File.separator + deckTitle + ".json");
    }
    
    public List<File> listDecksFiles() {
        checkDecksFolder();
        return new ArrayList(Arrays.asList(decksFolder.listFiles()));
    }
    
}
