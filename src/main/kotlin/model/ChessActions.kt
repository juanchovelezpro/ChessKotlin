package model

import java.io.Serializable

interface ChessActions : Serializable{
    fun onKill(murdered: Piece)
    fun onMovement(from: Coordinate, to: Coordinate, piece: Piece)
    fun onPromotion(pawn: Pawn, promPosition: Coordinate): Piece
    fun onCheck(team: Team)
    fun onCheckMate(winner: Team, loser: Team)
    fun onTie()
    fun onTurnChanged()
}