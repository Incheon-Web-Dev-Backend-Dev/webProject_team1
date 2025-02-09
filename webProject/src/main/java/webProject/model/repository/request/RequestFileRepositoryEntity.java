package webProject.model.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import webProject.model.entity.request.RequestFileEntity;

public interface RequestFileRepositoryEntity extends JpaRepository<RequestFileEntity, Integer> {
}
