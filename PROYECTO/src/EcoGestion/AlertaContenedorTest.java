package EcoGestion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class AlertaContenedorTest {

    @Test
    void testConstructor() {
        PuntoReciclaje punto = new PuntoReciclaje("Calle Falsa 123", "Barrio Ejemplo", "Lunes a Viernes de 8:00 a 20:00", 1000);
        LocalDate fechaAlert = LocalDate.now();
        int nivelOcupacion = 80;
        String mensaje = "Contenedor casi lleno";

        AlertaContenedor alerta = new AlertaContenedor(1, punto, fechaAlert, nivelOcupacion, mensaje);

        Assertions.assertEquals(1, alerta.getId());
        Assertions.assertEquals(punto, alerta.getPunto());
        Assertions.assertEquals(fechaAlert, alerta.getFechaAlert());
        Assertions.assertEquals(nivelOcupacion, alerta.getNivelOcupacion());
        Assertions.assertEquals(mensaje, alerta.getMensaje());
    }

    @Test
    void testSetMensaje() {
        AlertaContenedor alerta = new AlertaContenedor(1);
        String mensaje = "Este es un mensaje demasiado largo para ser almacenado en la base de datos, ya que supera el límite de 250 caracteres establecido en la clase AlertaContenedor.";
        alerta.setMensaje(mensaje);
        Assertions.assertEquals(250, alerta.getMensaje().length());
    }
}

