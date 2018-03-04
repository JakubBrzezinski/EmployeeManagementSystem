package pl.noonescode.employee_management_system;

import java.util.List;
import java.util.Scanner;

import pl.noonescode.dao.EmployeeDaoHibernateImpl;
import pl.noonescode.dao.EmployeesDao;
import pl.noonescode.dao.EmployeesDaoJdbcImpl;
import pl.noonescode.employee_management_system.model.Employee;

public class EmployeeManagementSystemApp {

	private static EmployeesDao emplDao = new EmployeeDaoHibernateImpl();

	private static Scanner sc;

	public static void main(String[] args) {

		int selection = 0;
		sc = new Scanner(System.in);
		do {
			printMenu();
			selection = getIntFromScanner();
			executeSelectedMethod(selection);
		} while (selection != 5);
		sc.close();

	}

	private static String getStringFromInputStream() {
		return sc.next();
	}

	private static void executeSelectedMethod(int selection) {
		switch (selection) {
		case 1:
			createEmployee();
			break;
		case 2:
			read();
			break;
		case 3:
			updateEmployee();
			break;
		case 4:
			deleteEmployee();
			break;
		case 5:
			System.out.println("Thank you for the session. See you next time!");
			break;
		default:
			System.out.println("Incorrect choice");
			break;
		}
	}

	private static void printMenu() {

		System.out.println(
				"Welcome in EmployeeManagementSystem.\nPlease choose one of the following to interact with database: \n");

		System.out.println("1. Create");
		System.out.println("2. Read");
		System.out.println("3. Update");
		System.out.println("4. Delete");
		System.out.println("5. Exit");

	}

	private static int getIntFromScanner() {
		int selection = 0;
		boolean isSelectionValid = false;
		do {
			if (sc.hasNextInt()) {
				selection = sc.nextInt();
				isSelectionValid = true;
			} else {
				sc.next();
			}
		} while (isSelectionValid == false);
		return selection;
	}

	private static void read() {
		int selection = 0;
		do {
			System.out.println("1 - Find employee by ID");
			System.out.println("2 - Show list of employees");
			System.out.println("3 - Back\n");

			selection = getIntFromScanner();

			switch (selection) {
			case 1:
				readEmployeeFromDatabaseById();
				break;
			case 2:
				readEmployeesFromDatabase();
				break;
			case 3:
				System.out.println("Going back to menu");
				break;
			default:
				System.out.println("Incorrect choice");
				break;
			}
		} while (selection != 3);

	}

	private static Employee setEmployeeValues() {
		System.out.print("Enter employee name:");
		String name = getStringFromInputStream();
		System.out.print("Enter employee surname:");
		String surname = getStringFromInputStream();
		System.out.println("Enter employee age:");
		Integer age = getIntFromScanner();

		Employee emp = new Employee();
		emp.setName(name);
		emp.setSurname(surname);
		emp.setAge(age);
		return emp;
	}

	private static void createEmployee() {
		Employee emp = setEmployeeValues();
		emplDao.insertEmployeeIntoDatabase(emp);
		System.out.println("Employee successfully added to the database");

	}

	private static void readEmployeesFromDatabase() {
		List<Employee> empList = emplDao.findAllEmployees();
		for (Employee singleEmp : empList) {
			printEmployee(singleEmp);
		}

	}

	private static void readEmployeeFromDatabaseById() {
		printIdSelectionQuery();
		Integer id = getIntFromScanner();
		Boolean employeeExists = emplDao.checkIfEmployeeIsInDatabase(id);
		if(employeeExists == false) {
			System.out.println("Such employee does not exist");
			return;
		} 
		Employee emp = emplDao.findOneEmployee(id);
		printEmployee(emp);
	}

	private static void updateEmployee() {
		printIdSelectionQuery();
		Integer id = getIntFromScanner();
		Boolean employeeExists = emplDao.checkIfEmployeeIsInDatabase(id);
		if(employeeExists == null || employeeExists == false) {
			System.out.println("Such employee does not exist");
			return;
		} 
		Employee emp = emplDao.findOneEmployee(id);
		System.out.print("You can now udpate the following employee: ");
		printEmployee(emp);
		Employee updatedEmp = setEmployeeValues();
		updatedEmp.setId(emp.getId());
		emplDao.updateEmployee(updatedEmp);
		System.out.println("Employee updated successfully!");
	}

	private static void deleteEmployee() {
		
		printIdSelectionQuery();
		Integer id = getIntFromScanner();
		Boolean employeeExists = emplDao.checkIfEmployeeIsInDatabase(id);
		if(employeeExists == false) {
			System.out.println("Such employee does not exist");
			return;
		} 
		emplDao.deleteEmployeeFromDatabase(id);
		System.out.println("Employee deleted successfully!");
		
	}
	
	private static void printIdSelectionQuery() {
		System.out.print("Enter ID to find employee in the database:");
	}
	private static void printEmployee(Employee emp) {

		System.out.println("ID: " + emp.getId() + ", " + emp.getName() + " " + emp.getSurname() + ", " + emp.getAge());
	}
}
