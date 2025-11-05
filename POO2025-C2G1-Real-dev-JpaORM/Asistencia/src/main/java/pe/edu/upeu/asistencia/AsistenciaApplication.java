package pe.edu.upeu.asistencia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AsistenciaApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        // 🔹 Inicializa Spring Boot sin lanzar la aplicación de consola
        springContext = new SpringApplicationBuilder(AsistenciaApplication.class)
                .headless(false) // ✅ necesario para aplicaciones JavaFX
                .run();
    }

    @Override
    public void start(Stage stage) {
        try {
            // 🔹 Carga FXML principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_asistencia.fxml"));

            // 🔹 Inyecta controladores gestionados por Spring
            loader.setControllerFactory(springContext::getBean);

            // 🔹 Crea la escena y muestra la ventana
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Sistema de Asistencia Deportiva");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error al cargar el archivo FXML principal.");
        }
    }

    @Override
    public void stop() {
        // 🔹 Cierra el contexto de Spring al salir
        if (springContext != null) {
            springContext.close();
        }
    }

    public static void main(String[] args) {
        launch(args); // 🔹 Inicia JavaFX
    }
}
