package org.elkinsm.junit5app.ejemplos.models;

import org.elkinsm.junit5app.ejemplos.exceptions.DineroInsuficienteException;
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
        assertNotNull(real);
        assertEquals(esperado, real);
        assertTrue(real.equals("Elkin"));
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));

        // assertNotEquals(cuenta2, cuenta);
        assertEquals(cuenta2, cuenta);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }


    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    /*
    Vamos a probar que si intendo retirar mas dinero
    del que tengo en la cuenta debe lanzarse la excepcion
     */
    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

    /*
     * Vamos a probar que al hacer un deposito
     * o un retiro se sume o reste el valor
     * segun corresponda
     *
     * */
    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Elkin", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    /*
     * Vamos a probar la relacion de las clases
     * Es decir que un banco puede tener muchas cuentas
     * Pero una cuenta debe tener solo un banco
     * Podemos obtener una cuenta y validar que pertenezca a una persona
     *
     * */
    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Elkin", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());

        //Filtramos donde la cuenta pertenesca a la persona con nomre expected
        assertEquals(2, banco.getCuentas().size());
        assertEquals("Banco del Estado", cuenta1.getBanco().getNombre());
        assertEquals("Elkin", banco.getCuentas().stream()
                .filter(c -> c.getPersona().equals("Elkin"))
                .findFirst()
                .get().getPersona()
        );

        //Si exsite algun match o coincidencia con "Elkin" o "Jhon Doe"
        assertTrue(banco.getCuentas().stream()
                .anyMatch(c -> c.getPersona().equals("Jhon Doe")));

    }

}