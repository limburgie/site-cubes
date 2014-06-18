package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {

	@Query("FROM Page WHERE parent IS NULL")
	List<Page> getRootPages();

	@Query("FROM Page WHERE friendlyUrl=:friendlyUrl")
	Page findByFriendlyUrl(@Param("friendlyUrl") String friendlyUrl);

}
