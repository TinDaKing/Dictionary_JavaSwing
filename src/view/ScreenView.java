package view;

import dao.EngViet;
import dao.FavoriteList;
import dao.VietEng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ScreenView extends JFrame {

    private JPanel topBar;
    private JTextField searchField;
    private JComboBox languageList;
    private JButton searchButton;
    private JPanel midPanel;
    private JScrollPane midContainer;
    private JPanel leftBar;
    private JPanel rightBar;
    private JButton addNewWord;
    private JButton removeWord;
    private JComboBox dictType;
    private JTextField wordText;
    private JComboBox wordType;
    private JTextArea meaningText;
    private JButton submitAddWord;
    private JButton submitRemoveWord;
    private String currentWord = "";
    private int currentLanguage;
    private JButton addToFavorite;
    private JButton viewFavorite;
    private JButton sortUp;
    private JButton sortDown;
    private JPanel panelFavorite;

    private ImageIcon icon = new ImageIcon("images/star.png");
    private ImageIcon icon2 = new ImageIcon("images/star_yellow.png");
    private ImageIcon icon3 = new ImageIcon("images/writing.png");
    private ImageIcon icon4 = new ImageIcon("images/delete.png");
    private ImageIcon icon5 = new ImageIcon("images/favorite.png");
    private ImageIcon icon6 = new ImageIcon("images/ascending.png");
    private ImageIcon icon7 = new ImageIcon("images/descending.png");


    public ScreenView() {

        new JFrame();

        setTitle("Dictionary by Tran Hoang Tin 20120385");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(1000, 600);

        topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        topBar.setBackground(new Color(20, 108, 148, 150));
        initTopBar();

        leftBar = new JPanel();
        leftBar.setLayout(new BoxLayout(leftBar, BoxLayout.Y_AXIS));
        leftBar.setBackground(new Color(20, 108, 148, 80));
        leftBar.setPreferredSize(new Dimension(180, 100));
        initLeftBar();

        rightBar = new JPanel();
        rightBar.setLayout(new BoxLayout(rightBar, BoxLayout.Y_AXIS));
        rightBar.setPreferredSize(new Dimension(150, 100));
        rightBar.setVisible(false);

        add(topBar, BorderLayout.NORTH);
        add(leftBar, BorderLayout.WEST);
        add(rightBar, BorderLayout.EAST);
        setVisible(true);

    }

    private void initTopBar() {
        searchField = new JTextField();
        // searchField.setText("Enter a word");
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchField.setPreferredSize(new Dimension(200, 30));

        String[] languages = {"EngVie", "VieEng"};

        languageList = new JComboBox(languages);
        languageList.setSelectedIndex(0);


        searchButton = new JButton();
        searchButton.setText("Search");

        topBar.add(searchField);
        topBar.add(languageList);
        topBar.add(searchButton);

        addListenerSearchButton();
    }

    private void addListenerSearchButton() {
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (midPanel == null) {
                        midPanel = new JPanel();
                    } else {
                        if (midContainer!= null)
                            remove(midContainer);
                        midPanel.removeAll();
                    }
                    midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));

                    JLabel meaning = null;

                    int lng = languageList.getSelectedIndex();
                    if (lng == 0) { // ENG_VIE
                        if (EngViet.getInstance().translateWord(searchField.getText()) == null) {
                            JOptionPane.showMessageDialog(null, "Cannot find your word! Please try again ^^");
                            return;
                        } else {
                            List<String> labels = EngViet.getInstance().translateWord(searchField.getText());
                            for (String l : labels) {
                                meaning = new JLabel(l);
                                meaning.setAlignmentX(Component.LEFT_ALIGNMENT);
                                midPanel.add(meaning);
                            }
                        }

                    } else { //VIE_ENG
                        if (VietEng.getInstance().translateWord(searchField.getText()) == null) {
                            JOptionPane.showMessageDialog(null, "Cannot find your word! Please try again ^^");
                            return;
                        } else {
                            List<String> labels = VietEng.getInstance().translateWord(searchField.getText());
                            for (String l : labels) {
                                meaning = new JLabel(l);
                                meaning.setAlignmentX(Component.LEFT_ALIGNMENT);
                                midPanel.add(meaning);
                            }
                        }
                    }

                    if (meaning != null) {
                        currentWord = searchField.getText();
                        currentLanguage = languageList.getSelectedIndex();

                        midContainer= new JScrollPane(midPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                        add(midContainer, BorderLayout.CENTER);
                        initRightBar();
                        rightBar.setVisible(true);
                        repaint();
                        setVisible(true);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Cannot find your word! Please try again ^^");
                }
            }
        });
    }

    private void initLeftBar() {
        addNewWord = new JButton("Add New Word");
        addNewWord.setIcon(icon3);
        addNewWord.setAlignmentX(Component.CENTER_ALIGNMENT);

        removeWord = new JButton("Remove Word ");
        removeWord.setIcon(icon4);
        removeWord.setAlignmentX(Component.CENTER_ALIGNMENT);

        viewFavorite = new JButton("View Favorite  ");
        viewFavorite.setIcon(icon5);
        viewFavorite.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftBar.add(new JLabel(" "));
        leftBar.add(addNewWord);
        leftBar.add(new JLabel(" "));
        leftBar.add(removeWord);
        leftBar.add(new JLabel(" "));
        leftBar.add(viewFavorite);

        addListenerAddNewWordButton();
        addListenerRemoveWordButton();
        addListenerShowFavoriteButton();
    }

    private void addListenerAddNewWordButton() {
        addNewWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (midPanel == null) {
                        midPanel = new JPanel();
                    } else {
                        if (midContainer!= null)
                            remove(midContainer);
                        midPanel.removeAll();
                    }
                    rightBar.setVisible(false);
                    midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));

                    String[] dicts = {"EngVie", "VieEng"};
                    JLabel dictLabel = new JLabel("Dictionary: ");
                    dictLabel.setBounds(0, 0, 40, 20);
                    if (dictType != null)
                        dictType.removeAll();
                    dictType = new JComboBox(dicts);
                    dictType.setSelectedIndex(0);

                    JPanel panel0 = new JPanel(new FlowLayout(10));
                    panel0.add(dictLabel);
                    panel0.add(dictType);
                    panel0.setAlignmentX(Component.LEFT_ALIGNMENT);

                    JLabel newWordLabel = new JLabel("New word: ");
                    newWordLabel.setBounds(0, 0, 40, 30);
                    if (wordText != null)
                        wordText.removeAll();
                    wordText = new JTextField();
                    wordText.setPreferredSize(new Dimension(100, 20));

                    JPanel panel1 = new JPanel(new FlowLayout(10));
                    panel1.add(newWordLabel);
                    panel1.add(wordText);
                    panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

                    String[] languages = {"Noun/Danh từ", "Verb/Động từ", "Adjective/Tính từ", "Adverb/Trạng từ", "Pronoun/Đại từ", "Preposition/Giới từ"};

                    JLabel typeLabel = new JLabel("Type: ");
                    typeLabel.setBounds(0, 0, 40, 20);
                    if (wordType != null)
                        wordType.removeAll();
                    wordType = new JComboBox(languages);
                    wordType.setSelectedIndex(0);

                    JPanel panel2 = new JPanel(new FlowLayout(10));
                    panel2.add(typeLabel);
                    panel2.add(wordType);
                    panel2.setAlignmentX(Component.LEFT_ALIGNMENT);

                    JLabel meaningLabel = new JLabel("Meaning: ");
                    meaningLabel.setBounds(0, 0, 40, 20);
                    if (meaningText != null)
                        meaningText.removeAll();
                    meaningText = new JTextArea();
                    meaningText.setPreferredSize(new Dimension(300, 150));
                    meaningText.setWrapStyleWord(true);
                    meaningText.setLineWrap(true);
                    JScrollPane jScrollPane = new JScrollPane(meaningText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                    JPanel panel3 = new JPanel(new FlowLayout(10));
                    panel3.add(meaningLabel);
                    panel3.add(jScrollPane);
                    panel3.setAlignmentX(Component.LEFT_ALIGNMENT);

                    if (submitAddWord != null) {
                        submitAddWord.removeAll();
                    }
                    submitAddWord = new JButton("Add Word");
                    submitAddWord.setAlignmentX(Component.LEFT_ALIGNMENT);

                    midPanel.add(panel0);
                    midPanel.add(panel1);
                    midPanel.add(panel2);
                    midPanel.add(panel3);
                    midPanel.add(submitAddWord);
                    midPanel.add(new JLabel(" "));

                    addListenerSubmitAddNewWord();

                    add(midPanel, BorderLayout.CENTER);
                    repaint();
                    setVisible(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addListenerSubmitAddNewWord() {
        submitAddWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (wordText == null || meaningText == null) {
                        JOptionPane.showMessageDialog(null, "Please fill the text field ^^");
                        return;
                    }
                    if (dictType.getSelectedIndex() == 0) { //ENG_VIE
                        if (!EngViet.getInstance().addNewWord(wordText.getText(), wordType.getSelectedItem() + "\n- " + meaningText.getText())) {
                            JOptionPane.showMessageDialog(null, "This word already existed ^^ You can remove it first");
                            return;
                        }
                    } else {
                        if (!VietEng.getInstance().addNewWord(wordText.getText(), wordType.getSelectedItem() + "\n- " + meaningText.getText())) {
                            JOptionPane.showMessageDialog(null, "This word already existed ^^ You can remove it first");
                            return;
                        }
                    }
                    if (midContainer!= null)
                        remove(midContainer);
                    midPanel.removeAll();
                    repaint();
                    setVisible(true);
                    JOptionPane.showMessageDialog(null, "Add successfully ^^");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addListenerRemoveWordButton() {
        removeWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (midPanel == null) {
                        midPanel = new JPanel();
                    } else {
                        if (midContainer!= null)
                            remove(midContainer);
                        midPanel.removeAll();
                    }
                    midPanel.setLayout(new FlowLayout());
                    rightBar.setVisible(false);

                    String[] dicts = {"EngVie", "VieEng"};
                    JLabel dictLabel = new JLabel("Dictionary: ");
                    dictLabel.setBounds(0, 0, 40, 20);
                    if (dictType != null)
                        dictType.removeAll();
                    dictType = new JComboBox(dicts);
                    dictType.setSelectedIndex(0);

                    JPanel panel0 = new JPanel(new FlowLayout(10));
                    panel0.add(dictLabel);
                    panel0.add(dictType);
                    panel0.setAlignmentX(Component.LEFT_ALIGNMENT);

                    JLabel newWordLabel = new JLabel("Word to delete: ");
                    newWordLabel.setBounds(0, 0, 40, 30);
                    if (wordText != null)
                        wordText.removeAll();
                    wordText = new JTextField();
                    wordText.setPreferredSize(new Dimension(100, 20));

                    JPanel panel1 = new JPanel(new FlowLayout(10));
                    panel1.add(newWordLabel);
                    panel1.add(wordText);
                    panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

                    if (submitRemoveWord != null) {
                        submitRemoveWord.removeAll();
                    }
                    submitRemoveWord = new JButton("Delete Word");
                    submitRemoveWord.setAlignmentX(Component.LEFT_ALIGNMENT);
                    addListenerSubmitRemoveWord();

                    midPanel.add(panel0);
                    midPanel.add(panel1);
                    midPanel.add(submitRemoveWord);
                    midPanel.add(new JLabel(" "));
                    midPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    add(midPanel, BorderLayout.CENTER);
                    repaint();
                    setVisible(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addListenerSubmitRemoveWord() {
        submitRemoveWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (wordText == null) {
                        JOptionPane.showMessageDialog(null, "Please fill the text field ^^");
                        return;
                    }
                    if (dictType.getSelectedIndex() == 0) { //ENG_VIE
                        if (!EngViet.getInstance().removeWord(wordText.getText())) {
                            JOptionPane.showMessageDialog(null, "This word not existed, cannot be deleted ^^");
                            return;
                        }
                    } else { //VIE_ENG
                        if (!VietEng.getInstance().removeWord(wordText.getText())) {
                            JOptionPane.showMessageDialog(null, "This word not existed, cannot be deleted ^^");
                            return;
                        }
                    }
                    if (midContainer!= null)
                        remove(midContainer);
                    midPanel.removeAll();
                    repaint();
                    setVisible(true);
                    JOptionPane.showMessageDialog(null, "Delete successfully ^^");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addListenerShowFavoriteButton() {
        viewFavorite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (midPanel == null) {
                        midPanel = new JPanel();
                    } else {
                        if (midContainer!= null)
                            remove(midContainer);
                        midPanel.removeAll();
                    }
                    midPanel.setLayout(null);

                    rightBar.setVisible(false);

                    Map<String, String> favList = FavoriteList.getInstance().showList();
                    if (favList == null) {
                        JOptionPane.showMessageDialog(null, "Favorite List is empty ^^");
                        return;
                    }
                    sortUp = new JButton();
                    sortUp.setIcon(icon6);
                    sortDown = new JButton();
                    sortDown.setIcon(icon7);

                    addListenerSortUpButton();
                    addListenerSortDownButton();

                    JPanel panel1 = new JPanel();
                    panel1.setLayout(new FlowLayout(10));
                    panel1.add(sortUp);
                    panel1.add(sortDown);

                    panelFavorite = new JPanel();
                    panelFavorite.setLayout(new BoxLayout(panelFavorite, BoxLayout.Y_AXIS));

                    JLabel word;
                    for (Map.Entry<String, String> entry : favList.entrySet()) {
                        word= new JLabel(entry.getKey());
                        word.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panelFavorite.add(word);
                    }
                    panel1.setBounds(0,0,300,60);
                    panelFavorite.setBounds(5,60,300,500);


                    midPanel.add(panel1);
                    midPanel.add(panelFavorite);
                    add(midPanel, BorderLayout.CENTER);
                    repaint();
                    setVisible(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addListenerSortUpButton() {
        sortUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    panelFavorite.removeAll();
                    SortedMap<String, String> favList = FavoriteList.getInstance().showList();


                    JLabel word;
                    for (Map.Entry<String, String> entry : favList.entrySet()) {
                        word= new JLabel(entry.getKey());
                        word.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panelFavorite.add(word);
                    }
                    panelFavorite.setBounds(5,60,300,500);

                    midPanel.add(panelFavorite);
                    add(midPanel, BorderLayout.CENTER);
                    repaint();
                    setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static <K extends Comparable, V> Map<K, V> sortByKeys(Map<K, V> map) {
        Map<K, V> treeMap = new TreeMap<>(new Comparator<K>() {
            @Override
            public int compare(K a, K b) {
                return b.compareTo(a);
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }

    private void addListenerSortDownButton() {
        sortDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    panelFavorite.removeAll();
                    Map<String, String> favList = sortByKeys(FavoriteList.getInstance().showList());


                    JLabel word;
                    for (Map.Entry<String, String> entry : favList.entrySet()) {
                        word = new JLabel(entry.getKey());
                        word.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panelFavorite.add(word);
                    }
                    panelFavorite.setBounds(5,60,300,500);

                    midPanel.add(panelFavorite);
                    add(midPanel, BorderLayout.CENTER);
                    repaint();
                    setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initRightBar() {

        if (addToFavorite != null) {
            rightBar.remove(addToFavorite);
        }
        addToFavorite = new JButton("Add to Favorite");

        if (FavoriteList.getInstance().getMeaning(currentWord) == null) {
            addToFavorite.setIcon(icon);
        } else {
            addToFavorite.setIcon(icon2);
        }
        addToFavorite.setAlignmentX(Component.CENTER_ALIGNMENT);
        addListenerFavoriteWord();
        rightBar.add(addToFavorite);

    }

    private void addListenerFavoriteWord() {
        addToFavorite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (FavoriteList.getInstance().getMeaning(currentWord) == null) { // does not have -> add
                        if (currentLanguage == 0) { // English
                            FavoriteList.getInstance().addNewWordToFavorite(currentWord, EngViet.getInstance().getMeaning(currentWord));
                        } else { // Vietnamese
                            FavoriteList.getInstance().addNewWordToFavorite(currentWord, VietEng.getInstance().getMeaning(currentWord));
                        }
                        addToFavorite.setIcon(icon2);
                        JOptionPane.showMessageDialog(null, "Add to Favorite successfully ^^");

                    } else {
                        FavoriteList.getInstance().removeWordFromFavorite(currentWord);
                        addToFavorite.setIcon(icon);
                        JOptionPane.showMessageDialog(null, "Delete from Favorite successfully ^^");
                    }
                    repaint();
                    setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
