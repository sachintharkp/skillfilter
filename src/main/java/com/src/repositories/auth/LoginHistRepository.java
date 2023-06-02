package com.src.repositories.auth;

import com.src.models.auth.LoginHistory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoginHistRepository extends CrudRepository<LoginHistory,Long> {

    Optional<List<LoginHistory> >  findByUserId(Long userId);

    @Modifying
    @Query(value = "update hist_login u set u.active = false where user_id=:userId",nativeQuery = true)
    @Transactional
    void deactivateUser(@Param("userId") Long userId);

}
