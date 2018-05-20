package view;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import view.UI;
import view.inputhandler.InputHandler;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.*;
import static org.mockito.Mockito.*;

public class InputHandlerTests {
    private InputHandler inputHandler;

    @Mock
    private UI ui;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        inputHandler = new InputHandler(ui);
    }

    @Test
    public void testIgnoreKeyEvent() {
    	inputHandler.handleKeyEvent(KEY_TYPED, 0, 'a');
        verifyZeroInteractions(ui);
    }

    @Test
    public void testNewInteraction() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_CONTROL, CHAR_UNDEFINED);
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_N, 'n');
        verify(ui, times(1)).createNewInteraction();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_N, 'n');
        verify(ui, times(2)).createNewInteraction();
    }

    @Test
    public void testDuplicateActiveWindow() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_CONTROL, CHAR_UNDEFINED);
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_D, 'd');
        verify(ui, times(1)).duplicateActiveWindow();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_D, 'd');
        verify(ui, times(2)).duplicateActiveWindow();
    }
    
    @Test
    public void testOpenDialogBox() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_CONTROL, CHAR_UNDEFINED);
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_ENTER, '\n');
        verify(ui, times(1)).openDialogBox();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_ENTER, '\n');
        verify(ui, times(2)).openDialogBox();
    }
    
    @Test
    public void testPressTab() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_TAB, '\t');
        verify(ui, times(1)).pressTab();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_TAB, '\t');
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_TAB, '\t');
        verify(ui, times(3)).pressTab();
    }
    
    @Test
    public void testDeleteComponent() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_DELETE, (char) 0x7F);
        verify(ui, times(1)).deleteComponent();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_DELETE, (char) 0x7F);
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_DELETE, (char) 0x7F);
        verify(ui, times(3)).deleteComponent();
    }
    
    @Test
    public void testConfirmLabel() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_ENTER, '\n');
        verify(ui, times(1)).confirmLabel();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_ENTER, '\n');
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_ENTER, '\n');
        verify(ui, times(3)).confirmLabel();
    }

    @Test
    public void testRemoveLabelCharacter() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_BACK_SPACE, '\b');
        verify(ui, times(1)).removeLabelCharacter();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_BACK_SPACE, '\b');
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_BACK_SPACE, '\b');
        verify(ui, times(3)).removeLabelCharacter();
    }

    @Test
    public void testPressSpace() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_SPACE, ' ');
        verify(ui, times(1)).pressSpace();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_SPACE, ' ');
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_SPACE, ' ');
        verify(ui, times(3)).pressSpace();
    }

    @Test
    public void testArrowUp() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_UP, CHAR_UNDEFINED);
        verify(ui, times(1)).arrowUp();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_UP, CHAR_UNDEFINED);
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_UP, CHAR_UNDEFINED);
        verify(ui, times(3)).arrowUp();
    }
    
    @Test
    public void testArrowDown() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_DOWN, CHAR_UNDEFINED);
        verify(ui, times(1)).arrowDown();
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_DOWN, CHAR_UNDEFINED);
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_DOWN, CHAR_UNDEFINED);
        verify(ui, times(3)).arrowDown();
    }

    @Test
    public void testAddLabelCharacter() {
    	inputHandler.handleKeyEvent(KEY_PRESSED, VK_L, 'l');
        verify(ui, times(1)).addLabelCharacter(VK_L, 'l');
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_L, 'l');
        inputHandler.handleKeyEvent(KEY_PRESSED, VK_L, 'l');
        verify(ui, times(3)).addLabelCharacter(VK_L, 'l');
    }

    @Test
    public void testClickedOnce() {
    	inputHandler.handleMouseEvent(MOUSE_CLICKED, 0, 0, 1);
        verify(ui, times(1)).clickedOnce(Mockito.any(Integer.class), Mockito.any(Integer.class));
        inputHandler.handleMouseEvent(MOUSE_CLICKED, 0, 0, 1);
        verify(ui, times(2)).clickedOnce(Mockito.any(Integer.class), Mockito.any(Integer.class));
        verify(ui, times(0)).released(Mockito.any(Integer.class), Mockito.any(Integer.class));
    }

    @Test
    public void testClickedTwice() {
    	inputHandler.handleMouseEvent(MOUSE_CLICKED, 0, 0, 2);
        verify(ui, times(1)).clickedTwice(Mockito.any(Integer.class), Mockito.any(Integer.class));
        inputHandler.handleMouseEvent(MOUSE_CLICKED, 0, 0, 2);
        verify(ui, times(2)).clickedTwice(Mockito.any(Integer.class), Mockito.any(Integer.class));
        verify(ui, times(0)).released(Mockito.any(Integer.class), Mockito.any(Integer.class));
    }
    
    @Test
    public void testPressed() {
    	inputHandler.handleMouseEvent(MOUSE_PRESSED, 0, 0, 1);
        verify(ui, times(1)).pressed(Mockito.any(Integer.class), Mockito.any(Integer.class));
        inputHandler.handleMouseEvent(MOUSE_PRESSED, 0, 0, 1);
        verify(ui, times(2)).pressed(Mockito.any(Integer.class), Mockito.any(Integer.class));
        verify(ui, times(0)).released(Mockito.any(Integer.class), Mockito.any(Integer.class));
    }

    @Test
    public void testDragged() {
    	inputHandler.handleMouseEvent(MOUSE_DRAGGED, 0, 0, 1);
        verify(ui, times(1)).dragged(Mockito.any(Integer.class), Mockito.any(Integer.class));
        inputHandler.handleMouseEvent(MOUSE_DRAGGED, 0, 0, 1);
        verify(ui, times(2)).dragged(Mockito.any(Integer.class), Mockito.any(Integer.class));
        verify(ui, times(0)).released(Mockito.any(Integer.class), Mockito.any(Integer.class));
    }

    @Test
    public void testReleased() {
    	inputHandler.handleMouseEvent(MOUSE_RELEASED, 0, 0, 1);
        verify(ui, times(1)).released(Mockito.any(Integer.class), Mockito.any(Integer.class));
        inputHandler.handleMouseEvent(MOUSE_RELEASED, 0, 0, 1);
        verify(ui, times(2)).released(Mockito.any(Integer.class), Mockito.any(Integer.class));
        verify(ui, times(0)).pressed(Mockito.any(Integer.class), Mockito.any(Integer.class));
    }
}
