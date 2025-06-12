package simulation

import scala.collection.mutable.ArrayBuffer

object SimulatorExpress {

  def main(args: Array[String]): Unit = {
    System.out.println("Fire Forest Simulator Express - version 0.1 - Copyright © 2025 SIEDEL")


    //region Densité de forêt

    val nSim = 1
    val simX: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    val simY: ArrayBuffer[Int] = new ArrayBuffer[Int]()

    for (i:Int <- 0 to 99){

      var buffer:ArrayBuffer[Float] = new ArrayBuffer[Float]()

      for(s:Int <- 0 until nSim){

        val sim: Simulation = new Simulation(50, 50, i, 0)
        sim.simulExpress(200)
        buffer.append(sim.getFireInfo)

      }

      val result = buffer.foldLeft(0.0f)((s:Float, e:Float) => s + e) / nSim

      println(i + " : " + result)

      simX.append(i)
      simY.append(result.toInt)


    }

    println("*********************************************************************")
    println("Simulation : Densité de forêt")

    print("X=[")
    for (i <- simX)
      print(i + ", ")
    print("]")

    println()

    print("Y=[")
    for (i <- simY)
      print(i + ", ")
    print("]")

    println("\n*********************************************************************")

    //endregion


  }

}
