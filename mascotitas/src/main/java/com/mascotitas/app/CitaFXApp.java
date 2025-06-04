package com.mascotitas.app;

import com.mascotitas.model.*;
import com.mascotitas.service.CitaService;
import com.mascotitas.service.PagoService;
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

    private void exportarResumenCita(Cita cita) {
        try {
            String nombreArchivo = "cita_" + cita.getNumeroCita() + ".txt";
            String contenido = "Resumen de Cita\n"
                    + "---------------------\n"
                    + "Número de cita: " + cita.getNumeroCita() + "\n"
                    + "Fecha: " + cita.getFechaHora() + "\n"
                    + "Cliente: " + (cita.getCliente() != null ? cita.getCliente().getNombreCompleto() : "No asignado") + "\n"
                    + "Mascota: " + (cita.getMascota() != null ? cita.getMascota().getNombre() : "No asignada") + "\n"
                    + "Veterinario: " + (cita.getVeterinario() != null ? cita.getVeterinario().getNombre() : "No asignado") + "\n"
                    + "Asistente: " + (cita.getAsistente() != null ? cita.getAsistente().getNombre() : "No asignado") + "\n"
                    + "Descripción: " + cita.getDescripcion() + "\n"
                    + "Paquetes: \n";

            for (Paquete p : cita.getPaquetes()) {
                contenido += "  - " + p.getNombre() + " ($" + p.getPrecio() + ")\n";
            }

            java.nio.file.Files.write(java.nio.file.Paths.get(nombreArchivo), contenido.getBytes());
            System.out.println("Resumen exportado: " + nombreArchivo);
        } catch (Exception e) {
            System.err.println("Error al exportar el resumen de la cita: " + e.getMessage());
        }
    }
    
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

        cbCliente.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Cargar mascotas directamente desde la base de datos
                List<Mascota> mascotasCliente = mascotaDAO.buscarPorClienteId(newVal.getNumeroCliente());
                cbMascota.setItems(FXCollections.observableArrayList(mascotasCliente));
            } else {
                cbMascota.getItems().clear();
            }
        });


        //cbMascota.setItems(FXCollections.observableArrayList(mascotaDAO.listarTodos()));
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
                List<Paquete> paquetesSeleccionados = new ArrayList<>(lvPaquetes.getSelectionModel().getSelectedItems());
                if (paquetesSeleccionados.isEmpty()) {
                    throw new Exception("Debe seleccionar al menos un paquete.");
                }
                cita.setPaquetes(paquetesSeleccionados);


                // VALIDACIÓN DE CLIENTE Y TARJETA
                if (cita.getCliente() == null || cita.getCliente().getTarjeta() == null) {
                    throw new Exception("El cliente o su tarjeta no están definidos.");
                }
                
                citaService.agendarCita(cita);
                Tarjeta tarjeta = cita.getCliente().getTarjeta();
                PagoService pagoService = new PagoService();
                double monto = 500.0;

                if (!pagoService.cobrar(tarjeta, monto)) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "No se pudo procesar el pago con tarjeta.");
                    error.show();
                    return;
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cita registrada y pagada exitosamente.");
                alert.show();
                exportarResumenCita(cita);

            } catch (Exception ex) {
                ex.printStackTrace();
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
