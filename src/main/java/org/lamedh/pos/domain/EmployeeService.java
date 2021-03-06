package org.lamedh.pos.domain;

import org.lamedh.pos.common.domain.DomainService;
import org.lamedh.pos.domain.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService implements DomainService<Employee> {

    private EmployeeRepository employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee create() {
        Employee employee = new Employee();
        employee.setCode(generateCode());
        return employee;
    }

    public Employee save(Employee employee) {
        employee.setCode(generateCode());
        return employeeRepo.save(employee);
    }

    public Optional<Employee> getById(int id) {
        return employeeRepo.getById(id);
    }

    public Page<Employee> getAll(Pageable page) {
        return employeeRepo.findAll(page);
    }

    public void delete(int id) {
        employeeRepo.delete(id);
    }

    private String generateCode() {
        return "E" + (employeeRepo.count() + 1);
    }
}
