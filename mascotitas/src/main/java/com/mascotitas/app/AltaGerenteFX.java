package com.mascotitas.app;

import com.mascotitas.dao.GerenteDAO;
import com.mascotitas.model.Gerente;
import com.mascotitas.model.enums.Sucursal;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class AltaGerenteFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField tfNombre = new TextField();
        TextField tfPaterno = new TextField();
        TextField tfMaterno = new TextField();
        TextField tfCurp = new TextField();
        DatePicker dpNacimiento = new DatePicker();
        ComboBox<Sucursal> cbSucursal = new ComboBox<>(FXCollections.observableArrayList(Sucursal.values()));

        Button btnGuardar = new Button("Guardar Gerente");

        btnGuardar.setOnAction(e -> {
            try {
                String nombre = tfNombre.getText();
                String paterno = tfPaterno.getText();
                String materno = tfMaterno.getText();
                String curp = tfCurp.getText();
                LocalDate fechaNac = dpNacimiento.getValue();
                Sucursal sucursal = cbSucursal.getValue();

                if (nombre.isEmpty() || paterno.isEmpty() || curp.isEmpty() || fechaNac == null || sucursal == null) {
                    mostrarAlerta("Error", "Por favor, llena todos los campos obligatorios.");
                    return;
                }

                Gerente gerente = new Gerente(nombre, paterno, materno, Date.valueOf(fechaNac), curp, sucursal);


                new GerenteDAO().guardar(gerente);
                mostrarAlerta("Éxito", "Gerente guardado exitosamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                mostrarAlerta("Error", "Ocurrió un error al guardar el gerente.");
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(tfNombre, 1, 0);
        grid.add(new Label("Apellido Paterno:"), 0, 1);
        grid.add(tfPaterno, 1, 1);
        grid.add(new Label("Apellido Materno:"), 0, 2);
        grid.add(tfMaterno, 1, 2);
        grid.add(new Label("CURP:"), 0, 3);
        grid.add(tfCurp, 1, 3);
        grid.add(new Label("Fecha de Nacimiento:"), 0, 4);
        grid.add(dpNacimiento, 1, 4);
        grid.add(new Label("Sucursal:"), 0, 5);
        grid.add(cbSucursal, 1, 5);
        grid.add(btnGuardar, 1, 6);

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setTitle("Alta de Gerente");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
