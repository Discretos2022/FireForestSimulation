import javax.swing.JFrame
import scala.util.Random

object Main {

  def main(args: Array[String]): Unit = {

    var world:Array[Array[Int]] = initGrid(20, 20)

    while(true){
      printGrid(world)
      world = updateGrid(world)
      print(Console.RESET)
      Thread.sleep(500)
    }



  }

  // Initialise une nouvelle grille
  def initGrid(w:Int, h:Int):Array[Array[Int]] = {

    val array:Array[Array[Int]] = Array.ofDim(w, h)

    for (i <- 0 until w) {
      for (j <- 0 until h){
        array(i)(j) = 0 //Random.between(0, 4)
      }
    }

    array(0)(0) = 1
    array(w-1)(h-1) = 1

    return array
  }

  // Met à jour la grille
  def updateGrid(world:Array[Array[Int]]): Array[Array[Int]] = {

    val w:Int = world.length
    val h:Int = world(0).length
    val newWorld:Array[Array[Int]] = Array.ofDim(world.length, world(0).length)

    for (i <- 0 until w) {
      for (j <- 0 until h) {

        if (world(i)(j) == Cell.Void){

          if(i > 0)
            if (world(i-1)(j) == Cell.Tree){
              newWorld(i)(j) = Cell.Tree
            }

          if (j > 0)
            if (world(i)(j-1) == Cell.Tree) {
              newWorld(i)(j) = Cell.Tree
            }

          if (i < w-1)
            if (world(i + 1)(j) == Cell.Tree) {
              newWorld(i)(j) = Cell.Tree
            }

          if (j < h-1)
            if (world(i)(j + 1) == Cell.Tree) {
              newWorld(i)(j) = Cell.Tree
            }


          if (i > 0 && j > 0)
            if (world(i - 1)(j - 1) == Cell.Tree) {
              newWorld(i)(j) = Cell.Tree
            }

          if (i < w-1 && j > 0)
            if (world(i + 1)(j - 1) == Cell.Tree) {
              newWorld(i)(j) = Cell.Tree
            }

          if (i < w-1 && j < h-1)
            if (world(i + 1)(j + 1) == Cell.Tree) {
              newWorld(i)(j) = Cell.Tree
            }

          if (i > 0 && j < h-1)
            if (world(i - 1)(j + 1) == Cell.Tree) {
              newWorld(i)(j) = Cell.Tree
            }

        }
        else
          newWorld(i)(j) = world(i)(j)

      }
    }

    return newWorld

  }

  // Print la grille
  def printGrid(array:Array[Array[Int]]): Unit = {

    println("-" * (array.length * 3 - 2))
    for (i <- 0 until array.length) {

      var str:String = ""
      for (j <- 0 until array(i).length) {
        if (array(i)(j) == Cell.Tree)
          str += "⍋  " //"\uD83C\uDF32 "
        //else if(array(i)(j) == Cell.Fire)
          //str += "\uD83D\uDF02  "
        else
          str += "   " //array(i)(j) + "  "
      }
      println(str)
    }
    println("-" * (array.length * 3 - 2))

  }

}
