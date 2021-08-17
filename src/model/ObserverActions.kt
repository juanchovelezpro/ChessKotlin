package model

interface ObserverActions {
    fun onKill(murdered: Piece)
    fun onMovement(from: Coordinate, to: Coordinate, piece: Piece)
    fun onPromotion(pawn: Pawn)
    fun onCheck()
    fun onCheckMate()
    fun onTurnChanged()
}