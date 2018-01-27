package ch.bfh.eadj.presentation;

import ch.bfh.eadj.application.exception.BookAlreadyExistsException;
import ch.bfh.eadj.application.exception.BookNotFoundException;
import ch.bfh.eadj.application.service.CatalogService;
import ch.bfh.eadj.persistence.entity.Book;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("books")
public class CatalogResource {

	@EJB
	private CatalogService catalogService;

	@POST
	@Consumes(APPLICATION_JSON)
	public Response addBook(Book book) {
		try {
			catalogService.addBook(book);
			return Response.status(Status.CREATED).build();
		} catch (BookAlreadyExistsException ex) {
			throw new WebApplicationException(Status.CONFLICT);
		}
	}

	@GET
	@Path("{isbn}")
	@Produces(APPLICATION_JSON)
	public Book findBook(@PathParam("isbn") String isbn) {
		try {
			return catalogService.findBook(isbn);
		} catch (BookNotFoundException ex) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
}