package model

abstract class Piece(var position: Coordinate, val team: Team, var observer: ObserverActions? = null) {

    lateinit var shape: String
    var firstMovementDone = false

    abstract fun possibleMovements(board: Array<Array<Box>>): ArrayList<Box>

    fun move(board: Array<Array<Box>>, destination: Coordinate) {

        val fromBox = board[position.x][position.y]
        val destinationBox = board[destination.x][destination.y]

        // This piece has done the first movement
        if(!firstMovementDone){
            firstMovementDone = true
        }

        if (possibleMovements(board).contains(destinationBox)) {

            if (destinationBox.piece != null) {
                // Tell observer this piece has killed another piece
                observer?.onKill(destinationBox.piece!!)
            }

            // Movement action
            fromBox.piece = null
            destinationBox.piece = this
            position = destination


            // Tell observer the movement
            observer?.onMovement(fromBox.position, destinationBox.position, this)

        }
    }

    fun isEnemy(piece: Piece): Boolean {
        return team != piece.team
    }

    override fun toString(): String {
        return shape
    }
}