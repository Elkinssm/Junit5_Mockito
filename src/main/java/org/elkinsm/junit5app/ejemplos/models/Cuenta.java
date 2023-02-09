package org.elkinsm.junit5app.ejemplos.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cuenta {
    private String persona;
    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona = persona;
    }

    public void debito(BigDecimal monto) {
        this.saldo = this.saldo.subtract(monto);
    }

    public void credito(BigDecimal monto) {
        this.saldo = this.saldo.add(monto);
    }


}
