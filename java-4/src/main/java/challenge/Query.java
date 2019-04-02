package challenge;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.ClassUtils;

public class Query<T extends Object> {

	private static final Logger LOG = Logger.getLogger(Query.class.getName());

	private PreparedStatement ptmt;

	private Connection conn;

	private Class<T> entityClass;

	private Query(String sql, Class<T> entityClass) {
		this.createPreparedStatement(sql);
		this.entityClass = entityClass;
	}

	public Query<T> setParameter(int index, Object value) {

		try {
			ptmt.setObject(index, value);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			throw new QueryException("Não foi possível adicionar o parâmetro na consulta.");
		}

		return this;
	}

	public Optional<T> uniqueResult() {
		return this.findAll().stream().findFirst();
	}

	public List<T> findAll() {

		ResultSet rs = null;

		List<T> list = new ArrayList<>();

		try {
			rs = this.ptmt.executeQuery();

			ResultSetMetaData metaData = ptmt.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				if (columnCount == 1 && ClassUtils.isPrimitiveOrWrapper(entityClass)) {
					list.add(entityClass.cast(this.getColumnValue(1, rs)));
				} else {
					list.add(createEntity(rs, metaData));
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			throw new QueryException("Ocorreu um erro. Não foi possível realizar a consulta.");
		} finally {
			this.closeResources(rs);
			this.closeResources();
		}

		return list;
	}

	private T createEntity(ResultSet resultSet, ResultSetMetaData metaData) throws SQLException {

		T entity = createEntity();

		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			Object columnValue = this.getColumnValue(i, resultSet);
			String columnLabel = metaData.getColumnLabel(i);
			this.setObjectValue(entity, columnLabel, columnValue);
		}

		return entity;
	}

	private T createEntity() {
		try {

			return this.entityClass.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			throw new QueryException(
					String.format("Não foi possível criar uma instância da classe %s.", this.entityClass.getName()));
		}
	}

	private void createPreparedStatement(String sql) {
		try {
			this.conn = ConnectionFactory.createConnection();
			this.ptmt = this.conn.prepareStatement(sql);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			this.closeResources();
			throw new QueryException("Não foi possível alocar os recursos para a consulta.");
		}
	}

	private void closeResources() {
		this.closeResources(this.ptmt);
		this.closeResources(this.conn);
	}

	private void closeResources(AutoCloseable closeable) {

		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	private void setObjectValue(T entity, String columnLabel, Object columnValue) {
		try {
			Field field = this.entityClass.getDeclaredField(columnLabel);
			field.setAccessible(Boolean.TRUE);
			field.set(entity, columnValue);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			throw new QueryException(String.format("Não foi possível adicionar o valor da coluna %s.", columnLabel));
		}
	}

	private Object getColumnValue(int columnIndex, ResultSet rs) {

		Object columnValue = null;
		try {
			columnValue = rs.getObject(columnIndex);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			throw new QueryException(String.format("Não foi possível obter o valor da coluna %d", columnIndex));
		}
		return columnValue;
	}

	public static <T> Query<T> create(String sql, Class<T> entityClass) {
		return new Query<T>(sql, entityClass);
	}

}
