package microblog.services.impl;

import microblog.repositories.PostRatingRepository;
import microblog.services.AlreadyVotedException;
import microblog.services.PostService;
import microblog.services.UserService;
import microblog.models.Post;
import microblog.models.PostRating;
import microblog.models.Rating;
import microblog.models.User;
import microblog.repositories.MicroblogRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postService")
public class PostServiceImpl implements PostService
{
    @Autowired
    private MicroblogRespository microblogRepository;

    @Autowired
    private PostRatingRepository postRatingRepository;
    
    @Autowired
    private UserService userService;
    
    @Override
    public Post getPost(int id) {
        return microblogRepository.findOne(id);
    }

    // probably needs to be cached somehow
    @Override
    public List<Post> getTopPostsList() {
        PageRequest pageRequest = new PageRequest(0, 10);

        return microblogRepository.findTopPosts(pageRequest);
    }

    @Override
    public void vote(int postId, boolean like, String userName) throws AlreadyVotedException {
        User currentUser = userService.registerIfNeeded(userName);

        Post post = getPost(postId);

        PostRating rating = postRatingRepository.findUserRating(postId, currentUser.getId());
        short currentVote = like ? Rating.LIKE_VALUE : Rating.DISLIKE_VALUE;
        if (rating != null) {
            short existingVote = rating.getValue();
            if(existingVote == currentVote)
            {
            	throw new AlreadyVotedException("cannot cast the same vote more than once");
            }
        }
        else {
            rating = new PostRating();
            rating.setPost(post);
            rating.setUser(currentUser);
        }


        rating.setValue(currentVote);
        postRatingRepository.saveAndFlush(rating);
    }
}
