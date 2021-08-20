package model

interface ChessActions {
    fun onKill(murdered: Piece)
    fun onMovement(from: Coordinate, to: Coordinate, piece: Piece)
    fun onPromotion(pawn: Pawn)
    fun onCheck(team: Team)
    fun onCheckMate(winner: Team, loser: Team)
    fun onTie()
    fun onTurnChanged()
}