/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fandrauss.notesfx.controller;

import br.com.fandrauss.notesfx.NotesFX;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

/**
 * FXML Controller class
 *
 * @author Fernando Andrauss
 */
public class AbautController implements Initializable {

    @FXML
    private Hyperlink hyperGit;

    @FXML
    private Button btnOk;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Platform.runLater(btnOk::requestFocus);

        hyperGit.setOnAction((evt) -> {
            HostServicesDelegate hostServices = HostServicesFactory.getInstance(new NotesFX());
            hostServices.showDocument("http://" + hyperGit.getText());
        });
    }

    @FXML
    private void close() {
        hyperGit.getScene().getWindow().hide();
    }

}
