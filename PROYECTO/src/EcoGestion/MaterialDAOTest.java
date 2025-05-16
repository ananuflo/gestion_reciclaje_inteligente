package EcoGestion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

class MaterialDAOTest {
    private static MaterialDAO materialDAO;
    private static Connection testConn;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Usa tus credenciales reales de MySQL
        String url = "jdbc:mysql://127.0.0.1/gestion_reciclaje"; 
        String usuario = "root"; 
        String password = "root"; 
        
        // Registrar driver de MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
        testConn = DriverManager.getConnection(url, usuario, password);
        
        // Limpiar y preparar BD de prueba
        limpiarBD(testConn);
        crearDatosPrueba(testConn);
        
        // Configurar MaterialDAO para pruebas
        materialDAO = new MaterialDAO() {
            private Connection conectar() {
                return testConn; // Usamos nuestra conexión de prueba
            }
        };
    }

    private static void limpiarBD(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Desactiva temporalmente las FK para poder limpiar
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            
            // Limpia todas las tablas (ajusta según tu esquema)
            stmt.execute("TRUNCATE TABLE actividades_reciclaje");
            stmt.execute("TRUNCATE TABLE vidrio");
            stmt.execute("TRUNCATE TABLE papel");
            stmt.execute("TRUNCATE TABLE plastico");
            stmt.execute("TRUNCATE TABLE organico");
            stmt.execute("TRUNCATE TABLE materiales");
            
            // Reactiva las FK
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    private static void crearDatosPrueba(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Insertar datos básicos de prueba
            stmt.executeUpdate("INSERT INTO materiales (nombre) VALUES ('papel')");
            stmt.executeUpdate("INSERT INTO materiales (nombre) VALUES ('vidrio')");
            stmt.executeUpdate("INSERT INTO materiales (nombre) VALUES ('plastico')");
            stmt.executeUpdate("INSERT INTO materiales (nombre) VALUES ('organico')");
            
            // Insertar datos específicos
            stmt.executeUpdate("INSERT INTO papel (material_id, tipo) VALUES (1, 'carton')");
            stmt.executeUpdate("INSERT INTO vidrio (material_id, GrosorMm) VALUES (2, 5)");
            stmt.executeUpdate("INSERT INTO plastico (material_id, esReciclable) VALUES (3, true)");
            stmt.executeUpdate("INSERT INTO organico (material_id, origen) VALUES (4, 'vegetal')");
        }
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        if (testConn != null && !testConn.isClosed()) {
            testConn.close();
        }
    }

    @Test
    void testInsertarMaterial() throws SQLException {
        materialDAO.insertarMaterial("metal");
        
        // Verificar inserción
        try (Statement stmt = testConn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM materiales WHERE nombre = 'metal'")) {
            assertTrue(rs.next(), "El material 'metal' debería existir");
        }
    }

    // Resto de tus tests (sin cambios)...
    @Test
    void testBuscarPorId() { /* ... */ }
    @Test
    void testObtenerTodos() { /* ... */ }
    @Test
    void testObtenerTotalRecicladoPorMaterial() { /* ... */ }
}
