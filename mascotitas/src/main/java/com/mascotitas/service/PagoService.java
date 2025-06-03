package com.mascotitas.service;

import com.mascotitas.model.Tarjeta;

public class PagoService {

    public boolean cobrar(Tarjeta tarjeta, double monto) {
        if (tarjeta == null) {
            System.out.println("❌ Cliente no tiene tarjeta registrada.");
            return false;
        }

        if (tarjeta.getSaldo() < monto) {
            System.out.println("❌ Saldo insuficiente en la tarjeta.");
            return false;
        }

        tarjeta.setSaldo(tarjeta.getSaldo() - monto);
        System.out.println("💳 Se cobró $" + monto + ". Nuevo saldo: $" + tarjeta.getSaldo());
        return true;
    }
}
