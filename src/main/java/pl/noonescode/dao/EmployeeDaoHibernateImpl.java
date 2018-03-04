package pl.noonescode.dao;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.noonescode.employee_management_system.model.Employee;
import pl.noonescode.hibernate.HibernateUtil;

public class EmployeeDaoHibernateImpl implements EmployeesDao {

	@Override
	public void updateEmployee(Employee updEmp) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(updEmp);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public Employee findOneEmployee(Integer id) {
		if (id == null) {
			return null;
		}
			Session session = HibernateUtil.getSession();
			Transaction tx = null;
			Employee ret = null;

		

		try {
			tx = session.beginTransaction();
			ret = session.get(Employee.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ret;
	}

	@Override
	public void deleteEmployeeFromDatabase(Integer id) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, id);
			session.delete(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void insertEmployeeIntoDatabase(Employee newEmp) {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(newEmp);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return;

	}

	@Override
	public List<Employee> findAllEmployees() {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		List<Employee> ret = new ArrayList<Employee>();
		try {
			tx = session.beginTransaction();
			ret = session.createQuery("FROM Employee").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ret;
	}

	@Override
	public Boolean checkIfEmployeeIsInDatabase(Integer id) {
		if (id == null) {
			return false;
		}
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		Employee employee = null;
		try {
			tx = session.beginTransaction();
			employee = (Employee) session.get(Employee.class, id);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		if (employee == null) {
			return false;
		} else {
			return true;

		}
	}
}