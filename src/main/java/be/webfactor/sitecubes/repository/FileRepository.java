package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.File;
import be.webfactor.sitecubes.domain.Site;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

	@Cacheable("file")
	@Query("FROM File WHERE site=?1")
	List<File> getSiteFiles(Site site);

	@CacheEvict(value = "file", allEntries = true)
	File save(File file);

	@CacheEvict(value = "file", allEntries = true)
	void delete(File file);

}
