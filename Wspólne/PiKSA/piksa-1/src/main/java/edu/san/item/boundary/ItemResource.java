package edu.san.item.boundary;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;

import edu.san.item.control.ItemController;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@RunOnVirtualThread
@Path("/items")
public class ItemResource {

  private final ItemController itemController;

  private final Logger log;

  public ItemResource(ItemController itemController, Logger log) {
    this.log = log;
    this.itemController = itemController;
    log.log(Level.INFO, "ItemResource()");
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(@Valid CreateItemCmd cmd) {
    log.log(Level.DEBUG, "ItemResource::create()");
    var item = itemController.createItem(cmd.name(), cmd.description());
    var response = new ItemCreatedResponse(item.id());
    return Response.status(Response.Status.CREATED).entity(response).build();
  }
}
