package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {

	@Query("FROM Page WHERE parent IS NULL ORDER BY position ASC")
	List<Page> getRootPages();

	@Query("SELECT COUNT(*) FROM Page WHERE parent IS NULL")
	int countRootPages();

	@Query("FROM Page WHERE friendlyUrl=:friendlyUrl")
	Page findByFriendlyUrl(@Param("friendlyUrl") String friendlyUrl);

	@Modifying @Query("UPDATE Page SET layout=?2 WHERE layout=?1")
	void updatePageLayout(PageLayout layout, PageLayout defaultLayout);

	@Modifying @Query("UPDATE Page SET position=position-1 WHERE parent=?1 AND position>?2")
	void movePagesUpFromPosition(Page parent, int position);

	@Modifying @Query("UPDATE Page SET position=position-1 WHERE parent IS NULL AND position>?1")
	void moveRootPagesUpFromPosition(int position);

}
