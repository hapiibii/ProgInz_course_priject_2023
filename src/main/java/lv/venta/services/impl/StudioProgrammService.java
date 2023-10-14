package lv.venta.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lv.venta.models.StudioProgramm;
import lv.venta.repos.IStudioProgrammRepo;
import lv.venta.services.IStudioProgrammService;

@Service
public class StudioProgrammService implements IStudioProgrammService {
	
	private final IStudioProgrammRepo studioProgrammRepo;
	
	@Autowired
	public StudioProgrammService(IStudioProgrammRepo studioProgrammRepo) {
		this.studioProgrammRepo = studioProgrammRepo;
	}
	
	 @Cacheable(cacheNames = "programms")
	 public Iterable<StudioProgramm> findAll() {
	        return studioProgrammRepo.findAll();
	 }

	 @Cacheable(cacheNames = "programm", key = "#id")
	 public Optional<StudioProgramm> findById(Long id) {
	    return studioProgrammRepo.findById(id);
	 }

	 @CacheEvict(cacheNames = "programms", allEntries = true)
	 public StudioProgramm save(StudioProgramm programm) {
		 return studioProgrammRepo.save(programm);
	 }

	 @CacheEvict(cacheNames = {"programms", "programm"}, allEntries = true)
	 public void delete(StudioProgramm programm) {
		 studioProgrammRepo.delete(programm);
	 }
}