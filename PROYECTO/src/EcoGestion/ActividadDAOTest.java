package EcoGestion;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActividadDAOTest {
    private static ActividadDAO actividadDAO;
    private static Connection testConn;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Configurar conexión de prueba
        String url = "jdbc:mysql://127.0.0.1/gestion_reciclaje";
        String usuario = "root";
        String password = "root";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        testConn = DriverManager.getConnection(url, usuario, password);
        
        // Configurar ActividadDAO para pruebas
        actividadDAO = new ActividadDAO() {
            private Connection conectar() {
                return testConn;
            }
        };
        
        // Preparar datos de prueba
        prepararDatosPrueba(testConn);
    }

    private static void prepararDatosPrueba(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Limpiar y preparar tablas
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("TRUNCATE TABLE actividades_reciclaje");
            stmt.execute("TRUNCATE TABLE materiales");
            stmt.execute("TRUNCATE TABLE puntos_reciclaje");
            stmt.execute("TRUNCATE TABLE usuario");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            
            // Insertar datos básicos
            stmt.execute("INSERT INTO usuario (id, nombre, email, barrio, puntosBonificacion) VALUES " +
                       "(1, 'Usuario prueba', 'test@test.com', 'Centro', 100)");
            
            stmt.execute("INSERT INTO puntos_reciclaje (id, direccion, barrio, horario, capacidad_kg) VALUES " +
                       "(1, 'Calle Prueba 123', 'Norte', 'L-V 9-18', 500)");
            
            stmt.execute("INSERT INTO materiales (id, nombre, descripcion) VALUES " +
                       "(1, 'vidrio', 'Envases de vidrio'), " +
                       "(2, 'papel', 'Papel y cartón'), " +
                       "(3, 'plastico', 'Envases plásticos')");
            
            stmt.execute("INSERT INTO vidrio (material_id, GrosorMm) VALUES (1, 5)");
            stmt.execute("INSERT INTO papel (material_id, tipo) VALUES (2, 'carton')");
            stmt.execute("INSERT INTO plastico (material_id, esReciclable) VALUES (3, true)");
            
            stmt.execute("INSERT INTO actividades_reciclaje " +
                       "(usuario_id, punto_id, material_id, fecha, peso_kg) VALUES " +
                       "(1, 1, 1, '2023-01-01', 5.0)");
        }
    }

    @Test
    void testBuscarPorUsuario() {
        List<ActividadReciclaje> actividades = actividadDAO.buscarPorUsuario(1);
        
        assertNotNull(actividades);
        assertEquals(1, actividades.size());
        
        ActividadReciclaje actividad = actividades.get(0);
        assertEquals(1, actividad.getUsuario().getId());
        assertEquals(1, actividad.getPunto().getId());
        assertEquals(1, actividad.getMaterial().getId());
        assertEquals(5.0, actividad.getPesoKg(), 0.01);
        assertEquals(LocalDate.of(2023, 1, 1), actividad.getFecha());
        assertTrue(actividad.getMaterial() instanceof Vidrio);
    }

    @AfterAll
    static void tearDownAfterClass() throws SQLException {
        // Limpiar datos de prueba
        try (Statement stmt = testConn.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("TRUNCATE TABLE actividades_reciclaje");
            stmt.execute("TRUNCATE TABLE vidrio");
            stmt.execute("TRUNCATE TABLE papel");
            stmt.execute("TRUNCATE TABLE plastico");
            stmt.execute("TRUNCATE TABLE materiales");
            stmt.execute("TRUNCATE TABLE puntos_reciclaje");
            stmt.execute("TRUNCATE TABLE usuario");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
        
        if (testConn != null && !testConn.isClosed()) {
            testConn.close();
        }
    }
}