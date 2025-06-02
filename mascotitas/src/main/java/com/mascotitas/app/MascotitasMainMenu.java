package com.mascotitas.app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.mascotitas.ui.ClienteControllerFX;


public class MascotitasMainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Botones del menú
        Button btnCliente = new Button("🧍 Registrar Cliente");
        Button btnClienteController = new Button("👨‍⚕️ Registrar Cliente (Controller)");
        Button btnMascota = new Button("🐶 Registrar Mascota");
        Button btnCita = new Button("📅 Registrar Cita");
        Button btnPaquete = new Button("📦 Registrar Paquete");

        btnCliente.setMinWidth(200);
        btnClienteController.setMinWidth(200);
        btnMascota.setMinWidth(200);
        btnCita.setMinWidth(200);
        btnPaquete.setMinWidth(200);

        // Acciones
        btnCliente.setOnAction(e -> new ClientRegister().start(new Stage())); // Reutiliza formulario de cliente
        btnClienteController.setOnAction(e -> new ClienteControllerFX().mostrarVentanaRegistro()); // Abre ventana de registro de client
        btnMascota.setOnAction(e -> new MascotaFXApp().start(new Stage()));
        btnPaquete.setOnAction(e -> new PaqueteFXApp().start(new Stage()));
        btnCita.setOnAction(e -> new CitaFXApp().start(new Stage()));

        VBox layout = new VBox(15, btnCliente, btnClienteController, btnMascota, btnCita, btnPaquete);
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
