package model

import java.awt.Color

class Box(var position: Coordinate, var piece: Piece?, var color: Color? = null, var originalColor: Color? = null) {

    override fun toString(): String {
        return "|$position | Piece: $piece|"
    }

    companion object {
        val WHITE_COLOR = Color(229, 207, 64)
        val BLACK_COLOR = Color(121, 92, 34)
        val POSSIBLE_COLOR = Color(0, 0, 255, 50).brighter()
        val SELECTED_COLOR = Color(220, 115, 0).brighter()
        val ERROR_COLOR = Color(255, 0, 0, 60).darker()
    }

}