package com.mascotitas.app;

import com.mascotitas.dao.VeterinarioDAO;
import com.mascotitas.dao.AsistenteDAO;
import com.mascotitas.model.Veterinario;
import com.mascotitas.model.Asistente;
import com.mascotitas.service.CitaService;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EliminarPersonalFXApp extends Application {

    private final VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    private final AsistenteDAO asistenteDAO = new AsistenteDAO();
    private final CitaService citaService = new CitaService();

    @Override
    public void start(Stage primaryStage) {
        ComboBox<String> tipoCombo = new ComboBox<>();
        tipoCombo.getItems().addAll("Veterinario", "Asistente");
        tipoCombo.setValue("Veterinario");

        ComboBox<Object> cbPersonal = new ComboBox<>();
        cbPersonal.setItems(FXCollections.observableArrayList(veterinarioDAO.listarTodos()));

        tipoCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if ("Veterinario".equals(newVal)) {
                cbPersonal.setItems(FXCollections.observableArrayList(veterinarioDAO.listarTodos()));
            } else {
                cbPersonal.setItems(FXCollections.observableArrayList(asistenteDAO.listarTodos()));
            }
        });

        Button btnEliminar = new Button("Eliminar");

        btnEliminar.setOnAction(e -> {
            Object seleccionado = cbPersonal.getValue();
            if (seleccionado == null) {
                mostrarAlerta("Debe seleccionar una persona.");
                return;
            }

            try {
                if (seleccionado instanceof Veterinario vet) {
                    if (!citaService.buscarCitasPorVeterinario(vet).isEmpty()) {
                        mostrarAlerta("No se puede eliminar: el veterinario tiene citas asignadas.");
                        return;
                    }
                    veterinarioDAO.eliminar(vet);
                    mostrarInfo("Veterinario eliminado correctamente.");
                    cbPersonal.setItems(FXCollections.observableArrayList(veterinarioDAO.listarTodos()));
                } else if (seleccionado instanceof Asistente asistente) {
                    if (!citaService.buscarCitasPorAsistente(asistente).isEmpty()) {
                        mostrarAlerta("No se puede eliminar: el asistente tiene citas asignadas.");
                        return;
                    }
                    asistenteDAO.eliminar(asistente);
                    mostrarInfo("Asistente eliminado correctamente.");
                    cbPersonal.setItems(FXCollections.observableArrayList(asistenteDAO.listarTodos()));
                }
            } catch (Exception ex) {
                mostrarAlerta("Error: " + ex.getMessage());
            }
        });

        VBox root = new VBox(10, new Label("Tipo"), tipoCombo, new Label("Selecciona"), cbPersonal, btnEliminar);
        root.setPadding(new Insets(20));

        primaryStage.setTitle("Eliminar Personal");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
