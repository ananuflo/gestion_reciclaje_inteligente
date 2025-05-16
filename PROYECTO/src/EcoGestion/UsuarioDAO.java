package EcoGestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Clase DAO para realizar las operaciones CRUD de la clase Usuario
 * Utiliza JDBC para conexiones SQL y PreparedStatement para evitar inyecciones
 */
public class UsuarioDAO {
	
	private static Connection conn;
	private final static String USUARIO= "root";
	private final static String PASSWORD= "root";
	private final static String MAQUINA= "127.0.0.1";
	private final static String BD= "gestion_reciclaje";
	
	
	public UsuarioDAO() {
		
		conn = conectar();
	}

	
	private Connection conectar() {
		Connection con = null;
		String url = "jdbc:mysql://" + MAQUINA + "/" + BD;
		try {
			con = DriverManager.getConnection(url, USUARIO, PASSWORD);
			System.out.println("UsuarioDAO CONECTADO A LA BASE DE DATOS: " + BD);
		} catch (SQLException e) {
			System.err.println("ERROR AL CONECTAR AL SGBD");
		}
		return con;
	}
	
	/**
	 * Crea un nuevo usuario
	 * @param usuario
	 */
	public void create(Usuario usuario) {
	    if(usuario != null) {
	        String sql = "INSERT INTO usuario(nombre, email, barrio, puntosBonificacion) VALUES(?,?,?,?)";
	        
	        try {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, usuario.getNombre());
	            ps.setString(2, usuario.getEmail());
	            ps.setString(3, usuario.getBarrio());
	            ps.setInt(4, usuario.getPuntosBonificacion());
	            ps.executeUpdate();
	            System.out.println("Usuario registrado correctamente.");
	        } catch(SQLException e) {
	            System.err.println("Error al insertar nuevo usuario: " + e.getMessage());
	        }
	    }
	}
	
	/**
	 * Busca un usuario por su email
	 * @param correo
	 * @return devuelve el usuario, o null si no se ha encontrado 
	 */
	public Usuario buscarPorCorreo(String correo) {
		
		String sql = "SELECT * FROM usuario where email = ?";
			
			try {
				
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, correo);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					return new Usuario(
							rs.getString("nombre"),
							rs.getString("email"),
							rs.getString("barrio"),
							rs.getInt("puntosBonificacion")
						);
				}
				
			}catch(SQLException e) {
				System.err.println("Error en la búsqueda de usuario: " + e.getMessage());
			}
			
			return null;
		}
	
	/**
	 * Obtiene los usuarios que participan en el sistema
	 * @return Lista de los usuarios participantes
	 */
	public List<Usuario> obtenerTodos(){
		
		List<Usuario> usuarios = new ArrayList<>();
		
		String sql = "SELECT * FROM usuario";
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				String nom = rs.getString("nombre");
				String email = rs.getString("email");
				String barrio= rs.getString("barrio");
				int puntos = rs.getInt("puntosBonificacion");
				
				usuarios.add(new Usuario(nom,email,barrio, puntos));
				
			}
			
		}catch(SQLException e) {
			System.out.println("Error" + e.getMessage());
		}
		
		return usuarios;
	}
	
	/**
	 * Busca el usuario por su ID
	 * @param id
	 * @return devuelve el usuario buscado con su información
	 */
	public Usuario buscarPorId(int id) {
		
		String sql = "SELECT * FROM usuario where id = ?";
		Usuario usuario = null;
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String nombre = rs.getString("nombre");
				String email = rs.getString("email");
				String barrio = rs.getString("barrio");
				int puntos = rs.getInt("puntosBonificacion");

				usuario = new Usuario(nombre, email, barrio, puntos);


			}
		}catch(SQLException e) {
			System.out.println("Usuario no encontrado: " + e.getMessage());
		}
		
		return usuario;
	}
	
	/**
	 * Busca entre los usuarios existentes por un ID y lo elimina 
	 * @param id
	 */
	public void eliminarUsuario(int id) {
		String sql = "DELETE FROM usuario where id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			
		}catch(SQLException e) {
			System.err.println("Error al eliminar usuario: " + e.getMessage());
			
		}		
	}
	
	/**
	 * Actualiza la dirección de un usuario buscado por su ID
	 * @param id
	 * @param nuevaDireccion 
	 */
	public void actualizarDireccion(int id, String nuevaDireccion) {
		
        String sql = "UPDATE usuarios SET direccion = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevaDireccion);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar direccion: " + e.getMessage());
        }
    }

	/**
	 * Obtiene el resultado de las actividades de reciclaje por barrio
	 * @return estadística de las actividades
	 */
    public Map<String, Long> obtenerEstadisticasPorBarrio() {
    	
        Map<String, Long> estadisticas = new HashMap<>();
        String sql = "SELECT direccion, COUNT(*) as total FROM usuarios GROUP BY direccion";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                estadisticas.put(rs.getString("direccion"), rs.getLong("total"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
        return estadisticas;
    }
    
    /**
     * Actualiza los puntos de bonificación de los usuarios según vayan obteniendo más
     * @param usuario
     */
    public void actualizarPuntos(Usuario usuario) {
        String sql = "UPDATE usuario SET puntosBonificacion = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuario.getPuntosBonificacion());
            ps.setInt(2, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar los puntos del usuario: " + e.getMessage());
        }
    }
}


