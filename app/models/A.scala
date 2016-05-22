package models

import play.modules.reactivemongo.ReactiveMongoApi
import play.api.Play.current
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.FailoverStrategy
//import reactivemongo.play.json.collection.JSONCollection
import scala.concurrent.duration._
object A {
  def reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  def db = reactiveMongoApi.db.copy(failoverStrategy = FailoverStrategy(
    1 second, 5, n => 1
  ))
  def collection: JSONCollection = db.collection[JSONCollection]("A")
}
