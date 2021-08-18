package model

class Rook(position: Coordinate, team: Team, observer: Chess) : Piece(position, team, observer) {

    init {

        shape = if (team == Team.WHITE) {
            "\u2656"
        } else {
            "\u265C"
        }

    }

    override fun possibleMovements(): ArrayList<Box> {
        val pMovements = ArrayList<Box>()
        val board = observer.board

        // Check movements -- left
        if (position.y > 0) {
            for (i in position.y - 1 downTo 0) {

                val box = board[position.x][i]

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

        // Check movements -- right
        if (position.y < 7) {

            for (i in position.y + 1..7) {

                val box = board[position.x][i]

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

        // Checking movements -- up
        if (position.x > 0) {

            for (i in position.x - 1 downTo 0) {

                val box = board[i][position.y]

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


        // Check movements -- down
        if (position.x < 7) {

            for (i in position.x + 1..7) {

                val box = board[i][position.y]

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

    override fun canKillBoxes(): ArrayList<Box> {
        val canKillBoxes = ArrayList<Box>()
        val board = observer.board

        // Check movements -- left
        if (position.y > 0) {
            for (i in position.y - 1 downTo 0) {

                val box = board[position.x][i]

                if (box.piece == null) {
                    canKillBoxes.add(box)
                } else {
                    if (!isEnemy(box.piece!!)) {
                        canKillBoxes.add(box)
                    } else {
                        continue
                    }
                    break
                }
            }
        }

        // Check movements -- right
        if (position.y < 7) {

            for (i in position.y + 1..7) {

                val box = board[position.x][i]

                if (box.piece == null) {
                    canKillBoxes.add(box)
                } else {
                    if (!isEnemy(box.piece!!)) {
                        canKillBoxes.add(box)
                    } else {
                        continue
                    }
                    break
                }
            }
        }

        // Checking movements -- up
        if (position.x > 0) {

            for (i in position.x - 1 downTo 0) {

                val box = board[i][position.y]

                if (box.piece == null) {
                    canKillBoxes.add(box)
                } else {
                    if (!isEnemy(box.piece!!)) {
                        canKillBoxes.add(box)
                    } else {
                        continue
                    }
                    break
                }
            }
        }


        // Check movements -- down
        if (position.x < 7) {

            for (i in position.x + 1..7) {

                val box = board[i][position.y]

                if (box.piece == null) {
                    canKillBoxes.add(box)
                } else {
                    if (!isEnemy(box.piece!!)) {
                        canKillBoxes.add(box)
                    } else {
                        continue
                    }
                    break
                }
            }
        }

        return canKillBoxes
    }


}