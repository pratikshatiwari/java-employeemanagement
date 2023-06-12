package net.javaguides.springboot.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import net.javaguides.springboot.model.Details;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
@Controller
@EnableAutoConfiguration
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	private static final String URI="EmployeeService";
	
	
	@Value("${admin.baseurl.path}")
	String baseUrl;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		logger.info("Details entered in employee form.");
		return "new_employee";
	}
	
	@PostMapping(value = "/saveEmployee")
	public String saveEmployee1(@ModelAttribute("employee") Employee employee)
	{
	
	   RestTemplate restTemplate = new RestTemplate();
	   Details dt = new Details(employee.getId(),employee.getFirstName());

	   HttpHeaders header = new HttpHeaders();
	   String reqHeader = UUID.randomUUID().toString();

	   header.setContentType(MediaType.APPLICATION_JSON);
	   HttpEntity<Details> enitity = new HttpEntity<Details>(dt,header);


	    ResponseEntity<Details> response = restTemplate.exchange(baseUrl,HttpMethod.POST,enitity,Details.class);
	    Employee employees = new Employee (employee.getId(),employee.getFirstName(), employee.getLastName(), employee.getEmail());
	    employeeService.saveEmployee(employees);
	    logger.info("Employee information added in consumer service with ID: " + employees.getId());
		return "redirect:/";
		}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		logger.info("Employee information updated in consumer service with ID: "+ id);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		this.employeeService.deleteEmployeeById(id);
		logger.info("employee information deleted in consumer service with ID: "+ id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}

public ResponseEntity<Object> connectServicefallback(Exception e){
    return new ResponseEntity<Object>("producer service is down...",HttpStatus.FORBIDDEN);
}


}
