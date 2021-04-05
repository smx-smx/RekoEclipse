package com.smx.rekoeclipse.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Button;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.ILazyTreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import org.eclipse.swt.layout.GridData;

public class Test {

	/**
	 * Create the composite.
	 * @param parent
	 */
	@PostConstruct
	public void createControl(Composite parent) {
		Composite testBox = new Composite(parent, SWT.BORDER);
		
		testBox.setLayout(new GridLayout(3, false));
		
		Button btnHelloWorld = new Button(testBox, SWT.NONE);
		btnHelloWorld.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println("I WAS CLICKED");
			}
		});
		btnHelloWorld.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnHelloWorld.setText("Hello World");
		
		Label lblLabel = new Label(testBox, SWT.BORDER | SWT.HORIZONTAL | SWT.CENTER);
		lblLabel.setAlignment(SWT.CENTER);
		lblLabel.setText("Label 123");
		
		Button btnHello = new Button(testBox, SWT.RADIO);
		btnHello.setText("Hello");
		new Label(testBox, SWT.NONE);
		new Label(testBox, SWT.NONE);
		new Label(testBox, SWT.NONE);
		new Label(testBox, SWT.NONE);
		new Label(testBox, SWT.NONE);
		new Label(testBox, SWT.NONE);
		
		Tree tree = new Tree(testBox, SWT.BORDER | SWT.VIRTUAL);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		new Label(testBox, SWT.NONE);
		new Label(testBox, SWT.NONE);		
	}
}
