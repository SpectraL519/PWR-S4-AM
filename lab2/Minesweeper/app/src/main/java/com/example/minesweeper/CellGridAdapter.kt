package com.example.minesweeper

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CellGridAdapter constructor(private var cells: List<Cell>, private val listener: CellListener) :
    RecyclerView.Adapter<CellGridAdapter.CellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return CellViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.bind(cells[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    // @SuppressLint("NotifyDataSetChanged")
    fun setCells(cells: List<Cell>) {
        this.cells = cells
        notifyDataSetChanged()
    }

        inner class CellViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            private var valueImageView: ImageView
            private val imageMap: Map<Int, Int> = mapOf(
                1 to R.drawable.one,
                2 to R.drawable.two,
                3 to R.drawable.three,
                4 to R.drawable.four,
                5 to R.drawable.five,
                6 to R.drawable.six,
                7 to R.drawable.seven,
                8 to R.drawable.eight,
                Cell.BLANK to R.drawable.blank
            )

            init {
                this.valueImageView = itemView.findViewById<ImageView>(R.id.cell_item)
            }

            fun bind(cell: Cell) {
                itemView.setBackgroundColor(Color.GRAY)
                itemView.setOnClickListener { listener.cellClick(cell) }

                if (cell.isRevealed()) {
                    when (val cellValue: Int = cell.getValue()) {
                        Cell.BOMB -> {
                            if (cell.gameWon()) {
                                this.valueImageView.setBackgroundResource(R.drawable.bomb)
                            } else {
                                this.valueImageView.setBackgroundResource(R.drawable.bomb_red)
                            }
                        }
                        else -> {
                            this.valueImageView.setBackgroundResource(this.imageMap[cellValue]!!)
                        }
                    }
                } else if (cell.isFlagged()) {
                    this.valueImageView.setBackgroundResource(R.drawable.flag)
                }
            }
        }
    }