package org.service.users.repositories;

import jakarta.transaction.Transactional;
import org.service.users.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {
    Optional<UserEntity> findById(Long id);
    Page<UserEntity> findAll(Pageable page);
    UserEntity findByMobile(String mobile);
    <S extends UserEntity> S save(S entity);

    @Modifying
    @Transactional
    @Query(value = "delete from users where id= :userId",nativeQuery = true)
    void deleteUser(@Param("userId") Long userId);
}
