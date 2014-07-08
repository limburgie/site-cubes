package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {

	@Query("FROM Page WHERE parent IS NULL ORDER BY position ASC")
	List<Page> getRootPages();

	@Query("FROM Page WHERE (?1 IS NULL AND parent_id IS NULL) OR parent=?1 ORDER BY position ASC")
	List<Page> getPagesForParent(Page page);

	@Query("FROM Page WHERE friendlyUrl=?1")
	Page findByFriendlyUrl(String friendlyUrl);

	@Modifying @Query("UPDATE Page SET layout=?2 WHERE layout=?1")
	void updatePageLayout(PageLayout layout, PageLayout defaultLayout);

}
