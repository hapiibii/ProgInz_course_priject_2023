package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Thesis;

public interface IThesisRepo extends CrudRepository<Thesis, Long> {

}
