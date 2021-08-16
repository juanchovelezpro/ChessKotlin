package model

abstract class Piece(var position: Coordinate, val team: Team) {

    lateinit var shape: String
    var firstMovementDone = false

    abstract fun possibleMovements(board: Array<Array<Box>>): ArrayList<Box>

    fun move(board: Array<Array<Box>>, destination: Coordinate) {
        if (possibleMovements(board).contains(board[destination.x][destination.y])) {
            board[position.x][position.y].piece = null
            board[destination.x][destination.y].piece = this
            position = destination
            firstMovementDone = true
        }
    }

    fun isEnemy(piece: Piece): Boolean {
        return team != piece.team
    }

    override fun toString(): String {
        return shape
    }
}