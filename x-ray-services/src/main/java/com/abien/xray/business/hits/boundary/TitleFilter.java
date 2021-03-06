package com.abien.xray.business.hits.boundary;

import com.abien.xray.business.hits.control.TitleFetcher;
import com.abien.xray.business.hits.entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Adam Bien, blog.adam-bien.com
 */
public class TitleFilter {

    @Inject
    TitleFetcher cache;

    List<Post> getPostsWithExistingTitle(List<Post> mostPopularPosts, int max) {
        List<Post> mostPopularPostsWithTitle = new ArrayList<>();
        for (Post post : mostPopularPosts) {
            if (isBogus(post)) {
                continue;
            }
            fetchTitle(post);
            if (!post.isTitleEmpty()) {
                mostPopularPostsWithTitle.add(post);
            }
        }
        return trim(mostPopularPostsWithTitle, max);
    }

    boolean isBogus(Post post) {
        return this.cache.isBogus(post.getUri());
    }

    void fetchTitle(Post post) {
        String uri = post.getUri();
        String title = cache.getTitle(uri);
        post.setTitle(title);
    }

    List<Post> trim(List<Post> posts, int max) {
        int listSize = posts.size();
        if (listSize > max) {
            return posts.subList(0, max);
        }
        return posts;

    }

}
