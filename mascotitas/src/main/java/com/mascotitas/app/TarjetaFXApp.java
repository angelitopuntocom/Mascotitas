package com.mascotitas.app;

import com.mascotitas.dao.ClienteDAO;
import com.mascotitas.model.Cliente;
import com.mascotitas.model.Tarjeta;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TarjetaFXApp extends Application {

    @Override
    public void start(Stage stage) {
        TextField numeroField = new TextField();
        numeroField.setPromptText("Número de tarjeta (16 dígitos)");

        TextField vencimientoField = new TextField();
        vencimientoField.setPromptText("Vencimiento (dd/MM/yyyy)");

        TextField cvcField = new TextField();
        cvcField.setPromptText("CVC");

        ComboBox<Cliente> clienteComboBox = new ComboBox<>();
        List<Cliente> clientes = new ClienteDAO().obtenerTodos();
        clienteComboBox.setItems(FXCollections.observableArrayList(clientes));
        clienteComboBox.setPromptText("Seleccionar Cliente");

        Button guardarBtn = new Button("Guardar tarjeta");
        Label statusLabel = new Label();

        guardarBtn.setOnAction(e -> {
            try {
                long numero = Long.parseLong(numeroField.getText());
                Date vencimiento = new SimpleDateFormat("dd/MM/yyyy").parse(vencimientoField.getText());
                short cvc = Short.parseShort(cvcField.getText());
                Cliente cliente = clienteComboBox.getValue();

                if (cliente == null) {
                    statusLabel.setText("⚠️ Selecciona un cliente.");
                    return;
                }

                Tarjeta tarjeta = new Tarjeta(numero, vencimiento, cvc);
                cliente.setTarjeta(tarjeta);

                new ClienteDAO().actualizar(cliente);

                statusLabel.setText("✅ Tarjeta guardada correctamente.");
                numeroField.clear(); vencimientoField.clear(); cvcField.clear();
                clienteComboBox.getSelectionModel().clearSelection();
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("❌ Error al guardar tarjeta.");
            }
        });

        VBox root = new VBox(10, numeroField, vencimientoField, cvcField, clienteComboBox, guardarBtn, statusLabel);
        root.setPadding(new Insets(20));
        stage.setScene(new Scene(root, 400, 350));
        stage.setTitle("Registrar Tarjeta");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
