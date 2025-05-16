package EcoGestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.*;

/**
 * Clase DAO para la gestión de operaciones CRUD de la clase ActividadReciclaje
 * Utiliza JDBC para conexiones SQL y PreparedStatement para evitar inyecciones
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */

public class ActividadDAO {
	
	static Connection conn;
	private final static String USUARIO= "root";
	private final static String PASSWORD= "root";
	private final static String MAQUINA= "127.0.0.1";
	private final static String BD= "gestion_reciclaje";
	private MaterialDAO materialDAO;
	
	
	public ActividadDAO() {
		
		  this.conn = conectar();
	        this.materialDAO = new MaterialDAO(); // Inicializamos MaterialDAO
	    }

	
	private Connection conectar() {
		Connection con = null;
		String url = "jdbc:mysql://" + MAQUINA + "/" + BD;
		try {
			con = DriverManager.getConnection(url, USUARIO, PASSWORD);
			System.out.println("ActividadDAO CONECTADO A LA BASE DE DATOS: " + BD);
		} catch (SQLException e) {
			System.err.println("ERROR AL CONECTAR AL SGBD");
		}
		return con;
	}

	/**
	 * Método que registra una nueva actividad de reciclaje
	 * @param actividad La actividad que se desea registrar
	 */
    public void registrarActividad(ActividadReciclaje actividad) {
    	
        String sql = "INSERT INTO actividades_reciclaje (usuario_id, punto_id, material_id, fecha, peso_kg) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, actividad.getUsuario().getId());
            stmt.setInt(2, actividad.getPunto().getId());
            stmt.setInt(3, actividad.getMaterial().getId());
            stmt.setDate(4, Date.valueOf(actividad.getFecha()));
            stmt.setDouble(5, actividad.getPesoKg());
            
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar actividad: " + e.getMessage());
        }
    }

    /**
     * Obtiene un ranking de usuarios basado en sus actividades de reciclaje
     * @return Un mapa donde la clave es el ID del usuario y el valor es su puntuación
     */
    public Map<Integer, Double> obtenerRankingUsuarios() {
    	
        Map<Integer, Double> ranking = new HashMap<>();
        
        String sql = "SELECT usuario_id, SUM(peso_kg) as total FROM actividades_reciclaje GROUP BY usuario_id ORDER BY total DESC";
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ranking.put(rs.getInt("usuario_id"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ranking: " + e.getMessage());
        }
        return ranking;
    }

    /**
     * Busca las actividades de reciclaje realizadas por un usuario específico
     * @param usuarioId El ID del usuario
     * @return Lista de actividades del usuario
     */
    public List<ActividadReciclaje> buscarPorUsuario(int usuarioId) {
        List<ActividadReciclaje> actividades = new ArrayList<>();
        String sql = "SELECT * FROM actividades_reciclaje WHERE usuario_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ActividadReciclaje act = new ActividadReciclaje();
                act.setPesoKg(rs.getDouble("peso_kg"));
                act.setFecha(rs.getDate("fecha").toLocalDate());
                
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("usuario_id"));
                act.setUsuario(usuario);
                
                PuntoReciclaje punto = new PuntoReciclaje();
                punto.setId(rs.getInt("punto_id"));
                act.setPunto(punto);
                
                // Usamos la instancia materialDAO en lugar del método estático
                Material mat = MaterialDAO.buscarPorId(rs.getInt("material_id"));
                act.setMaterial(mat);
                
                actividades.add(act);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar actividades: " + e.getMessage());
        }
        return actividades;
    }
}
