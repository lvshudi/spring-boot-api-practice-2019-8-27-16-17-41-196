package com.tw.apistackbase.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.apistackbase.entity.Employees;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

	private static List<Employees> employees = new ArrayList<Employees>() {
		{
			add(new Employees(1, "xiaoming", 18, "male"));
			add(new Employees(2, "xiaohong", 18, "female"));
			add(new Employees(3, "xiaozhang", 18, "male"));
			add(new Employees(4, "xiaoli", 18, "female"));
			add(new Employees(5, "xiaohua", 18, "male"));
		}
	};

	@GetMapping("/{gender}")
	public ResponseEntity<List<Employees>> getEmployees(@PathVariable String gender) {
		List<Employees> returnEmployees = new ArrayList<Employees>();
		for (Employees employee : employees) {
			if (employee.getGender().equals(gender)) {
				returnEmployees.add(employee);
			}
		}

		return ResponseEntity.ok(returnEmployees);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteEmployee(@PathVariable String id) {
		for (Employees employee : employees) {
			if (employee.getId() == Integer.parseInt(id)) {
				employees.remove(employee);
				break;
			}
		}
		return ResponseEntity.noContent().build();
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<Employees> updateEmployee(@RequestBody Employees e) {
		Optional<Employees> optionalEmployee = employees.stream().filter(employee -> employee.getId() == e.getId())
				.findAny();
		optionalEmployee.orElse(null).setName(e.getName());
		optionalEmployee.orElse(null).setAge(e.getAge());
		optionalEmployee.orElse(null).setGender(e.getGender());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping
	public ResponseEntity<ArrayList<Employees>> addEmploy(@RequestBody Employees employee) {
		employees.add(employee);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
