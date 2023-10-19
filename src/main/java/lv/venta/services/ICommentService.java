package lv.venta.services;

import java.util.List;

import lv.venta.models.Comment;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;

public interface ICommentService {

	void createComment(Comment comment);
    void updateComment(long idcom, String description) throws Exception;
    void deleteCommentById(long idcom);
    Comment getCommentById(long idcom);
	List<Comment> getAllComments();
}
