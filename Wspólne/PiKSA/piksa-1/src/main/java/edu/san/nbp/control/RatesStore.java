// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.nbp.control;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import edu.san.nbp.entity.ExchangeRate;
import edu.san.nbp.entity.RatesTable;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class RatesStore {

  private final NbpFetcher nbpFetcher;
  private final Logger log;

  private final AtomicReference<RatesTable> current = new AtomicReference<>();

  public RatesStore(NbpFetcher nbpFetcher, Logger log) {
    this.nbpFetcher = nbpFetcher;
    this.log = log;
  }

  void onStart(@SuppressWarnings("unused") @Observes StartupEvent ev) {
    refresh();
  }

  public void refresh() {
    log.log(Level.INFO, "RatesStore::refresh()");
    try {
      // Jeśli fetch() zwróci empty (fallback), current pozostaje bez zmian — stale data.
      nbpFetcher.fetch().ifPresent(current::set);
    } catch (Exception e) {
      log.log(Level.WARNING, "Unexpected error during NBP fetch", e);
    }
  }

  public Optional<RatesTable> get() {
    return Optional.ofNullable(current.get());
  }

  public Optional<ExchangeRate> getByCode(String code) {
    return Optional.ofNullable(current.get())
        .stream()
        .flatMap(t -> t.rates().stream())
        .filter(r -> r.code().equalsIgnoreCase(code))
        .findFirst();
  }
}
