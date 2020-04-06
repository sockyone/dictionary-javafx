package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.DataServices;
import sample.model.DataSharingServices;
import sample.model.Word;

public class WordAdd {
    @FXML private TextField word;
    @FXML private TextField pronounce;
    @FXML private TextArea description;
    @FXML private Label info;



    public void addWord() {
        if (word.getText().equals("")) {
            info.setText("Word field can't be empty.");
            return;
        }
        if (!DataServices.getInstance().isConnected()){
            DataServices.getInstance().connectToDataBase();
        }
        if (!DataServices.getInstance().isDataLoaded()) {
            DataServices.getInstance().getAll();
        }
        if (DataServices.getInstance().isContain(word.getText())) {
            info.setText("Word has been in Dictionary already.");
        }
        else {
            info.setText("Updating data base...");
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    DataServices.getInstance().insertWordToDataBase(new Word(word.getText(),description.getText(),pronounce.getText()));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            info.setText("Your word has been updated into database.");
                        }
                    });
            }
            };

            new Thread(task).start();
        }
    }

    public void back() {
        DataSharingServices.getInstance().setViewChange("../view/dictionarySearch.fxml");
    }

}
