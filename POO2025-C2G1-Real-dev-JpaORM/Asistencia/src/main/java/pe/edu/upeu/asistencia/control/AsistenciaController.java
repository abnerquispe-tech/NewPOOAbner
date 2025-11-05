package pe.edu.upeu.asistencia.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.servicio.UsuarioService;

import java.io.IOException;
import java.util.List;

@Component
public class AsistenciaController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextArea txtAreaUsuarios;

    @FXML
    private Label lblResultado;

    @Autowired
    private UsuarioService usuarioService;

    // ✅ Contexto de Spring para cargar controladores de subventanas
    @Autowired
    private ApplicationContext context;

    // 🔹 Botón Ingresar
    @FXML
    public void enviarDatos(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String codigo = txtCodigo.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || codigo.isEmpty()) {
            lblResultado.setText("⚠️ Complete todos los campos.");
            return;
        }

        Usuario usuario = usuarioService.login(nombre, correo, codigo);

        if (usuario != null) {
            lblResultado.setText("✅ Acceso permitido: " + usuario.getNombre());
        } else {
            lblResultado.setText("❌ Usuario no encontrado o datos incorrectos.");
        }
    }

    // 🔹 Botón Registrar → abre subventana de registro (FXML)
    @FXML
    public void registrarUsuario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registro_usuario.fxml"));

            // ✅ Usa el contexto de Spring para que funcione la inyección en el otro controlador
            loader.setControllerFactory(context::getBean);

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Usuario");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal hasta cerrar la subventana
            stage.showAndWait();

            // 🔹 Actualiza lista de usuarios después de cerrar la ventana
            mostrarUsuarios();

        } catch (IOException e) {
            e.printStackTrace();
            lblResultado.setText("❌ No se pudo cargar el archivo FXML de registro.");
        } catch (Exception ex) {
            ex.printStackTrace();
            lblResultado.setText("⚠️ Error inesperado al abrir la ventana de registro.");
        }
    }

    // 🔹 Mostrar lista de usuarios registrados
    private void mostrarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        StringBuilder datos = new StringBuilder("👥 Usuarios registrados:\n\n");

        for (Usuario u : usuarios) {
            datos.append("• ")
                    .append(u.getNombre())
                    .append(" | ")
                    .append(u.getCorreoElectronico())
                    .append(" | Código: ")
                    .append(u.getCodigo())
                    .append("\n");
        }

        txtAreaUsuarios.setText(datos.toString());
    }

    // 🔹 Botón Cancelar → limpia los campos
    @FXML
    public void cancelarAccion(ActionEvent event) {
        txtNombre.clear();
        txtCorreo.clear();
        txtCodigo.clear();
        lblResultado.setText("");
    }

    // 🔹 Inicialización automática al cargar la ventana principal
    @FXML
    public void initialize() {
        mostrarUsuarios(); // Muestra los usuarios guardados apenas carga la escena
    }
}
