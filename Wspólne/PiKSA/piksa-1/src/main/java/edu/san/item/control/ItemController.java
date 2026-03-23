// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.item.control;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.UUID;

import edu.san.item.entity.Item;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemController {

  private final Logger log;

  public ItemController(Logger log) {
    this.log = log;
  }

  public Item createItem(String name, String description) {
    log.log(Level.INFO, "ItemController::createItem()");
    return new Item(UUID.randomUUID(), name, description);
  }

}
