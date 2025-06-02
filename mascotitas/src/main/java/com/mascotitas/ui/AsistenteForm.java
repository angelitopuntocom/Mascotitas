package com.mascotitas.ui;

import com.mascotitas.dao.AsistenteDAO;
import com.mascotitas.model.Asistente;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AsistenteForm {

    public void mostrarFormulario() {
        Stage stage = new Stage();
        stage.setTitle("Registro de Asistente");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nombreField = new TextField();
        TextField paternoField = new TextField();
        TextField maternoField = new TextField();
        DatePicker fechaNacimientoPicker = new DatePicker();
        TextField curpField = new TextField();

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
        grid.add(guardarBtn, 1, 5);

        guardarBtn.setOnAction(e -> {
            try {
                Asistente asistente = new Asistente();
                asistente.setNombre(nombreField.getText());
                asistente.setPaterno(paternoField.getText());
                asistente.setMaterno(maternoField.getText());
                asistente.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimientoPicker.getValue()));
                asistente.setCurp(curpField.getText());

                new AsistenteDAO().guardar(asistente);
                new Alert(Alert.AlertType.INFORMATION, "Asistente guardado correctamente.").showAndWait();
                stage.close();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error al guardar: " + ex.getMessage()).showAndWait();
            }
        });

        stage.setScene(new Scene(grid, 400, 270));
        stage.show();
    }
}
