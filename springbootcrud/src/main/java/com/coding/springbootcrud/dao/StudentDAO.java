package com.coding.springbootcrud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.coding.springbootcrud.model.Student;

@Service
public class StudentDAO {

	JdbcTemplate template;
	
	@Autowired
	public void setDataSource(DataSource datasource) {
		template = new JdbcTemplate(datasource);
	}
	
	
	public void save(Student p)
	{
		String sql = "insert into Student(firstName,lastName,email,sex,dob,section,country,firstAttempt,subjects) values ('"+p.getFirstName()+"','"+p.getLastName()+"','"+p.getEmail()+"','"+p.getSex()+"','"+ConvertDate(p.getDob())+"','"+p.getSection()+"','"+p.getCountry()+"',"+p.isFirstAttempt()+",'"+convertListToDeLimtedString(p.getSubjects())+"')";
	
	    template.update(sql);
	
	}
	
	public List<Student> getAllStudents(){
		
		return template.query("select * from student", new ResultSetExtractor<List<Student>>() {
			
			public List<Student> extractData(ResultSet rs) throws SQLException,DataAccessException
			{
				List<Student> list = new ArrayList<Student>();
				while(rs.next())
				{
					Student e = new Student();
					e.setId(rs.getInt(1));
					e.setFirstName(rs.getString(2));
					e.setLastName(rs.getString(3));
					e.setSex(rs.getString(4));
					e.setDob(rs.getDate(5));
					e.setEmail(rs.getString(6));
					e.setSection(rs.getString(7));
					e.setCountry(rs.getString(8));
					e.setFirstAttempt(rs.getBoolean(9));
					e.setSubjects(convertDelimitedStringToList(rs.getString(10)));
					
					list.add(e);
					
					
					
					
				}
				return list;
			}
			
		});
	}
	
	
public List<Student> getStudentsByPage(int pageid, int total){
		
		
	String sql = "select * from student limit "+(pageid-1)+","+total;
	
	return template.query(sql, new ResultSetExtractor<List<Student>>() {
			
			public List<Student> extractData(ResultSet rs) throws SQLException,DataAccessException
			{
				List<Student> list = new ArrayList<Student>();
				while(rs.next())
				{
					Student e = new Student();
					e.setId(rs.getInt(1));
					e.setFirstName(rs.getString(2));
					e.setLastName(rs.getString(3));
					e.setSex(rs.getString(4));
					e.setDob(rs.getDate(5));
					e.setEmail(rs.getString(6));
					e.setSection(rs.getString(7));
					e.setCountry(rs.getString(8));
					e.setFirstAttempt(rs.getBoolean(9));
					e.setSubjects(convertDelimitedStringToList(rs.getString(10)));
					
					list.add(e);
					
					
					
					
				}
				return list;
			}
			
		});
	}
	
	
   public Student getStudentById(int id)
   {
	   return template.query("select * from student where ID="+id,new ResultSetExtractor<Student>() {
			
			public Student extractData(ResultSet rs) throws SQLException,DataAccessException
			{
				Student e = new Student();
				while(rs.next())
				{
					
					e.setId(rs.getInt(1));
					e.setFirstName(rs.getString(2));
					e.setLastName(rs.getString(3));
					e.setSex(rs.getString(4));
					e.setDob(rs.getDate(5));
					e.setEmail(rs.getString(6));
					e.setSection(rs.getString(7));
					e.setCountry(rs.getString(8));
					e.setFirstAttempt(rs.getBoolean(9));
					e.setSubjects(convertDelimitedStringToList(rs.getString(10)));
					
					
					
					
					
					
				}
				return e;
			}
			
		});
	
	   
	   
   }


	public void update(Student p)
	{
		String sql="update Student set firstName ='"+p.getFirstName()+"',lastName='"+p.getLastName()+"',sex='"+p.getSex()+"',dob='"+ConvertDate(p.getDob())+"',email='"+p.getEmail()+"',section='"+p.getSection()+"',country='"+p.getCountry()+"',firstAttempt="+p.isFirstAttempt()+",subjects='"+convertListToDeLimtedString(p.getSubjects())+"' where ID="+p.getId()+"";
	    template.update(sql);
	
	}
	
	public void delete(int id) {
		
		String sql="delete from Student where ID="+id+"";
		template.update(sql);
	}
	
	public void delete() {
		
		String sql = "delete from Student where ID>0";
		template.update(sql);
	}
	
	private String convertListToDeLimtedString(List<String> list)
	{
		
		String result = "";
		if(list != null) {
			
			result = StringUtils.arrayToCommaDelimitedString(list.toArray());
			
		}
		
		return result;
		
	}
	
	private String ConvertDate(Date date) {
		
		String formatDate = "";
		
		try
		{
			DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
			date = (Date)formatter.parse(date.toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			formatDate = cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DATE);
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return formatDate;
	}
	
	private List<String> convertDelimitedStringToList(String delimitedString){
		
		List<String> result = new ArrayList<String>();
		if(!StringUtils.isEmpty(delimitedString)) {
			result = Arrays.asList(StringUtils.delimitedListToStringArray(delimitedString, ","));
		}
		
		return result;
	}
	
	public int count() {
		String sql ="select count(*) from Student";
		int count = template.queryForObject(sql, Integer.class);
		return count;
	}
	
}
