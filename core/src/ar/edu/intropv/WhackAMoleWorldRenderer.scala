package ar.edu.intropv

import ar.com.pablitar.libgdx.commons.rendering.Renderers
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

object WhackAMoleWorldRenderer {
  def render(renderers: Renderers, world: WhackAMoleWorld) = {
    world.moles.foreach(MoleRenderer.render(renderers, _))
  }
}

object MoleRenderer {
  val BORDER_RADIUS = 5
  val BORDER_COLOR = Color.LIGHT_GRAY
  def render(renderers: Renderers, mole: Mole) = {
    renderers.withShapes(ShapeType.Filled) { shapes =>
      shapes.setColor(BORDER_COLOR)
      shapes.circle(mole.shape.x, mole.shape.y, mole.shape.radius)

      shapes.setColor(mole.color)
      shapes.circle(mole.shape.x, mole.shape.y, mole.shape.radius - BORDER_RADIUS)
    }
  }
}
