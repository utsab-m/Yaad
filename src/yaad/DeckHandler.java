package yaad;

import java.awt.Color;
import java.io.File;
import java.util.*;

public class DeckHandler {
    
    SettingsHandler sh = new SettingsHandler();
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    int width;
    
    String currentPath = System.getProperty("user.dir") + File.separator + "src";
    String decksPath = currentPath + File.separator + "decks";
    
    List<Deck> decks = new ArrayList();
    
    public DeckHandler(int w) {
        getSettings();
        width = w;
        updateDecks();
    }
    
    public void updateDecks() {
        if (changed()) {
            for (Deck deck: decksToRemove()) removeDeck(deck);
            for (Deck deck: decksToAdd()) addDeck(deck);
        }
    }
    
    public boolean changed() {
        return toFileArray().equals(listFiles());
    }
    
    public List<Deck> decksToAdd() {
        List<Deck> decksToAdd = new ArrayList();
        List<File> oldFileList = new ArrayList(), newFileList = listFiles();
        
        for (Deck deck: decks) oldFileList.add(deck.getFile());
        
        for (File newFile: newFileList) if (!oldFileList.contains(newFile)) {
            Deck deck = new Deck(newFile, fontName, fontColor, backgroundColor, buttonColor, width);
            decksToAdd.add(deck);
        }
        
        return decksToAdd;
    }
    
    public List<Deck> decksToRemove() {
        List<Deck> decksToRemove = new ArrayList();
        List<File> fileList = listFiles();
        
        for (Deck deck: decks) {
            if (!fileList.contains(deck.getFile())) decksToRemove.add(deck);
        }
        
        return decksToRemove;
    }
    
    public List<File> listFiles() {
        File dir = new File(decksPath);
        dir.mkdirs();
        return new ArrayList(Arrays.asList(dir.listFiles()));
    }
    
    public void addDeck(Deck deck) {
        int index = findIndex(deck);
        decks.add(index, deck);
    }
    
    public void removeDeck(Deck deck) {
        decks.remove(deck);
    }
    
    public int findIndex(Deck deck) {
        String title = deck.getTitle();
        List<String> stringArray = toStringArray();
        int n = stringArray.size();
        
        for (int i = 0; i < n; i++) {
            if (title.compareToIgnoreCase(stringArray.get(i)) < -1) return i;
        }
        
        return n;
    }
    
    public List<File> toFileArray() {
        List<File> files = new ArrayList();
        for (File file: listFiles()) {
            files.add(file);
        }
        return files;
    }
    
    public List<String> toStringArray() {
        List<String> stringArray = new ArrayList();
        for (Deck deck: decks) {
            stringArray.add(deck.getTitle());
        }
        return stringArray;
    }
    
    public void getSettings() {
        sh.setSettings();
        backgroundColor = sh.getBackgroundColor();
        buttonColor = sh.getButtonColor();
        fontColor = sh.getFontColor();
        fontName = sh.getFontName();
    }
    
    public Color getBackgroundColor() {return backgroundColor;}
    
    public void setBackgroundColor(Color c) {backgroundColor = c;}
    
    public Color getButtonColor() {return buttonColor;}
    
    public void setButtonColor(Color c) {buttonColor = c;}
    
    public Color getFontColor() {return fontColor;}
    
    public void setFontColor(Color c) {fontColor = c;}
    
    public String getFontName() {return fontName;}
    
    public void setFontName(String s) {fontName = s;}
    
    public List<Deck> getDecks() {return decks;}
}
