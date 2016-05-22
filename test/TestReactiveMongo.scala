import org.specs2.main.Arguments
import org.specs2.mutable.{Specification, SpecificationLike}
import de.flapdoodle.embed.mongo.config.{MongoCmdOptionsBuilder, MongodConfigBuilder, Net}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import de.flapdoodle.embed.mongo.MongodStarter
import org.specs2.specification.core.Fragments
import play.api.test.{FakeApplication, WithApplication}

trait EmbedConnection extends Specification {
  self: SpecificationLike =>
  isolated

  def sequentialyIsolated: Arguments = args(isolated = true, sequential = true)

  override def sequential: Arguments = args(isolated = false, sequential = true)

  override def isolated: Arguments = args(isolated = true, sequential = false)

  //Override this method to personalize testing port
  def embedConnectionPort(): Int = {
    12345
  }

  //Override this method to personalize MongoDB version
  def embedMongoDBVersion(): Version.Main = {
    Version.Main.DEVELOPMENT
  }

  lazy val network = new Net(embedConnectionPort, Network.localhostIsIPv6)

  lazy val mongodConfig = new MongodConfigBuilder()
    .version(embedMongoDBVersion)
    .net(network)
    .cmdOptions(new MongoCmdOptionsBuilder().useNoJournal(false).build())
    .build

  lazy val runtime = MongodStarter.getDefaultInstance

  lazy val mongodExecutable = runtime.prepare(mongodConfig)

  override def map(fs: => Fragments) = startMongo ^ fs ^ stopMongo

  private def startMongo() = {
    step({
      mongodExecutable.start;
      success
    })
  }

  private def stopMongo() = {
    step({
      mongodExecutable.stop
      success
    })
  }
}

object TestApplication {
  val configuration = Map(
    "mongodb.uri" -> "mongodb://localhost:12345/test"
  )

  def apply() = new FakeApplication(additionalConfiguration = configuration)
}

class TestReactiveMongo extends Specification with EmbedConnection {
  sequential

  "This test" should {
    "pass" in new WithApplication(TestApplication()) {
      true should beTrue
    }
  }
}
