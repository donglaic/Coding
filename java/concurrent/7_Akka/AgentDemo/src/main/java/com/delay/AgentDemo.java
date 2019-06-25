package com.delay;

import akka.actor.*;
import akka.agent.*;
import scala.concurrent.Future;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import scala.concurrent.duration.Duration;
import com.typesafe.config.ConfigFactory;
import akka.dispatch.OnComplete;
import akka.dispatch.Futures;
import akka.dispatch.ExecutionContexts;


public class AgentDemo{
	public static Agent<Integer> counterAgent = Agent.create(0,ExecutionContexts.global());
	static ConcurrentLinkedQueue<Future<Integer>> futures = new ConcurrentLinkedQueue<Future<Integer>>();

	public static void main(String[] args) throws InterruptedException, TimeoutException{
		final ActorSystem system = ActorSystem.create("agentdemo",ConfigFactory.load("samplehello.conf"));
		ActorRef[] counter = new ActorRef[10];
		for (int i=0;i<counter.length;i++){
			counter[i] = system.actorOf(Props.create(CounterActor.class),"counter_"+i);
		}
		final Inbox inbox = Inbox.create(system);
		for(int i=0;i<counter.length;i++){
			inbox.send(counter[i],1);
			inbox.watch(counter[i]);
		}

		int closeCount = 0;
		while(true){
			Object msg = inbox.receive(Duration.create(1,TimeUnit.SECONDS));
			if(msg instanceof Terminated){
				closeCount++;
				if(closeCount==counter.length){
					break;
				}
			}else{
				System.out.println(msg);
			}
		}

		Futures.sequence(futures,system.dispatcher()).onComplete(
			new OnComplete<Iterable<Integer>>(){
				@Override
				public void onComplete(Throwable arg0,Iterable<Integer> arg1) throws Throwable{
					System.out.println("counterAgent="+counterAgent.get());
					system.terminate();
				}
			},system.dispatcher());
	}
}
