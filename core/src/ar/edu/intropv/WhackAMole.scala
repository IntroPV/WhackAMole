package ar.edu.intropv

import ar.com.pablitar.libgdx.commons.rendering.Renderers
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.{ApplicationAdapter, Gdx, InputAdapter}

class WhackAMole extends ApplicationAdapter {
  val player = new Player(() => toWorld(new Vector2(Gdx.input.getX, Gdx.input.getY())))
  val world = new WhackAMoleWorld(player)

  lazy val renderers = new Renderers()
  lazy val scoreRenderer = new ScoreRenderer(new BitmapFont())

  val inputProcessor = new InputAdapter() {
    override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
      world.playerSmacked(player, toWorld(new Vector2(screenX, screenY)))
      true
    }
  }

  override def create(): Unit = {
    Gdx.input.setInputProcessor(inputProcessor)
    Gdx.graphics.setWindowedMode(Configuration.VIEWPORT_WIDTH, Configuration.VIEWPORT_HEIGHT)
  }

  override def render(): Unit = {
    world.update(Gdx.graphics.getDeltaTime)
    renderers.withRenderCycle() {
      WhackAMoleWorldRenderer.render(renderers, world)
      scoreRenderer.render(renderers, player.score)
    }
  }

  def toWorld(vector: Vector2): Vector2 = {
    vector.set(vector.x, Configuration.VIEWPORT_HEIGHT - vector.y)
  }
}
