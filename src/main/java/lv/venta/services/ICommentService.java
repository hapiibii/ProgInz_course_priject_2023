package lv.venta.services;

import lv.venta.models.Comment;

public interface ICommentService {

	void createComment(String description, long personelId, long thesisId);
    void updateComment(long commentId, String description) throws Exception;
    void deleteComment(long commentId) throws Exception;
    Comment getCommentById(long commentId);
}
