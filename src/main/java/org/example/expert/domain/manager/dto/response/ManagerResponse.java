package org.example.expert.domain.manager.dto.response;

import lombok.Getter;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ManagerResponse {

    private final Long id;
    private final UserResponse user;

    public ManagerResponse(Long id, UserResponse user) {
        this.id = id;
        this.user = user;
    }

    public static List<ManagerResponse> getResponseList (List<Manager> managerList) {
        List<ManagerResponse> dtoList = new ArrayList<>();
        for (Manager manager : managerList) {
            User user = manager.getUser();
            dtoList.add(new ManagerResponse(
                    manager.getId(),
                    UserResponse.fromEntity(user))
            );
        }
        return dtoList;
    }
}
