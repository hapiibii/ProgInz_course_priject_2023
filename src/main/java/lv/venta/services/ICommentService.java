package lv.venta.services;

import lv.venta.models.Comment;

public interface ICommentService {

	void createComment(Comment comment);
    void updateComment(long commentId, String description) throws Exception;
    void deleteComment(long commentId) throws Exception;
    Comment getCommentById(long commentId);
}
