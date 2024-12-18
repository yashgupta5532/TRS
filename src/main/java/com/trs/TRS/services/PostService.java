package com.trs.TRS.services;

import org.springframework.stereotype.Service;

import com.trs.TRS.models.Post;
import com.trs.TRS.repositories.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

}
