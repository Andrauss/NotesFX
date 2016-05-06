package br.com.fandrauss.notesfx;

import br.com.fandrauss.notesfx.controller.NoteController;
import br.com.fandrauss.notesfx.model.Nota;
import br.com.fandrauss.notesfx.model.dao.NotaDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import static javafx.application.Application.launch;

/**
 *
 * @author Fernando Andrauss
 */
public class NotesFX extends Application {

    public static Stage primaryS;
    public static ObservableList<Nota> notasList;

    @Override
    public void start(Stage primary) {

        notasList = FXCollections.observableArrayList();
        try {
            NotaDao.LoadNotes(notasList);
        } catch (Exception e) {
            if (e.getMessage() != null && !e.getMessage().contains("fim prematuro")) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erro");
                a.setHeaderText("Erro ao criar o arquivo de registro de ciclos!");
                a.setContentText(e.getMessage());
                a.show();
            }
        }

        notasList.addListener((ListChangeListener.Change<? extends Nota> c) -> {
            NotaDao.SaveNotes(notasList);
            if (notasList.isEmpty()) {
                Platform.exit();
            }
        });

        try {
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            primary.initStyle(StageStyle.DECORATED);
            primary.setScene(new Scene(
                    new AnchorPane(),
                    visualBounds.getWidth(),
                    visualBounds.getHeight()));

            primary.setOnCloseRequest((event) -> {
                NotaDao.SaveNotes(notasList);
                Platform.exit();
            });

            primary.getScene().setFill(null);
            primary.getScene().getWindow().setOpacity(0);
            primary.setTitle("NotesFX");
            primary.getIcons().add(new Image("/img/logo.png"));
            
            primaryS = primary;
            primaryS.show();

            for (Nota nota : notasList) {
                CreateNote(nota);
            }

            if (notasList.isEmpty()) {
                Nota n = new Nota();
                notasList.add(n);
                CreateNote(n);
            }

        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("Falha o iniciar a aplicação!");
            a.setContentText(ex.getMessage());
            a.show();
        }
    }

    public static void CreateNote(Nota n) throws Exception {
        Region root;
        FXMLLoader loader = new FXMLLoader(NotesFX.class.getResource("/note.fxml"));
        root = loader.load();
        root.setMinWidth(100);
        root.setMinWidth(100);

        Stage stage = new Stage();
        stage.initOwner(primaryS);
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        NoteController controller = loader.getController();

        root.setStyle("-fx-background-color: transparent;");
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.NONE);
        stage.setScene(scene);
        controller.setStage(stage);
        stage.setMinWidth(100);
        stage.setMinWidth(100);

        if (n.getX() != 0 || n.getY() != 0) {
            stage.setX(n.getX());
            stage.setY(n.getY());
        }

        if (n.getAltura() != 0 || n.getLargura() != 0) {
            stage.setWidth(n.getLargura());
            stage.setHeight(n.getAltura());
        }

        controller.setNota(n);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
