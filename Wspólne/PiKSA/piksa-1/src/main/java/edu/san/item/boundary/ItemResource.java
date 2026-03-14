package edu.san.item.boundary;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;

import edu.san.item.entity.Item;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/items")
@ApplicationScoped
public class ItemResource {

  private static final Logger LOG = System
      .getLogger(ItemResource.class.getName());

  public ItemResource() {
    LOG.log(Level.INFO, "ItemResource()");
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(Item item) {
    LOG.log(Level.INFO, "ItemResource::create()");
    return Response.status(Response.Status.CREATED).entity(item).build();
  }
}
