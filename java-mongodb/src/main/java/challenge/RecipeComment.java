package challenge;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Classe para mapear o comentario da receita no MongoDB
 *
 */
public class RecipeComment {

	@JsonProperty(access = Access.READ_ONLY)
	private String id;

	private String comment;

	@JsonCreator
	public RecipeComment(@JsonProperty("comment") String comment) {
		this.comment = comment;
	}

	public String getId() {
		return id;
	}
	
	public void generateId() {
		this.id = ObjectId.get().toHexString();
	}

	public String getComment() {
		return comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeComment other = (RecipeComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
