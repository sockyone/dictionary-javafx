package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class About {

    @FXML private TextArea info;

    public void initialize() {
        this.info.setText("\tAuthor: Nam Phan\n\tFrom: UET-VNU\n\n\n\n\n\n\tDictionary Test Application.");
    }

}
