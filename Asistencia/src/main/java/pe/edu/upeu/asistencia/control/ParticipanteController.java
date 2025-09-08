package pe.edu.upeu.asistencia.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;
import pe.edu.upeu.asistencia.modelo.Participante;
import pe.edu.upeu.asistencia.servicio.ParticipanteServicioI;

@Controller
public class ParticipanteController {

    @FXML
    private TextField txtNombres, txtDni, txtApellidos;
    @FXML
    private ComboBox<Carrera> cbxCarrera;
    @FXML
    private ComboBox<TipoParticipante> cbxTipoParticipante;
    @FXML
    private TextField TxtNombres, TxtApellidos, TxtDni;

    @FXML
    private TableView<Participante> tableView;
    ObservableList<Participante> listaParticipantes;
    @FXML
    private TableColumn<Participante, String> dniColum, nombreColum, apellidoColum, carraraColum, tipoPartColum;
    @Autowired
    ParticipanteServicioI ps;
    TableColumn<Participante, String> dniCol, nombreCol, apellidoCol, carreraCol, tipoParticipanteCol;

    @FXML
    public void initialize() {
        cbxCarrera.getItems().setAll(Carrera.values());
        cbxTipoParticipante.getItems().setAll(TipoParticipante.values());
        cbxCarrera.getSelectionModel().select(4);
        Carrera carrera = cbxCarrera.getSelectionModel().getSelectedItem();
        System.out.println(carrera.name());
        definirColumnas();
        listarPartipantes();
    }

    public void limpiarFormulario() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDni.setText("");
        cbxCarrera.getSelectionModel().clearSelection();
        cbxTipoParticipante.getSelectionModel().clearSelection();
    }

    @FXML
    public void registrarParticipante() {
        Participante p = new Participante();

        p.setDni(new SimpleStringProperty(txtDni.getText()));
        p.setNombre(new SimpleStringProperty(txtNombres.getText()));
        p.setApellidos(new SimpleStringProperty(txtApellidos.getText()));
        p.setCarrera(cbxCarrera.getSelectionModel().getSelectedItem());
        p.setTipoParticipante(cbxTipoParticipante.getSelectionModel().getSelectedItem());

        ps.save(p);
        limpiarFormulario();
        listarPartipantes();
    }


    public void definirColumnas() {
        dniCol = new TableColumn<>("DNI");
        nombreCol = new TableColumn<>("Nombre");
        apellidoCol = new TableColumn<>("Apellido");
        carreraCol = new TableColumn<>("Carrera");
        tipoParticipanteCol = new TableColumn<>("Tipo Participante");
        tableView.getColumns().addAll(dniCol, nombreCol, apellidoCol, carreraCol, tipoParticipanteCol);
    }

    public void listarPartipantes() {
        dniCol.setCellValueFactory(cellData -> cellData.getValue().getDni());
        nombreCol.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        apellidoCol.setCellValueFactory(cellData -> cellData.getValue().getApellidos());
        carreraCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getCarrera().toString())
        );
        listaParticipantes = FXCollections.observableList(ps.findAll());
        tableView.setItems(listaParticipantes);
    }
}