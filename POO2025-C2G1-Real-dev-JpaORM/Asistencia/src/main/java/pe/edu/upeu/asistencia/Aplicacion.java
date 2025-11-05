package pe.edu.upeu.asistencia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Aplicacion extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        // 🔹 Inicializa el contexto de Spring Boot en modo no web
        context = new SpringApplicationBuilder(Aplicacion.class)
                .web(WebApplicationType.NONE) // ❌ Evita intentar levantar un servidor web
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) {
        try {
            // 🔹 Carga el archivo FXML principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal.fxml"));

            // 🔹 Permite que los controladores sean gestionados por Spring
            loader.setControllerFactory(context::getBean);

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Sistema de Asistencia");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error al cargar la interfaz principal. Verifica la ruta y el archivo FXML.");
        }
    }

    @Override
    public void stop() {
        // 🔹 Cierra el contexto de Spring al salir
        if (context != null) {
            context.close();
        }
    }

    public static void main(String[] args) {
        launch(args); // 🔹 Inicia la aplicación JavaFX
    }
}
