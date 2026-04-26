package com.ainativecms.repository;

import com.ainativecms.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {
    Optional<Page> findByUrlPath(String urlPath);
}
