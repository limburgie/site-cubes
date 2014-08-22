package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.ContentItem;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentItemRepository extends JpaRepository<ContentItem, Long> {

	@Cacheable("content_item")
	List<ContentItem> findAll();

	@CacheEvict(value = "content_item", allEntries = true)
	ContentItem save(ContentItem item);

	@CacheEvict(value = "content_item", allEntries = true)
	void delete(Long id);

}
