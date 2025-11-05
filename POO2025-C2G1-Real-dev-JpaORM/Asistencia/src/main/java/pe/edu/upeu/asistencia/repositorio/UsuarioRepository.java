package pe.edu.upeu.asistencia.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.asistencia.modelo.Usuario;

@Repository
public interface UsuarioRepository extends org.springframework.data.jpa.repository.JpaRepository<Usuario, Long> {

    // Método para verificar login: devuelve el usuario si coincide nombre, dni y código
    @Query(value = "SELECT * FROM usuario u WHERE u.nombre = :nombre AND u.dni = :dni AND u.codigo = :codigo", nativeQuery = true)
    Usuario loginUsuario(
            @Param("nombre") String nombre,
            @Param("dni") String dni,
            @Param("codigo") String codigo
    );
}