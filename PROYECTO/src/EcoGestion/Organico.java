package EcoGestion;

/**
 * Clase que hereda de la clase abstracta Material con sus atributos
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */
public class Organico extends Material{
	
	private String origen;

	/**
	 * Constructor completo con atributos heredados y el propio de la clase
	 * @param nombre
	 * @param descripcion
	 * @param origen
	 */
	public Organico(String nombre, String descripcion, String origen) {
		super(nombre, descripcion);
		this.origen = origen;
	}
	
	
	public Organico(int id) {
		super(id);
	}


	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@Override
	public String toString() {
		return "Organico [origen=" + origen + "]";
	}
	
	

}
