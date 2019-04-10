package challenge;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Classe para mapear a receita no MongoDB
 *
 */
@Document(collection = "recipe")
@JsonInclude(Include.NON_EMPTY)
public class Recipe {

	@Id
	@JsonProperty(access = Access.READ_ONLY)
	private String id;

	private String title;

	private String description;

	@JsonProperty(access = Access.READ_ONLY)
	private Collection<String> likes;

	private Collection<String> ingredients;

	@JsonProperty(access = Access.READ_ONLY)
	private Collection<RecipeComment> comments;

	public Recipe update(Recipe recipe) {
		this.title = recipe.title;
		this.description = recipe.description;
		this.ingredients = recipe.ingredients;
		return this;
	}

	public void like(String userId) {
		if (userId != null && !StringUtils.isEmpty(userId)) {
			this.likes.add(userId);
		}
	}

	public void unlike(String userId) {
		if (userId != null && !StringUtils.isEmpty(userId)) {
			this.likes.remove(userId);
		}
	}

	public void addComment(RecipeComment recipeComment) {
		if (recipeComment != null) {
			recipeComment.generateId();
			this.comments.add(recipeComment);
		}
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Collection<String> getIngredients() {
		return ingredients;
	}

	public Collection<RecipeComment> getComments() {
		return comments;
	}

	public Collection<String> getLikes() {
		return likes;
	}

}
