package sample.model;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public final class DataServices {
    private static final DataServices instance = new DataServices();
    private DictionarySaver dicSaver;
    private ArrayList<Word> fullDic;
    private String linkDb;
    private Connection dbConnection;


    private DataServices() {
        this.linkDb="jdbc:sqlite:dictionarydata.db";
        this.dicSaver = null;
        fullDic = null;
    }

    public static DataServices getInstance() {
        return instance;
    }

    public boolean isConnected() {
        return (dbConnection!=null);
    }

    public boolean isDataLoaded() {
        return (fullDic!=null);
    }

    public void connectToDataBase() {
        this.dbConnection = null;
        try {
            this.dbConnection = DriverManager.getConnection(linkDb);
            System.out.println("Connection has been set.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isContain(String word) {
        return dicSaver.isContain(word);
    }

    public void getAll() {
        String sql = "SELECT id, word, description,pronounce FROM av";
        try {
            this.dicSaver = new DictionarySaver();
             Statement stmt = this.dbConnection.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                dicSaver.put(new Word(result.getString("word"),result.getString("description"),
                        result.getString("pronounce"),result.getInt("id")));
            }
            this.fullDic = this.dicSaver.getPrefix("");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertWordToDataBase(Word word) {
        String sql = "INSERT INTO av(word,pronounce,description) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
            pstmt.setString(1,word.word);
            pstmt.setString(2,word.pronounce);
            pstmt.setString(3,word.meaning);
            pstmt.executeUpdate();
            dicSaver.put(word);
            fullDic.add(word);
            Collections.sort(fullDic, Word.wordComparator);
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    public void updateWordToDataBase(int id,String word,String pronounce,String description) {
        String sql = "UPDATE av SET  pronounce = ? , description = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
            pstmt.setString(1,pronounce);
            pstmt.setString(2,description);
            pstmt.setInt(3,id);
            pstmt.executeUpdate();
            this.dicSaver.modifyWord(word,pronounce,description);
            this.fullDic = this.dicSaver.getPrefix("");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteWordInDataBase(int id) {
        String sql = "DELETE FROM av  WHERE id = ?";
        try {
            PreparedStatement pstmt = this.dbConnection.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            this.getAll();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Word> getDic() {
        return this.fullDic;
    }

    public ArrayList<Word> searchDic(String prefix) {
        return dicSaver.getPrefix(prefix);
    }


}
