package com.mascotitas.app;

import com.mascotitas.dao.ClienteDAO;
import com.mascotitas.dao.MascotaDAO;
import com.mascotitas.model.Cliente;
import com.mascotitas.model.Mascota;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MascotaFXApp extends Application {

    @Override
    public void start(Stage stage) {
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre de la mascota");

        TextField tipoField = new TextField();
        tipoField.setPromptText("Tipo (Perro, Gato...)");

        TextField razaField = new TextField();
        razaField.setPromptText("Raza");

        TextField edadField = new TextField();
        edadField.setPromptText("Edad");

        ComboBox<Cliente> clienteComboBox = new ComboBox<>();
        clienteComboBox.setPromptText("Seleccionar Cliente");

        List<Cliente> clientes = new ClienteDAO().obtenerTodos();
        clienteComboBox.getItems().addAll(clientes);

        Button guardarBtn = new Button("Registrar Mascota");
        Label statusLabel = new Label();

        guardarBtn.setOnAction(e -> {
            try {
                Mascota mascota = new Mascota();
                mascota.setNombre(nombreField.getText());
                mascota.setTipo(tipoField.getText());
                mascota.setRaza(razaField.getText());
                mascota.setEdad(Integer.parseInt(edadField.getText()));
                mascota.setCliente(clienteComboBox.getValue());

                new MascotaDAO().guardar(mascota);

                statusLabel.setText("Mascota registrada correctamente.");
                nombreField.clear(); tipoField.clear(); razaField.clear(); edadField.clear();
                clienteComboBox.getSelectionModel().clearSelection();
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Error al registrar mascota.");
            }
        });

        VBox layout = new VBox(10, nombreField, tipoField, razaField, edadField, clienteComboBox, guardarBtn, statusLabel);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 400);
        stage.setTitle("Registrar Mascota");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
