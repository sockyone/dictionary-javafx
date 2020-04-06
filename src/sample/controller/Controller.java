package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import sample.model.DataSharingServices;

public class Controller  {

    @FXML private BorderPane center;

    public void initialize() {
        DataSharingServices.getInstance().setView(center);
        DataSharingServices.getInstance().setViewChange("../view/dictionarySearch.fxml");

    }
    public void changeToAbout() {
        DataSharingServices.getInstance().setViewChange("../view/About.fxml");
    }

    public void changeToSearch() {
        DataSharingServices.getInstance().setViewChange("../view/dictionarySearch.fxml");
    }

    public void changeToGoogleTrans() {

        DataSharingServices.getInstance().setViewChange("../view/GoogleTrans.fxml");
    }

    public void changeToWordAdd() {
        DataSharingServices.getInstance().setViewChange("../view/WordAdd.fxml");
    }


}



