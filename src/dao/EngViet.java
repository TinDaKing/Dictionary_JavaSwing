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
import java.io.File;
import java.util.*;

public class EngViet {
    private SortedMap<String, String> dict;

    private static EngViet instance;

    public static EngViet getInstance() {
        if (instance == null) {
            instance = new EngViet();
        }
        return instance;
    }

    private EngViet() {
        try {
            Comparator<String> comp = new Comparator<String>() {
                @Override
                public int compare(String key1, String key2) {
                    int i = 0;

                    int minLen = Math.min(key1.length(), key2.length());

                    while (i < minLen && (Character.compare(key1.charAt(i), key2.charAt(i))) == 0) {
                        i++;
                    }
                    if (i == minLen) {
                        return key1.compareTo(key2);
                    }


                    return Character.compare(key1.charAt(i), key2.charAt(i));
                }
            };
            dict = new TreeMap<>(comp);

            readXMLFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean readXMLFile() {
        try {
            File inputFile = new File("dictionaryEV.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("record");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    dict.put(eElement.getElementsByTagName("word").item(0).getTextContent(), eElement.getElementsByTagName("meaning").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<String> translateWord(String word) {
        return reformatMeaningString(dict.get(word));
    }

    public boolean overwriteFile() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("dictionary");
            doc.appendChild(rootElement);

            for (Map.Entry<String, String> entry : dict.entrySet()) {

                Element record = doc.createElement("record");
                rootElement.appendChild(record);

                Element word = doc.createElement("word");
                word.appendChild(doc.createTextNode(entry.getKey()));
                record.appendChild(word);

                Element meaning = doc.createElement("meaning");
                meaning.appendChild(doc.createTextNode(entry.getValue()));
                record.appendChild(meaning);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("dictionaryEV.xml"));
            transformer.transform(source, result);

            System.out.println("Write ok");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private List<String> reformatMeaningString(String meaning) {
        if (meaning == null)
            return null;

        List<String> labels = new ArrayList<>();
        int prePlace = 0;
        for (int i = 0; i < meaning.length(); i++) {
            if (meaning.charAt(i) == '\n' && i < meaning.length() - 1) {
                labels.add(meaning.substring(prePlace, i));
                prePlace = i + 1;
                if (meaning.charAt(i + 1) == '*') {
                    labels.add("\n");
                }
            }

        }
        labels.add(meaning.substring(prePlace));
        return labels;
    }

    public boolean addNewWord(String word, String meaning) {
        if (dict.get(word) != null){ // word existed
            return false;
        }
        dict.put(word, meaning);
        overwriteFile();
        return true;
    }

    public boolean removeWord(String word) {
        if (dict.get(word) == null){ // word not existed
            return false;
        }
        dict.remove(word);
        overwriteFile();
        return true;
    }
}
