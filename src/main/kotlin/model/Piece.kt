package model

abstract class Piece(var position: Coordinate, val team: Team, var observer: Chess) : IMovement {

    lateinit var shape: String
    var firstMovementDone = false

    abstract override fun possibleMovements(): ArrayList<Box>

    override fun possibleMovementsWithCheck(): ArrayList<Box> {

        val board = observer.board
        val enemiesAlive: ArrayList<Piece>
        val myKingBox: Box

        if (team == Team.WHITE) {
            enemiesAlive = observer.blackPiecesAlive
            myKingBox = board[observer.whiteKing.position.x][observer.whiteKing.position.y]
        } else {
            enemiesAlive = observer.whitePiecesAlive
            myKingBox = board[observer.blackKing.position.x][observer.blackKing.position.y]
        }

        val pMovements = possibleMovements()
        val toVerify = ArrayList<Box>()
        toVerify.addAll(pMovements)

        return checkKingInDanger(pMovements, toVerify, myKingBox, enemiesAlive)

    }

    // An auxiliary method to check all the "possible movements" of the current piece if are valid.
    private fun checkKingInDanger(
        pMovements: ArrayList<Box>, pMovementsToVerify: ArrayList<Box>, myKingBox: Box, enemiesAlive: ArrayList<Piece>
    ): ArrayList<Box> {

        if (pMovementsToVerify.isEmpty()) {
            return pMovements
        } else {

            val board = observer.board
            val destination = pMovementsToVerify[0].position

            val fromBox = board[position.x][position.y]
            val destBox = board[destination.x][destination.y]

            // save the piece (if exists) in the dest position that the current piece is going to do
            val pieceInDest = destBox.piece

            // Simulate the movement
            fromBox.piece = null
            destBox.piece = this

            // Simulate the kill
            if (team == Team.WHITE) {
                observer.blackPiecesAlive.remove(pieceInDest)
            } else {
                observer.whitePiecesAlive.remove(pieceInDest)
            }

            val allEnemiesCanKillMovements = ArrayList<Box>()

            // Add all movements that enemies can attack
            enemiesAlive.forEach { enemy ->
                allEnemiesCanKillMovements.addAll(enemy.possibleMovements())
            }

            // Verify if the movements of all enemies contains the king box, that means attacking the king, if so,
            // the current movement is not valid
            if (allEnemiesCanKillMovements.contains(myKingBox)) {
                pMovements.remove(destBox)
            }

            // Remove the current movement to continue validating
            pMovementsToVerify.remove(destBox)

            // Put all things in their original places.

            // First revive the killed piece
            if (pieceInDest != null) {
                if (team == Team.WHITE) {
                    observer.blackPiecesAlive.add(pieceInDest)
                } else {
                    observer.whitePiecesAlive.add(pieceInDest)
                }
            }

            // Then put the pieces in their original places
            destBox.piece = pieceInDest
            fromBox.piece = this

            return checkKingInDanger(pMovements, pMovementsToVerify, myKingBox, enemiesAlive)
        }

    }

    abstract override fun canKillBoxes(): ArrayList<Box>

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