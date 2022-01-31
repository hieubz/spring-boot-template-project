package com.example.demo.repository.mongo.primary;

import com.example.demo.repository.mongo.entity.DemoLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DemoLogRepository extends MongoRepository<DemoLog, String> {
}
