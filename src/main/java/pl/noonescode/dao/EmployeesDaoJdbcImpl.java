package pl.noonescode.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pl.noonescode.config.DatabaseConfig;
import pl.noonescode.employee_management_system.model.Employee;

public class EmployeesDaoJdbcImpl implements EmployeesDao  {

	static final String SELECT_ALL_EMPLOYEES_SQL = "SELECT * From employees";
	static final String INSTER_INTO_DATABASE = "insert into employees (name, surname, age) values (?, ?, ?);";
	static final String DELETE_FROM_DATABASE = "DELETE FROM employees WHERE id = ?";
	static final String UPDATE_EMPLOYEE_IN_DATABASE = "UPDATE employees SET name=?, surname=?, age=? WHERE id = ?";
	static final String SELECT_ONE_EMPLOYEE_SQL = "SELECT * From employees where id = ?";

	private List<Employee> mapDbRecordToEmployee(ResultSet rs) throws SQLException {
		List<Employee> ret = new ArrayList<Employee>();
		while (rs.next()) {
			int id = rs.getInt("id");
			int age = rs.getInt("age");
			String name = rs.getString("name");
			String surname = rs.getString("surname");

			Employee emp = new Employee();
			emp.setId(id);
			emp.setAge(age);
			emp.setName(name);
			emp.setSurname(surname);

			ret.add(emp);
		}

		return ret;

	}

	private Employee mapDbRecordToSingleEmployee(ResultSet rs) throws Exception {
		if (rs.next() == false) {
			throw new Exception("ResultSet returned 0 records!");
		}

		int id = rs.getInt("id");
		int age = rs.getInt("age");
		String name = rs.getString("name");
		String surname = rs.getString("surname");

		Employee ret = new Employee();
		ret.setId(id);
		ret.setAge(age);
		ret.setName(name);
		ret.setSurname(surname);

		return ret;
	}

	@Override
	public void updateEmployee(Employee updEmp) {

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE_IN_DATABASE)) {

			statement.setString(1, updEmp.getName());
			statement.setString(2, updEmp.getSurname());
			statement.setInt(3, updEmp.getAge());
			statement.setInt(4, updEmp.getId());

			statement.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Employee findOneEmployee(Integer id) {
		if (id == null) {
			return null;
		}

		Employee ret = null;

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ONE_EMPLOYEE_SQL);

		) {

			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			ret = mapDbRecordToSingleEmployee(rs);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	@Override
	public void deleteEmployeeFromDatabase(Integer id) {
		if (id == null) {
			return;
		}

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_FROM_DATABASE);) {

			statement.setInt(1, id);

			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertEmployeeIntoDatabase(Employee newEmp) {

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(INSTER_INTO_DATABASE);) {

			statement.setString(1, newEmp.getName());
			statement.setString(2, newEmp.getSurname());
			statement.setInt(3, newEmp.getAge());

			statement.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Employee> findAllEmployees() {
		List<Employee> ret = new ArrayList<Employee>();

		try (Connection connection = createConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(SELECT_ALL_EMPLOYEES_SQL);) {

			ret = mapDbRecordToEmployee(rs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public Boolean checkIfEmployeeIsInDatabase(Integer id) {

		if (id == null) {
			return false;
		}

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ONE_EMPLOYEE_SQL);

		) {

			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.first()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	private Connection createConnection() throws SQLException {
		Connection ret = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.USER, DatabaseConfig.PASS);
		return ret;
	}
}
