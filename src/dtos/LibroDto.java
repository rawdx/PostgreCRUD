package dtos;

public class LibroDto {
	private long idLibro;
	private String titulo;
	private String autor;
	private String isbn;
	
	public LibroDto(long idLibro, String titulo, String autor, String isbn) {
		super();
		this.idLibro = idLibro;
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
	}
	
	public long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(long idLibro) {
		this.idLibro = idLibro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@Override
	public String toString() {
		return "LibroDto [idLibro=" + idLibro + ", titulo=" + titulo + ", autor=" + autor + ", isbn=" + isbn + "]";
	}

}
