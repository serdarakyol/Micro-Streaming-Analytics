package com.example.MSA.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.MSA.document.DataRecord;

@Repository
public interface DataRecordRepository extends MongoRepository<DataRecord, String>{
    
}
