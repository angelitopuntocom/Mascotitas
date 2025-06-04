package com.mascotitas.app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.mascotitas.ui.*;
import javafx.scene.Node;

public class MascotitasMainMenu extends Application {
    private boolean temaOscuro = false;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        Label titulo = new Label("🐾 Sistema Mascotitas");
        titulo.getStyleClass().add("titulo");

        VBox registroBox = crearGrupo(" Registro de Usuarios", new Button[]{
            crearBoton("Registrar Cliente", e -> new ClientRegister().start(new Stage())),
            crearBoton("Registrar Mascota", e -> new MascotaFXApp().start(new Stage())),
            crearBoton("Registrar Tarjeta", e -> new TarjetaFXApp().start(new Stage()))
        });

        VBox citaBox = crearGrupo(" Citas y Servicios", new Button[]{
            crearBoton("Registrar Cita", e -> new CitaFXApp().start(new Stage())),
            crearBoton("Consultar Citas", e -> new ConsultaCitasFXApp().start(new Stage())),
            crearBoton("Registrar Paquete", e -> new PaqueteFXApp().start(new Stage()))
        });

        VBox personalBox = crearGrupo(" Personal Médico y Administrativo", new Button[]{
            crearBoton("Registrar Veterinario", e -> new VeterinarioForm().mostrarFormulario()),
            crearBoton("Registrar Asistente", e -> new AsistenteForm().mostrarFormulario()),
            crearBoton("Registrar Gerente", e -> new AltaGerenteFX().start(new Stage())),
            crearBoton("Eliminar Personal", e -> new EliminarPersonalFXApp().start(new Stage()))
        });

        VBox adopcionBox = crearGrupo(" Adopciones y Devoluciones", new Button[]{
            crearBoton("Registrar Adopción", e -> new AdopcionFXApp().start(new Stage()))
        });

        Button btnTema = crearBoton(" Cambiar Tema", e -> cambiarTema());
        btnTema.getStyleClass().add("boton-cambiar-tema");

        HBox temaBox = new HBox(btnTema);
        temaBox.setAlignment(Pos.TOP_RIGHT);
        temaBox.setPadding(new Insets(0, 10, 10, 0));

        FlowPane panelGrupos = new FlowPane(Orientation.HORIZONTAL, 20, 20);
        panelGrupos.setAlignment(Pos.TOP_CENTER);
        panelGrupos.getChildren().addAll(registroBox, citaBox, personalBox, adopcionBox);

        VBox contenido = new VBox(10, temaBox, titulo, panelGrupos);
        contenido.setAlignment(Pos.TOP_CENTER);
        contenido.setPadding(new Insets(20));

        aplicarFade(contenido);

        StackPane root = new StackPane();
        root.getChildren().add(contenido);

        scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/menu.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Menú Principal - Mascotitas");
        primaryStage.show();
    }

    private Button crearBoton(String texto, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button btn = new Button(texto);
        btn.setMinWidth(250);
        btn.getStyleClass().add("button");
        btn.setOnAction(handler);
        return btn;
    }

    private VBox crearGrupo(String titulo, Button[] botones) {
        Label etiqueta = new Label(titulo);
        etiqueta.getStyleClass().add("seccion");

        VBox box = new VBox(10);
        box.getChildren().add(etiqueta);
        box.getChildren().addAll(botones);
        box.getStyleClass().add("group-box");
        box.setPadding(new Insets(10));
        box.setMaxWidth(300);
        return box;
    }

    private void cambiarTema() {
        String hojaEstilo = temaOscuro ? "/styles/menu.css" : "/styles/menu-dark.css";
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(hojaEstilo).toExternalForm());
        temaOscuro = !temaOscuro;
    }

    private void aplicarFade(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
