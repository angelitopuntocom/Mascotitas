package com.mascotitas.service;

import com.mascotitas.model.Cliente;
import com.mascotitas.model.Mascota;
import com.mascotitas.model.Tarjeta;
import com.mascotitas.exception.MascotaSinVacunasException;

import java.util.*;

public class AdopcionService {

    // Colección de mascotas disponibles
    private final List<Mascota> mascotasDisponibles;

    // Mapa de adopciones: Cliente -> Mascota
    private final Map<Cliente, Mascota> mascotasAdoptadas;

    public AdopcionService() {
        this.mascotasDisponibles = new ArrayList<>();
        this.mascotasAdoptadas = new HashMap<>();
    }

    public List<Mascota> getMascotasDisponibles() {
        return mascotasDisponibles;
    }

    public Map<Cliente, Mascota> getMascotasAdoptadas() {
        return mascotasAdoptadas;
    }

    /**
     * Registra una adopción si la mascota tiene vacunas
     */
    public void adoptarMascota(Cliente cliente, Mascota mascota) throws MascotaSinVacunasException {
        if (mascota.getVacunas().isEmpty()) {
            throw new MascotaSinVacunasException("No tiene vacunas suministradas.");
        }

        if (!mascotasDisponibles.contains(mascota)) {
            System.out.println("⚠️ La mascota no está disponible para adopción.");
            return;
        }

        mascotasDisponibles.remove(mascota);
        mascotasAdoptadas.put(cliente, mascota);

        System.out.println("✅ Adopción registrada: " + cliente.getNombreCompleto() + " adoptó a " + mascota.getNombre());
    }

    /**
     * Devuelve una mascota y la regresa a la lista de disponibles.
     * Si hubo maltrato, se aplica un cargo.
     */
    public void devolverMascota(Cliente cliente, boolean maltrato, double monto) {
        Mascota mascota = mascotasAdoptadas.remove(cliente);

        if (mascota == null) {
            System.out.println("❌ El cliente no tiene mascotas adoptadas.");
            return;
        }

        mascotasDisponibles.add(mascota);
        System.out.println("🐾 Mascota devuelta: " + mascota.getNombre());

        if (maltrato) {
            Tarjeta tarjeta = cliente.getTarjeta();
            if (tarjeta != null) {
                System.out.println("💳 Se ha cobrado $" + monto + " a la tarjeta del cliente " + cliente.getNombreCompleto());
                // Aquí podrías guardar un registro o descontar saldo simulado
            } else {
                System.out.println("❌ No se pudo cobrar: el cliente no tiene tarjeta registrada.");
            }
        }
    }

    /**
     * Carga mascotas iniciales al sistema
     */
    public void cargarMascotasIniciales(List<Mascota> mascotas) {
        mascotasDisponibles.addAll(mascotas);
    }

    /**
     * Verifica si una mascota está adoptada por un cliente
     */
    public boolean clienteAdopto(Cliente cliente) {
        return mascotasAdoptadas.containsKey(cliente);
    }

    /**
     * Verifica si una mascota está disponible
     */
    public boolean estaDisponible(Mascota mascota) {
        return mascotasDisponibles.contains(mascota);
    }
}
