package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.PageLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PageLayoutRepository extends JpaRepository<PageLayout, Long> {

	@Query("FROM PageLayout WHERE name=?1")
	PageLayout findByName(String name);

}
