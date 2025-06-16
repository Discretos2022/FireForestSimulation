package simulation

import java.awt.{Color, Graphics2D}
import java.util.Random
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

class Simulation(w:Int = 100, h:Int = 100, _density:Int = 40, _nStone:Int = 0, _temperature:Int = 0, _windDirection:Double = 0, _windIntensity:Int = 0, _humidity:Int = 0, _regrows:Boolean = false, _lightning:Int = 0, baseWorld:Array[Array[Cell]] = null) {

  val width:Int = w
  val height:Int = h
  val density: Int = _density
  val nStone:Int = _nStone
  val windDirection:Double = _windDirection
  val windIntensity:Int = _windIntensity
  val humidity:Int = _humidity
  val regrows:Boolean = _regrows
  val lightning:Int = _lightning
  var world: Array[Array[Cell]] = if (baseWorld == null) initGrid(width, height, density, nStone, lightning) else baseWorld

  var temperature: Int = _temperature // 253    // 0°C

  var cellSize = 5 // 20

  def initGrid(w:Int, h:Int, density:Int, nStone:Int, lightning:Int):Array[Array[Cell]] = {
    val array = Array.tabulate(w, h)((x, y) => new Void(x, y).asInstanceOf[Cell])

    /** PLACE INITIAL FIRE **/
    if (lightning == 0){
      val x: Int = new Random().nextInt(0, w)
      val y: Int = new Random().nextInt(0, h)
      array(x)(y) = new Fire(x, y)
    }

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

    @tailrec
    def placeStone(nStone: Int, grid: Array[Array[Cell]]): Array[Array[Cell]] = {
      if (nStone == 0)
        return grid
      else {
        val x: Int = new Random().nextInt(0, w)
        val y: Int = new Random().nextInt(0, h)

        if (grid(x)(y).isInstanceOf[Void] || grid(x)(y).isInstanceOf[Tree]) {
          grid(x)(y) = new Stone(x, y)
          return placeStone(nStone - 1, grid)
        }
        else
          return placeStone(nStone, grid)
      }
    }


    val arrayWithForest = placeTree(nTree, array)
    val arrayWithForestAndStone = placeStone(nStone, arrayWithForest)

    return arrayWithForestAndStone

    /** PLACE INITIAL RIVER **/
    //for (i <- 0 until 20)
      //array(i)(13) = new Water(i, 13)

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

            if(regrows) {
              if(treeCounter > 0 && fireCounter == 0)
                newWorld(i)(j) = Cell.tryTree(i, j)
            } else {
              newWorld(i)(j) = new Void(i, j)
            }

            //endregion

          }


          case tree: Tree => {

            //region Prise de Feu

            var fireCounter = 0
            if (windIntensity < 3){
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
            }

            if(windIntensity > 0) {

              // Normalized
              val nx: Double = Math.cos(_windDirection)
              val ny: Double = Math.sin(_windDirection)

              val startPoints:Array[Tuple2[Int, Int]] = {

                if (windDirection > 0 && windDirection < Math.PI/2)
                  Array((1, -1), (1, 0), (0, -1))
                else if (windDirection > Math.PI && windDirection < Math.PI)
                  Array((-1, -1), (-1, 0), (0, -1))
                else if (windDirection > 2*Math.PI && windDirection < Math.PI/2 + Math.PI)
                  Array((-1, 1), (-1, 0), (0, 1))
                else if (windDirection > 4 * Math.PI && windDirection < 2*Math.PI)
                  Array((1, 1), (1, 0), (0, 1))

                else if (windDirection == 0)
                  Array((0, -1), (1, 0), (0, 1))
                else if (windDirection == Math.PI/2)
                  Array((-1, 0), (1, 0), (0, -1))
                else if (windDirection == Math.PI)
                  Array((0, -1), (0, 1), (-1, 0))
                else if (windDirection == Math.PI/2 + Math.PI)
                  Array((1, -1), (0, 1), (1, 1))

                else
                  Array()

              }


              for (p <- startPoints){

                // Start Point
                val px: Int = i + p._1
                val py: Int = j + p._2

                for (l: Int <- 0 to windIntensity) {

                  val cellx: Int = (px - nx * l).toInt
                  val celly: Int = (py + ny * l).toInt

                  if(cellx >= 0 && cellx < width && celly >= 0 && celly < height)
                    if (world(cellx)(celly).isInstanceOf[Fire])
                      fireCounter += 1

                }

              }


            }

            newWorld(i)(j) = Cell.tryFire(fireCounter, i, j, humidity, lightning)

            //endregion

          }

          case water: Water =>
          case stone: Stone =>
          case fire: Fire => newWorld(i)(j) = new Burned(i, j)
          case burned: Burned =>
            if (regrows)
              newWorld(i)(j) = new Void(i, j)
            else
              newWorld(i)(j) = new Burned(i, j)
          //case _ => newWorld(i)(j) = world(i)(j)
        }
      }
    }

    return newWorld

  }

  def getTreeInfo: Float = {

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

  def getFireInfo: Float = {

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


  var iteration:Int = 0

  var simulation:Int = 0
  var simX:ArrayBuffer[Int] = new ArrayBuffer[Int]()
  var simY:ArrayBuffer[Int] = new ArrayBuffer[Int]()

  def init(): Unit = {

  }

  def update(): Unit = {
    world = updateGrid(world)
  }


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
      case _ => g.setColor(Color.WHITE)
    }

    g.fillRect(c.x * cellSize, c.y * cellSize, cellSize, cellSize)

  }


  def update(iteration:Int): Unit = {

    for (i:Int <- 0 until iteration){
      update()
    }

  }


}
