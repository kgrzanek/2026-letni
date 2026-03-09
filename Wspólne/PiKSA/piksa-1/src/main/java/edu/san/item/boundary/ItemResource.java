package edu.san.item.boundary;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;

import edu.san.item.entity.Item;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/items")
public class ItemResource {

  private final Logger log;

  public ItemResource(Logger log) {
    this.log = log;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(Item item) {
    log.log(Level.DEBUG, "ItemResource::create()");
    return Response.status(Response.Status.CREATED).entity(item).build();
  }
}
