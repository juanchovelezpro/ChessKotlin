package model

class Knight(position: Coordinate, team: Team) : Piece(position, team) {

    init {

        shape = if (team == Team.WHITE) {
            "\u2658"
        } else {
            "\u265E"
        }

    }

    override fun possibleMovements(board: Array<Array<Box>>): ArrayList<Box> {
        val pMovements = ArrayList<Box>()

        // L  -> UP LEFT
        if (position.x - 2 >= 0 && position.y - 1 >= 0) {
            val box = board[position.x - 2][position.y - 1]
            addBoxToPossibleMovements(box, pMovements)
        }

        // L mirror -> UP RIGHT
        if (position.x - 2 >= 0 && position.y + 1 <= 7) {
            val box = board[position.x - 2][position.y + 1]
            addBoxToPossibleMovements(box, pMovements)
        }

        // L -> MID UP LEFT
        if (position.x - 1 >= 0 && position.y - 2 >= 0) {
            val box = board[position.x - 1][position.y - 2]
            addBoxToPossibleMovements(box, pMovements)
        }

        // L -> MID UP RIGHT
        if (position.x - 1 >= 0 && position.y + 2 <= 7) {
            val box = board[position.x - 1][position.y + 2]
            addBoxToPossibleMovements(box, pMovements)
        }

        // L -> MID DOWN LEFT
        if (position.x + 1 <= 7 && position.y - 2 >= 0) {
            val box = board[position.x + 1][position.y - 2]
            addBoxToPossibleMovements(box, pMovements)
        }

        // L -> MID DOWN RIGHT
        if (position.x + 1 <= 7 && position.y + 2 <= 7) {
            val box = board[position.x + 1][position.y + 2]
            addBoxToPossibleMovements(box, pMovements)
        }

        // L -> LEFT DOWN
        if (position.x + 2 <= 7 && position.y - 1 >= 0) {
            val box = board[position.x + 2][position.y - 1]
            addBoxToPossibleMovements(box, pMovements)
        }

        // L -> RIGHT DOWN
        if (position.x + 2 <= 7 && position.y + 1 <= 7) {
            val box = board[position.x + 2][position.y + 1]
            addBoxToPossibleMovements(box, pMovements)
        }

        return pMovements
    }

    private fun addBoxToPossibleMovements(box: Box, pMovements: ArrayList<Box>) {
        if (box.piece == null) {
            pMovements.add(box)
        } else {
            if (isEnemy(box.piece!!)) {
                pMovements.add(box)
            }
        }
    }


}