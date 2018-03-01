package pl.noonescode.employee_management_system.model;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter

public class Employee {
	
	private Integer id;
	private String name;
	private String surname;
	private Integer age;

}
