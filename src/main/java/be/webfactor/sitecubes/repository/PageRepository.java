package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.domain.Site;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {

	@Cacheable("page")
	Page findOne(Long id);

	@Cacheable("page")
	List<Page> findAll();

	@Cacheable("page")
	@Query("FROM Page WHERE site=?1 AND ((?2 IS NULL AND parent_id IS NULL) OR parent=?2) ORDER BY position ASC")
	List<Page> getPagesForParent(Site site, Page page);

	@Cacheable("page")
	@Query("FROM Page WHERE site=?1 AND friendlyUrl=?2")
	Page findByFriendlyUrl(Site site, String friendlyUrl);

	@CacheEvict(value = "page", allEntries = true)
	Page save(Page page);

	@CacheEvict(value = "page", allEntries = true)
	Page saveAndFlush(Page page);

	@CacheEvict(value = "page", allEntries = true)
	void delete(Page page);

	@CacheEvict(value = "page", allEntries = true)
	@Modifying @Query("UPDATE Page SET layout=?2 WHERE layout=?1")
	void updatePageLayout(PageLayout layout, PageLayout defaultLayout);

}
