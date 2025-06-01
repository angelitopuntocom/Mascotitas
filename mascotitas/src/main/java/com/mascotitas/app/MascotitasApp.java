package com.mascotitas.app;

import com.mascotitas.model.*;
import com.mascotitas.model.enums.Sucursal;
import com.mascotitas.service.CitaService;
import com.mascotitas.exception.*;

import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Clase principal del sistema Mascotitas
 * Autores: [Agrega aquí los nombres del equipo]
 */
public class MascotitasApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Mascota> mascotasDisponibles = new ArrayList<>();
    private static final List<Paquete> paquetes = new ArrayList<>();
    private static final CitaService citaService = new CitaService();

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n----- MENÚ PRINCIPAL -----");
            System.out.println("1. Alta de cliente");
            System.out.println("2. Alta de mascota");
            System.out.println("3. Alta de paquete");
            System.out.println("4. Registrar cita");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero(scanner, "");

            switch (opcion) {
                case 1 -> altaCliente();
                case 2 -> altaMascota();
                case 3 -> altaPaquete();
                case 4 -> registrarCita();
                case 5 -> System.out.println("Gracias por usar Mascotitas.");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 5);
    }

    private static void altaCliente() {
        System.out.println("\n--- Alta de Cliente ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido paterno (puede ser vacío): ");
        String paterno = scanner.nextLine();

        System.out.print("Apellido materno (puede ser vacío): ");
        String materno = scanner.nextLine();

        System.out.print("CURP: ");
        String curp = scanner.nextLine();

        System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
        Date fechaNacimiento = leerFecha();

        Cliente cliente = new Cliente(nombre, paterno, materno, fechaNacimiento, curp);
        clientes.add(cliente);

        System.out.println("Cliente registrado correctamente.");
    }

    private static void altaMascota() {
        System.out.println("\n--- Alta de Mascota ---");

        System.out.print("Nombre de la mascota: ");
        String nombre = scanner.nextLine();

        System.out.print("Raza: ");
        String raza = scanner.nextLine();

        Mascota mascota = new Mascota(nombre, raza);
        System.out.print("¿Desea agregar vacunas? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Ingrese vacunas separadas por coma: ");
            String[] vacunas = scanner.nextLine().split(",");
            for (String vacuna : vacunas) {
                mascota.agregarVacuna(vacuna.trim());
            }
        }

        mascotasDisponibles.add(mascota);
        System.out.println("Mascota registrada correctamente.");
    }

    private static void altaPaquete() {
        System.out.println("\n--- Alta de Paquete ---");

        System.out.print("Nombre del paquete: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();

        Paquete paquete = new Paquete(nombre, descripcion, precio);
        paquetes.add(paquete);

        System.out.println("Paquete agregado exitosamente.");
    }

    private static void registrarCita() {
        try {
            System.out.println("\n--- Registrar Cita ---");

            if (clientes.isEmpty() || mascotasDisponibles.isEmpty()) {
                System.out.println("Debe haber al menos un cliente y una mascota disponible.");
                return;
            }

            Cliente cliente = seleccionarCliente();
            Mascota mascota = seleccionarMascota();

            System.out.print("Fecha y hora de la cita (dd/MM/yyyy HH:mm): ");
            Date fecha = leerFechaHora();

            System.out.print("Descripción del servicio: ");
            String descripcion = scanner.nextLine();

            Cita cita = new Cita(fecha, cliente, mascota, descripcion);

            System.out.println("Seleccione los paquetes contratados (0 para finalizar):");
            mostrarPaquetes();

            int seleccion;
            do {
                seleccion = leerEntero(scanner, "ID de paquete (1-n, 0 para finalizar): ");
                if (seleccion > 0 && seleccion <= paquetes.size()) {
                    cita.agregarPaquete(paquetes.get(seleccion - 1));
                } else if (seleccion != 0) {
                    System.out.println("ID fuera de rango.");
                }
            } while (seleccion != 0);

            citaService.agendarCita(cita);
            System.out.println("Cita registrada exitosamente.");

        } catch (CitaOcupadaException | MascotaNoVacunadaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private static Cliente seleccionarCliente() {
        System.out.println("Seleccione un cliente:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getNombre());
        }
        int index = leerEntero(scanner, "Número del cliente: ") - 1;
        return clientes.get(index);
    }

    private static Mascota seleccionarMascota() {
        System.out.println("Seleccione una mascota disponible:");
        for (int i = 0; i < mascotasDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + mascotasDisponibles.get(i));
        }
        int index = leerEntero(scanner, "Número de la mascota: ") - 1;
        return mascotasDisponibles.get(index);
    }

    private static void mostrarPaquetes() {
        for (int i = 0; i < paquetes.size(); i++) {
            System.out.println((i + 1) + ". " + paquetes.get(i));
        }
    }

    private static Date leerFecha() {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Formato incorrecto, intente de nuevo.");
            return leerFecha();
        }
    }

    private static Date leerFechaHora() {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Formato incorrecto, intente de nuevo.");
            return leerFechaHora();
        }
    }

    public static int leerEntero(Scanner scanner, String mensaje) {
        int numero = -1;
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                scanner.nextLine(); // limpiar el buffer
                break;
            } else {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
                scanner.nextLine(); // limpiar entrada inválida
            }
        }
        return numero;
    }
}
