package pl.noonescode.employee_management_system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Getter
@Setter

@Entity
@Table(name="employees")
public class Employee {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String surname;
	private Integer age;

}
