package lv.venta.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Course;
import lv.venta.repos.ICourseRepo;

@Service
public class CourseService {

    private final ICourseRepo courseRepo;

    @Autowired
    public CourseService(ICourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public Course addCourse(Course course) {
        return courseRepo.save(course);
    }

    public Course updateCourse(Course course) {
        return courseRepo.save(course);
    }

    public void deleteCourse(long courseId) {
        courseRepo.deleteById(courseId);
    }
}
