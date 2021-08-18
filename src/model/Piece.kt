package model

abstract class Piece(var position: Coordinate, val team: Team, var observer: Chess) : IMovement {

    lateinit var shape: String
    var firstMovementDone = false

    abstract fun possibleMovements(): ArrayList<Box>

    abstract fun canKillBoxes(): ArrayList<Box>

    override fun move(board: Array<Array<Box>>, destination: Coordinate) {

        val fromBox = board[position.x][position.y]
        val destinationBox = board[destination.x][destination.y]

        if (destinationBox.piece != null) {
            // Tell observer this piece has killed another piece
            observer.onKill(destinationBox.piece!!)
        }

        // Tell observer the movement
        observer.onMovement(fromBox.position, destinationBox.position, this)

        // Movement action
        fromBox.piece = null
        destinationBox.piece = this
        position = destination

        // This piece has done the first movement
        if (!firstMovementDone) {
            firstMovementDone = true
        }

        observer.onTurnChanged()


    }

    fun isEnemy(piece: Piece) = team != piece.team

    override fun handleMovement(from: Coordinate, to: Coordinate) {
        // Nothing by default
    }

    override fun toString() = shape

}