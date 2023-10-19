package lv.venta.services;

import java.util.Optional;

import lv.venta.models.StudioProgramm;

public interface IStudioProgrammService {

	public Iterable<StudioProgramm> findAll();
	public Optional<StudioProgramm> findById(Long id);
	public StudioProgramm save(StudioProgramm programm);
	public void delete(StudioProgramm programm);

}
