package com.mascotitas.app;

import com.mascotitas.model.*;
import com.mascotitas.service.CitaService;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import java.util.Collections;


import com.mascotitas.dao.VeterinarioDAO;
import com.mascotitas.dao.AsistenteDAO;

import java.util.List;

public class ConsultaCitasFXApp extends Application {

    private final CitaService citaService = new CitaService();

    @Override
    public void start(Stage primaryStage) {
        ComboBox<String> filtroCombo = new ComboBox<>();
        filtroCombo.getItems().addAll("Veterinario", "Asistente");
        filtroCombo.setValue("Veterinario");

        ComboBox<Veterinario> cbVeterinario = new ComboBox<>();
        ComboBox<Asistente> cbAsistente = new ComboBox<>();
        cbVeterinario.setItems(FXCollections.observableArrayList(new VeterinarioDAO().listarTodos()));
        cbAsistente.setItems(FXCollections.observableArrayList(new AsistenteDAO().listarTodos()));
        cbAsistente.setVisible(false);

        filtroCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            cbVeterinario.setVisible("Veterinario".equals(newVal));
            cbAsistente.setVisible("Asistente".equals(newVal));
        });

        Button buscarBtn = new Button("Buscar");

        TableView<Cita> tabla = new TableView<>();

        TableColumn<Cita, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(data -> {
            Cita cita = data.getValue();
            return new SimpleStringProperty(cita != null && cita.getCliente() != null
                ? cita.getCliente().getNombreCompleto()
                : "Sin cliente");
        });

        TableColumn<Cita, String> colMascota = new TableColumn<>("Mascota");
        colMascota.setCellValueFactory(data -> {
            Cita cita = data.getValue();
            return new SimpleStringProperty(cita != null && cita.getMascota() != null
                ? cita.getMascota().getNombre()
                : "Sin mascota");
        });

        TableColumn<Cita, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(data -> {
            Cita cita = data.getValue();
            return new SimpleStringProperty(cita != null && cita.getFechaHora() != null
                ? cita.getFechaHora().toString()
                : "Sin fecha");
        });

        Collections.addAll(tabla.getColumns(), colCliente, colMascota, colFecha);

        buscarBtn.setOnAction(e -> {
            List<Cita> citas;
            if ("Veterinario".equals(filtroCombo.getValue())) {
                Veterinario vet = cbVeterinario.getValue();
                if (vet == null) {
                    mostrarAlerta("Seleccione un veterinario.");
                    return;
                }
                citas = citaService.buscarCitasPorVeterinario(vet);
            } else {
                Asistente asist = cbAsistente.getValue();
                if (asist == null) {
                    mostrarAlerta("Seleccione un asistente.");
                    return;
                }
                citas = citaService.buscarCitasPorAsistente(asist);
            }
            tabla.setItems(FXCollections.observableArrayList(citas));
        });

        HBox filtrosBox = new HBox(10, new Label("Filtro por:"), filtroCombo, cbVeterinario, cbAsistente, buscarBtn);

        VBox layout = new VBox(10, filtrosBox, tabla);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Consulta de Citas");
        primaryStage.show();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
