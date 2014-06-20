package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentLocationRepository extends JpaRepository<ContentLocation, Long> {

	@Query("FROM ContentLocation WHERE page=?1")
	List<ContentLocation> findByPage(Page page);

}