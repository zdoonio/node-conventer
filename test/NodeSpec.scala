import model.{Node, NodeData}
import model.PersistanceContext._
import org.scalatest._
import net.fwbrasil.activate.test.{ActivateTest, ActivateTestStrategy, cleanDatabaseStrategy}
import play.api.Play.current
import java.io.File



/**
  * Created by Dominik Zduńczyk on 20.04.19.
  */
@SerialVersionUID(102L)
class NodeSpec extends FlatSpec with ActivateTest with Matchers {


  "Parse nodes" should "parse node from xls file" in activateTest(defaultStrategy) {
    val file = new File("public/test/test1.xlsx")
    Node.parseNodes(file)

    val mainNodes = Node.getMainNodeList

    mainNodes.size should equal(4)

  }

  override protected def defaultStrategy: ActivateTestStrategy = cleanDatabaseStrategy

 "Nodes main size " should "be 4" in activateTest(defaultStrategy) {
   for(i <- 0 to 3)
     Node(i, s"test$i", None)

   Node.getMainNodeList.size should equal (4)

 }

  "Create nodes" should "saved" in activateTest(defaultStrategy) {
    Node.create(NodeData(1, "test1", List()), List(NodeData(2, "test2", List()), NodeData(3, "test3", List())))

    val node = Node.getMainNodeList.head
    val subNodes = Node.getSubNodeList(node)

    node.name should equal("test1")
    subNodes.size should equal(2)
  }

}
