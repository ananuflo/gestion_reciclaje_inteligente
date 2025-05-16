package EcoGestion;

/**
 * Clase que hereda de la clase abstracta Material con sus atributos
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */
public class Plastico extends Material{
	
	private boolean esReciclable;

	/**
	 * Constructor completo con atributos heredados y su atributo propio
	 * @param nombre
	 * @param descripcion
	 * @param esReciclable Permite saber si es o no reciclable
	 */
	public Plastico(String nombre, String descripcion, boolean esReciclable) {
		super(nombre, descripcion);
		this.esReciclable = esReciclable;
	}
	
	/**
	 * Constructor solo con el ID de plástico
	 * @param id
	 */
	public Plastico(int id) {
		super(id);
	}

	/**
	 * Constructor vacío
	 */
	public Plastico() {
	}


	public boolean isReciclable() {
		return esReciclable;
	}

	public String descripcion() {
        return "Plástico: " + nombre + " - " + (esReciclable ? "Reciclable" : "No reciclable");
    }

	
}
