import net.fwbrasil.activate.test.ActivateTest
import org.scalatest.{FlatSpec, Matchers}
import org.specs2.mutable._
import model.PersistanceContext._
import play.api.test._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@SerialVersionUID(101L)
class IntegrationSpec extends FlatSpec with ActivateTest with Matchers{

  "Application" should "work from within a browser" in new WithBrowser {

      browser.goTo("http://localhost:" + port)

      browser.pageSource should include("Node conventer")
    }
}
