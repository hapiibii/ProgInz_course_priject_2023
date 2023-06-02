package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.users.AcademicPersonel;

public interface IAcademicPersonelRepo extends CrudRepository<AcademicPersonel, Long> {

}
