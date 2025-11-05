package pe.edu.upeu.asistencia.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Boleto {

    @Id
    private String dni;
    private String nombre;
    private String correo;
    private String telefono;
    private String origen;
    private String destino;
    private String fechaVuelo;
    private String clase;
    private String precio;
}
