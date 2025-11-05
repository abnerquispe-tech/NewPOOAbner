package pe.edu.upeu.asistencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Destino {
    JAPON(Facultad.FIA, "Japon"),
    CHINA(Facultad.FIA, "China"),
    PERU(Facultad.FIA, "Peru"),
    EEUU(Facultad.FCE, "Eeuu"),
    CHILE(Facultad.FCS, "Chile"),
    ARGENTINA(Facultad.FACIHED, "Argentina"),
    BRASIL(Facultad.GENERAL, "Brasil");

    private Facultad facultad;
    private String descripcion;

    @Override
    public String toString() {
        return descripcion;
    }
}
