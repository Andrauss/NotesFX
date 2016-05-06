package br.com.fandrauss.notesfx.model.dao;

import br.com.fandrauss.notesfx.model.Nota;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Andrauss
 */
@XmlRootElement(name = "notas")
public class NotaListWrapper {

    private List<Nota> notas;

    @XmlElement(name = "nota")
    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

}
