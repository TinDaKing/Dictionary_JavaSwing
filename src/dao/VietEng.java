package dao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VietEng {
    private Map<String,String> dict;

    private static VietEng instance;

    public static VietEng getInstance() {
        if (instance == null) {
            instance = new VietEng();
        }
        return instance;
    }

    private VietEng() {
        dict = new HashMap<>();
        try {
            File inputFile = new File("src/Viet_Anh/Viet_Anh.xml");
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
        }
    }

    public String translateWord(String word) {
        return dict.get(word);
    }

}
