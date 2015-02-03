	package com.variamos.gui.maineditor;

import java.awt.Color;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.variamos.gui.refas.editor.RefasGraph;
import com.variamos.gui.refas.editor.RefasGraphEditorFunctions;
import com.variamos.gui.refas.editor.RefasMenuBar;
import com.variamos.gui.refas.editor.SemanticPlusSyntax;
import com.variamos.refas.RefasModel;
import com.variamos.semantic.types.PerspectiveType;

public class MainFrame extends JFrame {
	public List<VariamosGraphEditor> getGraphEditors() {
		return graphEditors;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3838729345096823146L;
	private String perspectiveTitle;
	/**
	 * 
	 */
	protected String appTitle;
	private int perspective = 1;
	private Cursor waitCursor, defaultCursor;

	public int getPerspective() {
		return perspective;
	}

	private List<VariamosGraphEditor> graphEditors;
	private List<RefasMenuBar> editorsMenu;

	public MainFrame() {
		graphEditors = new ArrayList<VariamosGraphEditor>();
		editorsMenu = new ArrayList<RefasMenuBar>();
		this.appTitle = "VariaMos";
		this.perspectiveTitle = "Modeling Perspective";
		this.setTitle(perspectiveTitle + " - " + appTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1070, 740);
		RefasModel basicSyntaxRefas = new RefasModel(PerspectiveType.basicSyntax);
		RefasModel basicSemanticRefas = new RefasModel(PerspectiveType.basicSemantic);
		RefasModel semanticRefas = null;
		RefasModel syntaxRefas = null;
		RefasModel abstractModel = null;
		SemanticPlusSyntax sematicSyntaxObject = new SemanticPlusSyntax();
		VariamosGraphEditor.setSematicSyntaxObject(sematicSyntaxObject);
		RefasGraph refasGraph = null;
		Color bgColor = null;
		VariamosGraphEditor modelEditor = null;
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0: // semantic
				abstractModel = new RefasModel(basicSemanticRefas);
				semanticRefas = abstractModel;
				syntaxRefas = new RefasModel(PerspectiveType.syntax,
						basicSyntaxRefas, semanticRefas);
				bgColor = new Color(252, 233, 252);
				System.out.println("Creating Semantic Perspective...");
				break;

			case 1:// modeling
				abstractModel = new RefasModel(PerspectiveType.modeling,
						syntaxRefas, semanticRefas);

				bgColor = new Color(236, 238, 255);
				System.out.println("Creating Modeling Perspective...");
				break;

			case 2:// syntax
				abstractModel = syntaxRefas;

				bgColor = new Color(255, 255, 245);
				System.out.println("Creating Syntax Perspective...");
				break;

			case 3:// simulation
				abstractModel = new RefasModel(PerspectiveType.simulation,
						syntaxRefas, semanticRefas);
				bgColor = new Color(236, 252, 255);
				System.out.println("Creating Simulation Perspective...");
				break;

			}
			refasGraph = new RefasGraph(i+1, abstractModel);

			VariamosGraphEditor editor = new VariamosGraphEditor(this,
					new VariamosGraphComponent(refasGraph, bgColor), i + 1,
					abstractModel);
			editor.setGraphEditorFunctions(new RefasGraphEditorFunctions(editor));
			if (i == 1)
				modelEditor = editor;

			if (i == 3)
				editor.setModelEditor(modelEditor);

			waitCursor = new Cursor(Cursor.WAIT_CURSOR);
			defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

			graphEditors.add(editor);

			editorsMenu.add(new RefasMenuBar(graphEditors.get(i)));

			editor.updateView();
		}

		System.out.println("GUI creation complete");
		this.add(graphEditors.get(1));
		this.setJMenuBar(editorsMenu.get(1));
		this.setVisible(true);
	}

	public void waitingCursor(boolean waitingCursor) {
		if (waitingCursor)
			this.setCursor(waitCursor);
		else
			this.setCursor(defaultCursor);

	}

	public void setPerspective(int perspective) {
		this.perspective = perspective;
	}

	public void setLayout() {
		this.getRootPane().getContentPane().removeAll();
		this.add(graphEditors.get(perspective - 1));
		this.setJMenuBar(editorsMenu.get(perspective - 1));
		graphEditors.get(perspective - 1).updateObjects();
		graphEditors.get(perspective - 1).setVisibleModel(0, -1);
		graphEditors.get(perspective - 1).updateView();
		this.revalidate();
		this.repaint();
	}
}
