package model

interface IMovement {
    // Just move a piece from a position to the destination position
    fun move(board: Array<Array<Box>>, destination: Coordinate)

    // If the piece requires extra logic for special movements, it can be done here
    fun handleMovement(from: Coordinate, to: Coordinate)
}