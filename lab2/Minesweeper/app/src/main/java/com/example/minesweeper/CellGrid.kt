package com.example.minesweeper

import java.util.*

class CellGrid constructor(private val size: Int) {
    private val cells: MutableList<Cell>

    init {
        cells = ArrayList()
        val gridSize: Int = this.size * this.size
        for (i in 0 until gridSize) {
            cells.add(Cell(Cell.BLANK))
        }
    }

    fun getCells (): List<Cell> {
        return this.cells
    }

    fun cellAt (x: Int, y: Int): Cell? {
        return if (x < 0 || x >= this.size || y < 0 || y >= this.size) {
            null
        }
        else this.cells[this.indexOf(x, y)]
    }

    fun adjacentCells (x: Int, y: Int): List<Cell> {
        val adjacentCells: MutableList<Cell> = ArrayList()
        val cells: MutableList<Cell?> = ArrayList()

        cells.add(this.cellAt(x - 1, y - 1))
        cells.add(this.cellAt(x - 1, y))
        cells.add(this.cellAt(x - 1, y + 1))
        cells.add(this.cellAt(x, y - 1))
        cells.add(this.cellAt(x, y + 1))
        cells.add(this.cellAt(x + 1, y - 1))
        cells.add(this.cellAt(x + 1, y))
        cells.add(this.cellAt(x + 1, y + 1))

        for (cell in cells) {
            if (cell != null) {
                adjacentCells.add(cell)
            }
        }

        return adjacentCells
    }

    private fun indexOf (x: Int, y: Int): Int {
        return x + y * this.size
    }

    fun toCoordinates (index: Int): IntArray {
        val y = index / this.size
        val x = index - y * this.size
        return intArrayOf(x, y)
    }

    fun generateGrid (totalBombs: Int) {
        var bombsPlaced = 0
        while (bombsPlaced < totalBombs) {
            val x = Random().nextInt(this.size)
            val y = Random().nextInt(this.size)
            if (cellAt(x, y)!!.getValue() == Cell.BLANK) {
                this.cells[x + y * this.size] = Cell(Cell.BOMB)
                bombsPlaced++
            }
        }
        for (x in 0 until size) {
            for (y in 0 until size) {
                if (cellAt(x, y)!!.getValue() != Cell.BOMB) {
                    val adjacentCells = adjacentCells(x, y)
                    var countBombs = 0
                    for (cell in adjacentCells) {
                        if (cell.getValue() == Cell.BOMB) {
                            countBombs++
                        }
                    }
                    if (countBombs > 0) {
                        cells[x + y * size] = Cell(countBombs)
                    }
                }
            }
        }
    }

    fun revealBombs (gameWon: Boolean) {
        for (cell in cells) {
            if (cell.getValue() == Cell.BOMB) {
                cell.setRevealed(true)
                cell.setGameWon(gameWon)
            }
        }
    }
}