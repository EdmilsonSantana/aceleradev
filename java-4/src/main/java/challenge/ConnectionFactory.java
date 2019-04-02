package challenge;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

	private static final String DB_NAME = "database.sqlite";
	private static final Logger LOG = Logger.getLogger(QuoteDao.class.getName());

	private static URL url;

	static {

		url = Optional.ofNullable(ConnectionFactory.class.getClassLoader().getResource(DB_NAME))
				.orElseThrow(() -> new QueryException("Não foi possível encontrar a base de dados."));
	}

	public static Connection createConnection() {

		try {
			return DriverManager.getConnection(String.format("jdbc:sqlite:%s", url.getPath()));
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			throw new QueryException("Não foi possível obter a conexão com o banco de dados.");
		}
	}

}
