package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.ContentItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentItemRepository extends JpaRepository<ContentItem, Long> {
}
