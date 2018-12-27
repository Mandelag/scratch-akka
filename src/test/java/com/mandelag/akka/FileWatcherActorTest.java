package com.mandelag.akka;

import akka.actor.ActorSystem;
import org.junit.Test;

import java.io.IOException;

public class FileWatcherActorTest {

  @Test
  public void testWatch() throws IOException {
    ActorSystem system = ActorSystem.create("file-watcher-test");
    system.actorOf(FileWatcherActor.props("."));

    System.in.read();
    system.terminate();
  }
}
