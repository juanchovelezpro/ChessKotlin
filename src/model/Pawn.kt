package model

import kotlin.math.abs

class Pawn(position: Coordinate, team: Team, observer: Chess) : Piece(position, team, observer) {

    var canBeKilledEnPassant = false

    init {

        shape = if (team == Team.WHITE) {
            "\u2659"
        } else {
            "\u265F"
        }


    }

    override fun possibleMovements(): ArrayList<Box> {
        val pMovements = ArrayList<Box>()
        val board = observer.board

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


        // Check En Passant movement
        if (position.x == 3) {
            if (position.y > 0) {
                val left = board[position.x][position.y - 1].piece
                if (left is Pawn) {
                    if (left.canBeKilledEnPassant) {
                        val leftUp = board[position.x - 1][position.y - 1]
                        if (leftUp.piece == null) {
                            pMovements.add(leftUp)
                        }
                    }
                }
            }

            if (position.y < 7) {
                val right = board[position.x][position.y + 1].piece
                if (right is Pawn) {
                    if (right.canBeKilledEnPassant) {
                        val rightUp = board[position.x - 1][position.y + 1]
                        if (rightUp.piece == null) {
                            pMovements.add(rightUp)
                        }
                    }
                }
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

        // Check En Passant Movement
        if (position.x == 4) {
            if (position.y > 0) {
                val left = board[position.x][position.y - 1].piece
                if (left is Pawn) {
                    if (left.canBeKilledEnPassant) {
                        val leftDown = board[position.x + 1][position.y - 1]
                        if (leftDown.piece == null) {
                            pMovements.add(leftDown)
                        }
                    }
                }
            }

            if (position.y < 7) {
                val right = board[position.x][position.y + 1].piece
                if (right is Pawn) {
                    if (right.canBeKilledEnPassant) {
                        val rightDown = board[position.x + 1][position.y + 1]
                        if (rightDown.piece == null) {
                            pMovements.add(rightDown)
                        }
                    }
                }
            }
        }

    }

    // Check and set if a Pawn can be killed En Passant
    private fun checkEnPassant(from: Coordinate, to: Coordinate) {
        if (!firstMovementDone) {
            if (abs(from.x - to.x) == 2) {
                canBeKilledEnPassant = true
            }
        }
    }

    private fun handleEnPassantMovement(from: Coordinate, to: Coordinate) {
        val board = observer.board
        checkEnPassant(from, to)
        // Check if the movement is "En Passant"
        if (to.y != from.y) {
            val enPassantBox = board[to.x][to.y]
            if (enPassantBox.piece == null) {
                onEnPassant(to)
            }
        }
    }

    private fun onEnPassant(to: Coordinate) {

        val board = observer.board

        println("""------------------------- SPECIAL KILL "EN PASSANT" --------------------- """)
        if (team == Team.WHITE) {
            val boxMurder = board[to.x + 1][to.y]

            println("${boxMurder.piece} - ${boxMurder.piece?.team} has been killed with En Passant movement")
            observer.blackPiecesAlive.remove(boxMurder.piece)
            boxMurder.piece = null
            println("Black Pieces Remaining: ${observer.blackPiecesAlive.size} = ${observer.blackPiecesAlive}")

        } else {

            val boxMurder = board[to.x - 1][to.y]

            println("${boxMurder.piece} - ${boxMurder.piece?.team} has been killed with En Passant movement")
            observer.whitePiecesAlive.remove(boxMurder.piece)
            boxMurder.piece = null
            println("White Pieces Remaining: ${observer.whitePiecesAlive.size} = ${observer.whitePiecesAlive}")
        }
        println("------------------------------------------------------------------------------")
    }

    override fun handleMovement(from: Coordinate, to: Coordinate) {
        handleEnPassantMovement(from, to)
    }

    fun disableCanBeKilledEnPassant() {
        if (canBeKilledEnPassant) {
            canBeKilledEnPassant = false
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


