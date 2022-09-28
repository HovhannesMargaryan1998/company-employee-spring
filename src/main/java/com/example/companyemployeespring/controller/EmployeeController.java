package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${company.employee.images.folder}")
    private String folderPath;


    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("employeeImage") MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File file1 = new File(folderPath + File.separator + fileName);
            file.transferTo(file1);
            employee.setProfilePic(fileName);
        }

         employeeRepository.save(employee);

        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String employeesPage(ModelMap modelMap) {
        List<Employee> employeeList = employeeRepository.findAll();
        modelMap.addAttribute("employees", employeeList);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String addEmployeePage(ModelMap modelMap) {
        List<Company> companiesList = companyRepository.findAll();
        modelMap.addAttribute("companies", companiesList);
        return "addEmployee";
    }

    @GetMapping("/employeeGetImage")
    public @ResponseBody byte[] getImage(@RequestParam("picName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }
}
