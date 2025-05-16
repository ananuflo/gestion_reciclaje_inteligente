package EcoGestion;

import java.time.LocalDate;

/**
 * Clase de las alertas de contenedores con sus atributos
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */

public class AlertaContenedor {
	
	private final int TAM_mensaje= 250;
	
	private int id;
	private PuntoReciclaje punto;
	private LocalDate fechaAlert;
	private int nivelOcupacion;
	private String mensaje;
	
	/**
	 * Constructor completo
	 * @param id
	 * @param punto
	 * @param fechaAlert
	 * @param nivelOcupacion
	 * @param mensaje
	 */
	public AlertaContenedor(int id, PuntoReciclaje punto, LocalDate fechaAlert, int nivelOcupacion, String mensaje) {
		this.id = id;
		this.punto = punto;
		this.fechaAlert = fechaAlert;
		this.nivelOcupacion = nivelOcupacion;
		this.setMensaje(mensaje);
	}

	
	/**
	 * Constructor con el id de la alerta
	 * @param id
	 */
	public AlertaContenedor(int id) {
		super();
		this.id = id;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PuntoReciclaje getPunto() {
		return punto;
	}

	public void setPunto(PuntoReciclaje punto) {
		this.punto = punto;
	}

	public LocalDate getFechaAlert() {
		return fechaAlert;
	}

	public void setFechaAlert(LocalDate fechaAlert) {
		this.fechaAlert = fechaAlert;
	}

	public int getNivelOcupacion() {
		return nivelOcupacion;
	}

	public void setNivelOcupacion(int nivelOcupacion) {
		this.nivelOcupacion = nivelOcupacion;
	}

	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Establece el mensaje truncado si excede el límite de carácteres permitido
	 * @param mensaje El mensaje a establecer según si es mayor o menor al límite
	 */
	public void setMensaje(String mensaje) {
		if(mensaje.length() > TAM_mensaje) {
			this.mensaje = mensaje.substring(0, TAM_mensaje);
			System.out.println("Mensaje demasiado larga, se truncará a 100 carácteres.");
		}else {
			this.mensaje = mensaje;

		}
	}
	
	

}
