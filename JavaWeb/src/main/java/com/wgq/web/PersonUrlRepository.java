package com.wgq.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource(path="personurls")
public interface PersonUrlRepository extends JpaRepository<PersonUrl, Integer> {
    List<PersonUrl> findAllByCreateTime(Date createTime);

    List<PersonUrl> findAllByCreateTimeBetween(
            Date CreateTimeStart,
            Date CreateTimeEnd);

//    @Query("select a from PersonUrl a where a.creationDateTime <= :creationDateTime")
//    List<PersonUrl> findAllWithCreationDateTimeBefore(
//            @Param("creationDateTime") Date creationDateTime);
}