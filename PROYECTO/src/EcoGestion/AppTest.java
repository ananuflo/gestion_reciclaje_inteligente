package EcoGestion;

import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private static Connection testConn;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static InputStream originalIn;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Configurar conexión de prueba
        String url = "jdbc:mysql://127.0.0.1/gestion_reciclaje";
        String usuario = "root";
        String password = "root";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        testConn = DriverManager.getConnection(url, usuario, password);
        
        // Preparar datos de prueba
        prepararDatosPrueba(testConn);
        
        // Configurar DAOs para usar la conexión de prueba
        App.bdUsuario = new UsuarioDAO() {
            private Connection conectar() {
                return testConn;
            }
        };
        
        App.bdPunto = new PuntoReciclajeDAO() {
            private Connection conectar() {
                return testConn;
            }
        };
        
        App.bdMaterial = new MaterialDAO() {
            private Connection conectar() {
                return testConn;
            }
        };
        
        App.bdActividad = new ActividadDAO() {
            private Connection conectar() {
                return testConn;
            }
        };
    }

    private static void prepararDatosPrueba(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Limpiar e insertar datos de prueba
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            
            // Usuario de prueba
            stmt.execute("INSERT INTO usuario (nombre, email, barrio, puntosBonificacion) VALUES " +
                       "('Usuario Test', 'test@test.com', 'Centro', 100)");
            
            // Punto de reciclaje de prueba
            stmt.execute("INSERT INTO puntos_reciclaje (direccion, barrio, horario, capacidad_kg) VALUES " +
                       "('Calle Prueba 123', 'Centro', 'L-V 9-18', 500)");
            
            // Materiales de prueba
            stmt.execute("INSERT INTO materiales (nombre, descripcion) VALUES " +
                       "('vidrio', 'Envases de vidrio'), " +
                       "('plastico', 'Botellas plásticas')");
            
            stmt.execute("INSERT INTO vidrio (material_id, GrosorMm) VALUES (1, 5)");
            stmt.execute("INSERT INTO plastico (material_id, esReciclable) VALUES (2, true)");
            
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @AfterAll
    static void tearDownAfterClass() throws SQLException {
        // Limpiar datos de prueba
        try (Statement stmt = testConn.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("DELETE FROM usuario WHERE email LIKE '%@test.com'");
            stmt.execute("DELETE FROM puntos_reciclaje WHERE direccion LIKE '%Prueba%'");
            stmt.execute("DELETE FROM materiales WHERE nombre IN ('vidrio', 'plastico')");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
        
        if (testConn != null && !testConn.isClosed()) {
            testConn.close();
        }
    }

    @Test
    void testRegistrarUsuario() {
        // Simular entrada de usuario
        String input = "Test User\ntestuser@test.com\nNorte\n50\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        App.registrarUsuario();
        
        // Verificar salida
        String output = outContent.toString();
        assertTrue(output.contains("Introduzca los datos del nuevo usuario"));
        
        // Verificar en BD
        Usuario usuario = App.bdUsuario.buscarPorCorreo("testuser@test.com");
        assertNotNull(usuario);
        assertEquals("Test User", usuario.getNombre());
    }

    @Test
    void testRegistrarPunto() {
        String input = "Calle Test 456\nSur\nL-D 8-20\n750\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        App.registrarPunto();
        
        String output = outContent.toString();
        assertTrue(output.contains("Introduzca los datos para el nuevo punto de reciclaje"));
        
        // Verificar en BD
        List<PuntoReciclaje> puntos = App.bdPunto.obtenerTodos();
        boolean encontrado = puntos.stream()
            .anyMatch(p -> p.getDireccion().equals("Calle Test 456"));
        assertTrue(encontrado);
    }

    @Test
    void testBuscarUsuarioCorreo() {
        String input = "test@test.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        App.buscarUsuarioCorreo();
        
        String output = outContent.toString();
        assertTrue(output.contains("Usuario Test"));
    }

    @Test
    void testVerRankingRecicladores() {
        // Insertar actividad de prueba para ranking
        try (Statement stmt = testConn.createStatement()) {
            stmt.execute("INSERT INTO actividades_reciclaje " +
                       "(usuario_id, punto_id, material_id, fecha, peso_kg) VALUES " +
                       "(1, 1, 1, '2023-01-01', 10.5)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        App.verRankingRecicladores();
        
        String output = outContent.toString();
        assertTrue(output.contains("Total reciclado"));
        assertTrue(output.contains("10.5 kg"));
    }

    @Test
    void testVerEstadisticasPorMaterial() {
        App.verEstadisticasPorMaterial();
        
        String output = outContent.toString();
        assertTrue(output.contains("vidrio"));
        assertTrue(output.contains("plastico"));
    }
}
