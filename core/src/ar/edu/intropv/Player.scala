package ar.edu.intropv

import com.badlogic.gdx.math.Vector2

class Player(val currentPointerPositionProvider: () => Vector2) {
  var score = 0

  def sumScore() = score += 1

  def currentPointerPosition = currentPointerPositionProvider.apply()
}
