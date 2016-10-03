package org.kie.workbench.common.stunner.lienzo.grid;

import com.ait.lienzo.shared.core.types.Direction;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Grid implements Iterable<Grid.Point> {
    private int padding;
    private int iconSize;
    private int rows;
    private int cols;

    public Grid(int padding, int iconSize, int rows, int cols) {
        if (padding < 0 || iconSize < 0 || rows < 1 || cols < 1) {
            throw  new IllegalArgumentException("Not possible to instantiate grid.");
        }

        this.padding = padding;
        this.iconSize = iconSize;
        this.rows = rows;
        this.cols = cols;
    }

    public Point findPosition(Point anchorPoint, Direction direction) {
        int width = getWidth();
        int height = getHeight();
        int x = anchorPoint.getX();
        int y = anchorPoint.getY();

        switch (direction) {
            case NORTH:
                x -= width / 2;
                y -= height;
                break;
            case SOUTH:
                x -= width / 2;
                break;
            case EAST:
                y -= height / 2;
                break;
            case WEST:
                x -= width;
                y -= height / 2;
                break;
            case NONE:
                x -= width / 2;
                y -= height / 2;
                break;
            case NORTH_EAST:
                y -= height;
                break;
            case SOUTH_EAST:
                break;
            case SOUTH_WEST:
                x -= width;
                break;
            case NORTH_WEST:
                x -= width;
                y -= height;
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return new Point(x, y);
    }

    public Point findPosition(int row, int col) {
        if (!isInRange(row, getRows())) {
            throw new IllegalArgumentException(
                    row + " is incorrect row value. Value have to be from 0 to " + (getRows() - 1)
            );
        }

        if (!isInRange(col, getCols())) {
            throw new IllegalArgumentException(
                    col + " is incorrect col value. Value have to be from 0 to " + (getCols() - 1)
            );
        }

        int x = calculateDistance(col);
        int y = calculateDistance(row);
        return new Point(x, y);
    }

    private int calculateDistance(int position) {
        return padding + (position * (padding + iconSize));
    }

    private boolean isInRange(int value, int max) {
        return value >= 0 && value < max;
    }

    @Override
    public Iterator<Point> iterator() {
        return new GridIterator(this);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int size() {
        return getRows() * getCols();
    }

    public int getWidth() {
        return calculateDistance(getCols());
    }

    public int getHeight() {
        return calculateDistance(getRows());
    }

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    protected static class GridIterator implements Iterator<Point> {
        private final Grid grid;
        private int currentRow = 0;
        private int currentColumn = 0;


        public GridIterator(Grid grid) {
            this.grid = grid;
        }

        @Override
        public boolean hasNext() {
            return currentColumn <= lastColumn() && currentRow <= lastRow();
        }

        private int lastColumn() {
            return this.grid.getCols() - 1;
        }

        private int lastRow() {
            return this.grid.getRows() - 1;
        }

        @Override
        public Point next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int column = currentColumn;
            int row = currentRow;
            if (currentColumn == (this.grid.getCols() - 1)) {
                currentColumn = 0;
                currentRow++;
            } else {
                currentColumn++;
            }
            return this.grid.findPosition(row, column);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
