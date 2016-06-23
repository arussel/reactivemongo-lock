package models

import play.api.Play.current
import play.api.libs.json.{JsString, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.FailoverStrategy
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

object A {
  def reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  def db = reactiveMongoApi.database.map(_.copy(failoverStrategy = FailoverStrategy(
    1 second, 5, n => 1
  )))

  def collection: Future[JSONCollection] = db.map(_.collection[JSONCollection]("A"))

  def myaggregate() = collection.flatMap {
    coll =>
      import coll.BatchCommands.AggregationFramework._
      coll.aggregate(Group(JsString("$state"))("totalPop" -> SumField("population")),
        List(Match(Json.obj("totalPop" -> Json.obj("$gte" -> 10000000L)))))
  }
}
