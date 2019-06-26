package com.delay.Tutorial.serializition;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.persistence.SnapshotOffer;
import akka.persistence.UntypedPersistentActor;
import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import static java.util.Arrays.asList;

class Cmd implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String data;

    public Cmd(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

class Evt implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String data;
    private final String uuid;

    public Evt(String data, String uuid) {
        this.data = data;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getData() {
        return data;
    }
}

class ExampleState implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ArrayList<String> events;

    public ExampleState() {
        this(new ArrayList<String>());
    }

    public ExampleState(ArrayList<String> events) {
        this.events = events;
    }

    public ExampleState copy() {
        return new ExampleState(new ArrayList<String>(events));
    }

    public void update(Evt evt) {
        events.add(evt.getData());
    }

    public int size() {
        return events.size();
    }

    @Override
    public String toString() {
        return events.toString();
    }
}

class ExamplePersistentActor extends UntypedPersistentActor {

    LoggingAdapter log = Logging.getLogger(getContext().system (), this );

    @Override
    public String persistenceId() { return "sample-id-1"; }

    private ExampleState state = new ExampleState();

    public int getNumEvents() {
        return state.size();
    }

    /**
     * Called on restart. Loads from Snapshot first, and then replays Journal Events to update state.
     * @param msg
     */
    @Override
    public void onReceiveRecover(Object msg) {
        log.info("onReceiveRecover: " + JSON.toJSONString(msg));
        if (msg instanceof Evt) {
            log.info("onReceiveRecover -- msg instanceof Event");
            log.info("event --- " + ((Evt) msg).getData());
            state.update((Evt) msg);
        } else if (msg instanceof SnapshotOffer) {
            log.info("onReceiveRecover -- msg instanceof SnapshotOffer");
            state = (ExampleState)((SnapshotOffer)msg).snapshot();
        } else {
          unhandled(msg);
        }
    }

    /**
     * Called on Command dispatch
     * @param msg
     */
    @Override
    public void onReceiveCommand(Object msg) {
        log.info("onReceiveCommand: " + JSON.toJSONString(msg));
        if (msg instanceof Cmd) {
            final String data = ((Cmd)msg).getData();

            // generate an event we will persist after being enriched with a uuid
            final Evt evt1 = new Evt(data + "-" + getNumEvents(), UUID.randomUUID().toString());
            final Evt evt2 = new Evt(data + "-" + (getNumEvents() + 1), UUID.randomUUID().toString());

            // persist event and THEN update the state of the processor
            persistAll(asList(evt1, evt2), evt -> {
                state.update(evt);
                if (evt.equals(evt2)) {
                    // broadcast event on eventstream 发布该事件
                    getContext().system().eventStream().publish(evt);
                }
            });
        } else if (msg.equals("snap")) {
            // IMPORTANT: create a copy of snapshot
            // because ExampleState is mutable !!!
            saveSnapshot(state.copy());
        } else if (msg.equals("print")) {
            System.out.println("state:  " + state);
        } else {
            unhandled(msg);
        }
    }
}

class EventHandler extends UntypedActor {


    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object msg ) throws Exception {
        log.info( "Handled Event: " + JSON.toJSONString(msg));
    }
}


public class MainTest {

  public static final Logger log = LoggerFactory.getLogger(System.class);

  public static void main(String... args) throws Exception {

    final ActorSystem actorSystem = ActorSystem.create("actor-server");

    final ActorRef handler = actorSystem.actorOf(Props.create(EventHandler. class));
    // 订阅
    actorSystem.eventStream().subscribe(handler , Evt.class);

    Thread.sleep(5000);

    final ActorRef actorRef = actorSystem.actorOf(Props.create(ExamplePersistentActor. class), "eventsourcing-processor" );

    actorRef.tell( new Cmd("CMD 1" ), null);
    actorRef.tell( new Cmd("CMD 2" ), null);
    actorRef.tell( new Cmd("CMD 3" ), null);
    actorRef.tell( "snap", null );//发送保存快照命令
    actorRef.tell( new Cmd("CMD 4" ), null);
    actorRef.tell( new Cmd("CMD 5" ), null);
    actorRef.tell( "print", null );

    Thread.sleep(5000);

    log.info( "Actor System Shutdown Starting..." );

    actorSystem.shutdown();
  }
}
