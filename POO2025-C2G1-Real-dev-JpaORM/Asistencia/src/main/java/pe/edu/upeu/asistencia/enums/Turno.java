package pe.edu.upeu.asistencia.enums;

public enum Turno {
    MAÑANA("Mañana"),
    TARDE("Tarde"),
    NOCHE("Noche");

    private final String displayName;

    Turno(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
