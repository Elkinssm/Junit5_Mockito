package org.elkinsm.junit5app.ejemplos.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
//        cuenta.setPersona("Elkin");
        String esperado = "Elkin";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
        assertTrue(real.equals("Elkin"));
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }
}