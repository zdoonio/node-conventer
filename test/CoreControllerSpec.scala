import net.fwbrasil.activate.test.ActivateTest
import model.PersistanceContext._
import org.specs2.runner._
import org.junit.runner._
import org.scalatest.{FlatSpec, Matchers}
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@SerialVersionUID(100L)
class CoreControllerSpec extends FlatSpec with ActivateTest with Matchers {

  "Application" should "send 404 on a bad request" in new WithApplication {
      route(FakeRequest(GET, "/boum")) should equal(None)
    }

    "Application" should "render the index page" in new WithApplication {
      val home = route(FakeRequest(GET, "/")).get

      status(home) should equal(OK)
      contentAsString(home) should include ("Node conventer")
    }
}
