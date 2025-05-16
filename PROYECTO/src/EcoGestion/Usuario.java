package EcoGestion;

/**
 * Clase Usuario con sus atributos 
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */
public class Usuario {

	private final int TAM_nombre=100;
	private final int TAM_email=100;
	private final int TAM_barrio=50;

	
	private int id;
	private String nombre;
	private String email;
	private String barrio;
	private int puntosBonificacion;
	
	/**
	 * Constructor completo con todos sus atributos
	 * @param nombre
	 * @param email
	 * @param barrio
	 * @param puntosBonificacion Puntos que tiene el usuario por realizar actividades de reciclaje
	 */
	public Usuario(String nombre, String email, String barrio, int puntosBonificacion) {
		this.setNombre(nombre);
		this.setEmail(email);
		this.setBarrio(barrio);
		this.puntosBonificacion = 0;
	}

		
	public Usuario(int id) {
		super();
		this.id = id;
	}


	public Usuario() {
		super();
	}


	public int getPuntosBonificacion() {
		return puntosBonificacion;
	}



	public void setPuntosBonificacion(int puntosBonificacion) {
		this.puntosBonificacion = puntosBonificacion;
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

	public void setNombre(String nombre) {
		if(nombre.length() > TAM_nombre) {
			this.nombre = nombre.substring(0, TAM_nombre);
			System.out.println("Nombre demasiado largo, se truncará a 100 carácteres.");
		}else {
			this.nombre = nombre;

		}
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(email.length() > TAM_email) {
			this.email = email.substring(0, TAM_email);
			System.out.println("Email demasiado largo, se truncará a 100 carácteres.");
		}else {
			this.email = email;

		}
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		if(barrio.length() > TAM_barrio) {
			this.barrio = barrio.substring(0, TAM_barrio);
			System.out.println("Nombre del barrio demasiado largo, se truncará a 50 carácteres.");
		}else {
			this.barrio = barrio;

		}
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", barrio=" + barrio + "]";
	}
	
	public void agregarPuntosBonificacion(int puntos) {
        this.puntosBonificacion += puntos; // Añadimos los puntos a la bonificación actual
    }
	
}
