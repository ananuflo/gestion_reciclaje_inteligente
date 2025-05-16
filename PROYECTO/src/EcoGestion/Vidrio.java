package EcoGestion;

/**
 * Clase que hereda de la clase abstracta Material con su atributo
 */
public class Vidrio extends Material{
	
	private int grosorMm;

	/**
	 * Constructor completo con todos los atributos, heredados y el propio de la clase
	 * @param nombre
	 * @param descripcion
	 * @param grosorMm
	 */
	public Vidrio(String nombre, String descripcion, int grosorMm) {
		super(nombre, descripcion);
		this.grosorMm = grosorMm;
	}
	
	/**
	 * Constructor con el ID del material
	 * @param id
	 */
	public Vidrio(int id) {
		super(id);
	}

	/**
	 * Constructor vacío
	 */
	public Vidrio() {
	}


	public int getGrosorMm() {
		return grosorMm; 
		}

    public String descripcion() {
        return "Vidrio: " + nombre + " - Grosor: " + grosorMm + " mm";
    }


	public void setGrosorMm(int i) {
		// TODO Auto-generated method stub
		
	}
    
    
}
 