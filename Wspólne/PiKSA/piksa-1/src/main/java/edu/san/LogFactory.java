// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san;

import java.lang.System.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LogFactory {

  @SuppressWarnings("static-method")
  @Produces
  public Logger produceLogger(InjectionPoint ip) {
    return System.getLogger(ip.getMember().getDeclaringClass().getName());
  }

}
