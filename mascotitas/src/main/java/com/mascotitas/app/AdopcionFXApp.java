package com.mascotitas.app;

import com.mascotitas.dao.ClienteDAO;
import com.mascotitas.dao.MascotaDAO;
import com.mascotitas.exception.MascotaSinVacunasException;
import com.mascotitas.model.Cliente;
import com.mascotitas.model.Mascota;
import com.mascotitas.model.Tarjeta;
import com.mascotitas.service.AdopcionService;
import com.mascotitas.service.PagoService;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdopcionFXApp extends Application {

    private final AdopcionService adopcionService = new AdopcionService();
    /**
     * Inicia la aplicación JavaFX.
     * Crea la interfaz de usuario para adoptar y devolver mascotas.
     */
    @Override
    public void start(Stage stage) {

        ComboBox<Cliente> cbClientes = new ComboBox<>();
        ClienteDAO clienteDAO = new ClienteDAO();
        cbClientes.setItems(FXCollections.observableArrayList(clienteDAO.listarTodos()));

        ComboBox<Mascota> cbMascotas = new ComboBox<>();
        MascotaDAO mascotaDAO = new MascotaDAO();
        List<Mascota> todasLasMascotas = mascotaDAO.listarTodos();
        List<Mascota> disponibles = todasLasMascotas.stream()
            .filter(m -> m.getCliente() == null)
            .toList();

        adopcionService.cargarMascotasIniciales(disponibles);
        cbMascotas.setItems(FXCollections.observableArrayList(disponibles));


        // 🎛 Controles
        //ComboBox<Cliente> cbClientes = new ComboBox<>(FXCollections.observableArrayList(clientesSimulados));
        //ComboBox<Mascota> cbMascotas = new ComboBox<>(FXCollections.observableArrayList(adopcionService.getMascotasDisponibles()));
        Button btnAdoptar = new Button("Adoptar");
        Button btnDevolver = new Button("Devolver mascota");
        CheckBox cbMaltrato = new CheckBox("Devolución por maltrato");
        TextField tfMonto = new TextField("500");
        TextArea output = new TextArea();
        output.setEditable(false);

        // 🎯 Adoptar acción
        btnAdoptar.setOnAction(e -> {
            Cliente cliente = cbClientes.getValue();
            Mascota mascota = cbMascotas.getValue();

            if (cliente == null || mascota == null) {
                output.appendText("⚠️ Selecciona cliente y mascota.\n");
                return;
            }

            try {
                adopcionService.adoptarMascota(cliente, mascota);

                // 🔽 AQUI VA LA ACTUALIZACIÓN EN LA BASE DE DATOS
                mascota.setCliente(cliente);
                new MascotaDAO().actualizar(mascota); // Esto guarda en la BD que ya fue adoptada

                cbMascotas.setItems(FXCollections.observableArrayList(adopcionService.getMascotasDisponibles()));
                output.appendText("✅ Adopción: " + cliente.getNombre() + " adoptó a " + mascota.getNombre() + "\n");

            } catch (MascotaSinVacunasException ex) {
                output.appendText("❌ " + ex.getMessage() + "\n");
            }
        });


        btnDevolver.setOnAction(e -> {
            Cliente cliente = cbClientes.getValue();
            if (cliente == null) {
                output.appendText("⚠️ Selecciona un cliente.\n");
                return;
            }

            boolean maltrato = cbMaltrato.isSelected();
            double monto;
            try {
                monto = Double.parseDouble(tfMonto.getText());
            } catch (NumberFormatException ex) {
                output.appendText("❌ Monto inválido.\n");
                return;
            }

            if (maltrato) {
                Tarjeta tarjeta = cliente.getTarjeta();
                PagoService pagoService = new PagoService();

                if (!pagoService.cobrar(tarjeta, monto)) {
                    output.appendText("❌ No se pudo procesar el cobro por maltrato.\n");
                    return;
                }
            }

            adopcionService.devolverMascota(cliente, maltrato, monto);
            cbMascotas.setItems(FXCollections.observableArrayList(adopcionService.getMascotasDisponibles()));
            output.appendText("↩️ Devolución procesada para " + cliente.getNombre() + "\n");
        });


        // 🖼 Layout
        VBox root = new VBox(10,
                new Label("Selecciona Cliente:"),
                cbClientes,
                new Label("Selecciona Mascota:"),
                cbMascotas,
                btnAdoptar,
                new Separator(),
                cbMaltrato,
                new HBox(5, new Label("Monto castigo:"), tfMonto),
                btnDevolver,
                new Label("Registro:"),
                output
        );
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 450, 500));
        stage.setTitle("🐾 Adopción y Devolución de Mascotas");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
