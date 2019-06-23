package com.delay;

import akka.actor.*;
import akka.actor.*;
import com.typesafe.config.ConfigFactory;

/**
 * Hello world!
 *
 */
public class HelloMainSimple 
{
    public static void main( String[] args )
    {
		run();
    }
	
    public static void run( )
    {
		ActorSystem system = ActorSystem.create("Hello",ConfigFactory.load("samplehello.conf"));
		ActorRef a = system.actorOf(Props.create(HelloWorld.class),"helloWorld");
		System.out.println("HelloWorld ActorPath:"+a.path());
    }
}
