/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fandrauss.notesfx.model.dao;

import br.com.fandrauss.notesfx.model.Nota;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.Alert;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Fernando Andrauss
 */
public class NotaDao {

    public static void SaveNotes(List<Nota> notasList) {
        verificarArquivo();
        File file = new File(getPathToNotes());
        try {
            JAXBContext context = JAXBContext.newInstance(NotaListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            NotaListWrapper wrapper = new NotaListWrapper();
            wrapper.setNotas(notasList);

            m.marshal(wrapper, file);

        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("Erro ao criar o arquivo de registro de ciclos!");
            a.setContentText(ex.getMessage());
            a.show();
        }
    }

    public static void LoadNotes(List<Nota> listaCiclos) throws Exception {
        verificarArquivo();
        File file = new File(getPathToNotes());

        JAXBContext context = JAXBContext.newInstance(NotaListWrapper.class);

        Unmarshaller um = context.createUnmarshaller();

        NotaListWrapper wrapper = (NotaListWrapper) um.unmarshal(file);

        listaCiclos.clear();
        listaCiclos.addAll(wrapper.getNotas());
    }

    private static String getPathToNotes() {
        String path = System.getProperty("user.home") + File.separator + ".notesFX";
        return path + File.separator + "data" + File.separator + "notes.xml";
    }

    private static void verificarArquivo() {

        String path = System.getProperty("user.home") + File.separator + ".notesFX" + File.separator + "data";
        File f = new File(path);
        boolean exists = f.exists();
        try {
            if (!exists) {

                File file = new File(path);
                file.mkdir();
                file = new File(path + File.separator + "notes.xml");
                file.createNewFile();
            }
        } catch (IOException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("Erro ao criar o arquivo de registro de ciclos!");
            a.setContentText(ex.getMessage());
            a.show();
        }
    }
}
