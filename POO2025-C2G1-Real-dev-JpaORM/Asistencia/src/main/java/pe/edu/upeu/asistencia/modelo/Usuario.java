package pe.edu.upeu.asistencia.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios") // ✅ Asigna un nombre claro a la tabla en la BD
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 100)
    private String correoElectronico;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // 🔹 Constructor vacío requerido por JPA
    public Usuario() {}

    // 🔹 Constructor con parámetros
    public Usuario(String nombre, String codigo, String correoElectronico) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.correoElectronico = correoElectronico;
        this.fechaRegistro = LocalDateTime.now();
    }

    // 🔹 Getters y Setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // 🔹 Representación legible (útil para logs o ListView/TableView)
    @Override
    public String toString() {
        return nombre + " (" + correoElectronico + ")";
    }
}
