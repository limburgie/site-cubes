package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {

	@QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
	Page findOne(Long aLong);

	@QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
	List<Page> findAll();

	@Query("FROM Page WHERE parent IS NULL")
	List<Page> getRootPages();

	@Query("FROM Page WHERE friendlyUrl=:friendlyUrl")
	Page findByFriendlyUrl(@Param("friendlyUrl") String friendlyUrl);

	@Modifying
	@Query("UPDATE Page SET layout=?2 WHERE layout=?1")
	void updatePageLayout(PageLayout layout, PageLayout defaultLayout);

}
