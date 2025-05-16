package EcoGestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.*;

/**
 * Clase DAO para la gestión de operaciones CRUD de la clase PuntoReciclaje
 * Utiliza JDBC para conexiones SQL y PreparedStatement para evitar inyecciones
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */
public class PuntoReciclajeDAO {
	private static Connection conn;
	private final static String USUARIO= "root";
	private final static String PASSWORD= "root";
	private final static String MAQUINA= "127.0.0.1";
	private final static String BD= "gestion_reciclaje";
	
	
	public PuntoReciclajeDAO() {
		
		conn = conectar();
	}

	
	private Connection conectar() {
		Connection con = null;
		String url = "jdbc:mysql://" + MAQUINA + "/" + BD;
		try {
			con = DriverManager.getConnection(url, USUARIO, PASSWORD);
			System.out.println("PuntoReciclajeDAO CONECTADO A LA BASE DE DATOS: " + BD);
		} catch (SQLException e) {
			System.err.println("ERROR AL CONECTAR AL SGBD");
		}
		return con;
	}

	    public PuntoReciclaje buscarPorId(int id) {
	        String sql = "SELECT * FROM puntos_reciclaje WHERE id = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, id);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                return new PuntoReciclaje(	                    
	                    rs.getString("direccion"),
	                    rs.getString("barrio"),
	                    rs.getString("horario"),
	                    rs.getInt("capacidad_Kg")
	                );
	            }
	        } catch (SQLException e) {
	            System.err.println("Error al buscar punto de reciclaje por ID: " + e.getMessage());
	        }
	        return null;
	    }

	    /**
	     * Busca los puntos de reciclaje del sistema 
	     * @return Lista de los puntos de reciclaje existentes
	     */
	    
	    public List<PuntoReciclaje> obtenerTodos() {
	    	
	        List<PuntoReciclaje> puntos = new ArrayList<>();
	        
	        String sql = "SELECT * FROM puntos_reciclaje";
	        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
	            while (rs.next()) {
	                puntos.add(new PuntoReciclaje(	                    
		                    rs.getString("direccion"),
		                    rs.getString("barrio"),
		                    rs.getString("horario"),
		                    rs.getInt("capacidad_Kg")
		                ));
	            }
	        } catch (SQLException e) {
	            System.err.println("Error al obtener puntos de reciclaje: " + e.getMessage());
	        }
	        return puntos;
	    }

	    /**
	     * Inserta un nuevo punto de reciclaje
	     * @param punto 
	     */
	    public void insertarPunto(PuntoReciclaje punto) {
	        String sql = "INSERT INTO puntos_reciclaje (direccion, barrio, horario, capacidad_kg) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, punto.getDireccion());
	            stmt.setString(2, punto.getBarrio());
	            stmt.setString(3, punto.getHorario());
	            stmt.setInt(4, punto.getCapacidadKg());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            System.err.println("Error al insertar punto: " + e.getMessage());
	        }
	    }
	}
