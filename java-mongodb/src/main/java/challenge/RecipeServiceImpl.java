package challenge;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository repository;

	@Autowired
	private MongoTemplate template;

	@Override
	public Recipe save(Recipe recipe) {
		return repository.save(recipe);
	}

	private Optional<Recipe> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public void update(String id, Recipe recipe) {
		this.findById(id).ifPresent(
				(obtainedRecipe)-> this.repository.save(obtainedRecipe.update(recipe)) );
	}

	@Override
	public void delete(String id) {
		this.findById(id).ifPresent((recipe)->this.repository.delete(recipe));
	}

	@Override
	public Recipe get(String id) {
		return this.findById(id).orElse(null);
	}

	@Override
	public List<Recipe> listByIngredient(String ingredient) {

		return this.repository.findByIngredientsOrderByTitle(ingredient);
	}

	@Override
	public List<Recipe> search(String searchedText) {

		Query query = new Query();

		query.addCriteria(new Criteria().orOperator(
				Criteria.where("title").regex(searchedText, "i"),
				Criteria.where("description").regex(searchedText, "i")));

		query.with(new Sort(Sort.Direction.ASC, "title"));
		
		return template.find(query, Recipe.class);
	}

	@Override
	public void like(String id, String userId) {
		Recipe recipe = this.get(id);
		if(recipe != null) {
			recipe.like(userId);
			this.repository.save(recipe);
		}
	}

	@Override
	public void unlike(String id, String userId) {
		Recipe recipe = this.get(id);
		if(recipe != null) {
			recipe.unlike(userId);
			this.repository.save(recipe);
		}
	}

	@Override
	public RecipeComment addComment(String id, RecipeComment comment) {
		RecipeComment addedComment = null;
		
		Recipe recipe = this.get(id);
		if(recipe != null) {
			recipe.addComment(comment);
			this.repository.save(recipe);
			addedComment = comment;
		}		
		
		return addedComment;
	}

	@Override
	public void updateComment(String id, String commentId, RecipeComment comment) {
		Query query = this.findComment(id, commentId);
		Update update = new Update().set("comments.$.comment", comment.getComment());
		template.updateFirst(query, update, Recipe.class);
	}

	@Override
	public void deleteComment(String id, String commentId) {
		Query query = this.findComment(id, commentId);
		Update update = new Update().pull("comments", new BasicDBObject("_id", commentId));
		template.updateFirst(query, update, Recipe.class);
	}

	private Query findComment(String id, String commentId) {
		return new Query(new Criteria().andOperator(Criteria.where("_id").is(id),
				Criteria.where("comments").elemMatch(Criteria.where("_id").is(commentId))));
	}

}
