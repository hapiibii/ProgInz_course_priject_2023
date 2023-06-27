package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Submission;

public interface ISubmissionRepo extends CrudRepository<Submission, Long>{

}
