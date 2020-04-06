package sample.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import sample.model.*;

import java.util.ArrayList;


public class DictionarySearch {
    @FXML
    private ListView<Word> wordsView;
    @FXML
    private TextField searchText;

    @FXML
    private Label wordText;
    @FXML
    private Label pronounceText;
    @FXML
    private Label descriptionText;
    @FXML
    private HBox modifier;
    @FXML private ImageView voiceImage;

    public void initialize() {

        wordText.setText("Loading the data...");

        modifier.setVisible(false);
        voiceImage.setVisible(false);



        Runnable task = new Runnable() {
            @Override
            public void run() {

                if (!DataServices.getInstance().isConnected()) {
                    DataServices.getInstance().connectToDataBase();
                }

                if (!DataServices.getInstance().isDataLoaded()) {
                    DataServices.getInstance().getAll();
                }
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    System.out.println("Sleep failed");
//                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        wordsView.getItems().addAll(DataServices.getInstance().getDic());
                        wordText.setText("");
                    }
                });

            }
        };

        new Thread(task).start();

        wordsView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.5)));

        timeLine.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        Word temp = new Word(DataSharingServices.getInstance().textValue,"","D");
                        try {
                            DataSharingServices.getInstance().resultFromGoogle = GoogleTransAPI.translate(DataSharingServices.getInstance().textValue);

                            temp.meaning = DataSharingServices.getInstance().resultFromGoogle;
                            temp.pronounce = "Taken from Google Translate API";

                        } catch (Exception e) {
                            System.out.println(e.toString());
                            temp.meaning = "Disconnected From Internet";
                            temp.pronounce = "Disconnected From Internet";
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                wordsView.getItems().clear();
                                DataSharingServices.getInstance().setChoosingWord(temp);
                                wordsView.getItems().add(temp);
                                wordsView.setDisable(false);
                                modifier.setVisible(false);
                            }
                        });
                    }
                };
                new Thread(task).start();
            }
        });

        ChangeListener<Word> viewListener = new ChangeListener<Word>() {
            @Override
            public void changed(ObservableValue<? extends Word> observable, Word oldValue, Word newValue) {
                if (!((newValue.pronounce.equals("Taken from Google Translate API")||(newValue.pronounce.equals("Disconnected From Internet")))))
                    modifier.setVisible(true);
                else modifier.setVisible(false);
                voiceImage.setVisible(false);
                Word word = wordsView.getSelectionModel().getSelectedItem();
                DataSharingServices.getInstance().setChoosingWord(word);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        voiceImage.setImage(new Image(getClass().getResourceAsStream("../image/loud.png")));
                        voiceImage.setDisable(false);
                        try {
                            VoiceAPI.loadVoice(DataSharingServices.getInstance().getChoosingWord().word);
                            DataSharingServices.getInstance().loadPlayer();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            voiceImage.setDisable(true);
                            voiceImage.setImage(new Image(getClass().getResourceAsStream("../image/disconnect.png")));
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                voiceImage.setVisible(true);
                            }
                        });
                    }
                }).start();
                wordText.setText(word.word);
                pronounceText.setText("/" + (word.pronounce.equals("") ? "updating..." : word.pronounce) + "/");
                descriptionText.setText(word.meaning);
            }

        };


        wordsView.getSelectionModel().selectedItemProperty().addListener(viewListener);

        searchText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                wordsView.getSelectionModel().selectedItemProperty().removeListener(viewListener);
                wordsView.getItems().clear();
                if (newValue.equals("")) {
                    wordsView.getItems().addAll(DataServices.getInstance().getDic());
                } else {
                    ArrayList<Word> list= DataServices.getInstance().searchDic(newValue);
                    if (!list.isEmpty()) {
                        wordsView.getItems().addAll(list);

                    } else {
                        DataSharingServices.getInstance().textValue = newValue;
                        wordsView.getItems().add(new Word("searching...","",""));
                        wordsView.setDisable(true);

                        timeLine.playFromStart();
                    }

                }
                wordsView.scrollTo(0);
                wordsView.getSelectionModel().selectedItemProperty().addListener(viewListener);
            }
        });

    }

    public void modifyWord() {
        DataSharingServices.getInstance().setViewChange("../view/ModifyWord.fxml");
    }

    public void deleteWord() {
        wordText.setText("Deleting...");
        descriptionText.setText("");
        pronounceText.setText("");
        modifier.setVisible(false);
        Runnable task = new Runnable() {
            @Override
            public void run() {

                if (!DataServices.getInstance().isConnected()) {
                    DataServices.getInstance().connectToDataBase();
                }
                if (!DataServices.getInstance().isDataLoaded()) {
                    DataServices.getInstance().getAll();
                }
                DataServices.getInstance().deleteWordInDataBase(DataSharingServices.getInstance().getChoosingWord().id);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        wordText.setText("");
                    }
                });

            }
        };
        new Thread(task).start();
    }

    public void playVoice() {
        DataSharingServices.getInstance().mediaPlayer.stop();
        DataSharingServices.getInstance().mediaPlayer.play();
    }
}