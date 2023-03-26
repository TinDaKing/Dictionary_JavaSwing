import dao.EngViet;

public class Main {
    public static void main(String[] args) {
        System.out.println(EngViet.getInstance().translateWord("account"));
        EngViet.getInstance().overwriteFile();

    }
}