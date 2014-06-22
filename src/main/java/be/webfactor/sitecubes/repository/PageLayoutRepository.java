package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.PageLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageLayoutRepository extends JpaRepository<PageLayout, Long> {

	@Query("FROM PageLayout WHERE name=?1")
	PageLayout findByName(String name);

	@Query("FROM PageLayout WHERE defaultLayout=true")
	List<PageLayout> getDefault();

	@Modifying @Query("UPDATE PageLayout SET defaultLayout=false")
	void undefaultAll();

	@Modifying @Query("UPDATE PageLayout SET defaultLayout=true WHERE id=?1")
	void setDefault(long id);

}
