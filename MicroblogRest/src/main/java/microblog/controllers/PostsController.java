package microblog.controllers;

import org.springframework.web.bind.annotation.RestController;

import microblog.models.Post;
import microblog.models.User;
import microblog.repositories.MicroblogRespository;
import microblog.services.AlreadyVotedException;
import microblog.services.PostService;
import microblog.services.UserService;
import microblog.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class PostsController {
	
	@Autowired
	MicroblogRespository microblogRespository;
	
    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/post/create")
    public Post create(@RequestBody Map<String, String> body){
        int id = Integer.parseInt(body.get("id"));
        String title = body.get("title");
        String content = body.get("content");
        String userName = body.get("username");
        User user = userService.registerIfNeeded(userName);

        return microblogRespository.save(new Post(id, title, content, user));
    }
    
    @PutMapping("/post/{id}/update")
    public @ResponseBody String update(@PathVariable String id, @RequestBody Map<String, String> body) throws NotYourPostException{
        int postId = Integer.parseInt(id);
        String userName = body.get("username");

        // getting post
        Post post = microblogRespository.findOne(postId);
        if (!post.getAuthor().getUsername().equals(userName)) {
            throw new NotYourPostException("cannot update post that you didn't create");
        }
        User user = userService.registerIfNeeded(userName);
        post.setAuthor(user);
        post.setTitle(body.get("title"));
        post.setContent(body.get("content"));
        microblogRespository.save(post);
        return "ok";
    }

    @DeleteMapping("post/{id}/delete")
    public @ResponseBody String delete(@PathVariable String id, @RequestBody Map<String, String> body) throws NotYourPostException{
    	int postId = Integer.parseInt(id);
        String userName = body.get("username");

    	Post post = microblogRespository.findOne(postId);
       if (!post.getAuthor().getUsername().equals(userName)) {
            throw new NotYourPostException("cannot delete post that you didn't create");
       }
        microblogRespository.delete(postId);
        return "ok";
    }
    
    @PostMapping("/post/{id}/like")
    public @ResponseBody String like(@PathVariable int id, @RequestBody Map<String, String> body) {
        try {
            String userName = body.get("username");
            postService.vote(id, true, userName);
        } catch (AlreadyVotedException e) {
            return "already_voted";
        }

        return "ok";
    }
    
    @PostMapping("/post/{id}/dislike")
    public @ResponseBody String dislike(@PathVariable int id, @RequestBody Map<String, String> body) {
        try {
        	String userName = body.get("username");
            postService.vote(id, false, userName);
        } catch (AlreadyVotedException e) {
            return "already_voted";
        }

        return "ok";
    }
    
    
    @GetMapping(value = {"/post/top"}, headers="Accept=application/json", produces = "application/json;charset=UTF-8")
    public @ResponseBody String getTopPostsList() {
        List<Post> posts = postService.getTopPostsList();

        return "[" + posts.stream().map(this::toJsonLink).collect(Collectors.joining(", \n")) + "]";
    }
    
    private String toJsonLink(Post post) {
        return "{" + JsonUtils.toJsonField("id", String.valueOf(post.getId())) + ", " + JsonUtils.toJsonField("title", post.getTitle()) + "}";
    }

}
