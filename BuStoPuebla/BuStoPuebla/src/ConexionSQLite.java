import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {
    @SuppressWarnings("CallToPrintStackTrace")
    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el driver JDBC para SQLite
            Class.forName("org.sqlite.JDBC");
            // Ruta a la base de datos SQLite
            String url = "jdbc:sqlite:administrador.db";
            conexion = DriverManager.getConnection(url);
            System.out.println("Conexión exitosa a SQLite");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver SQLite");
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos");
            e.printStackTrace();
        }
        return conexion;
    }

    public static void main(String[] args) {
        conectar();
    }
}