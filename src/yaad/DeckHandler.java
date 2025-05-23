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
    
    List<Deck> decks;
    
    public DeckHandler(int w) {
        getSettings();
        width = w;
        decks = new ArrayList();
    }
    
    public List<Deck> decksToAdd() {
        List<Deck> newDecks = new ArrayList();
        List<File> fileList = listFiles();
        
        for (Deck d: decks) {
            if (!fileList.contains(d.getFile())) {
                Deck deck = new Deck(f);
                decks.add(deck);
                System.out.println("Added " + deck.getTitle());
            }
        }
        
        return newDecks;
    }
    
    public List<Deck> decksToRemove() {
        
    }
    
    public void update() {
        
        ArrayList<Deck> removedDecks = new ArrayList();
        
        deckLoop:
        for (Deck d: decks) {
            for (File f: listFiles()) {
                if (d.getFile().getAbsolutePath().equals(f.getAbsolutePath())) continue deckLoop;
            }
            System.out.println("Removed " + d.getTitle());
            removedDecks.add(d);
        }
        
        for (Deck d: removedDecks) {
            decks.remove(d);
            System.out.println("Removed " + d.getFile().getName());
        }
    }
    
    public ArrayList<File> listFiles() {
        File dir = new File(decksPath);
        dir.mkdirs();
        return new ArrayList(Arrays.asList(dir.listFiles()));
    }
    
    public SortedSet<File> setFiles() {
        File dir = new File(decksPath);
        dir.mkdirs();
        return new TreeSet(Arrays.asList(dir.listFiles()));
    }
    
    /*
    public void addDeck(File f) {
        int i = findInsertionPoint(f);
        addDeck(i, f);
        System.out.println("Method: addDeck(f), File name: " + f.getName() + ", Files size: " + files.size() + ", Decks size: " + decks.size());
    }
    
    public void addDeck(int i, File f) {
        files.add(i, f);
        String deckTitle = removeExt(f.getName());
        JLabel deck = createDeck(deckTitle);
        
        try {
            deckDisplay.add(deck, i);
            decks.add(i, deck);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        fix(deckDisplay);
        System.out.println("Method: addDeck(i, f), Index: " + i + ", Files size: " + files.size() + ", Decks size: " + decks.size());
    }
    
    public void updateDecks(Collection<File> fileList) {
        for (File f: fileList) {
            addDeck(f);
        }
        fix(deckDisplay);
    }
    
    public void removeDeck(int i) {
        deckDisplay.remove(i);
        fix(deckDisplay);
        decks.remove(i);
        files.remove(i);
        System.out.println("Method: removeDeck, Index: " + i + ", Files size: " + files.size() + ", Decks size: " + decks.size());
    }
    
    public void removeDeck(File f) {
        int i = findIndex(f);
        removeDeck(i);
        System.out.println("Method: removeDeck, Index: " + i + ", Files size: " + files.size() + ", Decks size: " + decks.size());
    }
    
    public void removeAllDecks() {
        for (File f: files) removeDeck(f);
        fix(deckDisplay);
    }
    
    public String removeExt(String fileName) {
        System.out.println("Removed ext for fileName");
        return (fileName.replace(".json", ""));
    }
    
    public String getFileName(int i) {
        System.out.println("Method: getFileName, Index: " + i + ", Files size: " + files.size() + ", Decks size: " + decks.size());
        return files.get(i).getName();
    }
    
    public int findInsertionPoint(File f) {
        //Assume files is updated because it is called from methods where files is fixed before findIndex is called\
        int size = files.size();
        
        String newFileName = f.getName();
        
        for (int i = 0; i < size; i++) {
            String oldFileName = files.get(i).getName();
            if (newFileName.compareToIgnoreCase(oldFileName) < 0) return i;
        }
        return size;
    }
    
    public int findIndex(File f) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).equals(f)) return i;
        }
        return -1;
    }
    */
    
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
