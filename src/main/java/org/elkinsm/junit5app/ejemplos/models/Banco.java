package org.elkinsm.junit5app.ejemplos.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Banco {
    private String nombre;
    private List<Cuenta> cuentas = new ArrayList<>();

    public void addCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
        cuenta.setBanco(this);
    }

    public void transferir(Cuenta origen, Cuenta destino, BigDecimal monto) {
        //Retiro
        origen.debito(monto);

        //Deposito
        destino.credito(monto);
    }
}
