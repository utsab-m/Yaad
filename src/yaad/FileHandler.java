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

public final class FileHandler {
    private static final String currentPath = System.getProperty("user.dir") + File.separator + "src";
    private static final String decksPath = currentPath + File.separator + "decks";
    private static final String settingsPath = currentPath + File.separator + "settings.json";
    
    private static final File decksFolder = new File(decksPath);
    private static final File settingsFile = new File(settingsPath);
    
    private FileHandler() {}
    
    public static boolean decksFolderExists() {
        return decksFolder.exists();
    }
    
    public static boolean fileExists(File file) {
        return file.exists();
    }
    
    public static boolean settingsFileExists() {
        return fileExists(settingsFile);
    }
    
    public static boolean deckFileExists(String deckTitle) {
        File deckFile = getDeckFile(deckTitle);
        return fileExists(deckFile);
    }
    
    public static void createDecksFolder() {
        decksFolder.mkdirs();
    }
    
    public static void createFile(File file) {
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void createDeckFile(String deckTitle) {
        File deckFile = getDeckFile(deckTitle);
        createFile(deckFile);
    }
    
    public static void createSettingsFile() {
        createFile(settingsFile);
    }
    
    public static void checkDecksFolder() {
        if (!decksFolderExists()) createDecksFolder();
    }
    
    public static void checkFile(File file) {
        if (!file.exists()) createFile(file);
    }
    
    public static void checkDecksFile(String deckTitle) {
        File deckFile = getDeckFile(deckTitle);
        checkFile(deckFile);
    }
    
    public static void checkSettingsFile() {
        checkFile(settingsFile);
    }
    
    public static boolean updateFile(String s, File f) {
        checkSettingsFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
            writer.write(s);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean updateDeckFile(String s, String deckTitle) {
        File deckFile = new File(decksPath + File.separator + deckTitle + ".json");
        return updateFile(s, deckFile);
    }
    
    public static boolean updateSettingsFile(String s) {
        checkSettingsFile();
        return updateFile(s, settingsFile);
    }
    
    public static String readFile(File file) {
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
    
    public static String readDeckFile(String deckTitle) {
        File deckFile = getDeckFile(deckTitle);
        return readFile(deckFile);
    }
    
    public static String readSettingsFile() {
        return readFile(settingsFile);
    }
    
    public static File getDeckFile(String deckTitle) {
        return new File(decksPath + File.separator + deckTitle + ".json");
    }
    
    public static File getSettingsFile() {
        return settingsFile;
    }
    
    public static String removeExt(File file) {
        String fileName = file.getName();
        if (!fileName.contains(".")) return fileName;
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
    
    public static boolean deleteDeckFile(Deck deck) {
        File deckFile = deck.getFile();
        return deckFile.delete();
    }
    
    public static List<File> listDecksFiles() {
        checkDecksFolder();
        return new ArrayList(Arrays.asList(decksFolder.listFiles()));
    }
    
}
