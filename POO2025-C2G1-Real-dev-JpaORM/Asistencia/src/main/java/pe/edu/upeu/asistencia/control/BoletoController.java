package pe.edu.upeu.asistencia.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.modelo.Boleto;

@Controller
public class BoletoController {


    @FXML private TextField txtNombre, txtDni, txtCorreo, txtTelefono, txtPrecio;
    @FXML private ComboBox<String> cbxOrigen, cbxDestino, cbxClase;
    @FXML private DatePicker dpFechaVuelo;
    @FXML private Label lblMensaje;

    // TABLA
    @FXML private TableView<Boleto> tblBoletos;

    ObservableList<Boleto> boletos = FXCollections.observableArrayList();


    private TableColumn<Boleto, String> dniCol, nombreCol, correoCol, telefonoCol,
            origenCol, destinoCol, fechaCol, claseCol, precioCol;
    private TableColumn<Boleto, Void> opcCol;

    private int indexEdit = -1;

    @FXML
    public void initialize() {

        cbxOrigen.getItems().addAll("Lima", "Cusco", "Arequipa", "Trujillo");
        cbxDestino.getItems().addAll("Lima", "Cusco", "Arequipa", "Trujillo");
        cbxClase.getItems().addAll("Económica", "Ejecutiva");


        definirColumnas();
        listarBoletos();
    }

    @FXML
    public void venderBoleto() {
        if (txtNombre.getText().isEmpty() || txtDni.getText().isEmpty()) {
            lblMensaje.setText("Completa los campos obligatorios.");
            return;
        }

        Boleto b = new Boleto();
        b.setNombre(txtNombre.getText());
        b.setDni(txtDni.getText());
        b.setCorreo(txtCorreo.getText());
        b.setTelefono(txtTelefono.getText());
        b.setOrigen(cbxOrigen.getSelectionModel().getSelectedItem());
        b.setDestino(cbxDestino.getSelectionModel().getSelectedItem());
        b.setClase(cbxClase.getSelectionModel().getSelectedItem());
        b.setFechaVuelo(dpFechaVuelo.getValue() != null ? dpFechaVuelo.getValue().toString() : "");
        b.setPrecio(txtPrecio.getText());

        if (indexEdit == -1) {
            boletos.add(b);
        } else {
            boletos.set(indexEdit, b);
            indexEdit = -1;
        }

        limpiarCampos();
        listarBoletos();
        lblMensaje.setText("Boleto vendido correctamente!");
    }

    @FXML
    public void limpiarCampos() {
        txtNombre.clear();
        txtDni.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtPrecio.clear();
        cbxOrigen.getSelectionModel().clearSelection();
        cbxDestino.getSelectionModel().clearSelection();
        cbxClase.getSelectionModel().clearSelection();
        dpFechaVuelo.setValue(null);
        lblMensaje.setText("");
        indexEdit = -1;
    }

    private void definirColumnas() {
        // Limpiar cualquier columna existente
        tblBoletos.getColumns().clear();

        dniCol = new TableColumn<>("DNI");
        nombreCol = new TableColumn<>("Nombre");
        correoCol = new TableColumn<>("Correo");
        telefonoCol = new TableColumn<>("Teléfono");
        origenCol = new TableColumn<>("Origen");
        destinoCol = new TableColumn<>("Destino");
        fechaCol = new TableColumn<>("Fecha Vuelo");
        claseCol = new TableColumn<>("Clase");
        precioCol = new TableColumn<>("Precio");
        opcCol = new TableColumn<>("Opciones");
        opcCol.setPrefWidth(180);

        tblBoletos.getColumns().addAll(
                dniCol, nombreCol, correoCol, telefonoCol,
                origenCol, destinoCol, fechaCol, claseCol, precioCol, opcCol
        );
    }

    private void listarBoletos() {
        dniCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        nombreCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        correoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        telefonoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        origenCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrigen()));
        destinoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestino()));
        fechaCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaVuelo()));
        claseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClase()));
        precioCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrecio()));

        agregarAccionesButton();

        tblBoletos.setItems(boletos);
    }

    private void eliminarBoleto(String dni) {
        boletos.removeIf(b -> b.getDni().equals(dni));
        listarBoletos();
    }

    private void editarBoleto(Boleto b, int index) {
        txtNombre.setText(b.getNombre());
        txtDni.setText(b.getDni());
        txtCorreo.setText(b.getCorreo());
        txtTelefono.setText(b.getTelefono());
        txtPrecio.setText(b.getPrecio());
        cbxOrigen.getSelectionModel().select(b.getOrigen());
        cbxDestino.getSelectionModel().select(b.getDestino());
        cbxClase.getSelectionModel().select(b.getClase());
        dpFechaVuelo.setValue(b.getFechaVuelo() != null ? java.time.LocalDate.parse(b.getFechaVuelo()) : null);

        indexEdit = index;
    }

    private void agregarAccionesButton() {
        Callback<TableColumn<Boleto, Void>, TableCell<Boleto, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEdit = new Button("Editar");
            private final Button btnDelete = new Button("Eliminar");

            {
                btnEdit.setOnAction(event -> {
                    Boleto b = getTableView().getItems().get(getIndex());
                    editarBoleto(b, getIndex());
                });
                btnDelete.setOnAction(event -> {
                    Boleto b = getTableView().getItems().get(getIndex());
                    eliminarBoleto(b.getDni());
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(btnEdit, btnDelete);
                    hbox.setSpacing(10);
                    setGraphic(hbox);
                }
            }
        };

        opcCol.setCellFactory(cellFactory);
    }
}
