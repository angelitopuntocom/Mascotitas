package com.mascotitas.model;

import jakarta.persistence.*;
import java.util.Date;

/*
 * Clase: Tarjeta
 * Representa una tarjeta de pago asociada al cliente
 */
@Entity
@Table(name = "tarjetas")
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // ID interno de la tabla

    @Column(nullable = false, unique = true)
    private long numero;

    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(nullable = false)
    private short cvc;

    @Column(nullable = false)
    private double saldo;  // Puedes usarlo para simular cargos

    public Tarjeta() {}

    public Tarjeta(long numero, Date fechaVencimiento, short cvc, double saldoInicial) {
        this.numero = numero;
        this.fechaVencimiento = fechaVencimiento;
        this.cvc = cvc;
        this.saldo = saldoInicial;
    }

    public int getId() { return id; }

    public long getNumero() { return numero; }
    public void setNumero(long numero) { this.numero = numero; }

    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public short getCvc() { return cvc; }
    public void setCvc(short cvc) { this.cvc = cvc; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public boolean realizarCargo(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "****" + (numero % 10000) + " - vence: " + fechaVencimiento;
    }
}
