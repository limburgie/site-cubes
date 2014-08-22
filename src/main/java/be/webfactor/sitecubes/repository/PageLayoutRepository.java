package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.PageLayout;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageLayoutRepository extends JpaRepository<PageLayout, Long> {

	@Cacheable("page_layout")
	PageLayout findOne(Long id);

	@Cacheable("page_layout")
	List<PageLayout> findAll();

	@CacheEvict(value = "page_layout", allEntries = true)
	PageLayout save(PageLayout layout);

	@Cacheable("page_layout")
	long count();

	@Cacheable("page_layout")
	@Query("FROM PageLayout WHERE name=?1")
	PageLayout findByName(String name);

	@Cacheable("page_layout")
	@Query("FROM PageLayout WHERE defaultLayout=true")
	List<PageLayout> getDefault();

	@CacheEvict(value = "page_layout", allEntries = true)
	@Modifying @Query("UPDATE PageLayout SET defaultLayout=false")
	void undefaultAll();

	@CacheEvict(value = "page_layout", allEntries = true)
	@Modifying @Query("UPDATE PageLayout SET defaultLayout=true WHERE id=?1")
	void setDefault(long id);

}
