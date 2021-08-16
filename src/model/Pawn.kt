package model

class Pawn(position: Coordinate, team: Team) : Piece(position, team) {


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
            pMovements.addAll(possibleWhiteMovements(board))
        } else {
            pMovements.addAll(possibleBlackMovements(board))
        }

        return pMovements
    }

    private fun possibleBlackMovements(board: Array<Array<Box>>): ArrayList<Box> {

        val pMovements = ArrayList<Box>()

        if (position.x != 1) {

            // x < 7 to avoid out of bounds exception if this piece is on 7, then is a Queen.
            if (position.x < 7) {

                // Check the next box
                if (board[position.x + 1][position.y].piece == null) {
                    pMovements.add(board[position.x + 1][position.y])
                }
            }

        } else {

            // Check two next boxes for first movement
            for (i in position.x + 1..position.x + 2) {
                if (board[i][position.y].piece == null) {
                    pMovements.add(board[i][position.y])
                } else {
                    // If there is another piece, then stop.
                    break
                }
            }

        }

        // Check diagonals

        if (position.x < 7) {
            val downRight = if (position.y in 0..6) {
                checkDiagonal(board, 4)
            } else {
                null
            }

            val downLeft = if (position.y in 1..7) {
                checkDiagonal(board, 3)
            } else {
                null
            }

            if (downLeft != null) {
                pMovements.add(downLeft)
            }

            if (downRight != null) {
                pMovements.add(downRight)
            }
        }

        return pMovements
    }

    private fun possibleWhiteMovements(board: Array<Array<Box>>): ArrayList<Box> {

        val pMovements = ArrayList<Box>()

        // The first movement has been done
        if (position.x != 6) {

            // x > 0 to avoid out of bounds exception if this piece is on 0, then is a Queen.
            if (position.x > 0) {

                // Check next box
                if (board[position.x - 1][position.y].piece == null) {
                    pMovements.add(board[position.x - 1][position.y])
                }

            }

            // In the initial position
        } else {

            // Check the next two boxes for first movement
            for (i in position.x - 1 downTo position.x - 2) {

                if (board[i][position.y].piece == null) {
                    pMovements.add(board[i][position.y])
                } else {
                    // If there is another piece, then stop
                    break
                }

            }

        }

        // Check diagonals

        if (position.x > 0) {
            val upRight = if (position.y in 0..6) {
                checkDiagonal(board, 2)
            } else {
                null
            }

            val upLeft = if (position.y in 1..7) {
                checkDiagonal(board, 1)
            } else {
                null
            }

            if (upRight != null) {
                pMovements.add(upRight)
            }

            if (upLeft != null) {
                pMovements.add(upLeft)
            }
        }

        return pMovements
    }

    private fun checkDiagonal(board: Array<Array<Box>>, diagonal: Int): Box? {

        var box = when (diagonal) {

            // UP LEFT
            1 -> {
                board[position.x - 1][position.y - 1]
            }

            // UP RIGHT
            2 -> {
                board[position.x - 1][position.y + 1]
            }

            // DOWN LEFT
            3 -> {
                board[position.x + 1][position.y - 1]
            }

            // DOWN RIGHT
            4 -> {
                board[position.x + 1][position.y + 1]
            }

            else -> null
        }

        if (box?.piece != null) {
            if (!isEnemy(box.piece!!)) {
                box = null
            }
        } else {
            box = null
        }


        return box

    }


}