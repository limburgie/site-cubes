package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
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
	@Query("FROM Page WHERE (?1 IS NULL AND parent_id IS NULL) OR parent=?1 ORDER BY position ASC")
	List<Page> getPagesForParent(Page page);

	@Cacheable("page")
	@Query("FROM Page WHERE friendlyUrl=?1")
	Page findByFriendlyUrl(String friendlyUrl);

	@CacheEvict(value = "page", allEntries = true)
	Page save(Page page);

	@CacheEvict(value = "page", allEntries = true)
	void delete(Long id);

	@CacheEvict(value = "page", allEntries = true)
	@Modifying @Query("UPDATE Page SET layout=?2 WHERE layout=?1")
	void updatePageLayout(PageLayout layout, PageLayout defaultLayout);

	@CacheEvict(value = "page", allEntries = true)
	@Modifying(clearAutomatically = true) @Query("UPDATE Page SET position=position+1 WHERE parent=?1 AND position>=?2 ORDER BY position DESC")
	void movePagesDownForParentFromPosition(Page parent, int position);

}
