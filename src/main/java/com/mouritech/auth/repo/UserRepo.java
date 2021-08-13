package com.mouritech.auth.repo;

import com.mouritech.auth.dao.User;
import com.mouritech.auth.dto.UserAuthDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    @Query(value = "SELECT U.USER_NAME as userName ,u.USER_PASSWORD as userPassword,r.ROLE_NAME as roleName " +
            " FROM APP_USER U, app_role R , USER_ROLE UR " +
            "WHERE U.APP_USER_ID =UR.APP_USER_ID AND R.APP_ROLE_ID = UR.APP_ROLE_ID and U.USER_NAME = :username",
            nativeQuery = true)
    List<UserAuthDto> findByUsername(@Param("username") String username);

}
