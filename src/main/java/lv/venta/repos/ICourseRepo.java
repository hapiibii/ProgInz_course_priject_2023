package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Course;

public interface ICourseRepo extends CrudRepository<Course, Long>{

}
