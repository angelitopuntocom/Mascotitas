package com.mascotitas.app;

import com.mascotitas.dao.ClienteDAO;
import com.mascotitas.model.Cliente;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientRegister extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Campos del formulario
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        TextField paternoField = new TextField();
        paternoField.setPromptText("Apellido paterno");

        TextField maternoField = new TextField();
        maternoField.setPromptText("Apellido materno");

        TextField curpField = new TextField();
        curpField.setPromptText("CURP");

        DatePicker fechaNacimientoPicker = new DatePicker();

        TextField telefonoField = new TextField();
        telefonoField.setPromptText("Teléfono");

        Button guardarBtn = new Button("Guardar Cliente");

        Label statusLabel = new Label();

        guardarBtn.setOnAction(e -> {
            try {
                Cliente cliente = new Cliente();
                cliente.setNombre(nombreField.getText());
                cliente.setPaterno(paternoField.getText());
                cliente.setMaterno(maternoField.getText());
                cliente.setCurp(curpField.getText());
                cliente.setTelefono(telefonoField.getText());
                cliente.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimientoPicker.getValue()));

                new ClienteDAO().guardar(cliente);
                statusLabel.setText("Cliente guardado correctamente.");
                nombreField.clear(); paternoField.clear(); maternoField.clear(); curpField.clear(); telefonoField.clear(); fechaNacimientoPicker.setValue(null);
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Error al guardar cliente.");
            }
        });

        VBox root = new VBox(10, nombreField, paternoField, maternoField, curpField, fechaNacimientoPicker, telefonoField, guardarBtn, statusLabel);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Registro de Clientes - Mascotitas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
