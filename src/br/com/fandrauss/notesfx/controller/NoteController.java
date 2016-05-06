package br.com.fandrauss.notesfx.controller;

import br.com.fandrauss.notesfx.util.ResizeHelper;
import br.com.fandrauss.notesfx.NotesFX;
import br.com.fandrauss.notesfx.model.Nota;
import br.com.fandrauss.notesfx.model.NoteColor;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Fernando Andrauss
 */
public class NoteController {

    @FXML
    private AnchorPane headerPane;

    @FXML
    private Button btnAddNota;

    @FXML
    private Button btnDeleteNota;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TextArea txtTexto;

    private Stage stage;
    private Nota nota;
    private double xInicial;
    private double yInicial;

    private final BooleanProperty entered = new SimpleBooleanProperty(false);

    public void initialize() {

        btnAddNota.setVisible(false);
        btnDeleteNota.setVisible(false);

        Platform.runLater(() -> {
            txtTexto.requestFocus();
        });

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(createDefaultMenuItems(txtTexto));
        contextMenu.getItems().add(new SeparatorMenuItem());
        MenuItem itSobre = new MenuItem("Sobre");
        itSobre.setOnAction((evt) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/abaut.fxml"));
                Region root = loader.load();
                root.setStyle("-fx-background-color: transparent;");

                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);

                Stage s = new Stage(StageStyle.TRANSPARENT);
                s.initModality(Modality.APPLICATION_MODAL);
                s.setTitle("Sobre");
                s.setScene(scene);

                s.show();
            } catch (Exception e) {
                // Ignorado
            }
        });
        contextMenu.getItems().addAll(Arrays.asList(createColorMenus()));
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(itSobre);

        txtTexto.setContextMenu(contextMenu);

        btnDeleteNota.setOnAction((evt) -> {

            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir a anotação?",
                    ButtonType.YES,
                    ButtonType.NO);

            a.setHeaderText("Excluir Nota");
            a.initStyle(StageStyle.UTILITY);
            a.initOwner(getStage());
            a.showAndWait().ifPresent((t) -> {

                if (t.equals(ButtonType.YES)) {

                    NotesFX.notasList.remove(nota);
                    getStage().close();

                }
            });

        });

        btnAddNota.setOnAction((ActionEvent event) -> {
            try {
                createNote();
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erro");
                a.setHeaderText("Falha o criar nota!");
                a.setContentText(ex.getMessage());
                a.show();
            }
        });

        headerPane.setOnMousePressed((MouseEvent me) -> {
            if (me.getButton() != MouseButton.MIDDLE) {
                xInicial = me.getSceneX();
                yInicial = me.getSceneY();
            }
        });

        headerPane.setOnMouseDragged((MouseEvent me) -> {
            if (me.getButton() != MouseButton.MIDDLE) {
                headerPane.getScene().getWindow().setX(me.getScreenX() - xInicial);
                headerPane.getScene().getWindow().setY(me.getScreenY() - yInicial);
            }
        });

        createHandlers();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        ResizeHelper.addResizeListener(this.stage);
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
        bindValues(this.nota);
    }

    private void bindValues(Nota nota) {
        txtTexto.textProperty().bindBidirectional(this.nota.textoProperty());
        nota.xProperty().bind(stage.xProperty());
        nota.yProperty().bind(stage.yProperty());
        nota.larguraProperty().bind(stage.widthProperty());
        nota.alturaProperty().bind(stage.heightProperty());
        ChangeNoteColor(NoteColor.valueOf(nota.getCor()));
    }

    private void ChangeNoteColor(NoteColor cor) {
        headerPane.setStyle("-fx-background-color: " + cor.getHeaderColor());
        contentPane.setStyle("-fx-background-color: " + cor.getBodyColor());
        btnAddNota.setStyle("-fx-base: " + cor.getHeaderColor());
        btnDeleteNota.setStyle("-fx-base: " + cor.getHeaderColor());
    }

    private MenuItem[] createColorMenus() {
        MenuItem[] menus = new MenuItem[]{
            new MenuItem("Azul", new ImageView("/img/not_azul.png")),
            new MenuItem("Verde", new ImageView("/img/not_verde.png")),
            new MenuItem("Rosa-Choque", new ImageView("/img/not_rosa.png")),
            new MenuItem("Roxo", new ImageView("/img/not_roxo.png")),
            new MenuItem("Branco", new ImageView("/img/not_branco.png")),
            new MenuItem("Amarelo", new ImageView("/img/not_amarelo.png"))};

        for (MenuItem menu : menus) {
            menu.setOnAction((evt) -> {
                String color_name = menu.getText().toUpperCase().replace("-", "");
                ChangeNoteColor(NoteColor.valueOf(color_name));
                nota.setCor(color_name);
            });
        }

        return menus;
    }

    private List<MenuItem> createDefaultMenuItems(TextInputControl t) {
        MenuItem cut = new MenuItem("Recortar");
        cut.setOnAction(e -> t.cut());
        MenuItem copy = new MenuItem("Copiar");
        copy.setOnAction(e -> t.copy());
        MenuItem paste = new MenuItem("Colar");
        paste.setOnAction(e -> t.paste());
        MenuItem delete = new MenuItem("Excluir");
        delete.setOnAction(e -> t.deleteText(t.getSelection()));
        MenuItem selectAll = new MenuItem("Selecionar Tudo");
        selectAll.setOnAction(e -> t.selectAll());

        BooleanBinding emptySelection = Bindings.createBooleanBinding(
                () -> t.getSelection().getLength() == 0,
                t.selectionProperty());

        cut.disableProperty().bind(emptySelection);
        copy.disableProperty().bind(emptySelection);
        delete.disableProperty().bind(emptySelection);

        return Arrays.asList(cut, copy, paste, delete, new SeparatorMenuItem(), selectAll);
    }

    private void createHandlers() {
        txtTexto.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (entered.not().get()) {
                btnAddNota.setVisible(newValue);
                btnDeleteNota.setVisible(newValue);
            }
        });

        headerPane.setOnMouseEntered((MouseEvent event) -> {
            btnAddNota.setVisible(true);
            btnDeleteNota.setVisible(true);
            entered.set(true);
        });

        headerPane.setOnMouseExited((MouseEvent event) -> {
            if (txtTexto.focusedProperty().not().get()
                    && (btnAddNota.focusedProperty().not().get()
                    && btnDeleteNota.focusedProperty().not().get())) {
                btnAddNota.setVisible(false);
                btnDeleteNota.setVisible(false);
            }
            entered.set(false);
        });
    }

    private void createNote() throws IOException {
        Region root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/note.fxml"));
        root = loader.load();
        Nota n = new Nota();
        n.setCor(nota.getCor());

        Scene scene = new Scene(root);
        Stage s = new Stage(StageStyle.UNDECORATED);
        NoteController controller = loader.getController();

        root.setStyle("-fx-background-color: transparent;");
        scene.setFill(Color.TRANSPARENT);
        s.initStyle(StageStyle.TRANSPARENT);
        s.initModality(Modality.NONE);
        s.setScene(scene);
        s.initOwner(NotesFX.primaryS);
        controller.setStage(s);
        controller.setNota(n);
        scene.getWindow().setX(headerPane.getScene().getWindow().getX() + 25);
        scene.getWindow().setY(headerPane.getScene().getWindow().getY() + 25);
        s.show();
        NotesFX.notasList.add(n);
    }

}
