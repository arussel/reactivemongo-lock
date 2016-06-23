import org.specs2.mutable.Specification
import play.api.test.{FakeApplication, WithApplication}

object TestApplication {
  val configuration = Map(
    "mongodb.uri" -> "mongodb://localhost:27017/test"
  )

  def apply() = new FakeApplication(additionalConfiguration = configuration)
}

class TestReactiveMongo extends Specification {
  sequential

  "This test" should {
    "pass" in new WithApplication(TestApplication()) {
      true should beTrue
    }
  }
}
