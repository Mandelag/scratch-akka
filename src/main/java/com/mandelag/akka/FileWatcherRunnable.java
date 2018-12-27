package com.mandelag.akka;

import akka.actor.ActorRef;

import java.io.IOException;
import java.nio.file.*;

public class FileWatcherRunnable implements Runnable {

  private final Path path;
  private final ActorRef wrapper;

  public FileWatcherRunnable(Path path, ActorRef wrapper) {
    this.path = path;
    this.wrapper = wrapper;
  }

  @Override
  public void run() {
    try (WatchService ws = FileSystems.getDefault().newWatchService()) {
      path.register(ws,
//          StandardWatchEventKinds.ENTRY_CREATE,
          StandardWatchEventKinds.ENTRY_DELETE,
          StandardWatchEventKinds.ENTRY_MODIFY);
      WatchKey watchKey;
      while ( (watchKey = ws.take() ) != null) {
        for(WatchEvent<?> event : watchKey.pollEvents()) {
          wrapper.tell(event, wrapper);
        }
        watchKey.reset();
      }
    } catch (IOException | InterruptedException e) {
      System.err.println(e);
    }
  }
}
