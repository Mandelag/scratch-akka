package com.mandelag.akka;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;

public class FileWatcherActor extends AbstractLoggingActor {

  private final Path path;
  private Thread watcherThread;

  public static Props props(String path) {
    return Props.create(FileWatcherActor.class, Paths.get(path));
  }

  public FileWatcherActor(Path path) {
    this.path = path;
  }

  @Override
  public void preStart() {
    watcherThread = new Thread(new FileWatcherRunnable(this.path, getSelf()));
    watcherThread.start();
    log().info(String.format("Watcher thread started on %s.", this.path.toAbsolutePath()));
  }

  @Override
  public AbstractActor.Receive createReceive() {
    return receiveBuilder()
        .match(WatchEvent.class, event -> {
          log().info(
              "Event type:" + event.context().toString() + ". " +
              "Event kind:" + event.kind() + ". " +
                  "File affected: " + event.context() + ".");
        })
        .matchAny(msg -> log().info(msg.toString()))
        .build();
  }

  @Override
  public void postStop() {
    watcherThread.interrupt();
    log().info("Watcher thread stopped.");
  }
}
