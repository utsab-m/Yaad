package yaad;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class DeckHandler {
    
    SettingsHandler sh = new SettingsHandler();
    Settings settings;
    
    Color backgroundColor, buttonColor, fontColor;
    String fontName;
    
    int width;
    
    String currentPath = System.getProperty("user.dir") + File.separator + "src";
    String decksPath = currentPath + File.separator + "decks";
    
    List<Deck> decks = new ArrayList();
    
    static ObjectMapper mapper = new ObjectMapper();
    
    public DeckHandler() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        getSettings();
        updateDecks();
        
        for (File file: FileHandler.listDecksFiles()) {
            Deck deck = new Deck(file);
            addDeck(deck);
        }
    }
    
    public void updateDecks() {
        if (different()) {
            for (Deck deck: decksToRemove()) removeDeck(deck);
            for (Deck deck: decksToAdd()) addDeck(deck);
        }
    }
    
    public boolean different() {
        return toFileArray().equals(FileHandler.listDecksFiles());
    }
    
    public List<Deck> decksToAdd() {
        List<Deck> decksToAdd = new ArrayList();
        List<File> oldFileList = new ArrayList(), newFileList = FileHandler.listDecksFiles();
        
        for (Deck deck: decks) oldFileList.add(deck.getFile());
        
        settings = sh.getSettings();
        
        for (File newFile: newFileList) if (!oldFileList.contains(newFile)) {
            Deck deck = new Deck(newFile);
            decksToAdd.add(deck);
        }
        
        return decksToAdd;
    }
    
    public List<Deck> decksToRemove() {
        List<Deck> decksToRemove = new ArrayList();
        List<File> fileList = FileHandler.listDecksFiles();
        
        for (Deck deck: decks) {
            if (!fileList.contains(deck.getFile())) decksToRemove.add(deck);
        }
        
        return decksToRemove;
    }
    
    public void addDeck(Deck deck) {
        int index = findIndex(deck);
        decks.add(index, deck);
    }
    
    public void removeDeck(Deck deck) {
        decks.remove(deck);
    }
    
    public boolean deleteDeckFile(Deck deck) {
        return FileHandler.deleteDeckFile(deck.getTitle());
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
        for (Deck deck: decks) {
            files.add(deck.getFile());
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
    
    public static List<Flashcard> getFlashcards(Deck deck) {
        String json = FileHandler.readDeckFile(deck.getTitle());
        List<Flashcard> flashcards;
        try {
            flashcards = mapper.readValue(json, new TypeReference<List<Flashcard>>() {});
            return flashcards;
        } catch (IOException e) {
            System.out.println(e);
            return new ArrayList<Flashcard>();
        }
    }
    
    public static boolean setFlashcards(Deck deck, List<Flashcard> flashcards) {
        try {
            mapper.writeValue(FileHandler.getDeckFile(deck.getTitle()), flashcards);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
