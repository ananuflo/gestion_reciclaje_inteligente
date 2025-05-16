package EcoGestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Clase DAO para la gestion de operaciones CRUD de la clase Material
 * Utiliza JDBC para conexiones SQL y PreparedStatement para evitar inyecciones
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */
public class MaterialDAO {
	
	static Connection conn;
	private final static String USUARIO= "root";
	private final static String PASSWORD= "root";
	private final static String MAQUINA= "127.0.0.1";
	private final static String BD= "gestion_reciclaje";
	
	
	public MaterialDAO() {
		
		conn = conectar();
	}

	
	private Connection conectar() {
        try {
            String url = "jdbc:mysql://" + MAQUINA + "/" + BD;
            return DriverManager.getConnection(url, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.err.println("ERROR AL CONECTAR AL SGBD: " + e.getMessage());
            return null;
        }
    }
	
	/**
	 * Inserta un nuevo material en el sistema
	 * @param nombre Nombre del material
	 */
	public void insertarMaterial(String nombre) {
		
        String sql = "INSERT INTO materiales (nombre) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al insertar material: " + e.getMessage());
        }
    }
	
	/**
	 * Busca el material por ID
	 * @param id ID del material
	 * @return Objeto Material correspondiente al ID
	 */
	public static Material buscarPorId(int id) {
        if (conn == null) {
            System.err.println("La conexión no está inicializada");
            return null;
        }
        
        String sql = "SELECT * FROM materiales WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String nombre = rs.getString("nombre").toLowerCase();
                switch(nombre) {
                    case "papel": return new Papel(id);
                    case "vidrio": return new Vidrio(id);
                    case "plastico": return new Plastico(id);
                    case "organico": return new Organico(id);
                    default: return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en buscarPorId: " + e.getMessage());
        }
        return null;
    }
	
	/**
	 * Obtiene todos los materiales
	 * @return Lista de materiales
	 */
	public List<Material> obtenerTodos(){
		
		List<Material> materiales = new ArrayList<>();
		
		String sql = "SELECT * FROM materiales";
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				materiales.add(buscarPorId(rs.getInt("id")));
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
		
		return materiales;
	}
	
	/**
	 * Obtiene el total reciclado para un material específico
	 * @param id ID del material
	 * @return Total reciclado(por ejemplo, en Kg)
	 */
	
	public double obtenerTotalRecicladoPorMaterial(int id) {
	    double totalReciclado = 0;
	    
	    String sql = "SELECT SUM(peso_kg) AS total FROM actividades_reciclaje WHERE material_id = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            totalReciclado = rs.getDouble("total");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al obtener el total reciclado: " + e.getMessage());
	    }

	    return totalReciclado;
	}
}
