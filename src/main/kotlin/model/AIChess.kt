package model

import kotlin.random.Random

class AIChess(val team: Team, val chess: Chess) {

    private fun getPieces(): ArrayList<Piece> {
        return if (team == Team.WHITE) {
            chess.whitePiecesAlive
        } else {
            chess.blackPiecesAlive
        }
    }

    private fun getPiecesWithPossibleMovements(): ArrayList<Piece> {

        val piecesWithMovements = ArrayList<Piece>()

        val pieces = getPieces()

        pieces.forEach { piece ->
            val possibleMovements = piece.possibleMovementsWithCheck()
            // This piece has movements ?
            if (possibleMovements.isNotEmpty()) {
                piecesWithMovements.add(piece)
            }
        }

        return piecesWithMovements

    }

    private fun selectPieceToMove(): Piece? {

        val piecesWithMovements = getPiecesWithPossibleMovements()

        return if (piecesWithMovements.size > 0) {
            val randomNumber = Random.nextInt(piecesWithMovements.size)
            piecesWithMovements[randomNumber]
        } else {
            null
        }
    }

    fun move() {

        val piece = selectPieceToMove()
        val movements = piece?.possibleMovementsWithCheck()

        if (movements != null) {
            if (movements.size > 0) {
                val moveToDo = movements[Random.nextInt(movements.size)]
                piece.move(chess.board, moveToDo.position)
            }
        }
    }


}