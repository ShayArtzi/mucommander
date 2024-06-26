/*
 * This file is part of muCommander, http://www.mucommander.com
 *
 * muCommander is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * muCommander is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mucommander.ui.main.frame;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Collection;
import java.util.Collections;

import com.mucommander.ui.main.MainFrame;
import com.mucommander.ui.main.WindowManager;

/**
 * 
 * @author Arik Hadas
 */
public class ClonedMainFrameBuilder extends MainFrameBuilder {
	
	/** Number of pixels a new MainFrame will be moved to the left from its parent. */
    private static final int X_OFFSET = 22;
    /** Number of pixels a new MainFrame will be moved down from its parent. */
    private static final int Y_OFFSET = 22;

    public int getSelectedFrame() {
    	return WindowManager.getMainFrames().size()-1;
    }

	@Override
	public Collection<MainFrame> build() {
		MainFrame currentMainFrame = WindowManager.getCurrentMainFrame();
		
		MainFrame mainFrame = new MainFrame(currentMainFrame);
		
		// If this is a cloned window, use the same dimensions as the previous MainFrame, with
        // a slight horizontal and vertical offset to make sure we keep both of them visible.
		
		Dimension screenSize   = Toolkit.getDefaultToolkit().getScreenSize();
		int x             = currentMainFrame.getJFrame().getX() + X_OFFSET;
        int y             = currentMainFrame.getJFrame().getY() + Y_OFFSET;
        int width         = currentMainFrame.getJFrame().getWidth();
        int height        = currentMainFrame.getJFrame().getHeight();

        // Make sure we're still within the screen.
        // Note that while the width and height tests look redundant, they are required. Some
        // window managers, such as Gnome, return rather peculiar results.
        if(!isInsideUsableScreen(currentMainFrame.getJFrame(), x + width, -1))
            x = 0;
        if(!isInsideUsableScreen(currentMainFrame.getJFrame(), -1, y + height))
            y = 0;
        if(width + x > screenSize.width)
            width = screenSize.width - x;
        if(height + y > screenSize.height)
            height = screenSize.height - y;
        
        mainFrame.getJFrame().setBounds(new Rectangle(x, y, width, height));
        
		return Collections.singleton(mainFrame);
	}
	
	// - Screen handling --------------------------------------------------------
    // --------------------------------------------------------------------------
    /**
     * Computes the screen's insets for the specified window and returns them.
     * <p>
     * While this might seem strange, screen insets can change from one window
     * to another. For example, on X11 windowing systems, there is no guarantee that
     * a window will be displayed on the same screen, let alone computer, as the one
     * the application is running on.
     * </p>
     * @param window the window for which screen insets should be computed.
     * @return the screen's insets for the specified window
     */
    public static Insets getScreenInsets(Window window) {
        return Toolkit.getDefaultToolkit().getScreenInsets(window.getGraphicsConfiguration());
    }
	
	
    /**
     * Checks whether the specified frame can be moved to the specified coordinates and still
     * be fully visible.
     * <p>
     * If <code>x</code> (resp. <code>y</code>) is <code>null</code>, this method won't test
     * whether the frame is within horizontal (resp. vertical) bounds.
     * </p>
     * @param frame frame who's visibility should be tested.
     * @param x     horizontal coordinate of the upper-leftmost corner of the area to check for.
     * @param y     vertical coordinate of the upper-leftmost corner of the area to check for.
     * @return      <code>true</code> if the frame can be moved at the specified location,
     *              <code>false</code> otherwise.
     */
    public static boolean isInsideUsableScreen(Frame frame, int x, int y) {
        Insets    screenInsets;
        Dimension screenSize;

        screenInsets = getScreenInsets(frame);
        screenSize   = Toolkit.getDefaultToolkit().getScreenSize();

        return (x < 0 || (x >= screenInsets.left && x < screenSize.width - screenInsets.right))
            && (y < 0 || (y >= screenInsets.top && y < screenSize.height - screenInsets.bottom));
    }
}
