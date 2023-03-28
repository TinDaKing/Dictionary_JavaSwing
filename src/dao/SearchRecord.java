package dao;

import java.io.Serializable;
import java.util.Date;

public class SearchRecord implements Serializable {
    private String word;
    private Date dateSearch;

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
}
