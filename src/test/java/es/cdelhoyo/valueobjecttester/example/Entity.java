package es.cdelhoyo.valueobjecttester.example;

public class Entity {

	private Long id;
	private String name;
	
    public Long getId() {
		return id;
	}

    public String getName() {
		return name;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Entity other = (Entity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static abstract class Builder<B extends Builder<B, T>, T extends Entity> {

        protected T entity;

        public B withId(Long id) {
            ((Entity) entity).setId(id);
            return (B) this;
        }

        public B withName(String name) {
            ((Entity) entity).setName(name);
            return (B) this;
        }

        public abstract T build();
    }
}
