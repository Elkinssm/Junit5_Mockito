package org.elkinsm.junit5app.ejemplos.models;

import org.elkinsm.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {
    Cuenta cuenta;

    //Garantizamos que se ejecuta de primero que todo
    @BeforeEach
    void initMetodoTest() {
        this.cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));

        System.out.println("Iniciando el metodo.");
    }

    //Garantizamos que se ejecuta de al final de todo
    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el metodo de prueb");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando el test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando el test");
    }

    @Test
    @DisplayName("Probando el nombre de la cuenta corriente!")
    void testNombreCuenta() {
//        Cuenta cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
//        cuenta.setPersona("Elkin");
        String esperado = "Elkin";
        String real = cuenta.getPersona();
        assertNotNull(real, () -> "La cuenta no puede ser nula");
        assertEquals(esperado, real, () -> "El nombre del acuenta no es el que se esperaba");
        assertTrue(real.equals("Elkin"), () -> "Nombre de la cuenta esperada debe ser igual al real");
    }

    @Test
    @DisplayName("Probando el saldo de la cuenta correinte, que no sea null , mayor que cero , valor esperado")
    void testSaldoCuenta() {
//        cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Testeando referencias que sean iguales con el metodo equals.")
    void testReferenciaCuenta() {
        cuenta = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));

        // assertNotEquals(cuenta2, cuenta);
        assertEquals(cuenta2, cuenta);
    }

    @Test
    void testDebitoCuenta() {
        cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }


    @Test
    void testCreditoCuenta() {
        //Cuenta cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
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
        //   Cuenta cuenta = new Cuenta("Elkin", new BigDecimal("1000.12345"));
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
    @Disabled
    @Test
    @DisplayName("Probando relaciones entre las cuentas y el banco con assertAll")
    void testRelacionBancoCuentas() {
        fail();
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Elkin", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertAll(() -> {
            assertEquals("1000.8989", cuenta2.getSaldo().toPlainString()
                    , () -> "El valor del saldo de cuenta2 no es el esperado"
            );
        }, () -> {
            assertEquals("3000", cuenta1.getSaldo().toPlainString()
                    , () -> "El valor del saldo de cuenta1 no es el esperado"
            );
        }, () -> {
            //Filtramos donde la cuenta pertenesca a la persona con nomre expected
            assertEquals(2, banco.getCuentas().size()
                    , () -> "El banco no tiene las cuentas esperadas"
            );
        }, () -> {
            assertEquals("Banco del Estado", cuenta1.getBanco().getNombre());
        }, () -> {
            assertEquals("Elkin", banco.getCuentas().stream()
                    .filter(c -> c.getPersona().equals("Elkin"))
                    .findFirst()
                    .get().getPersona());
        }, () -> {
            //Si exsite algun match o coincidencia con "Elkin" o "Jhon Doe"
            assertTrue(banco.getCuentas().stream()
                    .anyMatch(c -> c.getPersona().equals("Jhon Doe")));

        });
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows() {

    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void testSoloLinuxMac() {

    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows() {

    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void soloJdk8() {

    }

    @Test
    @EnabledOnJre(JRE.JAVA_15)
    void soloJDK15() {
    }

    @Test
    @DisabledOnJre(JRE.JAVA_15)
    void testNoJdk15() {
    }
}