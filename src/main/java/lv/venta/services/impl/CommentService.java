package lv.venta.services.impl;

import lv.venta.models.Comment;
import lv.venta.repos.ICommentRepo;
import lv.venta.services.ICommentService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ICommentService {

	private ICommentRepo commentRepo;

	

    @Autowired
    public CommentService(ICommentRepo commentRepo) {
        this.commentRepo = commentRepo;

    }

    @Override
    public void createComment(Comment comment) {
        commentRepo.save(comment);
    }

    @Override
    public void updateComment(long commentId, String description) throws Exception {
        Comment comment = commentRepo.findById(commentId).orElse(null);
        if (comment == null) {
            throw new Exception("Komentārs ar ID " + commentId + " nav atrasts.");
        }
        comment.setDescription(description);
        comment.setDate(LocalDateTime.now());
        commentRepo.save(comment);
    }

    @Override
    public void deleteComment(long commentId) throws Exception {
        Comment comment = commentRepo.findById(commentId).orElse(null);
        if (comment == null) {
            throw new Exception("Komentārs ar ID " + commentId + " nav atrasts.");
        }
        commentRepo.delete(comment);
    }

    @Override
    public Comment getCommentById(long commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }
}

