package client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.GUI.State.BatchStateListener;
import client.GUI.State.Cell;

import shared.communication.DownloadBatch_Result;
import shared.model.Field;

public class ImageComponent extends JComponent implements BatchStateListener{
	
	private DownloadBatch_Result result;
	private State state;

	private int w_originX;
	private int w_originY;
	private double scale;
	private boolean invert;

	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;
	private DrawingRect drawingRect;
	private BufferedImage image;

	private ArrayList<DrawingShape> shapes;
	
	public ImageComponent() {
		this.setBackground(Color.GRAY);
		this.setPreferredSize(new Dimension(1000, 500));
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);	
		this.addMouseWheelListener(mouseAdapter);
		this.invert = false;
		shapes = new ArrayList<DrawingShape>();	
		this.repaint();
	}
	
	public void setState(State state){
		this.state = state;
		this.result = state.getDownloadResult();
		state.addListener(this);
		if(state.getRecords() != 0){
			attachImage(state.getDownloadResult(), state.getOriginX(), state.getOriginY(), state.getScale());
			if(state.isInvert()){
				firstInvert();
			}
		}
	}
	
	public void attachImage(DownloadBatch_Result result, int x, int y, double s){
		w_originX = x;
		w_originY = y;
		scale = s;
		
		image = null;
		String url = result.getImage_url();
		System.out.println("Download Image: " + url);
		
		try {
			image = ImageIO.read(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		shapes.add(new DrawingImage(image, new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null))));
			
		drawingRect = new DrawingRect(new Rectangle2D.Double(result.getAllfields().get(0).getXcoord(),
				result.getFirst_y_coord(), 
				result.getAllfields().get(0).getWidth(), 
				result.getRecord_height()), new Color(50, 250, 100, 192));
		shapes.add(drawingRect);
		
		this.repaint();
	}
	
	public int getW_originX() {
		return w_originX;
	}
	public void setW_originX(int w_originX) {
		this.w_originX = w_originX;
	}
	public int getW_originY() {
		return w_originY;
	}
	public void setW_originY(int w_originY) {
		this.w_originY = w_originY;
	}
//	public void removeAllshapes(){
//		shapes.clear();
//		this.repaint();
//	}
	
	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		this.setSelectedCell(newSelectedCell);		
	}

	@Override
	public void batchDownloaded(DownloadBatch_Result result) {
		this.result = result;
		attachImage(result, 0, 0, 0.5);
		setOrigin(image.getWidth()/2, image.getHeight()/2);
		this.repaint();
	}

	@Override
	public void initializeState() {
		shapes.clear();
		this.result = null;
		this.repaint();
	}
	
	public void setSelectedCell(Cell newSelectedCell){
		
//		System.out.println("record " + newSelectedCell.record + " field " + newSelectedCell.field);
		shapes.remove(drawingRect);
		drawingRect = new DrawingRect(new Rectangle2D.Double(result.getAllfields().get(newSelectedCell.field-1).getXcoord(),
				result.getFirst_y_coord() + result.getRecord_height()*newSelectedCell.record, 
				result.getAllfields().get(newSelectedCell.field-1).getWidth(), 
				result.getRecord_height()), new Color(50, 250, 100, 192));
		if(state.isHightlighton()){
			shapes.add(drawingRect);
		}
		this.repaint();
	}

	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}	
	
	public void setScale(double newScale) {
		scale = newScale;
		if(scale < 0.2){
			scale = 0.2;
		}
		if(scale > 2.5){
			scale = 2.5;
		}
		this.repaint();
	}
	public double getScale(){
		return scale;
	}	
	public boolean isInvert() {
		return invert;
	}
	public void setInvert(boolean invert) {
		this.invert = invert;
	}

	public void setOrigin(int w_newOriginX, int w_newOriginY) {
		w_originX = w_newOriginX;
		w_originY = w_newOriginY;
		this.repaint();
	}
	
	public void firstInvert(){
		for(int i = 0; i < image.getWidth(); i++) {
		    for(int j = 0; j < image.getHeight(); j++) {
	            Color c = new Color(image.getRGB(i, j));
	            c = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue(), 255 - c.getAlpha());
	            image.setRGB(i, j, c.getRGB());		 
	        }
		}
		this.repaint();
		
	}
	
	public void invertImage(){
		
		if(invert){
			invert = false;
			System.out.println("no invert");
		}
		else{
			invert = true;
			System.out.println("invert");
		}
		
		for(int i = 0; i < image.getWidth(); i++) {
		    for(int j = 0; j < image.getHeight(); j++) {
	            Color c = new Color(image.getRGB(i, j));
	            c = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue(), 255 - c.getAlpha());
	            image.setRGB(i, j, c.getRGB());		 
	        }
		}
		this.repaint();
	}
	
	public void toggleHighlight(){
		
		if(state.isHightlighton()){
			shapes.remove(drawingRect);
			state.setHightlighton(false);
		}
		else{
			shapes.add(drawingRect);
			state.setHightlighton(true);
		}
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);

		g2.translate(getWidth()/2, getHeight()/2);
		g2.scale(scale, scale);
				
		// Think about this translate
		// There is something that we ALWAYS need to do
		// when centering the image...
		
		g2.translate(-w_originX, -w_originY);

		drawShapes(g2);
	}	
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
	}
	
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e){
			double mouse_x = e.getX();
			double mouse_y = e.getY();
			
			double x = mouse_x + w_originX*scale - getWidth()/2;
			double y = mouse_y + w_originY*scale - getHeight()/2;
			
			for(int i = 0; i < result.getNum_fields(); i++){
				for(int j = 0; j < result.getNum_records(); j++){
					Field field = result.getAllfields().get(i);
					if(x >= field.getXcoord()*scale && x < (field.getXcoord() + field.getWidth())*scale){
						if(y >= (result.getFirst_y_coord() + j*result.getRecord_height())*scale && y < (result.getFirst_y_coord() + result.getRecord_height()*(j+1))*scale){
							state.getSelectedCell().record = j;
							state.getSelectedCell().field = i + 1;
							state.setSelectedCell(state.getSelectedCell());
						}
					}
				}
			}
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(getWidth()/2, getHeight()/2);
			transform.scale(scale, scale);
			
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				AffineTransform transform = new AffineTransform();
				transform.translate(getWidth()/2, getHeight()/2);	
				transform.scale(scale, scale);
				
				transform.translate(-w_dragStartOriginX, -w_dragStartOriginY);
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					transform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
						
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			setScale(scale - e.getPreciseWheelRotation()/15);
			return;
		}	
	};	
	
	// Drawing Shape
	
	interface DrawingShape{
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
	}
	class DrawingRect implements DrawingShape{

		private Rectangle2D rect;
		private Color color;
		
		public DrawingRect(Rectangle2D rect, Color color){
			this.rect = rect;
			this.color = color;
		}
		
		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			Color c = null;
			if(state.isHightlighton()){
				g2.setColor(color);
			}
			else{
				c = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
				g2.setColor(c);				
			}
			g2.fill(rect);
		}

		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
		
	}
		
	class DrawingImage implements DrawingShape{
		
		private BufferedImage image;
		private Rectangle2D rect;
		
		public DrawingImage(BufferedImage image, Rectangle2D rect){
			this.image = image;
			this.rect = rect;
		}
		
		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			//We need to get the bounds of the current rectangle
			//From there we use those bounds when drawing the image
			
			
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
					0, 0, image.getWidth(null), image.getHeight(null), null);			
		}

		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
		
	}

	
}
