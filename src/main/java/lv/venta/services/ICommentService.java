package lv.venta.services;

import lv.venta.models.Comment;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;

public interface ICommentService {

	void createComment(Comment comment);
    void updateComment(long commentId, String description) throws Exception;
    void deleteComment(long commentId) throws Exception;
    Comment getCommentById(long commentId);
}
