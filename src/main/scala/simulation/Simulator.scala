package simulation

import java.awt.Graphics2D
import scala.collection.mutable.ArrayBuffer

object Simulator {

  var density:Int = 0
  var iteration:Int = 0

  var simulation:Int = 0
  var simX:ArrayBuffer[Int] = new ArrayBuffer[Int]()
  var simY:ArrayBuffer[Int] = new ArrayBuffer[Int]()


  var sim1:Simulation = new Simulation(100, 100, 0, 0, 0, 0, 0, 0)

  def init(): Unit = {
    sim1.init()
  }

  def update(): Unit = {

    sim1.update()

    iteration += 1

    if(iteration == 200){

      simX.append(density)
      simY.append(sim1.getFireInfo.toInt)

      sim1 = new Simulation(100, 100, density, 0, 0, 0, 0, 0, false, 0)

      simulation += 1
      density += 1
      iteration = 0

      if(density >= 99){      // 50°
        println("*********************************************************************\n")

        print("[")
        for (i <- simX)
          print(i + ", ")
        print("]")

        println()
        println()

        print("[")
        for (i <- simY)
          print(i + ", ")
        print("]")

        println("\n*********************************************************************")

        sys.exit(404)
      }


    }

  }

  def draw(g: Graphics2D): Unit = {

    // Draw cells
    sim1.draw(g)

  }


}
