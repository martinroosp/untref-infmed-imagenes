import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {

	private Connection conexion = null;

	public void connect() throws SQLException {

		try {

			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/info_medica_db";
			conexion = DriverManager.getConnection(url, "postgres",
					"root");

			if (conexion != null) {

				System.out.println("Conexion realizada con exito...\n");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
	  }
	}

	public void save() {

		try {

			connect();

			File file = new File("resources/radiografia_mano.jpg");
			FileInputStream fis = new FileInputStream(file);
			PreparedStatement ps = conexion
					.prepareStatement("INSERT INTO images VALUES (?, ?)");
			ps.setString(1, file.getName());
			ps.setBinaryStream(2, fis, (int) file.length());
			ps.executeUpdate();
			ps.close();
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void read() throws SQLException, IOException {

		PreparedStatement ps = conexion
				.prepareStatement("SELECT * FROM images");
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			while (rs.next()) {
				InputStream is = rs.getBinaryStream(2);
				// use the stream in some way here

				System.out.println(getStringFromInputStream(is));

				is.close();
			}
			rs.close();
		}
		ps.close();
	}

	public void delete() throws SQLException {

		PreparedStatement ps = conexion.prepareStatement("DELETE FROM images");

		ps.executeUpdate();
		ps.close();
	}

	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
	
	public static void main(String[] args) throws SQLException, IOException {
		
		ConnectDB db = new ConnectDB();
		db.connect();
		db.save();
		db.read();
	}
}
