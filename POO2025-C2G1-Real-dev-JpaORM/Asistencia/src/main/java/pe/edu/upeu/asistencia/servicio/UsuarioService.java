package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.repositorio.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // ✅ Garantiza transacciones seguras al guardar, actualizar o eliminar
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 🔹 Login (busca usuario según nombre, correo y código)
    public Usuario login(String nombre, String correoElectronico, String codigo) {
        return usuarioRepository.loginUsuario(nombre, correoElectronico, codigo);
    }

    // 🔹 Registrar nuevo usuario (con validación de correo)
    public void registrarUsuario(Usuario usuario) {
        if (!usuario.getCorreoElectronico().contains("@")) {
            throw new IllegalArgumentException("El correo electrónico debe contener '@'.");
        }
        usuarioRepository.save(usuario);
    }

    // 🔹 Obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    // 🔹 Actualizar datos del usuario existente
    public Usuario actualizarUsuario(Long id, Usuario nuevosDatos) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNombre(nuevosDatos.getNombre());
            usuario.setCorreoElectronico(nuevosDatos.getCorreoElectronico());
            usuario.setCodigo(nuevosDatos.getCodigo());
            return usuarioRepository.save(usuario);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
    }

    // 🔹 Eliminar usuario por ID
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
