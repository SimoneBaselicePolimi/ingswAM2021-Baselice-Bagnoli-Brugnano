package it.polimi.ingsw.client.cli.view.grid;

import it.polimi.ingsw.client.cli.CliClientManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GridViewTest {

    @Mock
    CliClientManager clientManager;

    GridView grid;

    @BeforeEach
    void setUp() {
        grid = new GridView(clientManager, 4, 3, 2, 31, 32);
        grid.setRowWeight(0,1);
        grid.setRowWeight(1, 2);
        grid.setRowWeight(2, 3);
        grid.setRowWeight(3, 1);
        grid.setColWeight(0, 1);
        grid.setColWeight(1, 2);
        grid.setColWeight(2, 1);
    }

    @Test
    void testGetRowStartIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRowStartIndex(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRowStartIndex(4));
        assertEquals(2, grid.getRowStartIndex(0));
        assertEquals(7, grid.getRowStartIndex(1));
        assertEquals(15, grid.getRowStartIndex(2));
        assertEquals(26, grid.getRowStartIndex(3));
    }

    @Test
    void testGetRowEndIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRowEndIndex(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getRowEndIndex(4));
        assertEquals(4, grid.getRowEndIndex(0));
        assertEquals(12, grid.getRowEndIndex(1));
        assertEquals(23, grid.getRowEndIndex(2));
        assertEquals(28, grid.getRowEndIndex(3));
    }

    @Test
    void testGetColStartIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColStartIndex(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColStartIndex(3));
        assertEquals(2, grid.getColStartIndex(0));
        assertEquals(10, grid.getColStartIndex(1));
        assertEquals(24, grid.getColStartIndex(2));
    }

    @Test
    void testGetColEndIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColEndIndex(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.getColEndIndex(3));
        assertEquals(7, grid.getColEndIndex(0));
        assertEquals(21, grid.getColEndIndex(1));
        assertEquals(29, grid.getColEndIndex(2));
    }

}