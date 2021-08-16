package model

class Bishop(position: Coordinate, team: Team) : Piece(position, team) {

    init {

        shape = if (team == Team.WHITE) {
            "\u2657"
        } else {
            "\u265D"
        }


    }

    override fun possibleMovements(board: Array<Array<Box>>): ArrayList<Box> {
        val pMovements = ArrayList<Box>()


        // Check diagonal up right
        if (position.x >= 0 && position.y <= 7) {

            var x = position.x - 1
            var y = position.y + 1

            while (x >= 0 && y <= 7) {

                val box = board[x--][y++]

                if (box.piece == null) {
                    pMovements.add(box)
                } else {
                    if (isEnemy(box.piece!!)) {
                        pMovements.add(box)
                    }
                    break
                }
            }
        }

        // Check diagonal up left
        if (position.x >= 0 && position.y >= 0) {

            var x = position.x - 1
            var y = position.y - 1

            while (x >= 0 && y >= 0) {

                val box = board[x--][y--]

                if (box.piece == null) {
                    pMovements.add(box)
                } else {
                    if (isEnemy(box.piece!!)) {
                        pMovements.add(box)
                    }
                    break
                }
            }
        }

        // Check diagonal down left
        if (position.x <= 7 && position.y >= 0) {

            var x = position.x + 1
            var y = position.y - 1

            while (x <= 7 && position.y >= 0) {

                val box = board[x++][y--]

                if (box.piece == null) {
                    pMovements.add(box)
                } else {
                    if (isEnemy(box.piece!!)) {
                        pMovements.add(box)
                    }
                    break
                }
            }
        }

        // Check diagonal down right
        if (position.x <= 7 && position.y <= 7) {

            var x = position.x + 1
            var y = position.y + 1

            while (x <= 7 && y <= 7) {

                val box = board[x++][y++]

                if (box.piece == null) {
                    pMovements.add(box)
                } else {
                    if (isEnemy(box.piece!!)) {
                        pMovements.add(box)
                    }
                    break
                }
            }
        }


        return pMovements
    }


}