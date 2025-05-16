package EcoGestion;

/**
 * Clase para los puntos de reciclaje con sus atributos
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */
public class PuntoReciclaje {

	private final int TAM_direccion=100;
	private final int TAM_barrio=50;
	private final int TAM_horario=100;

	
	private int id;
	private String direccion;
	private String barrio;
	private String horario;
	private int capacidadKg;
	
	/**
	 * Constructor completo
	 * @param direccion
	 * @param barrio
	 * @param horario
	 * @param capacidadKg
	 */
	public PuntoReciclaje(String direccion, String barrio, String horario, int capacidadKg) {
		this.setDireccion(direccion);
		this.setBarrio(barrio);
		this.setHorario(horario);
		this.capacidadKg = capacidadKg;
	}
	
	
	/**
	 * Constructor con el ID del punto de reciclaje
	 * @param id
	 */
	public PuntoReciclaje(int id) {
		super();
		this.id = id;
	}



	public PuntoReciclaje() {
		super();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	/**
	 * Establece el mensaje truncado si excede el límite de carácteres permitido
	 * @param direccion 
	 */
	public void setDireccion(String direccion) {
		if(direccion.length() > TAM_direccion) {
			this.direccion = direccion.substring(0, TAM_direccion);
			System.out.println("Descripción demasiado larga, se truncará a 100 carácteres.");
		}else {
			this.direccion = direccion;

		}
	}

	public String getBarrio() {
		return barrio;
	}

	/**
	 * Establece el mensaje truncado si excede el límite de carácteres permitido
	 * @param barrio
	 */
	public void setBarrio(String barrio) {
		if(barrio.length() > TAM_barrio) {
			this.barrio = barrio.substring(0, TAM_barrio);
			System.out.println("Nombre del barrio demasiado largo, se truncará a 100 carácteres.");
		}else {
			this.barrio = barrio;

		}
	}
	public String getHorario() {
		return horario;
	}

	/**
	 * Establece el mensaje truncado si excede el límite de carácteres permitido
	 * @param horario
	 */
	public void setHorario(String horario) {
	    if (horario.length() > TAM_horario) {
	        this.horario = horario.substring(0, TAM_horario);
	        System.out.println("Horario demasiado largo, se truncará a 100 caracteres.");
	    } else {
	        this.horario = horario;
	    }
	}

	public int getCapacidadKg() {
		return capacidadKg;
	}

	public void setCapacidadKg(int capacidadKg) {
		this.capacidadKg = capacidadKg;
	}

	@Override
	public String toString() {
		return "PuntoReciclaje [id=" + id + ", direccion=" + direccion + ", barrio=" + barrio + ", horario=" + horario
				+ ", capacidadKg=" + capacidadKg + "]";
	}
	
	
	
}
