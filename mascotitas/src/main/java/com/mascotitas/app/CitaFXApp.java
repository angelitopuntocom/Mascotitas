package com.mascotitas.app;

import com.mascotitas.model.*;
import com.mascotitas.service.CitaService;
import com.mascotitas.exception.*;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import com.mascotitas.dao.ClienteDAO;
import com.mascotitas.dao.MascotaDAO;
import com.mascotitas.dao.PaqueteDAO;
import com.mascotitas.dao.VeterinarioDAO;
import com.mascotitas.dao.AsistenteDAO;


public class CitaFXApp extends Application {

    private final CitaService citaService = new CitaService();

    @Override
    public void start(Stage primaryStage) {
        ComboBox<Cliente> cbCliente = new ComboBox<>();
        ComboBox<Mascota> cbMascota = new ComboBox<>();
        ComboBox<Veterinario> cbVeterinario = new ComboBox<>();
        ComboBox<Asistente> cbAsistente = new ComboBox<>();
        ListView<Paquete> lvPaquetes = new ListView<>();
        lvPaquetes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TextArea descripcionArea = new TextArea();
        DatePicker datePicker = new DatePicker();
        TextField timeField = new TextField("10:00");

        // Cargar datos usando instancias DAO
        ClienteDAO clienteDAO = new ClienteDAO();
        MascotaDAO mascotaDAO = new MascotaDAO();
        VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
        AsistenteDAO asistenteDAO = new AsistenteDAO();
        PaqueteDAO paqueteDAO = new PaqueteDAO();

        cbCliente.setItems(FXCollections.observableArrayList(clienteDAO.listarTodos()));
        cbMascota.setItems(FXCollections.observableArrayList(mascotaDAO.listarTodos()));
        cbVeterinario.setItems(FXCollections.observableArrayList(veterinarioDAO.listarTodos()));
        cbAsistente.setItems(FXCollections.observableArrayList(asistenteDAO.listarTodos()));
        lvPaquetes.setItems(FXCollections.observableArrayList(paqueteDAO.listarTodos()));

        Button btnRegistrar = new Button("Registrar Cita");

        btnRegistrar.setOnAction(e -> {
            try {
                Cita cita = new Cita();
                cita.setCliente(cbCliente.getValue());
                cita.setMascota(cbMascota.getValue());

                LocalDate fecha = datePicker.getValue();
                LocalTime hora = LocalTime.parse(timeField.getText());
                cita.setFechaHora(java.sql.Timestamp.valueOf(LocalDateTime.of(fecha, hora)));

                cita.setVeterinario(cbVeterinario.getValue());
                cita.setAsistente(cbAsistente.getValue());
                cita.setDescripcion(descripcionArea.getText());
                cita.setPaquetes(new ArrayList<>(lvPaquetes.getSelectionModel().getSelectedItems()));

                citaService.agendarCita(cita);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cita registrada exitosamente.");
                alert.show();
            } catch (Exception ex) {
                Alert error = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                error.show();
            }
        });

        VBox layout = new VBox(10,
            new Label("Cliente"), cbCliente,
            new Label("Mascota"), cbMascota,
            new Label("Fecha"), datePicker,
            new Label("Hora (HH:MM)"), timeField,
            new Label("Veterinario"), cbVeterinario,
            new Label("Asistente"), cbAsistente,
            new Label("Descripción"), descripcionArea,
            new Label("Paquetes"), lvPaquetes,
            btnRegistrar
        );

        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 400, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Registrar Cita");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
