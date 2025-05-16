package EcoGestion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MaterialTest {

	@Test
	void testConstructor() {
	    String nombre = "Papel";
	    String descripcion = "Material reciclable procedente de árboles";
	    String tipo = "folio";

	    // Crear una instancia de Papel en lugar de Material
	    Material material = new Papel(nombre, descripcion, tipo);

	    Assertions.assertEquals(nombre, material.getNombre());
	    Assertions.assertEquals(descripcion, material.getDescripcion());
	}

	@Test
	void testSetNombre() {
	    // Crear una instancia de Papel en lugar de Material
	    Material material = new Papel("Papel","Material reciclable pocedente de árboles", "folio A4");
	    String nombreLargo = "Este es un nombre demasiado largo para ser almacenado en la base de datos, ya que supera el límite de 50 caracteres establecido en la clase Material.";
	    material.setNombre(nombreLargo);
	    Assertions.assertEquals(50, material.getNombre().length());
	}

	@Test
	void testSetDescripcion() {
	    // Crear una instancia de Papel en lugar de Material
	    Material material = new Papel("Papel","Material reciclable pocedente de árboles", "folio A4");
	    String descripcionLarga = "Esta es una descripción demasiado larga para ser almacenada en la base de datos, ya que supera el límite de 100 caracteres establecido en la clase Material.";
	    material.setDescripcion(descripcionLarga);
	    Assertions.assertEquals(100, material.getDescripcion().length());
	}

}

