package sample.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public final class DataSharingServices {
    private static final DataSharingServices instance = new DataSharingServices();
    //private BooleanProperty isSome;
    private BorderPane view;
    private Word choosingWord;
    public String textValue;
    public String resultFromGoogle;
    public MediaPlayer mediaPlayer;


    public void loadPlayer() {
        String musicFile = "../voice.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());

        this.mediaPlayer = new MediaPlayer(sound);
    }



    public static DataSharingServices getInstance() {
        return instance;
    }

    public void setView(BorderPane view) {
        this.view = view;
    }

    public void setViewChange(String value) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(value));
            view.setCenter(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Cracked in loading fxml file.");
        }
    }

    public void setChoosingWord(Word word) {
        this.choosingWord = word;
    }

    public Word getChoosingWord() {
        return this.choosingWord;
    }
}
