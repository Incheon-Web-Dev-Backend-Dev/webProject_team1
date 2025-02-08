package webProject.model.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import webProject.model.entity.request.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
}
