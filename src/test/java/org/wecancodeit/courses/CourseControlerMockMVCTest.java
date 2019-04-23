package org.wecancodeit.courses;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import courses.courses.Course;
import courses.courses.CourseController;
import courses.courses.CourseRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import static java.util.Arrays.asList;

import java.util.Collection;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
public class CourseControlerMockMVCTest {

	@Resource
	private MockMvc mvc;
	
	@Mock
	private Course firstCourse;
	
	@Mock
	private Course secondCourse;
	
	@MockBean
	private CourseRepository repository;
	
	@Test
	public void shouldBeOkForAllCourses() throws Exception {
		mvc.perform(get("/show-courses")).andExpect(view().name(is("courses")));
				
	}
	
	@Test
	public void shouldPutAllCoursesIntoModel() throws Exception {
		Collection<Course> allCourses = asList(firstCourse, secondCourse);
		when(repository.findAll()).thenReturn(allCourses);
		mvc.perform(get("/show-courses")).andExpect(model().attribute("courses", is(allCourses)));
	}
	
	@Test
	public void shouldBeOkForSingleCourse() throws Exception {
		mvc.perform(get("/course?id=1")).andExpect(status().isOk());
		
	}
	
	@Test
	public void shouldRouteToSingleCoursesView() throws Exception {
		mvc.perform(get("/course?id=1")).andExpect(view().name(is("course")));
	}
	
	@Test
	public void shouldPutASingleCourseIntoModel() throws Exception {
		when(repository.findOne(1L)).thenReturn(firstCourse);
		
		mvc.perform(get("/course?id=1")).andExpect(model().attribute("courses", is(firstCourse)));
	}
}
