package com.task.taskmanagement.repository;

import com.task.taskmanagement.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
}