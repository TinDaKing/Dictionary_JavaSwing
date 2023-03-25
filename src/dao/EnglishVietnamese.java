package dao;

import java.util.ArrayList;
import java.util.List;

public class EnglishVietnamese {
    private  List<String> dict;

    private static EnglishVietnamese instance;

    public static EnglishVietnamese getInstance(){
        if(instance == null){
            instance = new EnglishVietnamese();
        }
        return instance;
    }

    private EnglishVietnamese(){
        dict = new ArrayList<>();
    }
    private EnglishVietnamese(List<String> param){
        dict  = param;
    }


}
