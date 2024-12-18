package com.trs.TRS.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trs.TRS.models.Post;
import com.trs.TRS.services.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    public ResponseEntity<?> createPost(@RequestBody Post post) {

        Post createdPost = postService.createPost(post);
        Map<String, Object> response = new HashMap<>();

        response.put("data", createdPost);
        response.put("message", "Post Created Successfully");
        return ResponseEntity.ok(response);
    }

}
