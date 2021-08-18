package model

class Queen(position: Coordinate, team: Team, observer: Chess) : Piece(position, team, observer) {

    init {

        shape = if (team == Team.WHITE) {
            "\u2655"
        } else {
            "\u265B"
        }

    }

    override fun possibleMovements(): ArrayList<Box> {
        val pMovements = ArrayList<Box>()

        // As the Queen movements are like Rook and Bishop movements, then we can get from those classes

        val rook: Rook
        val bishop: Bishop

        if (team == Team.WHITE) {
            rook = Rook(Coordinate(position.x, position.y), Team.WHITE, observer)
            bishop = Bishop(Coordinate(position.x, position.y), Team.WHITE, observer)
        } else {
            rook = Rook(Coordinate(position.x, position.y), Team.BLACK, observer)
            bishop = Bishop(Coordinate(position.x, position.y), Team.BLACK, observer)
        }

        pMovements.addAll(rook.possibleMovements())
        pMovements.addAll(bishop.possibleMovements())


        return pMovements
    }

    override fun canKillBoxes(): ArrayList<Box> {
        val canKillBoxes = ArrayList<Box>()

        // As the Queen movements are like Rook and Bishop movements, then we can get from those classes
        val rook: Rook
        val bishop: Bishop

        if (team == Team.WHITE) {
            rook = Rook(Coordinate(position.x, position.y), Team.WHITE, observer)
            bishop = Bishop(Coordinate(position.x, position.y), Team.WHITE, observer)
        } else {
            rook = Rook(Coordinate(position.x, position.y), Team.BLACK, observer)
            bishop = Bishop(Coordinate(position.x, position.y), Team.BLACK, observer)
        }

        canKillBoxes.addAll(rook.canKillBoxes())
        canKillBoxes.addAll(bishop.canKillBoxes())

        return canKillBoxes

    }


}