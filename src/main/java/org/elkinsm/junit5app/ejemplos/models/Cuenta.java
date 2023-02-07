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
}
