package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Activities;

public interface IActivitiesRepo extends CrudRepository<Activities, Long> {

}
