package pl.noonescode.dao;

import java.util.List;

import pl.noonescode.employee_management_system.model.Employee;


public interface EmployeesDao {
	public void updateEmployee(Employee updEmp);
	public Employee findOneEmployee(Integer id);
	public void deleteEmployeeFromDatabase(Integer id);
	public void insertEmployeeIntoDatabase(Employee newEmp);
	public List<Employee> findAllEmployees();
	public Boolean checkIfEmployeeIsInDatabase(Integer id);
	
}
