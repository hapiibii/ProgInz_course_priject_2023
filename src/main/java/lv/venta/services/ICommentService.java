package lv.venta.services;

import java.util.List;

import lv.venta.models.Comment;

public interface ICommentService {

	void createComment(Comment comment);
    void updateComment(long idcom, String description) throws Exception;
    void deleteCommentById(long idcom);
    Comment getCommentById(long idcom);
	List<Comment> getAllComments();
}
