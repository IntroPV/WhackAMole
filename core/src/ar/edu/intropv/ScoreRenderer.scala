package ar.edu.intropv

import ar.com.pablitar.libgdx.commons.rendering.Renderers
import com.badlogic.gdx.graphics.g2d.BitmapFont

class ScoreRenderer(font: BitmapFont) {

  def render(renderers: Renderers, score: Int) = {
    renderers.withSprites { sprites =>
      font.draw(sprites, "Score: " + score, 30, Configuration.VIEWPORT_HEIGHT - 30)
    }
  }

}
