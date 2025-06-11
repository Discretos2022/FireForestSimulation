package simulation

import java.util.Random


class Cell(_x:Int, _y:Int) {

  val x = _x
  val y = _y

}

class Void(_x:Int, _y:Int) extends Cell(_x, _y){

}

class Tree(_x:Int, _y:Int) extends Cell(_x, _y){

}

class Water(_x:Int, _y:Int) extends Cell(_x, _y){

}

class Stone(_x:Int, _y:Int) extends Cell(_x, _y){

}

class Fire(_x:Int, _y:Int) extends Cell(_x, _y){

}

class Burned(_x:Int, _y:Int) extends Cell(_x, _y){

}

class Burned2(_x:Int, _y:Int) extends Cell(_x, _y){

}

class Burned3(_x:Int, _y:Int) extends Cell(_x, _y){

}

class Burned4(_x:Int, _y:Int) extends Cell(_x, _y){

}


object Cell {

  val Void:Int = 0
  val Tree:Int = 1
  val Water:Int = 2
  val Stone:Int = 3
  val Fire:Int = 4
  val Burned:Int = 5

  def tryTree(x:Int, y:Int, temperature:Int): Cell = {
    /*val r: Int = new Random().nextInt(0, 10)
    if (r >= 9) new Tree(x, y) // 8
    else new Void(x, y)*/

    return new Void(x, y)

    val r: Int = new Random().nextInt(0, 100)
    if (r <= 80) new Tree(x, y)
    else new Void(x, y)

  }

  def tryFire(fire:Int, temperature:Int, x:Int, y:Int): Cell = {

    /*if(fire == 0){
      val r: Int = new Random().nextInt(0, 1000)
      if (r <= 0 + temperature*5) new Fire(x, y)
      else new Tree(x, y)
    }
    else {
      val r: Int = new Random().nextInt(0, 800)
      if (r < 100 * fire + temperature*5) new Fire(x, y)
      else new Tree(x, y)
    }*/

    /*if (fire == 0) {
      val r: Int = new Random().nextInt(0, 1000)
      if (r <= 0 + temperature/2) new Fire(x, y)
      else new Tree(x, y)
    }
    else {
      val r: Int = new Random().nextInt(0, 800)
      if (r < 10 * fire + temperature) new Fire(x, y)
      else new Tree(x, y)
    }*/

    if(fire > 0)
      return new Fire(x, y)
    else
      return new Tree(x, y)

  }


}
