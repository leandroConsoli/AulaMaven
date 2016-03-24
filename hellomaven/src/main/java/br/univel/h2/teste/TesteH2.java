package br.univel.h2.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteH2 {

	private Connection con;

	public TesteH2() throws SQLException {

		abrirConexao();

		reset();
		
		create();
		
		read();
		
		update();
		
		delete();

		fecharConexao();
	}

	private void reset() throws SQLException {

		String sql = "DELETE FROM PESSOA";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			int res = ps.executeUpdate();
			System.out.println(res + " registros apagados.");
		}
		
		
	}

	private void abrirConexao() throws SQLException {

		String url = "jdbc:h2:./aulah2";
		String user = "sa";
		String pass = "sa";
		con = DriverManager.getConnection(url, user, pass);

	}

	private void fecharConexao() throws SQLException {
		con.close();
	}

	private void create() throws SQLException {
		PreparedStatement ps = con
				.prepareStatement("INSERT INTO PESSOA (ID, NOME) VALUES (?, ?)");
		ps.setInt(1, 1);
		ps.setString(2, "Hugo");
		
		int res = ps.executeUpdate();
		
		// Aqui não garante que executa o close.
		ps.close();
		
		System.out.println(res + " registros alterados.");

	}

	private void read() {
		
		Statement st = null;
		ResultSet result = null;
		try {
			try {
				st = con.createStatement();
				result = st.executeQuery("SELECT * FROM PESSOA");
				while (result.next()) {
					int id = result.getInt(1);
					String nome = result.getString("nome");
					System.out.println(id + " " + nome);
				}
			} finally {
				if (st != null) st.close();
				if (result != null) result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void update() {

		String sql = "UPDATE PESSOA SET ID = ?, NOME = ? WHERE ID = ?";
	}

	private void delete() {
		
		String sql = "DELETE FROM PESSOA WHERE ID = ?";

	}

	public static void main(String[] args) {
		try {
			new TesteH2();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
