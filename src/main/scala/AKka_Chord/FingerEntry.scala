package AKka_Chord

import Akka_Chord.Node
import akka.actor.typed.ActorRef


class FingerEntry(start: Int, var interval: Interval, var node: ActorRef[Node.Command]) {
  def setNode(node: ActorRef[Node.Command]): Unit =
    this.node = node
  def getNode: ActorRef[Node.Command] =
    this.node
  def getStart: Int =
    this.start
  def getInterval: Interval =
    this.interval
  def set_end(end: Int): Unit =
    this.interval = new Interval(start, end)
}
