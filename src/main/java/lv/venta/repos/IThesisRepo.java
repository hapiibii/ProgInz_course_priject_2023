package lv.venta.repos;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.AcceptanceStatus;
import lv.venta.models.Thesis;

public interface IThesisRepo extends CrudRepository<Thesis, Long> {

	//findByAcceptanceStatus
	ArrayList<Thesis> findByAcceptanceStatus(AcceptanceStatus acceptanceStatus);
	
	//findBySupervisorStatus
	ArrayList<Thesis> findBySupervisorStatus(boolean status);
	
}
