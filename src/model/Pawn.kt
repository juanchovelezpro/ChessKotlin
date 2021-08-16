package model

class Pawn(position: Coordinate, team: Team) : Piece(position, team) {

    var firstMoveWasTwo = false
    var canBeKilledEnPassant = false

    init {

        shape = if (team == Team.WHITE) {
            "\u2659"
        } else {
            "\u265F"
        }

    }


    override fun possibleMovements(board: Array<Array<Box>>): ArrayList<Box> {
        val pMovements = ArrayList<Box>()

        if (team == Team.WHITE) {
            possibleWhiteMovements(board, pMovements)
        } else {
            possibleBlackMovements(board, pMovements)
        }

        return pMovements
    }

    private fun possibleWhiteMovements(board: Array<Array<Box>>, pMovements: ArrayList<Box>) {
        // If it is in the initial position
        if (position.x == 6) {

            val nextBox = board[position.x - 1][position.y]
            val nextTwoBox = board[position.x - 2][position.y]

            if (nextBox.piece == null && nextTwoBox.piece == null) {
                pMovements.add(nextTwoBox)
            }


        }

        // Check next
        if (position.x > 0) {

            val box = board[position.x - 1][position.y]
            addIfItIsPossibleMovement(box, pMovements, normal = true)

            // Check diagonal left up
            if (position.y > 0) {
                val leftUp = board[position.x - 1][position.y - 1]
                addIfItIsPossibleMovement(leftUp, pMovements, kill = true)
            }

            // Check diagonal right up
            if (position.y < 7) {
                val rightUp = board[position.x - 1][position.y + 1]
                addIfItIsPossibleMovement(rightUp, pMovements, kill = true)
            }

        }

    }

    private fun possibleBlackMovements(board: Array<Array<Box>>, pMovements: ArrayList<Box>) {

        // If it is in the initial position
        if (position.x == 1) {

            val nextBox = board[position.x + 1][position.y]
            val nextTwoBox = board[position.x + 2][position.y]

            if (nextBox.piece == null && nextTwoBox.piece == null) {
                pMovements.add(nextTwoBox)
            }

        }

        // Check next
        if (position.x < 7) {

            val box = board[position.x + 1][position.y]
            addIfItIsPossibleMovement(box, pMovements, normal = true)

            // Check diagonal left down
            if (position.y > 0) {
                val leftDown = board[position.x + 1][position.y - 1]
                addIfItIsPossibleMovement(leftDown, pMovements, kill = true)
            }

            // Check diagonal right down
            if (position.y < 7) {
                val rightDown = board[position.x + 1][position.y + 1]
                addIfItIsPossibleMovement(rightDown, pMovements, kill = true)
            }

        }


    }

    private fun addIfItIsPossibleMovement(
        box: Box,
        pMovements: ArrayList<Box>,
        kill: Boolean = false,
        normal: Boolean = false
    ) {
        if (normal) {
            if (box.piece == null) {
                pMovements.add(box)
            }
        }

        if (kill) {
            if (box.piece != null && isEnemy(box.piece!!)) {
                pMovements.add(box)
            }
        }
    }


}