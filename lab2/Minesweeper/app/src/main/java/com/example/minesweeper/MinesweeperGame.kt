package com.example.minesweeper

class MinesweeperGame constructor(size: Int, private val numBombs: Int) {
    private var cellGrid: CellGrid? = null
    private var gameOver = false
    private var flagMode = false
    private var clearMode = true
    private var flagCount: Int = 0
    private var timeExpired: Boolean = false

    init {
        this.cellGrid = CellGrid(size)
        this.cellGrid?.generateGrid(this.numBombs)
    }

    fun gameOver(): Boolean {
        return this.gameOver
    }

    fun getCellGrid(): CellGrid? {
        return this.cellGrid
    }

    fun inFlagMode(): Boolean {
        return this.flagMode
    }

    fun getFlagCount(): Int {
        return this.flagCount
    }

    fun getNumBombs(): Int {
        return this.numBombs
    }

    fun toggleMode() {
        this.clearMode = !this.clearMode
        this.flagMode = !this.flagMode
    }

    fun outOfTime() {
        this.timeExpired = true
    }

    fun handleCellClick(cell: Cell) {
        if (!this.gameOver && !this.gameWon && !this.timeExpired && !cell.isRevealed()) {
            if (this.clearMode) {
                clear(cell)
            }
            else if (this.flagMode) {
                flag(cell)
            }
        }
    }

    private fun clear(cell: Cell) {
        val index: Int = this.cellGrid?.getCells()?.indexOf(cell)!!
        val coordinates: IntArray = this.cellGrid?.toCoordinates(index)!!
        this.cellGrid?.cellAt(coordinates[0], coordinates[1])?.setRevealed(true)

        if (cell.getValue() == Cell.BOMB) {
            this.gameOver = true
        }
        else if (cell.getValue() == Cell.BLANK) {
            val toClear: MutableList<Cell> = ArrayList()
            val adjacentToCheck: MutableList<Cell> = ArrayList()
            adjacentToCheck.add(cell)

            while (adjacentToCheck.size > 0) {
                val c = adjacentToCheck[0]
                val cellIndex = this.cellGrid?.getCells()?.indexOf(c)!!
                val cellPos: IntArray = this.cellGrid?.toCoordinates(cellIndex)!!

                for (adjacent in this.cellGrid?.adjacentCells(cellPos[0], cellPos[1])!!) {
                    if (adjacent.getValue() == Cell.BLANK) {
                        if (!toClear.contains(adjacent)) {
                            if (!adjacentToCheck.contains(adjacent)) {
                                adjacentToCheck.add(adjacent)
                            }
                        }
                    }
                    else {
                        if (!toClear.contains(adjacent)) {
                            toClear.add(adjacent)
                        }
                    }
                }
                adjacentToCheck.remove(c)
                toClear.add(c)
            }
            for (c in toClear) {
                c.setRevealed(true)
            }
        }
    }

    private fun flag(cell: Cell) {
        cell.setFlagged(!cell.isFlagged())
        var count = 0
        for (c in this.cellGrid?.getCells()!!) {
            if (c.isFlagged()) {
                count++
            }
        }
        this.flagCount = count
    }

    val gameWon: Boolean
        get() {
            var numUnrevealed = 0
            for (c in this.cellGrid?.getCells()!!) {
                if (c.getValue() != Cell.BOMB && c.getValue() != Cell.BLANK && !c.isRevealed()) {
                    numUnrevealed++
                }
            }
            return (numUnrevealed == 0)
        }
}