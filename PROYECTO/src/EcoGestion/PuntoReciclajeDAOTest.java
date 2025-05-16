package EcoGestion;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PuntoReciclajeDAOTest {
    private static PuntoReciclajeDAO puntoDAO;
    private static Connection testConn;
    private static int testPuntoId; // Para almacenar el ID del punto de prueba

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Configurar conexión usando los mismos parámetros que el DAO
        String url = "jdbc:mysql://127.0.0.1/gestion_reciclaje";
        String usuario = "root";
        String password = "root";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        testConn = DriverManager.getConnection(url, usuario, password);
        
        // Configurar PuntoReciclajeDAO para pruebas
        puntoDAO = new PuntoReciclajeDAO() {
            private Connection conectar() {
                return testConn; // Usamos nuestra conexión de prueba
            }
        };
        
        // Limpiar y preparar datos de prueba
        prepararDatosPrueba(testConn);
    }

    private static void prepararDatosPrueba(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Insertar punto de prueba
            stmt.executeUpdate(
                "INSERT INTO puntos_reciclaje (direccion, barrio, horario, capacidad_kg) VALUES " +
                "('Calle Prueba 123', 'Centro', 'L-V 8:00-18:00', 500)",
                Statement.RETURN_GENERATED_KEYS);
            
            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                testPuntoId = rs.getInt(1);
            }
        }
    }

    @Test
    void testBuscarPorId() {
        PuntoReciclaje punto = puntoDAO.buscarPorId(testPuntoId);
        
        assertNotNull(punto, "Debería encontrar el punto por ID");
        assertEquals("Calle Prueba 123", punto.getDireccion());
        assertEquals(500, punto.getCapacidadKg());
    }

    @Test
    void testObtenerTodos() {
        List<PuntoReciclaje> puntos = puntoDAO.obtenerTodos();
        
        assertFalse(puntos.isEmpty(), "Debería haber al menos un punto");
        boolean encontrado = puntos.stream()
            .anyMatch(p -> p.getDireccion().equals("Calle Prueba 123"));
        assertTrue(encontrado, "Debería incluir el punto de prueba");
    }

    @Test
    void testInsertarPunto() {
        // Corregimos el método insertarPunto en el DAO primero
        PuntoReciclaje nuevoPunto = new PuntoReciclaje(
            "Nueva Dirección 456", 
            "Norte", 
            "L-S 9:00-20:00", 
            750
        );
        
        // Ejecutamos
        puntoDAO.insertarPunto(nuevoPunto);
        
        // Verificamos
        List<PuntoReciclaje> puntos = puntoDAO.obtenerTodos();
        boolean insertado = puntos.stream()
            .anyMatch(p -> p.getDireccion().equals("Nueva Dirección 456"));
        assertTrue(insertado, "El nuevo punto debería estar en la lista");
    }

    @AfterEach
    void limpiarDatos() throws SQLException {
        // Limpiar solo los datos de prueba adicionales
        try (Statement stmt = testConn.createStatement()) {
            stmt.executeUpdate("DELETE FROM puntos_reciclaje WHERE direccion LIKE 'Nueva Dirección%'");
        }
    }

    @AfterAll
    static void tearDownAfterClass() throws SQLException {
        // Limpiar todos los datos de prueba
        try (Statement stmt = testConn.createStatement()) {
            stmt.executeUpdate("DELETE FROM puntos_reciclaje WHERE id = " + testPuntoId);
        }
        
        if (testConn != null && !testConn.isClosed()) {
            testConn.close();
        }
    }
}
