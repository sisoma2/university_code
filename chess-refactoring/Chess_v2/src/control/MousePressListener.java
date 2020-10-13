package control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import view.Chess32;

/**
 * The listener interface for receiving mousePress events.
 * The class that is interested in processing a mousePress
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMousePressListener<code> method. When
 * the mousePress event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MousePressEvent
 */
public class MousePressListener extends MouseAdapter{
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		Chess32 c = (Chess32) event.getSource();
		
		String temp;
		temp = Integer.toString(y / 50 + 1);
		System.out.println("rawx = " + temp);
		temp = Integer.toString(x / 50 + 1);
		System.out.println("rawy = " + temp);
		c.setNumclicks((c.getNumclicks() + 1) % 2);
		// this way we can move pieces with alternating mouse clicks
		// when ok = true movement takes place

		if (c.getOk())
			c.movePiece(x, y);
		else
			// when ok = false we look for a piece
			c.boardID(x, y);
		c.repaint();
	}
}
