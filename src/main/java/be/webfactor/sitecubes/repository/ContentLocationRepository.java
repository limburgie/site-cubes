package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentLocationRepository extends JpaRepository<ContentLocation, Long> {

	@Query("FROM ContentLocation WHERE page=?1 ORDER BY position ASC")
	List<ContentLocation> findByPage(Page page);

	@Modifying @Query("DELETE FROM ContentLocation WHERE item=?1")
	void deleteItemLocations(ContentItem item);

	@Modifying @Query("UPDATE ContentLocation SET position=position+1 WHERE columnId=?1 AND position>=?2")
	void moveItemsInColumnDownFromPosition(String columnId, int position);

	@Modifying @Query("UPDATE ContentLocation SET position=position-1 WHERE columnId=?1 AND position>?2")
	void moveItemsInColumnUpFromPosition(String columnId, int position);

}
