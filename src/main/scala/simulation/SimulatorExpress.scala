package simulation

import scala.collection.mutable.ArrayBuffer

object SimulatorExpress {

  def main(args: Array[String]): Unit = {
    System.out.println("Fire Forest Simulator Express - version 0.1 - Copyright © 2025 SIEDEL")

    val simX: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    val simY: ArrayBuffer[Int] = new ArrayBuffer[Int]()

    //region Densité de forêt

    var nSim = 1

    for (i:Int <- 0 to 99){

      var buffer:ArrayBuffer[Float] = new ArrayBuffer[Float]()

      for(s:Int <- 0 until nSim){

        val sim: Simulation = new Simulation(50, 50, i, 0)
        sim.update(200)
        buffer.append(sim.getFireInfo)

      }

      val result = buffer.foldLeft(0.0f)((s:Float, e:Float) => s + e) / nSim

      //println(i + " : " + result)

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


    // region Densité + Vent Violent

    /*nSim = 50
    simX.clear()
    simY.clear()

    for (i: Int <- 0 to 99) {

      var buffer: ArrayBuffer[Float] = new ArrayBuffer[Float]()

      for (s: Int <- 0 until nSim) {

        val sim: Simulation = new Simulation(50, 50, i, 0, 0, Math.PI/5, 5)
        sim.simulExpress(200)
        buffer.append(sim.getFireInfo)

      }

      val result = buffer.foldLeft(0.0f)((s: Float, e: Float) => s + e) / nSim

      println(i + " : " + result)

      simX.append(i)
      simY.append(result.toInt)

    }

    println("*********************************************************************")
    println("Simulation : Densité de forêt + Vent Violent")

    print("X=[")
    for (i <- simX)
      print(i + ", ")
    print("]")

    println()

    print("Y=[")
    for (i <- simY)
      print(i + ", ")
    print("]")

    println("\n*********************************************************************")*/

    //endregion


    // region Humidité

    /*nSim = 1
    simX.clear()
    simY.clear()

    for (i: Int <- 0 to 99) {

      var buffer: ArrayBuffer[Float] = new ArrayBuffer[Float]()

      for (s: Int <- 0 until nSim) {

        val sim: Simulation = new Simulation(50, 50, 50, 0, 0, 0, 0, i)
        sim.simulExpress(200)
        buffer.append(sim.getFireInfo)

      }

      val result = buffer.foldLeft(0.0f)((s: Float, e: Float) => s + e) / nSim

      //println(i + " : " + result)

      simX.append(i)
      simY.append(result.toInt)

    }

    println("*********************************************************************")
    println("Simulation : Humidité")

    print("X=[")
    for (i <- simX)
      print(i + ", ")
    print("]")

    println()

    print("Y=[")
    for (i <- simY)
      print(i + ", ")
    print("]")

    println("\n*********************************************************************")*/

    //endregion


    // region Repousse après incendie

    nSim = 1
    simX.clear()
    simY.clear()

    val sim: Simulation = new Simulation(50, 50, 1, 0, 0, 0, 0, 0, true)

    for (i: Int <- 0 to 99) {
      sim.update(1)
      simX.append(i)
      simY.append(sim.getTreeInfo.toInt)
    }

    println("*********************************************************************")
    println("Simulation : Repousse de la forêt après incendie")

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
