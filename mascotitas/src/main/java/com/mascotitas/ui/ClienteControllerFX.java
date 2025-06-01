package com.mascotitas.ui;

import com.mascotitas.dao.ClienteDAO;
import com.mascotitas.model.Cliente;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClienteControllerFX {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public void mostrarVentanaRegistro() {
        Stage stage = new Stage();
        stage.setTitle("Registro de Cliente");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtNombre = new TextField();
        TextField txtPaterno = new TextField();
        TextField txtMaterno = new TextField();
        DatePicker dpFechaNacimiento = new DatePicker();
        TextField txtCurp = new TextField();
        TextField txtTelefono = new TextField();

        Button btnGuardar = new Button("Guardar Cliente");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);

        grid.add(new Label("Apellido Paterno:"), 0, 1);
        grid.add(txtPaterno, 1, 1);

        grid.add(new Label("Apellido Materno:"), 0, 2);
        grid.add(txtMaterno, 1, 2);

        grid.add(new Label("Fecha Nacimiento:"), 0, 3);
        grid.add(dpFechaNacimiento, 1, 3);

        grid.add(new Label("CURP:"), 0, 4);
        grid.add(txtCurp, 1, 4);

        grid.add(new Label("Teléfono:"), 0, 5);
        grid.add(txtTelefono, 1, 5);

        grid.add(btnGuardar, 1, 6);

        btnGuardar.setOnAction(e -> {
            try {
                Cliente cliente = new Cliente();
                cliente.setNombre(txtNombre.getText());
                cliente.setPaterno(txtPaterno.getText());
                cliente.setMaterno(txtMaterno.getText());
                cliente.setFechaNacimiento(java.sql.Date.valueOf(dpFechaNacimiento.getValue()));
                cliente.setCurp(txtCurp.getText());
                cliente.setTelefono(txtTelefono.getText());

                clienteDAO.guardar(cliente);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Cliente guardado correctamente.");
                alert.showAndWait();

                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se pudo guardar el cliente");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        stage.setScene(new Scene(grid, 400, 350));
        stage.show();
    }
}
