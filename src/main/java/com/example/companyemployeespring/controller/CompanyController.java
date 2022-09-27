package com.example.companyemployeespring.controller;


import com.example.companyemployeespring.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.companyemployeespring.repository.CompanyRepository;


@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/companies")
    public String companies(ModelMap modelMap) {
        modelMap.addAttribute("companies", companyRepository.findAll());
        return "companies";
    }

    @GetMapping("/company/add")
    public String addCompanyPage() {
        return "addCompany";
    }

    @PostMapping("/company/add")
    public String addCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/companies";
    }
}