package lv.venta.services;

import lv.venta.models.Course;
import lv.venta.repos.ICourseRepo;

public interface ICourseService {

	Course addCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourse(long courseId);
	
}
