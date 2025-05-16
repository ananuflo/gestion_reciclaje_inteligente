package EcoGestion;

import java.time.LocalDate;

public class ActividadReciclaje {

	private int id;
	private Usuario usuario;
	private PuntoReciclaje punto;
	private Material material;
	private LocalDate fecha;
	private double pesoKg;
	
	/**
	 * Constructor completo
	 * @param usuario 
	 * @param punto
	 * @param material
	 * @param fecha
	 * @param pesoKg
	 */
	public ActividadReciclaje(Usuario usuario, PuntoReciclaje punto, Material material, LocalDate fecha,
			double pesoKg) {
		this.usuario = usuario;
		this.punto = punto;
		this.material = material;
		this.fecha = fecha;
		this.pesoKg = pesoKg;
	}

		/**
		 * Constructor sin atributos
		 */
	public ActividadReciclaje() {
		super();
	}


	/**
	 * Constructor con el ID de la actividad de reciclaje
	 * @param id
	 */
	public ActividadReciclaje(int id) {
		super();
		this.id = id;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PuntoReciclaje getPunto() {
		return punto;
	}

	public void setPunto(PuntoReciclaje punto) {
		this.punto = punto;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public double getPesoKg() {
		return pesoKg;
	}

	public void setPesoKg(double pesoKg) {
		this.pesoKg = pesoKg;
	}

	@Override
	public String toString() {
		return "ActividadReciclaje [id=" + id + ", usuario=" + usuario + ", punto=" + punto + ", material=" + material
				+ ", fecha=" + fecha + ", pesoKg=" + pesoKg + "]";
	}
	
	
	
}
