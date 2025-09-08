package pe.edu.upeu.asistencia.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
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
    TableColumn<Participante, Void> opcCol;

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
        opcCol = new TableColumn<>("Opciones");
        tableView.getColumns().addAll(dniCol, nombreCol, apellidoCol, carreraCol, tipoParticipanteCol,opcCol);
    }

    public void listarPartipantes() {
        dniCol.setCellValueFactory(cellData -> cellData.getValue().getDni());
        nombreCol.setCellValueFactory(cellData -> cellData.getValue().getNombre());
        apellidoCol.setCellValueFactory(cellData -> cellData.getValue().getApellidos());
        carreraCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getCarrera().toString())
        );
        agregarAccionesButton();
        listaParticipantes = FXCollections.observableList(ps.findAll());
        tableView.setItems(listaParticipantes);
    }
    public void eliminarPartipante(int index) {
        ps.delete(index);
        listarPartipantes();

    }
    public void editarPartipante(Participante p, int index) {
        txtDni.setText(p.getDni().toString());
        txtNombres.setText(p.getNombre().toString());
        txtApellidos.setText(p.getApellidos().toString());

    }

    public void agregarAccionesButton(){
        Callback<TableColumn<Participante, Void>, TableCell<Participante, Void>>
                cellFactory = param -> new TableCell<>(){
            private final Button btnEdit = new Button("editar");
            private final Button btnDelet = new Button("Eliminar");
            {
                btnEdit.setOnAction(event -> {
                    Participante participante = getTableView().getItems().get(getIndex());
                    editarPartipante(participante,getIndex());
                });
                btnDelet.setOnAction(event -> {
                   eliminarPartipante(getIndex());
                });
            }
            @Override
            public void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                if(empty){  setGraphic(null);  }else {
                    HBox hbox = new HBox(btnDelet, btnEdit);
                    hbox.setSpacing(10);
                    setGraphic(hbox);
                }
            }
        };
        opcCol.setCellFactory(cellFactory);
    }

}
