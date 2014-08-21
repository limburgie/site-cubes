package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {

	@Cacheable("page")
	Page findOne(Long id);

	@Cacheable("page")
	List<Page> findAll();

	@Cacheable("page")
	@Query("FROM Page WHERE parent IS NULL")
	List<Page> getRootPages();

	@Cacheable("page")
	@Query("FROM Page WHERE friendlyUrl=:friendlyUrl")
	Page findByFriendlyUrl(@Param("friendlyUrl") String friendlyUrl);

	@CacheEvict(value = "page", allEntries = true)
	Page save(Page page);

	@CacheEvict(value = "page", allEntries = true)
	void delete(Long id);

	@CacheEvict(value = "page", allEntries = true)
	@Modifying @Query("UPDATE Page SET layout=?2 WHERE layout=?1")
	void updatePageLayout(PageLayout layout, PageLayout defaultLayout);

}
