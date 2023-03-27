package view;

import dao.EngViet;
import dao.VietEng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ScreenView extends JFrame {

    JPanel topBar;
    JTextField searchField;
    JComboBox languageList;
    JButton searchButton;
    JPanel midPanel;
    JPanel leftBar;
    JPanel rightBar;
    JButton addNewWord;
    JButton removeWord;
    JComboBox dictType;
    JTextField wordText;
    JComboBox wordType;
    JTextArea meaningText;
    JButton submitAddWord;
    JButton submitRemoveWord;

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
        leftBar.setPreferredSize(new Dimension(150, 100));
        initLeftBar();

        rightBar = new JPanel();
        rightBar.setLayout(new BoxLayout(rightBar, BoxLayout.Y_AXIS));


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
                        midPanel.removeAll();
                    }
                    midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));

                    JLabel meaning = null;

                    int lng = languageList.getSelectedIndex();
                    if (lng == 0) { // ENG_VIE
                        if (EngViet.getInstance().translateWord(searchField.getText()) == null) {
                            JOptionPane.showMessageDialog(null, "Cannot find your word! Please try again ^^");
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
                        add(midPanel, BorderLayout.CENTER);
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
        addNewWord.setAlignmentX(Component.CENTER_ALIGNMENT);

        removeWord = new JButton("Remove Word ");
        removeWord.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftBar.add(new JLabel(" "));
        leftBar.add(addNewWord);
        leftBar.add(new JLabel(" "));
        leftBar.add(removeWord);

        addListenerAddNewWordButton();
        addListenerRemoveWordButton();
    }

    private void addListenerAddNewWordButton() {
        addNewWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (midPanel == null) {
                        midPanel = new JPanel();
                    } else {
                        midPanel.removeAll();
                    }
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

                    JPanel panel3 = new JPanel(new FlowLayout(10));
                    panel3.add(meaningLabel);
                    panel3.add(meaningText);
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
                        midPanel.removeAll();
                    }
                    midPanel.setLayout(new FlowLayout());


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
}
