package br.com.fandrauss.notesfx.model;

import br.com.fandrauss.notesfx.NotesFX;
import br.com.fandrauss.notesfx.model.dao.NotaDao;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Fernando Andrauss
 */
public class Nota {

    private final DoubleProperty x = new SimpleDoubleProperty(0);
    private final DoubleProperty y = new SimpleDoubleProperty(0);
    private final StringProperty texto = new SimpleStringProperty("");
    private final DoubleProperty largura = new SimpleDoubleProperty(0);
    private final DoubleProperty altura = new SimpleDoubleProperty(0);
    private final StringProperty cor = new SimpleStringProperty("AMARELO");

    public String getCor() {
        return cor.get();
    }

    public void setCor(String value) {
        cor.set(value);
    }

    public StringProperty corProperty() {
        return cor;
    }

    public double getAltura() {
        return altura.get();
    }

    public void setAltura(double value) {
        altura.set(value);
    }

    public DoubleProperty alturaProperty() {
        return altura;
    }

    public double getLargura() {
        return largura.get();
    }

    public void setLargura(double value) {
        largura.set(value);
    }

    public DoubleProperty larguraProperty() {
        return largura;
    }

    public Nota() {
        texto.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            NotaDao.SaveNotes(NotesFX.notasList);
        });

        cor.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            NotaDao.SaveNotes(NotesFX.notasList);
        });

//        x.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            NotaDao.SaveNotes(NotesFX.notasList);
//        });
//
//        y.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            NotaDao.SaveNotes(NotesFX.notasList);
//        });
//        altura.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            NotaDao.SaveNotes(NotesFX.notasList);
//        });
//
//        largura.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            NotaDao.SaveNotes(NotesFX.notasList);
//        });
    }

    public String getTexto() {
        return texto.get();
    }

    public void setTexto(String value) {
        texto.set(value);
    }

    public StringProperty textoProperty() {
        return texto;
    }

    public double getY() {
        return y.get();
    }

    public void setY(double value) {
        y.set(value);
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public double getX() {
        return x.get();
    }

    public void setX(double value) {
        x.set(value);
    }

    public DoubleProperty xProperty() {
        return x;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nota other = (Nota) obj;
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }
        return true;
    }

}
