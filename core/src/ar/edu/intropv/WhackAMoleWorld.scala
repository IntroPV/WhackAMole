package ar.edu.intropv

import ar.com.pablitar.libgdx.commons.ColorUtils
import ar.com.pablitar.libgdx.commons.extensions.VectorExtensions._
import com.badlogic.gdx.math.{Circle, MathUtils, Vector2}

class WhackAMoleWorld(val player: Player) {
  var moles = Set.empty[Mole]

  val width = 1280
  val height = 720

  val center: Vector2 = (width / 2, height / 2)

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
  val SPAWN_TIME = 0.3f

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

class Mole(var position: Vector2, world: WhackAMoleWorld) {

  world.center

  val speed = 640f

  var velocity = (world.center - position).nor() * speed + (0, 300)

  def dragMagnitude = 700f

  def drag = velocity.cpy().scl(-1).nor() * dragMagnitude

  def acceleration: Vector2 = new Vector2(0, -640f)
    //(world.player.currentPointerPosition - position).nor() * accelerationMagnitude

  val color = ColorUtils.fromHSV(MathUtils.random(360) , 80, 60)

  val MAX_LIFE = 30f

  def shape = new Circle(position, 40)

  var life = MAX_LIFE


  def update(delta: Float) = {
    life -= delta
    if (life <= 0) remove()
    velocity += acceleration * delta

    if(velocity.len() < (drag * delta).len()) {
      velocity = (0, 0)
    } else {
      velocity += drag * delta
    }

    position += velocity * delta
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
