package tws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tws.entity.Employee;
import tws.repository.EmployeeMapper;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeMapper.selectAll());
    }

    @GetMapping("/{employeeID}")
    public ResponseEntity<Employee> selectOneEmployeeByID(@PathVariable String employeeID) {
        Employee employee = employeeMapper.selectOneEmployee(employeeID);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{employeeID}")
    public ResponseEntity<Employee> updateEmployeeByID(@PathVariable String employeeID,@RequestBody Employee employee){
        employeeMapper.updateEmployee(employeeID,employee);
        employee.setId(employeeID);
        return ResponseEntity.ok(employee);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        String id = UUID.randomUUID().toString();
        employee.setId(id);
        System.out.println(employee.toString());
        employeeMapper.addEmployee(employee);
        return ResponseEntity.created(URI.create("/employees/"+id)).build();
    }

    @DeleteMapping("/{employeeID}")
    public ResponseEntity<Employee> deleteEmployeeByID(@PathVariable String employeeID){
        employeeMapper.deleteEmployee(employeeID);
        return ResponseEntity.ok().build();
    }


}
