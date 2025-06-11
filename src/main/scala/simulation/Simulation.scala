package simulation

import java.awt.{Color, Graphics2D}
import java.util.Random
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

object Simulation {

  /*def main(args: Array[String]): Unit = {

    var world:Array[Array[Int]] = initGrid(20, 20)

    while(true){
      printGrid(world)
      world = updateGrid(world)
      print(Console.RESET)
      Thread.sleep(500)
    }



  }*/

  // Initialise une nouvelle grille
  /*def initGrid(w:Int, h:Int):Array[Array[Int]] = {

    val array:Array[Array[Int]] = Array.ofDim(w, h)

    for (i <- 0 until w) {
      for (j <- 0 until h){
        array(i)(j) = 0 //Random.between(0, 4)
      }
    }

    array(0)(0) = 1
    array(w-1)(h-1) = 1


    for(i <- 0 until 10)
      array(5)(i) = Cell.Stone



    // array(19)(5) = Cell.Stone
    // array(15)(10) = Cell.Stone

    return array
  }*/

  def initGrid(w:Int, h:Int, density:Int = 80):Array[Array[Cell]] = {
    val array = Array.tabulate(w, h)((x, y) => new Void(x, y).asInstanceOf[Cell])

    /** PLACE INITIAL STONE **/
    //for (i <- 0 until 10)
      //array(5)(i) = new Stone(5, i)

    /** PLACE INITIAL TREE **/
    //array(0)(0) = new Tree(0, 0)
    //array(w - 1)(h - 1) = new Tree(w-1, h-1)

    /** PLACE INITIAL FIRE **/
    val x: Int = new Random().nextInt(0, w)
    val y: Int = new Random().nextInt(0, h)
    array(x)(y) = new Fire(x, y)

    /** PLACE INITIAL RANDOM FOREST **/
    val nTree:Int = (w * h * (density / 100.0)).toInt

    @tailrec
    def placeTree(nTree: Int, grid: Array[Array[Cell]]): Array[Array[Cell]] = {
      if (nTree == 0)
        return grid
      else {
        val x: Int = new Random().nextInt(0, w)
        val y: Int = new Random().nextInt(0, h)

        if (grid(x)(y).isInstanceOf[Void]) {
          grid(x)(y) = new Tree(x, y)
          return placeTree(nTree - 1, grid)
        }
        else
          return placeTree(nTree, grid)
      }
    }

    return placeTree(nTree, array)


    /** PLACE INITIAL RIVER **/
    //for (i <- 0 until 20)
      //array(i)(13) = new Water(i, 13)

    array
  }



  // Met à jour la grille
  def updateGrid(world:Array[Array[Cell]]): Array[Array[Cell]] = {

    val w:Int = world.length
    val h:Int = world(0).length
    val newWorld:Array[Array[Cell]] = world.map(_.clone())

    for (i <- 0 until w) {
      for (j <- 0 until h) {

        val c: Cell = world(i)(j)

        c match {

          case void: Void => {

            //region Repousse

            var treeCounter = 0
            var fireCounter = 0

            // Horizontale / Verticale
            if (i > 0) {
              world(i - 1)(j) match {
                case _: Tree => treeCounter += 1
                case _: Fire => fireCounter += 1
                case _ =>
              }
            }

            if (j > 0) {
              world(i)(j - 1) match {
                case _: Tree => treeCounter += 1
                case _: Fire => fireCounter += 1
                case _ =>
              }
            }

            if (i < w - 1) {
              world(i + 1)(j) match {
                case _: Tree => treeCounter += 1
                case _: Fire => fireCounter += 1
                case _ =>
              }
            }

            if (j < h - 1) {
              world(i)(j + 1) match {
                case _: Tree => treeCounter += 1
                case _: Fire => fireCounter += 1
                case _ =>
              }
            }

            // Diagonale
            if (i > 0 && j > 0) {
              world(i - 1)(j - 1) match {
                case _: Tree => treeCounter += 1
                case _: Fire => fireCounter += 1
                case _ =>
              }

            }

            if (i < w - 1 && j > 0) {
              world(i + 1)(j - 1) match {
                case _: Tree => treeCounter += 1
                case _: Fire => fireCounter += 1
                case _ =>
              }
            }

            if (i < w - 1 && j < h - 1) {
              world(i + 1)(j + 1) match {
                case _: Tree => treeCounter += 1
                case _: Fire => fireCounter += 1
                case _ =>
              }
            }

            if (i > 0 && j < h - 1) {
              world(i - 1)(j + 1) match {
                case _: Tree => treeCounter += 1
                case _: Fire => fireCounter += 1
                case _ =>
              }
            }

            if(treeCounter > 0 && fireCounter == 0)
              newWorld(i)(j) = Cell.tryTree(i, j, temperature)

            //endregion

          }


          case tree: Tree => {

            //region Prise de Feu

            var fireCounter = 0

            if (i > 0)
              if (world(i - 1)(j).isInstanceOf[Fire])
                fireCounter += 1

            if (j > 0)
              if (world(i)(j - 1).isInstanceOf[Fire])
                fireCounter += 1

            if (i < w - 1)
              if (world(i + 1)(j).isInstanceOf[Fire])
                fireCounter += 1

            if (j < h - 1)
              if (world(i)(j + 1).isInstanceOf[Fire])
                fireCounter += 1


            if (i > 0 && j > 0)
              if (world(i - 1)(j - 1).isInstanceOf[Fire])
                fireCounter += 1

            if (i < w - 1 && j > 0)
              if (world(i + 1)(j - 1).isInstanceOf[Fire])
                fireCounter += 1

            if (i < w - 1 && j < h - 1)
              if (world(i + 1)(j + 1).isInstanceOf[Fire])
                fireCounter += 1

            if (i > 0 && j < h - 1)
              if (world(i - 1)(j + 1).isInstanceOf[Fire])
                fireCounter += 1

            newWorld(i)(j) = Cell.tryFire(fireCounter, temperature, i, j)

            //endregion
          }




          //case water: Water =>
          //case stone: Stone =>
          case fire: Fire => newWorld(i)(j) = new Burned(i, j)
          case burned: Burned => newWorld(i)(j) = new Burned(i, j)
          case burned: Burned2 => newWorld(i)(j) = new Burned3(i, j)
          case burned: Burned3 => newWorld(i)(j) = new Burned4(i, j)
          case burned: Burned4 => newWorld(i)(j) = new Void(i, j)
          case _ => newWorld(i)(j) = world(i)(j)
        }
      }
    }

    return newWorld

  }

  def getGridInfo(world:Array[Array[Cell]]): Float = {

    val treeInfo = world.foldLeft(0)((n, l:Array[Cell]) =>
      n + l.foldLeft(0)((t:Int, e:Cell) => (

        e match {
          case void: Void => t
          case tree:Tree => t + 1
          case stone:Stone => t
          case river:Water => t
          case fire: Fire => t
          case burned: Burned => t
          case _ => t
        }

        )
      )
    )

    val numOfCell:Int = world.length * world(0).length

    treeInfo*100 / numOfCell

  }

  def getFireInfo(world: Array[Array[Cell]]): Float = {

    val treeInfo = world.foldLeft(0)((n, l: Array[Cell]) =>
      n + l.foldLeft(0)((t: Int, e: Cell) => (

        e match {
          case void: Void => t
          case tree: Tree => t
          case stone: Stone => t
          case river: Water => t
          case fire: Fire => t + 1
          case burned: Burned => t + 1
          case _ => t
        }

        )
      )
    )

    val nTree:Int = (world.length * world(0).length * (density / 100.0)).toInt

    if(nTree == 0) return 0
    else return treeInfo * 100 / nTree

  }

  // Print la grille
  /*def printGrid(array:Array[Array[Cell]]): Unit = {

    println("-" * (array.length * 3 - 2))
    for (i <- array.indices) {

      var str:String = ""
      for (j <- array(i).indices) {
        if (array(i)(j).ID == Cell.Tree)
          str += "⍋  " //"\uD83C\uDF32 "
        else if(array(i)(j).ID == Cell.Fire)
          str += "F  "
          //str += "\uD83D\uDF02  "
        else if (array(i)(j).ID == Cell.Water)
          str += "░  "
        else if (array(i)(j).ID == Cell.Stone)
          str += "█  "
        else if (array(i)(j).ID == Cell.MiniTree)
          str += "▲  "
        else
          str += "   " //array(i)(j) + "  "
      }
      println(str)
    }
    println("-" * (array.length * 3 - 2))

  }*/


  var density:Int = 40
  var world:Array[Array[Cell]] = initGrid(100, 100, density)

  var temperature:Int = 253       // 0°C

  var cellSize = 5    // 20

  var Xplot: ArrayBuffer[Int] = new ArrayBuffer[Int]()
  var Yplot: ArrayBuffer[Int] = new ArrayBuffer[Int]()
  var iteration:Int = 0

  var simulation:Int = 0
  var simX:ArrayBuffer[Int] = new ArrayBuffer[Int]()
  var simY:ArrayBuffer[Int] = new ArrayBuffer[Int]()

  def init(): Unit = {

  }

  def update(): Unit = {

    //printGrid(world)
    world = updateGrid(world)
    //println(getGridInfo(world))

    Xplot.append(iteration)
    Yplot.append(getFireInfo(world).toInt)

    iteration += 1

    /*if(iteration == 500){

      println("*********************************************************************\n")

      print("[")
      for(i <- Xplot)
        print(i + ", ")
      print("]")

      println()
      println()

      print("[")
      for (i <- Yplot)
        print(i + ", ")
      print("]")

      println("\n*********************************************************************")

      sys.exit(404)

    }*/

    if(iteration == 200){

      simX.append(density)
      simY.append(getFireInfo(world).toInt)
      println(density)

      simulation += 1
      //temperature += 1
      density += 1
      iteration = 0
      world = initGrid(world.length, world(0).length, density)

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


    //print(Console.RESET)

  }

  var counter:Int = 0
  var f:Int = 2

  def draw(g: Graphics2D): Unit = {

    // Draw cells
    world.map((l) => l.map((e) => drawCell(e, g)))

  }


  def drawCell(c:Cell, g: Graphics2D): Unit = {

    c match {
      case void: Void => g.setColor(Color.WHITE)
      case tree: Tree => g.setColor(Color.GREEN)
      case water: Water => g.setColor(Color.BLUE)
      case stone: Stone => g.setColor(Color.GRAY)
      case fire: Fire => g.setColor(Color.RED)
      case burned: Burned => g.setColor(Color.black)
      case burned: Burned2 => g.setColor(Color.black)
      case burned: Burned3 => g.setColor(Color.black)
      case burned: Burned4 => g.setColor(Color.black)
      case _ => g.setColor(Color.WHITE)
    }

    g.fillRect(c.x * cellSize, c.y * cellSize, cellSize, cellSize)

  }




}
