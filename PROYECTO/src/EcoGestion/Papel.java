package EcoGestion;

/**
 * Clase que hereda de la clase abstracta Material con sus atributos
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */
public class Papel extends Material {
	
   private String tipo;

   /**
    * Constructor completo con atributos heredados y su atributo propio
    * @param nombre
    * @param descripcion
    * @param tipo
    */
   public Papel(String nombre, String descripcion, String tipo) {
	super(nombre, descripcion);
	this.tipo = tipo;
}
   

	public Papel(int id) {
		super(id);
}


	public String getTipo() {
	return tipo;
}

	public void setTipo(String tipo) {
	this.tipo = tipo;
}

	@Override
	public String toString() {
		return "Papel [tipo=" + tipo + "]";
	}
   
   
}
