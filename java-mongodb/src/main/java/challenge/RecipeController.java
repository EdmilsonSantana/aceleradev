package challenge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

	@Autowired
	private RecipeService service;

	@PostMapping(produces="application/json")
	public Recipe save(@RequestBody Recipe recipe) {
		return service.save(recipe);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable String id, @RequestBody Recipe recipe) {
		service.update(id, recipe);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		service.delete(id);
	}

	@GetMapping(value="/{id}", produces="application/json")
	public Recipe get(@PathVariable String id) {
		return service.get(id);
	}

	@GetMapping(value="/ingredient", produces="application/json")
	public List<Recipe> listByIngredient(@RequestParam("ingredient") String ingredient) {
		return service.listByIngredient(ingredient);
	}

	@GetMapping(value="/search",produces="application/json")
	public List<Recipe> search(@RequestParam("search") String searchedText) {
		return service.search(searchedText);
	}

	@PostMapping("/{id}/like/{userId}")
	public void like(@PathVariable("id") String id, @PathVariable("userId") String userId) {
		service.like(id, userId);
	}

	@DeleteMapping("/{id}/like/{userId}")
	public void unlike(@PathVariable("id") String id, @PathVariable("userId") String userId) {
		service.unlike(id, userId);
	}

	@PostMapping(value="{id}/comment", produces="application/json")
	public RecipeComment addComment(@PathVariable("id") String id, @RequestBody RecipeComment comment) {
		return service.addComment(id, comment);
	}

	@PutMapping("/{id}/comment/{commentId}")
	public void updateComment(@PathVariable String id, @PathVariable String commentId,
			@RequestBody RecipeComment comment) {
		service.updateComment(id, commentId, comment);
	}

	@DeleteMapping("/{id}/comment/{commentId}")
	public void deleteComment(@PathVariable String id, @PathVariable String commentId) {
		service.deleteComment(id, commentId);
	}

}
