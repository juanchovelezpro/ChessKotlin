package model

class King(position: Coordinate, team: Team, observer: Chess) : Piece(position, team, observer) {

    init {

        shape = if (team == Team.WHITE) {
            "\u2654"
        } else {
            "\u265A"
        }

    }

    override fun possibleMovements(): ArrayList<Box> {
        val pMovements = ArrayList<Box>()
        val board = observer.board

        if (!firstMovementDone) {
            verifyCastling(board, pMovements)
        }

        // Left
        if (position.y > 0) {
            val box = board[position.x][position.y - 1]
            addIfItsPossibleMovement(box, pMovements)
        }

        // Right
        if (position.y < 7) {
            val box = board[position.x][position.y + 1]
            addIfItsPossibleMovement(box, pMovements)
        }

        // Up
        if (position.x > 0) {
            val box = board[position.x - 1][position.y]
            addIfItsPossibleMovement(box, pMovements)
        }

        // Down
        if (position.x < 7) {
            val box = board[position.x + 1][position.y]
            addIfItsPossibleMovement(box, pMovements)
        }

        // Diagonal Left Up
        if (position.x > 0 && position.y > 0) {
            val box = board[position.x - 1][position.y - 1]
            addIfItsPossibleMovement(box, pMovements)
        }

        // Diagonal Left Down
        if (position.x < 7 && position.y > 0) {
            val box = board[position.x + 1][position.y - 1]
            addIfItsPossibleMovement(box, pMovements)
        }

        // Diagonal Right Up
        if (position.x > 0 && position.y < 7) {
            val box = board[position.x - 1][position.y + 1]
            addIfItsPossibleMovement(box, pMovements)
        }

        // Diagonal Right Down
        if (position.x < 7 && position.y < 7) {
            val box = board[position.x + 1][position.y + 1]
            addIfItsPossibleMovement(box, pMovements)
        }


        return pMovements
    }

    fun possibleMovementsWithCheck(): ArrayList<Box> {
        return handleCheck()
    }

    private fun addIfItsPossibleMovement(box: Box, pMovements: ArrayList<Box>) {
        if (box.piece == null) {
            pMovements.add(box)
        } else {
            if (isEnemy(box.piece!!)) {
                pMovements.add(box)
            }
        }
    }

    private fun verifyCastling(board: Array<Array<Box>>, pMovements: ArrayList<Box>) {

        // Verify short castling (Right Castling)
        val nextBox = board[position.x][position.y + 1]
        val nextTwoBox = board[position.x][position.y + 2]
        val rookBox = board[position.x][position.y + 3]

        if (nextBox.piece == null && nextTwoBox.piece == null) {
            if (rookBox.piece != null && !rookBox.piece!!.firstMovementDone) {
                pMovements.add(nextTwoBox)
            }
        }

        // Verify long castling (Left Castling)
        val prevBox = board[position.x][position.y - 1]
        val prevTwoBox = board[position.x][position.y - 2]
        val prevThreeBox = board[position.x][position.y - 3]
        val prevRookBox = board[position.x][position.y - 4]

        if (prevBox.piece == null && prevTwoBox.piece == null && prevThreeBox.piece == null) {
            if (prevRookBox.piece != null && !prevRookBox.piece!!.firstMovementDone) {
                pMovements.add(prevTwoBox)
            }
        }
    }

    private fun handleCastlingMovement(from: Coordinate, to: Coordinate) {
        // Castling left
        if (from.y - to.y == 2) {
            onCastling(to, false)
            // Castling right
        } else if (from.y - to.y == -2) {
            onCastling(to, true)
        }
    }

    // side = true, checks right castling, side = false, checks left castling
    private fun onCastling(kingDestination: Coordinate, side: Boolean) {

        val board = observer.board

        val rookBox: Box
        val rook: Piece?

        if (side) {

            rookBox = board[kingDestination.x][kingDestination.y + 1]
            rook = rookBox.piece

            rook?.position?.y = kingDestination.y - 1

            rookBox.piece = null
            board[rook?.position?.x!!][rook.position.y].piece = rook


        } else {

            rookBox = board[kingDestination.x][kingDestination.y - 2]
            rook = rookBox.piece

            rook?.position?.y = kingDestination.y + 1

            rookBox.piece = null
            board[rook?.position?.x!!][rook.position.y].piece = rook

        }

    }

    private fun handleCheck(): ArrayList<Box> {

        val enemiesAlive = if (team == Team.WHITE) {
            observer.blackPiecesAlive
        } else {
            observer.whitePiecesAlive
        }

        val kingPossibleMovements = possibleMovements()
        val allEnemiesPossibleMovements = ArrayList<Box>()

        enemiesAlive.forEach { enemy -> allEnemiesPossibleMovements.addAll(enemy.possibleMovements()) }

        kingPossibleMovements.removeAll { allEnemiesPossibleMovements.contains(it) }

        return kingPossibleMovements

    }

    override fun handleMovement(from: Coordinate, to: Coordinate) {
        handleCastlingMovement(from, to)
    }


}