package org.example.expert.domain.comment.dto.response;

import lombok.Getter;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponse {

    private final Long id;
    private final String contents;
    private final UserResponse user;

    public CommentResponse(Long id, String contents, UserResponse user) {
        this.id = id;
        this.contents = contents;
        this.user = user;
    }

    public static List<CommentResponse> getResponseList(List<Comment> commentList) {
        List<CommentResponse> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = comment.getUser();
            CommentResponse dto = new CommentResponse(
                    comment.getId(),
                    comment.getContents(),
                    UserResponse.fromEntity(user)
            );
            dtoList.add(dto);
        }
        return dtoList;
    }
}
