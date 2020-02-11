package com.wgq.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="personurls")
public interface PersonUrlRepository extends JpaRepository<PersonUrl, Integer> {

}