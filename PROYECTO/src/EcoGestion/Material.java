package EcoGestion;

/**
 * Clase Material con sus atributos
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */
public abstract class Material {

	private final int TAM_nombre=50;
	private final int TAM_descripcion=100;

	
	protected int id;
	protected String nombre;
	protected String descripcion;
	
	/**
	 * Constructor completo
	 * @param nombre Nombre del material
	 * @param descripcion Descripción del material
	 */
	public Material(String nombre, String descripcion) {
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
	}

	/**
	 * Constructor vacío
	 */
	public Material() {
		super();
	}


	/**
	 * Constructor con el ID del material
	 * @param id ID del material
	 */
	public Material(int id) {
		super();
		this.id = id;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el mensaje truncado si excede el límite de carácteres permitido
	 * @param nombre Nombre del material introducido
	 */
	public void setNombre(String nombre) {
		if(nombre.length() > TAM_nombre) {
			this.nombre = nombre.substring(0, TAM_nombre);
			System.out.println("Nombre demasiado largo, se truncará a 50 carácteres.");
		}else {
			this.nombre = nombre;

		}
	}

	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el mensaje truncado si excede el límite de carácteres permitido
	 * @param descripcion Descripción del material introducido
	 */
	public void setDescripcion(String descripcion) {
		if(descripcion.length() > TAM_descripcion) {
			this.descripcion = descripcion.substring(0, TAM_descripcion);
			System.out.println("Descripcion demasiado larga, se truncará a 100 carácteres.");
		}else {
			this.descripcion = descripcion;

		}
	}

	@Override
	public String toString() {
		return "Material [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
	
	
	
	
}
