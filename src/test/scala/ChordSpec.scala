import java.util.concurrent.TimeUnit

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import com.typesafe.config.{Config, ConfigFactory}
import net.ceedubs.ficus.Ficus._
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.duration._

class ChordSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {
  import Akka_Chord.Chord.{distributedMapInitialized, initializeNodesWithConfig, keyLookup}
  import Akka_Chord.User.{Command, queryResponse}


  "Chord actor import values from application.conf" in {
    val receiver = createTestProbe[distributedMapInitialized]()
    val ChordActor = spawn(Chord())

    val config: Config = ConfigFactory.load("simpleData.conf")
    val configDictionary = config.as[Map[String, String]]("dictionary")

    ChordActor ! initializeNodesWithConfig(config, receiver.ref)
    val message = receiver.receiveMessage()
    val dictionary = message.dictionary
    dictionary.foreach(e => {
      configDictionary.get(e._1) should not equal (None)
    })
  }


    "Chord actor can query" in {
      val receiver_1 = createTestProbe[distributedMapInitialized]()
      val user = createTestProbe[Command]()
      val ChordActor = spawn(Chord())

      val config: Config = ConfigFactory.load("simpleData.conf")
      val configDictionary = config.as[Map[String, String]]("dictionary")

      ChordActor ! initializeNodesWithConfig(config, receiver_1.ref)
      val message = receiver_1.receiveMessage()
      ChordActor ! keyLookup("Heat", user.ref)
      val answer = user.receiveMessage(new FiniteDuration(20, TimeUnit.SECONDS))
      answer match {
        case queryResponse(key, value) =>
          value should not equal(None)
          println(" KEY:"+key+ " FOUND VALUE: "+ value.getOrElse("notFound"))
      }
    }




}
