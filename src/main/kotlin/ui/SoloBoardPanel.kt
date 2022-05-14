package ui

import model.*
import kotlin.random.Random

class SoloBoardPanel(window: Window) : BoardPanel(window), ChessActions {

    val AI = AIChess(Team.BLACK, window.chess)

    override fun onPromotion(pawn: Pawn, promPosition: Coordinate): Piece {

        return if (pawn.team == Team.BLACK) {

            val options = arrayOf("Rook", "Bishop", "Knight", "Queen")

            when (options[Random.nextInt(0, options.lastIndex)]) {
                "Rook" -> Rook(promPosition, pawn.team, pawn.observer)
                "Bishop" -> Bishop(promPosition, pawn.team, pawn.observer)
                "Knight" -> Knight(promPosition, pawn.team, pawn.observer)
                else -> Queen(promPosition, pawn.team, pawn.observer)
            }
        } else {
            super.onPromotion(pawn, promPosition)
        }
    }


}