package pe.edu.upeu.asistencia.control; // <- corregido

import org.springframework.stereotype.Component;
import javafx.fxml.FXML;

@Component
public class PrincipalController {

    @FXML
    private void handleButtonClick() {
        System.out.println("¡Botón presionado! La interfaz funciona correctamente.");
    }
}
