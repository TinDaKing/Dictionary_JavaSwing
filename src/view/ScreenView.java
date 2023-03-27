package view;

import dao.EngViet;
import dao.VietEng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ScreenView extends JFrame{

    JFrame frame;
    JPanel searchBar;
    JTextField searchField;
    JComboBox languageList;
    JButton searchButton;
    JPanel translatePanel;
    JPanel leftBar;
    JPanel rightBar;
    

    public ScreenView() {

        frame = new JFrame();

        setTitle("Dictionary by Tran Hoang Tin 20120385");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(1000, 750);

        searchBar = new JPanel();
        searchBar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        searchBar.setBackground(new Color(20,108,148,150));

        leftBar = new JPanel();
        leftBar.setLayout(new FlowLayout(FlowLayout.LEFT, 70, 100));
        leftBar.setBackground(new Color(20,108,148,80));

        rightBar = new JPanel();
        rightBar.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 100));

        searchField = new JTextField();
        // searchField.setText("Enter a word");
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchField.setPreferredSize(new Dimension(200, 30));

        String[] languages = {"EngVie", "VieEng"};

        languageList = new JComboBox(languages);
        languageList.setSelectedIndex(0);


        searchButton = new JButton();
        searchButton.setText("Search");

        searchBar.add(searchField);
        searchBar.add(languageList);
        searchBar.add(searchButton);

        addListenerSearchButton();

        add(searchBar, BorderLayout.NORTH);
        add(leftBar,BorderLayout.WEST);
        add(rightBar,BorderLayout.EAST);
        setVisible(true);

    }

    private void addListenerSearchButton() {
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (translatePanel == null) {
                        translatePanel = new JPanel();
                        translatePanel.setLayout(new BoxLayout(translatePanel, BoxLayout.Y_AXIS));
                    } else {
                        translatePanel.removeAll();
                    }
                    JLabel meaning = null;

                    int lng = languageList.getSelectedIndex();
                    if (lng == 0) { // ENG_VIE
                        if (EngViet.getInstance().translateWord(searchField.getText()) == null) {
                            JOptionPane.showMessageDialog(null, "Cannot find your word! Please try again ^^");
                        } else {
                            List<String> labels = EngViet.getInstance().translateWord(searchField.getText());
                            for (String l: labels){
                                meaning = new JLabel(l);
                                meaning.setAlignmentX(Component.LEFT_ALIGNMENT);
                                translatePanel.add(meaning);
                            }
                        }

                    } else { //VIE_ENG
                        if (VietEng.getInstance().translateWord(searchField.getText()) == null) {
                            JOptionPane.showMessageDialog(null, "Cannot find your word! Please try again ^^");
                        } else {
                            List<String> labels = VietEng.getInstance().translateWord(searchField.getText());
                            for (String l: labels){
                                meaning = new JLabel(l);
                                meaning.setAlignmentX(Component.LEFT_ALIGNMENT);
                                translatePanel.add(meaning);
                            }
                        }
                    }

                    if (meaning != null) {
                        add(translatePanel, BorderLayout.CENTER);
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


}
