package pe.edu.upeu.asistencia.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.servicio.UsuarioService;

@Component // ✅ Spring gestiona este controlador
public class RegistroUsuarioController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private Label lblMensaje;

    @Autowired // ✅ Inyección automática del servicio
    private UsuarioService usuarioService;

    // 🔹 Botón Guardar
    @FXML
    public void guardarUsuario(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String codigo = txtCodigo.getText().trim();

        // 🔸 Validar campos vacíos
        if (nombre.isEmpty() || correo.isEmpty() || codigo.isEmpty()) {
            lblMensaje.setText("⚠️ Complete todos los campos.");
            return;
        }

        // 🔸 Validar formato de correo electrónico
        if (!correo.contains("@")) {
            lblMensaje.setText("❌ El correo electrónico no es válido (falta '@').");
            return;
        }

        try {
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setCorreoElectronico(correo);
            nuevo.setCodigo(codigo);

            usuarioService.registrarUsuario(nuevo);
            lblMensaje.setText("✅ Usuario registrado correctamente.");

            // 🔸 Limpiar los campos después de registrar
            limpiarCampos(event);

            // 🔸 Cerrar ventana tras éxito (opcional)
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            lblMensaje.setText("❌ Error al registrar el usuario.");
            e.printStackTrace();
        }
    }

    // 🔹 Botón Limpiar → usado por el FXML (onAction="#limpiarCampos")
    @FXML
    private void limpiarCampos(ActionEvent event) {
        txtNombre.clear();
        txtCorreo.clear();
        txtCodigo.clear();
        lblMensaje.setText("");
    }
}
