package com.coding.springbootcrud.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coding.springbootcrud.dao.StudentDAO;
import com.coding.springbootcrud.model.Student;

@Controller
public class StudentEnrollmentController {
	
	@Autowired
	private StudentDAO studentDao;
	
	@RequestMapping(value = "/enroll", method=RequestMethod.GET)
	public String newResgistration(ModelMap model) {
		
		Student student = new Student();
		model.addAttribute("student", student);
		return "enroll";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveRegistration(@Valid Student student,BindingResult result, ModelMap model,RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			
			return "enroll";
			
		}
		
		studentDao.save(student);
		  
		return "redirect:/viewstudents/1";
	}
	
	@RequestMapping("/viewstudents")  
    public ModelAndView viewstudents(){  
        List<Student> list=studentDao.getAllStudents();
        return new ModelAndView("viewstudents","list",list);  
    } 
	
	
	@RequestMapping(value="/viewstudents/{pageid}")
	public ModelAndView edit(@PathVariable int pageid) {
		
		int total=2;
		
		
		if(pageid==1) {
			
		}else {
			pageid=(pageid-1)*total+1;
		}
		List<Student> list=studentDao.getStudentsByPage(pageid, total);
		return new ModelAndView("viewstudents", "list", list);
		
	}
	
	@RequestMapping(value="/editstudent/{id}")  
    public String edit(@PathVariable int id,ModelMap model){  
       Student student=studentDao.getStudentById(id);  
       model.addAttribute("student", student);
		return "editstudent";
        
        
    } 
	
	@RequestMapping(value="/editsave", method=RequestMethod.POST)
	public ModelAndView editsave(@ModelAttribute("student") Student p) {
		System.out.println("id is"+p.getId());
		studentDao.update(p);
		return new ModelAndView("redirect:/viewstudents/1");
	}
	
	@RequestMapping(value="/deletestudent/{id}", method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id) {
		
		studentDao.delete(id);
		
		return new ModelAndView("redirect:/viewstudents/1");
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView delete() {
		
		studentDao.delete();
		
		return new ModelAndView("redirect:/viewstudents/1");
	}

	
	@ModelAttribute("sections")
	public List<String> intializeSections(){
		List<String> sections = new ArrayList<String>();
		sections.add("Graduate");
		sections.add("Post Graduate");
		sections.add("Research");
		return sections;
	}
	
	@ModelAttribute("countries")
	public List<String> initializeCountries(){
		
		List<String> countries = new ArrayList<String>();
		countries.add("India");
		countries.add("Italy");
		countries.add("USA");
		countries.add("Canada");
		countries.add("OTHER");
		return countries;
		
	}
	
	@ModelAttribute("subjects")
	public List<String> initializeSubjects(){
		
		List<String> subjects = new ArrayList<String>();
		subjects.add("Physics");
		subjects.add("Chemistry");
		subjects.add("Maths");
		subjects.add("Computer Science");
		return subjects;
		
	}
	
	@ModelAttribute("pageCount")
	public List<String> initializePageCount(){
		
		int total = 2;
		List<String> pageCount = new ArrayList<String>();
		int count = studentDao.count();
		int result = ((count/total)+ (count%total));
		for(int k=0;k<result;k++) {
			pageCount.add(new Integer(k).toString());
		}
		
		return pageCount;
	}
}
