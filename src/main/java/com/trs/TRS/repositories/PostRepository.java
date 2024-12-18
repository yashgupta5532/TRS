package com.trs.TRS.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.trs.TRS.models.Post;

public interface PostRepository extends MongoRepository<Post,String>{
    
}
