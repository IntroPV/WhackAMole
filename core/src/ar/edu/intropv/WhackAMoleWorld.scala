package ar.edu.intropv

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, MathUtils, Vector2}

class WhackAMoleWorld {
  var moles = Set.empty[Mole]

  val width = 1280
  val height = 720

  def addMole(mole: Mole): Unit = moles = moles + mole

  def removeMole(mole: Mole) = moles = moles - mole

  val moleSpawner = new MoleSpawner(this)

  def update(delta: Float): Unit = {
    moleSpawner.update(delta)
    moles.foreach(_.update(delta))
  }

  def playerSmacked(player: Player, position: Vector2): Unit = {
    moles.find(_.isTouchedBy(position)).foreach(_.smackedBy(player))
  }
}

class MoleSpawner(world: WhackAMoleWorld) {
  val SPAWN_TIME = 3f

  var timeToNextSpawn = SPAWN_TIME

  def spawnMole(): Unit = world.addMole(new Mole(new Vector2(MathUtils.random(world.width), MathUtils.random(world.height)), world))

  def update(delta: Float): Unit = {
    timeToNextSpawn -= delta
    if (timeToNextSpawn <= 0) {
      timeToNextSpawn = SPAWN_TIME

      spawnMole()
    }
  }
}

class Mole(val position: Vector2, world: WhackAMoleWorld) {

  val color = Color.GREEN

  val MAX_LIFE = 2f

  val shape = new Circle(position, 40)

  var life = MAX_LIFE


  def update(delta: Float) = {
    life -= delta
    if (life <= 0) remove()
  }

  def remove() = {
    world.removeMole(this)
  }

  def isTouchedBy(touchPoint: Vector2) = {
    shape.contains(touchPoint)
  }

  def smackedBy(player: Player): Unit = {
    player.sumScore()
    remove()
  }
}
