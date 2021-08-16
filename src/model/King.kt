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

        return pMovements
    }

    private fun isOnAttack(board: Array<Array<Box>>, box: Box): Boolean {
        return false
    }

}