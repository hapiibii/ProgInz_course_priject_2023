package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.users.Student;

public interface IStudentRepo extends CrudRepository<Student, Long>{

}
