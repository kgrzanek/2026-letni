// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.nbp.boundary;

import edu.san.nbp.control.RatesStore;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@RunOnVirtualThread
@Path("/rates")
public class RatesResource {

  private final RatesStore ratesStore;

  public RatesResource(RatesStore ratesStore) {
    this.ratesStore = ratesStore;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRates() {
    return ratesStore.get()
        .<Response>map(t -> Response.ok(t.rates()).build())
        .orElse(Response.status(Response.Status.SERVICE_UNAVAILABLE).build());
  }

  @GET
  @Path("/{code}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRate(@PathParam("code") String code) {
    return ratesStore.getByCode(code)
        .<Response>map(r -> Response.ok(r).build())
        .orElse(Response.status(Response.Status.NOT_FOUND).build());
  }

  @POST
  @Path("/refresh")
  public Response refresh() {
    ratesStore.refresh();
    return Response.noContent().build();
  }
}
