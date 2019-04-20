import model.{Node, NodeData}
import net.fwbrasil.activate.test.ActivateTest
import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json
import play.api.test.Helpers._
import model.PersistanceContext._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@SerialVersionUID(100L)
class ApiControllerSpec extends FlatSpec with ActivateTest with Matchers {

  "Application" should "get content Json" in new WithApplication {
    transactional(Node.create(NodeData(1, "test1", List()), List(NodeData(2, "test2", List()), NodeData(3, "test3", List()))))
      val node = route(FakeRequest(GET, "/node")).get

      status(node) should equal(OK)
      contentAsJson(node) should equal (Json.parse("""{ "nodes":[{"nodeId":1,"name":"test1","nodes":[{"nodeId":3,"name":"test3","nodes":[]},{"nodeId":2,"name":"test2","nodes":[]}]}]}"""))
    }
}


