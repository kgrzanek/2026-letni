package edu.san.greeting.boundary;

import edu.san.session.UserSession;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/hello")
public class GreetingResource {

  @Inject
  UserSession userSession;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "Hello from Open Liberty REST (visit #"
        + userSession.incrementAndGet()
        + " in this session)";
  }
}
