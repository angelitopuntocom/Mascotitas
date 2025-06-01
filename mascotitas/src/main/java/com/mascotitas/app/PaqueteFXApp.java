package com.mascotitas.app;

import com.mascotitas.dao.PaqueteDAO;
import com.mascotitas.model.Paquete;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaqueteFXApp extends Application {

    @Override
    public void start(Stage stage) {
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre del paquete");

        TextArea descripcionField = new TextArea();
        descripcionField.setPromptText("Descripción");
        descripcionField.setPrefRowCount(3);

        TextField precioField = new TextField();
        precioField.setPromptText("Precio");

        Button guardarBtn = new Button("Guardar paquete");

        Label statusLabel = new Label();

        guardarBtn.setOnAction(e -> {
            try {
                String nombre = nombreField.getText();
                String descripcion = descripcionField.getText();
                double precio = Double.parseDouble(precioField.getText());

                Paquete paquete = new Paquete(nombre, descripcion, precio);
                new PaqueteDAO().guardar(paquete);

                statusLabel.setText("✅ Paquete guardado con éxito");
                nombreField.clear();
                descripcionField.clear();
                precioField.clear();
            } catch (Exception ex) {
                statusLabel.setText("❌ Error al guardar: " + ex.getMessage());
            }
        });

        VBox root = new VBox(10, nombreField, descripcionField, precioField, guardarBtn, statusLabel);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 350, 300);
        stage.setTitle("Registro de Paquetes");
        stage.setScene(scene);
        stage.show();
    }
}
