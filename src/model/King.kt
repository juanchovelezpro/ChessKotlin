package model

class King(position: Coordinate, team: Team) : Piece(position, team) {

    init {

        shape = if (team == Team.WHITE) {
            "\u2654"
        } else {
            "\u265A"
        }

    }

    override fun possibleMovements(board: Array<Array<Box>>): ArrayList<Box> {
        val pMovements = ArrayList<Box>()

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

    private fun addIfItsPossibleMovement(box: Box, pMovements: ArrayList<Box>) {
        if (box.piece == null) {
            pMovements.add(box)
        }
    }

    private fun isOnAttack(board: Array<Array<Box>>, box: Box): Boolean {
        return false
    }

    private fun verifyCastling(board: Array<Array<Box>>, pMovements: ArrayList<Box>) {

        // Verify short castling
        val nextBox = board[position.x][position.y + 1]
        val nextTwoBox = board[position.x][position.y + 2]
        val rookBox = board[position.x][position.y + 3]

        if (nextBox.piece == null && nextTwoBox.piece == null) {
            if (rookBox.piece != null && !rookBox.piece!!.firstMovementDone) {
                pMovements.add(nextTwoBox)
            }
        }

    }

}