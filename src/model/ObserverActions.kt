package model

interface ObserverActions {
    fun onKill(murdered: Piece)
    fun onMovement(from: Coordinate, to: Coordinate, piece: Piece)
    fun onEnPassant(from: Coordinate, to: Coordinate, piece: Piece)
    fun onPromotion(pawn: Pawn)
    fun onCastling(kingDestination: Coordinate, side : Boolean)
    fun onCheck()
    fun onCheckMate()
    fun onTurnChanged()
}