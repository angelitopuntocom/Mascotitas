/*
 * @author jariv
 */
import java.util.*;

public class Mascotitas {
    /**
     * @param args the command line arguments
     */
    // Enum de sucursales con método para obtener por índice
    enum Sucursal {
        CENTRO, SUR, NORTE, PONIENTE, ORIENTE;

        public static Sucursal fromInt(int opcion) {
            if (opcion >= 1 && opcion <= values().length) {
                return values()[opcion - 1];
            } else {
                return null;
            }
        }

        public static void mostrarSucursales() {
            for (int i = 0; i < values().length; i++) {
                System.out.println((i + 1) + ". " + values()[i]);
            }
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
        Sucursal sucursalActual = null;

        // Elegir sucursal al inicio
        System.out.println("Seleccione la sucursal en la que desea operar:");
        Sucursal.mostrarSucursales();
        int opcionSucursal = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        sucursalActual = Sucursal.fromInt(opcionSucursal);

        if (sucursalActual == null) {
            System.out.println("Sucursal inválida. Saliendo del sistema.");
            return;
        }

        // Colecciones para adopciones
        ArrayList<Mascota> mascotasDisponibles = new ArrayList<>();
        HashMap<Cliente, Mascota> adopciones = new HashMap<>();

        int opcion;
        do {
            System.out.println("\nMenú:");
            System.out.println("1. Alta de cliente");
            System.out.println("2. Alta de mascota");
            System.out.println("3. Alta y Baja de veterinarios o asistente personal");
            System.out.println("4. Alta de gerente en sucursal");
            System.out.println("5. Registro de citas de veterinarios a domicilio");
            System.out.println("6. Alta de paquetes (cortes, baño, desparasitación, esterilización, etc.)");
            System.out.println("7. Adopción o devolución de mascotas");
            System.out.println("8. Pago de paquetes (cortes, baño, desparasitación, esterilización, etc.)");
            System.out.println("9. Consulta de citas de veterinarios");
            System.out.println("10. Consulta de paquetes");
            System.out.println("11. Consulta de adopciones");
            System.out.println("12. Consulta de veterinarios");
            System.out.println("13. Escritura a archivo de citas dar opción todas o de una fecha en específico.");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    // Alta de cliente
                    break;
                case 2:
                    // Alta de mascota
                    break;
                case 3:
                    // Alta y baja de veterinario/asistente
                    break;
                case 4:
                    // Alta de gerente
                    break;
                case 5:
                    // Registro de citas
                    break;
                case 6:
                    // Alta de paquetes
                    break;
                case 7:
                    // Adopción o devolución
                    System.out.println("1. Adopción");
                    System.out.println("2. Devolución");
                    System.out.print("Seleccione una opción: ");
                    int subOpcion = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer

                    if (subOpcion == 1) {
        		// --- Adopción ---
        		if (mascotasDisponibles.isEmpty()) {
                            System.out.println("No hay mascotas disponibles para adopción.");
                            break;
        		}

        		// Mostrar mascotas disponibles
        		System.out.println("Mascotas disponibles para adopción:");
        		for (int i = 0; i < mascotasDisponibles.size(); i++) {
                            System.out.println((i + 1) + ". " + mascotasDisponibles.get(i).nombre);
        		}

        		// Elegir mascota
        		System.out.print("Seleccione el número de la mascota a adoptar: ");
        		int indice = scanner.nextInt() - 1;
        		scanner.nextLine(); // Limpiar buffer

        		if (indice < 0 || indice >= mascotasDisponibles.size()) {
                            System.out.println("Selección inválida.");
                            break;
        		}

        		Mascota mascotaSeleccionada = mascotasDisponibles.get(indice);

        		// Crear cliente adoptante (versión simplificada)
        		Cliente cliente = new Cliente();
        		System.out.print("Nombre del cliente: ");
        		cliente.nombre = scanner.nextLine();
        		// Aquí podrías capturar más datos si lo deseas

        		// Asociar y actualizar
        		adopciones.put(cliente, mascotaSeleccionada);
        		mascotasDisponibles.remove(indice);

        		System.out.println("La mascota " + mascotaSeleccionada.nombre + " ha sido adoptada por " + cliente.nombre + ".");
                    } else if (subOpcion == 2) {
        		// --- Devolución ---
        		if (adopciones.isEmpty()) {
                            System.out.println("No hay adopciones registradas.");
                            break;
                        }

        		System.out.print("Nombre del cliente que devuelve: ");
        		String nombreCliente = scanner.nextLine();

        		Cliente clienteDevuelve = null;

        		// Buscar cliente por nombre
        		for (Cliente c : adopciones.keySet()) {
                            if (c.nombre.equalsIgnoreCase(nombreCliente)) {
                		clienteDevuelve = c;
                		break;
                            }
        		}

        		if (clienteDevuelve == null) {
                            System.out.println("Cliente no encontrado.");
                            break;
        		}

        		// Obtener mascota y actualizar colecciones
        		Mascota mascotaDevuelta = adopciones.remove(clienteDevuelve);
        		mascotasDisponibles.add(mascotaDevuelta);

        		System.out.println("La mascota " + mascotaDevuelta.nombre + " ha sido devuelta a la lista de adopciones.");
                    } else {
        		System.out.println("Opción inválida.");
                    }
                    break;
                case 8:
                    // Pago de paquetes
                    break;
                case 9:
                    // Consulta de citas
                    break;
                case 10:
                    // Consulta de paquetes
                    break;
                case 11:
                    // Consulta de adopciones
                    break;
                case 12:
                    // Consulta de veterinarios
                    break;
                case 13:
                    // Escritura a archivo
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }   
}
