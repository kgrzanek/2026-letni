// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.session;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.enterprise.context.SessionScoped;

@SessionScoped
public class UserSession implements Serializable {

  private final AtomicLong requestCount = new AtomicLong(0);

  public long incrementAndGet() {
    return requestCount.incrementAndGet();
  }

  public long getRequestCount() {
    return requestCount.get();
  }
}
