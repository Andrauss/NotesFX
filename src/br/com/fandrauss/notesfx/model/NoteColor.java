package br.com.fandrauss.notesfx.model;

/**
 *
 * @author Fernando Andrauss
 */
public enum NoteColor {

    
    AZUL("rgb(201, 236, 248)", " linear-gradient(rgb(214, 241, 250), rgb(185, 220, 244))"),
    VERDE("#C5F7C1", " linear-gradient(#CFFDC9, #B1E8AE)"),
    ROSACHOQUE("rgb(241, 195, 241)", " linear-gradient(rgb(245, 208, 245), rgb(235, 175, 235))"),
    ROXO("rgb(212, 205, 243)", " linear-gradient(rgb(220, 215, 254), rgb(199, 186, 254))"),
    BRANCO("rgb(244, 245, 244)", " linear-gradient(rgb(252, 253, 251), rgb(233, 237, 235))"),
    AMARELO("#f8f7b6", " linear-gradient(#fdfdca, #fcf9a2)");
    

    private NoteColor(String headerColor, String bodyColor) {
        this.headerColor = headerColor;
        this.bodyColor = bodyColor;
    }

    private String headerColor;
    private String bodyColor;

    public String getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    public String getBodyColor() {
        return bodyColor;
    }

    public void setBodyColor(String bodyColor) {
        this.bodyColor = bodyColor;
    }

}
