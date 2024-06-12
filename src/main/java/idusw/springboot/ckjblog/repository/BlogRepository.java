package idusw.springboot.ckjblog.repository;

import idusw.springboot.ckjblog.entity.BlogEntity;
import idusw.springboot.ckjblog.model.BlogDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    Optional<BlogEntity> findByIdx(BlogDto dto); // interface 상속,

}
