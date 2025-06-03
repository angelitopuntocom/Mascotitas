package com.mascotitas.app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.mascotitas.ui.ClienteControllerFX;

import com.mascotitas.ui.AsistenteForm;
import com.mascotitas.ui.VeterinarioForm;


public class MascotitasMainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Botones del menú
        Button btnCliente = new Button("🧍 Registrar Cliente");
        Button btnClienteController = new Button("👨‍⚕️ Registrar Cliente (Controller)");
        Button btnMascota = new Button("🐶 Registrar Mascota");
        Button btnCita = new Button("📅 Registrar Cita");
        Button btnPaquete = new Button("📦 Registrar Paquete");
        Button btnVeterinario = new Button("👨‍⚕️ Registrar Veterinario");
        Button btnAsistente = new Button("👩‍⚕️ Registrar Asistente");
        Button btnGerente = new Button("👨‍💼 Registrar Gerente");
        Button btnAdopcion = new Button("🐾 Registrar Adopción");
        Button btnTarjeta = new Button("💳 Registrar Tarjeta");
        Button btnConsultaCitas = new Button("🔍 Consultar Citas");

        btnCliente.setMinWidth(200);
        btnClienteController.setMinWidth(200);
        btnMascota.setMinWidth(200);
        btnCita.setMinWidth(200);
        btnPaquete.setMinWidth(200);
        btnVeterinario.setMinWidth(200);
        btnAsistente.setMinWidth(200);
        btnGerente.setMinWidth(200);
        btnAdopcion.setMinWidth(200);
        btnTarjeta.setMinWidth(200);
        btnConsultaCitas.setMinWidth(200);

        // btnCliente.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        // btnClienteController.setStyle("-fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        // btnMascota.setStyle("-fx-font-size: 16px; -fx-background-color: #FF9800; -fx-text-fill: white;");
        // btnCita.setStyle("-fx-font-size: 16px; -fx-background-color: #9C27B0; -fx-text-fill: white;");
        // btnPaquete.setStyle("-fx-font-size: 16px; -fx-background-color: #3F51B5; -fx-text-fill: white;");
        // btnVeterinario.setStyle("-fx-font-size: 16px; -fx-background-color: #009688; -fx-text-fill: white;");
        // btnAsistente.setStyle("-fx-font-size: 16px; -fx-background-color: #8BC34A; -fx-text-fill: white;");
        // btnGerente.setStyle("-fx-font-size: 16px; -fx-background-color: #FF5722; -fx-text-fill: white;");
        // btnAdopcion.setStyle("-fx-font-size: 16px; -fx-background-color: #795548; -fx-text-fill: white;");
        // btnTarjeta.setStyle("-fx-font-size: 16px; -fx-background-color: #607D8B; -fx-text-fill: white;");
        // btnConsultaCitas.setStyle("-fx-font-size: 16px; -fx-background-color: #9E9E9E; -fx-text-fill: white;");
        // // Acciones
        btnCliente.setOnAction(e -> new ClientRegister().start(new Stage())); // Reutiliza formulario de cliente
        btnClienteController.setOnAction(e -> new ClienteControllerFX().mostrarVentanaRegistro()); // Abre ventana de registro de client
        btnMascota.setOnAction(e -> new MascotaFXApp().start(new Stage()));
        btnPaquete.setOnAction(e -> new PaqueteFXApp().start(new Stage()));
        btnCita.setOnAction(e -> new CitaFXApp().start(new Stage()));
        btnVeterinario.setOnAction(e -> new VeterinarioForm().mostrarFormulario());
        btnAsistente.setOnAction(e -> new AsistenteForm().mostrarFormulario());
        btnGerente.setOnAction(e -> new AltaGerenteFX().start(new Stage()));
        btnAdopcion.setOnAction(e -> new AdopcionFXApp().start(new Stage()));
        btnTarjeta.setOnAction(e -> new TarjetaFXApp().start(new Stage())); // Abre ventana de registro de tarjeta
        btnConsultaCitas.setOnAction(e -> new ConsultaCitasFXApp().start(new Stage())); // Abre ventana de consulta de citas

        VBox layout = new VBox(15, btnCliente, btnClienteController, btnMascota, btnCita, btnPaquete, btnVeterinario, btnAsistente, btnGerente, btnAdopcion, btnTarjeta, btnConsultaCitas);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30; -fx-background-color: linear-gradient(to bottom right, #F3F3F3, #E8EAF6);");

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistema Mascotitas - Menú Principal");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
