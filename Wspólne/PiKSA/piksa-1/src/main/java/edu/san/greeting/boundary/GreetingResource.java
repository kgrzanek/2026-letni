package edu.san.greeting.boundary;

import edu.san.session.UserSession;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@RunOnVirtualThread
@Path("/hello")
public class GreetingResource {

  private final UserSession userSession;

  public GreetingResource(UserSession userSession) {
    this.userSession = userSession;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "Hello from Quarkus REST (visit #" + userSession.incrementAndGet()
        + " in this session)";
  }
}
