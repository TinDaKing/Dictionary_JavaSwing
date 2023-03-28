package dao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

public class SearchRecord implements Serializable {
    private Date dateSearch;
    private String word;

    public SearchRecord(String word, Date dateSearch) {
        this.word = word;
        this.dateSearch = dateSearch;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getDateSearch() {
        return dateSearch;
    }

    public void setDateSearch(Date dateSearch) {
        this.dateSearch = dateSearch;
    }


    @Override
    public String toString() {
        return word + " " +dateSearch.toString();
    }
}
