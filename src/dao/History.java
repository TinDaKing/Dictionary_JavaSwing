package dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

public class History {
    private List<SearchRecord> searchRecords;
    private static final String fileName = "history.bin";


    private static History instance;

    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    private History() {
        try {
            searchRecords = new ArrayList<>();

            if (!readBinaryFile()) {
                System.out.println("History list empty!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean readBinaryFile() {
        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream is = new ObjectInputStream(file);

            while (file.available() > 0) {
                searchRecords.add((SearchRecord) is.readObject());
            }
            file.close();
            is.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Done reading");
        return true;

    }

    private int countWordInDate(String word, Date dateStart, Date dateEnd) {
        int count = 0;
        for (SearchRecord sr : searchRecords) {
            if (sr.getWord() == word) {
                if (sr.getDateSearch().compareTo(dateStart) >= 0 && sr.getDateSearch().compareTo(dateEnd) <= 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private List<String> getWordInDate(Date start, Date end) {
        List<String> words = new ArrayList<>();
        for (SearchRecord sr : searchRecords) {
            if (sr.getDateSearch().compareTo(start) >= 0 && sr.getDateSearch().compareTo(end) <= 0) {
                words.add(sr.getWord());
            }
        }
        return words;
    }

    public Map<String, Integer> getListWordsAndCount(Date start, Date end) {
        Map<String, Integer> wordCount = new HashMap<>();
        List<String> words = getWordInDate(start, end);
        for (String w : words) {
            wordCount.put(w, countWordInDate(w, start, end));
        }
        return wordCount;
    }

    public boolean overwriteFile() {
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream os = new ObjectOutputStream(file);

            for (SearchRecord s : searchRecords) {
                os.writeObject(s);
            }
            file.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Done writing");
        return true;
    }

    public boolean addNewRecord(String word, Date date) {
        try {
            searchRecords.add(new SearchRecord(word, date));
            overwriteFile();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
