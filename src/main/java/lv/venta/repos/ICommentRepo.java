package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Comment;

public interface ICommentRepo extends CrudRepository<Comment, Long>{

	
}
