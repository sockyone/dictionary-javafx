package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.DataServices;
import sample.model.DataSharingServices;
import sample.model.Word;

public class ModifyWord {

    @FXML private TextField word;
    @FXML private TextField pronounce;
    @FXML private TextArea description;
    @FXML private Label info;
    private Word wordObj;
    private int id;


    public void initialize() {
        this.wordObj = DataSharingServices.getInstance().getChoosingWord();
        this.id = wordObj.id;
        word.setText(wordObj.word);
        pronounce.setText(wordObj.pronounce);
        description.setText(wordObj.meaning);
    }

    public void modifyWord() {
        info.setText("Modifying word...");
        Runnable task = new Runnable() {
            @Override
            public void run() {
                DataServices.getInstance().updateWordToDataBase(id,wordObj.word,pronounce.getText(),description.getText());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        info.setText("Word has been modified.");
                    }
                });
            }
        };
        new Thread(task).start();
    }

    public void back() {
        DataSharingServices.getInstance().setViewChange("../view/dictionarySearch.fxml");
    }

}
