package client.GUI;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollBar;

import shared.communication.DownloadBatch_Result;

public class State implements java.io.Serializable { 
	
	private String[][] values;
	private Cell selectedCell;
	private transient List<BatchStateListener> listeners;
	
	private boolean highlight_on;
	private double scale;
	private int originX;
	private int originY;
	private boolean invert;
	
	private int windowPositionX;
	private int windowPositionY;
	private Dimension windowSize;
	private int horizontalSplit;
	private int verticalSplit;
	private int initialX;
	
//	private int tableScrollY;
	
	private int records;
	private int fields;
	private DownloadBatch_Result result;
	
	private boolean downloadenble;
	private boolean submitable;
	
	class Cell implements java.io.Serializable{
		int record;
		int field;
	}
	
	interface BatchStateListener{
		
		public void valueChanged(Cell cell, String newValue);
		public void selectedCellChanged(Cell newSelectedCell);
		public void batchDownloaded(DownloadBatch_Result result);
		public void initializeState();
	
	}
		
	public State() {
		selectedCell = new Cell();
		listeners = new ArrayList<BatchStateListener>();
		this.downloadenble = true;
		this.highlight_on = true;
		this.records = 0;
		this.fields = 0;
	}
	
	public String[][] getValues(){
		return values;
	}
	public int getRecords(){
		return records;
	}
	public int getFields(){
		return fields;
	}
	public void setDownloadResult(DownloadBatch_Result result){
		this.result = result;
	}
	public DownloadBatch_Result getDownloadResult(){
		return result;
	}
	
	public void createListener(){
		listeners = new ArrayList<BatchStateListener>();
	}
	public int getListeners(){
		return listeners.size();
	}
	
	public boolean isHightlighton(){
		return highlight_on;
	}
	public void setHightlighton(boolean highlight_on){
		this.highlight_on = highlight_on;
	}
	public int getOriginX() {
		return originX;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}
	public int getOriginY() {
		return originY;
	}
	public void setOriginY(int originY) {
		this.originY = originY;
	}
	public double getScale() {
		return scale;
	}
	public boolean isInvert() {
		return invert;
	}
	public void setInvert(boolean invert) {
		this.invert = invert;
	}

	public void createValues(int records, int fields){
		this.records = records;
		this.fields = fields;
		values = new String[records][fields];
	}
	public String getValue(Cell cell){
		return values[cell.record][cell.field];
	}
	
	public void setWindowPositionX(int windowPositionX){
		this.windowPositionX = windowPositionX;
	}
	public void setWindowPositionY(int windowPositionY){
		this.windowPositionY = windowPositionY;
	}
	public int getWindowPositionX(){
		return windowPositionX;
	}
	public int getWindowPositionY(){
		return windowPositionY;
	}
	public void setWindowSize(Dimension windowSize){
		this.windowSize = windowSize;
	}
	public Dimension getWindowSize(){
		return windowSize;
	}
	public int getHorizontalSplit(){
		return horizontalSplit;
	}
	public int getVerticalSplit(){
		return verticalSplit;
	}
	public void setHorizontalSplit(int horizontalSplit){
		this.horizontalSplit = horizontalSplit;
	}
	public void setVerticalSplit(int verticalSplit){
		this.verticalSplit = verticalSplit;
	}
	public int getInitialX() {
		return initialX;
	}
	public void setInitialX(int initialX) {
		this.initialX = initialX;
	}

	public boolean isDownloadenble() {
		return downloadenble;
	}
	public void setDownloadenble(boolean downloadenble) {
		this.downloadenble = downloadenble;
	}
	
	public boolean isSubmitable() {
		return submitable;
	}
	public void setSubmitable(boolean submitable) {
		this.submitable = submitable;
	}

//	public int getTableScrollY() {
//		return tableScrollY;
//	}
//	public void setTableScrollY(int tableScrollY) {
//		this.tableScrollY = tableScrollY;
//	}
	
	public void setScale(double s){
		this.scale = s;
	}
	
	public void addListener(BatchStateListener l){
		listeners.add(l);
	}
	
	public void removeAllListener(){
		listeners.clear();
	}
	
	public void setValue(Cell cell, String value){
		values[cell.record][cell.field] = value;
		
//		System.out.println("Value-changed cell: " + cell.record + " " + cell.field);
		
		for(BatchStateListener l: listeners){
			l.valueChanged(cell, value);
		}
	}
	
	public void setSelectedCell(Cell setCell){
		selectedCell = setCell;
		
		for(BatchStateListener l: listeners){
			l.selectedCellChanged(setCell);
		}
	}
	
	public Cell getSelectedCell(){
		return selectedCell;
	}
	public void downloadBatch(DownloadBatch_Result result){
		for(BatchStateListener l: listeners){
			l.batchDownloaded(result);
		}
	}
	
	public void initializeState(){
		this.result = null;
		this.records = 0;
		this.fields = 0;		
		for(BatchStateListener l: listeners){
			l.initializeState();
		}
		
	}
	
}
