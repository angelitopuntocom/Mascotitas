package com.mascotitas.model;

import jakarta.persistence.*;
import java.util.Date;

/*
 * Clase: Tarjeta
 * Representa una tarjeta de crédito o débito vinculada a un cliente
 */
@Entity
@Table(name = "tarjetas")
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private long numero;

    @Temporal(TemporalType.DATE)
    private Date vencimiento;

    @Column(nullable = false)
    private short cvc;

    public Tarjeta() {}

    public Tarjeta(long numero, Date vencimiento, short cvc) {
        this.numero = numero;
        this.vencimiento = vencimiento;
        this.cvc = cvc;
    }

    public int getId() { return id; }

    public long getNumero() { return numero; }
    public void setNumero(long numero) { this.numero = numero; }

    public Date getVencimiento() { return vencimiento; }
    public void setVencimiento(Date vencimiento) { this.vencimiento = vencimiento; }

    public short getCvc() { return cvc; }
    public void setCvc(short cvc) { this.cvc = cvc; }

    @Override
    public String toString() {
        return "**** " + String.valueOf(numero).substring(Math.max(0, String.valueOf(numero).length() - 4));
    }
}
