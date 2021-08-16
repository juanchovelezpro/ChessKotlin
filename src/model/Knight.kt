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

            if (box.piece == null) {
                pMovements.add(box)
            } else {
                if (isEnemy(box.piece!!)) {
                    pMovements.add(box)
                }
            }

        }

        // L mirror -> UP RIGHT
        if (position.x - 2 >= 0 && position.y + 1 <= 7) {
            val box = board[position.x - 2][position.y + 1]

            if (box.piece == null) {
                pMovements.add(box)
            } else {
                if (isEnemy(box.piece!!)) {
                    pMovements.add(box)
                }
            }
        }

        // L -> MID UP LEFT
        if (position.x - 1 >= 0 && position.y - 2 >= 0) {
            val box = board[position.x - 1][position.y - 2]

            if (box.piece == null) {
                pMovements.add(box)
            } else {
                if (isEnemy(box.piece!!)) {
                    pMovements.add(box)
                }
            }
        }

        // L -> MID UP RIGHT
        if (position.x - 1 >= 0 && position.y + 2 <= 7) {
            val box = board[position.x - 1][position.y + 2]

            if (box.piece == null) {
                pMovements.add(box)
            } else {
                if (isEnemy(box.piece!!)) {
                    pMovements.add(box)
                }
            }
        }

        // L -> MID DOWN LEFT
        if (position.x + 1 <= 7 && position.y - 2 >= 0) {
            val box = board[position.x + 1][position.y - 2]

            if (box.piece == null) {
                pMovements.add(box)
            } else {
                if (isEnemy(box.piece!!)) {
                    pMovements.add(box)
                }
            }
        }

        // L -> MID DOWN RIGHT
        if (position.x + 1 <= 7 && position.y + 2 <= 7) {
            val box = board[position.x + 1][position.y + 2]

            if (box.piece == null) {
                pMovements.add(box)
            } else {
                if (isEnemy(box.piece!!)) {
                    pMovements.add(box)
                }
            }
        }

        // L -> LEFT DOWN
        if(position.x + 2 <= 7 && position.y - 1 >= 0){
            val box = board[position.x + 2][position.y - 1]

            if (box.piece == null) {
                pMovements.add(box)
            } else {
                if (isEnemy(box.piece!!)) {
                    pMovements.add(box)
                }
            }
        }

        // L -> RIGHT DOWN
        if(position.x + 2 <= 7 && position.y + 1 <= 7){
            val box = board[position.x + 2][position.y + 1]

            if (box.piece == null) {
                pMovements.add(box)
            } else {
                if (isEnemy(box.piece!!)) {
                    pMovements.add(box)
                }
            }
        }





        return pMovements
    }


}