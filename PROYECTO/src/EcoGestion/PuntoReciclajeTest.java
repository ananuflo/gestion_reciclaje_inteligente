package EcoGestion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PuntoReciclajeTest {

    @Test
    void testConstructor() {
        String direccion = "Calle Falsa 123";
        String barrio = "Barrio Ejemplo";
        String horario = "Lunes a Viernes de 8:00 a 20:00";
        int capacidadKg = 1000;

        PuntoReciclaje punto = new PuntoReciclaje(direccion, barrio, horario, capacidadKg);

        Assertions.assertEquals(direccion, punto.getDireccion());
        Assertions.assertEquals(barrio, punto.getBarrio());
        Assertions.assertEquals(horario, punto.getHorario());
        Assertions.assertEquals(capacidadKg, punto.getCapacidadKg());
    }

    @Test
    void testSetDireccion() {
        PuntoReciclaje punto = new PuntoReciclaje();
        String direccionLarga = "Esta es una dirección demasiado larga para ser almacenada en la base de datos, ya que supera el límite de 100 caracteres establecido en la clase PuntoReciclaje.";
        punto.setDireccion(direccionLarga);
        Assertions.assertEquals(100, punto.getDireccion().length());
    }

    @Test
    void testSetBarrio() {
        PuntoReciclaje punto = new PuntoReciclaje();
        String barrioLargo = "Este es un nombre de barrio demasiado largo para ser almacenado en la base de datos, ya que supera el límite de 50 caracteres establecido en la clase PuntoReciclaje.";
        punto.setBarrio(barrioLargo);
        Assertions.assertEquals(50, punto.getBarrio().length());
    }

    @Test
    void testSetHorario() {
        PuntoReciclaje punto = new PuntoReciclaje();
        punto.setDireccion("Calle Falsa 123");
        punto.setBarrio("Barrio Ejemplo");
        String horarioLargo = "Este es un horario demasiado largo para ser almacenado en la base de datos, ya que supera el límite de 100 caracteres establecido en la clase PuntoReciclaje.";
        punto.setHorario(horarioLargo);
        Assertions.assertEquals(100, punto.getHorario().length());
    }
}

