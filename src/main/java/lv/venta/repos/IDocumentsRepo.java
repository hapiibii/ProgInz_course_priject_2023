package lv.venta.repos;


import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Documents;

public interface IDocumentsRepo extends CrudRepository<Documents, Long>{

}
