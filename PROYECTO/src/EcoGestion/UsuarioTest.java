package EcoGestion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void testConstructor() {
        String nombre = "John Doe";
        String email = "john.doe@example.com";
        String barrio = "Barrio Ejemplo";
        int puntosBonificacion = 100;

        Usuario usuario = new Usuario(nombre, email, barrio, puntosBonificacion);

        Assertions.assertEquals(nombre, usuario.getNombre());
        Assertions.assertEquals(email, usuario.getEmail());
        Assertions.assertEquals(barrio, usuario.getBarrio());
        Assertions.assertEquals(puntosBonificacion, usuario.getPuntosBonificacion());
    }

    @Test
    void testSetNombre() {
        Usuario usuario = new Usuario();
        String nombreLargo = "Este es un nombre de usuario demasiado largo para ser almacenado en la base de datos, ya que supera el límite de 100 caracteres establecido en la clase Usuario.";
        usuario.setNombre(nombreLargo);
        Assertions.assertEquals(100, usuario.getNombre().length());
    }

    @Test
    void testSetEmail() {
        Usuario usuario = new Usuario();
        String emailLargo = "Este es un email demasiado largo para ser almacenado en la base de datos, ya que supera el límite de 100 caracteres establecido en la clase Usuario.";
        usuario.setEmail(emailLargo);
        Assertions.assertEquals(100, usuario.getEmail().length());
    }

    @Test
    void testSetBarrio() {
        Usuario usuario = new Usuario();
        String barrioLargo = "Este es un nombre de barrio demasiado largo para ser almacenado en la base de datos, ya que supera el límite de 50 caracteres establecido en la clase Usuario.";
        usuario.setBarrio(barrioLargo);
        Assertions.assertEquals(50, usuario.getBarrio().length());
    }
}

