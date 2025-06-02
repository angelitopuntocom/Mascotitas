package com.mascotitas.ui;

import com.mascotitas.dao.VeterinarioDAO;
import com.mascotitas.model.Veterinario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VeterinarioForm {

    public void mostrarFormulario() {
        Stage stage = new Stage();
        stage.setTitle("Registro de Veterinario");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nombreField = new TextField();
        TextField paternoField = new TextField();
        TextField maternoField = new TextField();
        DatePicker fechaNacimientoPicker = new DatePicker();
        TextField curpField = new TextField();
        TextField cedulaField = new TextField();

        Button guardarBtn = new Button("Guardar");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Apellido Paterno:"), 0, 1);
        grid.add(paternoField, 1, 1);
        grid.add(new Label("Apellido Materno:"), 0, 2);
        grid.add(maternoField, 1, 2);
        grid.add(new Label("Fecha Nacimiento:"), 0, 3);
        grid.add(fechaNacimientoPicker, 1, 3);
        grid.add(new Label("CURP:"), 0, 4);
        grid.add(curpField, 1, 4);
        grid.add(new Label("Cédula Profesional:"), 0, 5);
        grid.add(cedulaField, 1, 5);
        grid.add(guardarBtn, 1, 6);

        guardarBtn.setOnAction(e -> {
            try {
                Veterinario vet = new Veterinario();
                vet.setNombre(nombreField.getText());
                vet.setPaterno(paternoField.getText());
                vet.setMaterno(maternoField.getText());
                vet.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimientoPicker.getValue()));
                vet.setCurp(curpField.getText());
                vet.setCedula(Integer.parseInt(cedulaField.getText()));

                new VeterinarioDAO().guardar(vet);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veterinario guardado correctamente.");
                alert.showAndWait();
                stage.close();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error al guardar: " + ex.getMessage()).showAndWait();
            }
        });

        stage.setScene(new Scene(grid, 400, 300));
        stage.show();
    }
}
