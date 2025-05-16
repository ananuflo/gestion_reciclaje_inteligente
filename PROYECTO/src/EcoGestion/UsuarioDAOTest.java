package EcoGestion;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDAOTest {
    private static UsuarioDAO usuarioDAO;
    private static Connection testConn;
    private static int testUserId; // Para almacenar el ID del usuario de prueba

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Configurar conexión usando los mismos parámetros que UsuarioDAO
        String url = "jdbc:mysql://127.0.0.1/gestion_reciclaje";
        String usuario = "root";
        String password = "root";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        testConn = DriverManager.getConnection(url, usuario, password);
        
        // Configurar UsuarioDAO para pruebas
        usuarioDAO = new UsuarioDAO() {
            private Connection conectar() {
                return testConn; // Usamos nuestra conexión de prueba
            }
        };
        
        // Limpiar y preparar datos de prueba
        prepararDatosPrueba(testConn);
    }

    private static void prepararDatosPrueba(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Insertar usuario de prueba
            stmt.executeUpdate(
                "INSERT INTO usuario (nombre, email, barrio, puntosBonificacion) VALUES " +
                "('Usuario Test', 'test@test.com', 'Centro', 100)",
                Statement.RETURN_GENERATED_KEYS);
            
            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                testUserId = rs.getInt(1);
            }
        }
    }

    @Test
    void testCreateUsuario() {
        Usuario nuevoUsuario = new Usuario(
            "Nuevo Usuario", 
            "nuevo@test.com", 
            "Norte", 
            50
        );
        
        usuarioDAO.create(nuevoUsuario);
        
        // Verificación mejorada
        Usuario encontrado = usuarioDAO.buscarPorCorreo("nuevo@test.com");
        assertNotNull(encontrado, "El usuario debería existir después de crearlo");
        assertEquals("Nuevo Usuario", encontrado.getNombre());
        assertEquals(50, encontrado.getPuntosBonificacion());
    }

    @Test
    void testBuscarPorCorreo() {
        Usuario usuario = usuarioDAO.buscarPorCorreo("test@test.com");
        
        assertNotNull(usuario, "Debería encontrar el usuario de prueba");
        assertEquals("Usuario Test", usuario.getNombre());
        assertEquals(100, usuario.getPuntosBonificacion());
    }

    @Test
    void testBuscarPorId() {
        Usuario usuario = usuarioDAO.buscarPorId(testUserId);
        
        assertNotNull(usuario, "Debería encontrar el usuario por ID");
        assertEquals("test@test.com", usuario.getEmail());
    }

    @Test
    void testObtenerTodos() {
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
        
        assertFalse(usuarios.isEmpty(), "Debería haber al menos un usuario");
        assertTrue(usuarios.size() >= 1, "Debería incluir el usuario de prueba");
    }

    void testActualizarPuntos() {
        // Crear usuario de prueba específico para este test
        Usuario usuarioPuntos = new Usuario(
            "Usuario Puntos", 
            "puntos@test.com", 
            "Sur", 
            100
        );
        usuarioDAO.create(usuarioPuntos);
        
        // Obtener el ID del usuario recién creado
        Usuario tmp = usuarioDAO.buscarPorCorreo("puntos@test.com");
        assertNotNull(tmp);
        usuarioPuntos.setId(tmp.getId()); // Asumimos que Usuario tiene setId()
        
        // Actualizar puntos
        int nuevosPuntos = 200;
        usuarioPuntos.setPuntosBonificacion(nuevosPuntos);
        usuarioDAO.actualizarPuntos(usuarioPuntos);
        
        // Verificar
        Usuario actualizado = usuarioDAO.buscarPorId(usuarioPuntos.getId());
        assertEquals(nuevosPuntos, actualizado.getPuntosBonificacion());
    }

    @AfterEach
    void limpiarDatos() throws SQLException {
        // Limpiar solo los datos de prueba que creamos (excepto el usuario de prueba principal)
        try (Statement stmt = testConn.createStatement()) {
            stmt.executeUpdate("DELETE FROM usuario WHERE email LIKE '%@test.com' AND id != " + testUserId);
        }
    }

    @AfterAll
    static void tearDownAfterClass() throws SQLException {
        // Limpiar todos los datos de prueba
        try (Statement stmt = testConn.createStatement()) {
            stmt.executeUpdate("DELETE FROM usuario WHERE email LIKE '%@test.com'");
        }
        
        if (testConn != null && !testConn.isClosed()) {
            testConn.close();
        }
    }
}
