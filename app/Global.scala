import models.A
import play.api.{GlobalSettings, Application}
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.indexes.{IndexType, Index}
//import reactivemongo.play.json.collection.JSONCollection
import scala.concurrent.ExecutionContext.Implicits.global

object Global extends GlobalSettings {

  def ensureIndex(collection: JSONCollection, index: Index, retry: Int = 10): Unit = {
    println(s"trying to ensure index: $index $retry")
    collection.indexesManager.ensure(index) map {
      r =>
        println("returned from ensuring index")
        println(r)
    } recover {
      case t: Throwable =>
        if (retry > 0)
          ensureIndex(collection, index, retry - 1)
        else throw t
    }
  }

  override def onStart(app: Application) {
    ensureIndex(A.collection, Index(Seq("a1" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a2" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a3" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a4" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a5" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a6" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a7" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a8" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a9" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a0" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a10" -> IndexType.Ascending)))
    ensureIndex(A.collection, Index(Seq("a11" -> IndexType.Ascending)))
    println("\n\nStarting on start")
  }
}
