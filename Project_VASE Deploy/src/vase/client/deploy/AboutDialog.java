/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

/**
 * About Dialog in the GuiMain, called by the GuiMenuBar
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class AboutDialog extends GuiDialog implements ActionListener
{
	private static final long serialVersionUID = 8326248087517279792L;
	private JTextArea jtaAbout;
	private JButton jbConfirm;
	
	/**
	 * Creates a new Dialog displaying the "About" information
	 * @param parent the parent window calling this dialog
	 */
	public AboutDialog(GuiWindow parent)
	{
		super(parent, "About VASE Deploy", DIM_DIALOG_ABOUT_MAIN);
		
		makeItems();
		makePanels();
		setMnemonics();
		pack();
		setVisible(true);
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jbConfirm = Utilities.createButton("OK", true);
		jbConfirm.addActionListener(this);
		
		jtaAbout = new JTextArea();
		jtaAbout.setEditable(false);
		jtaAbout.setFocusable(false);
		jtaAbout.setCursor(null);
		jtaAbout.setOpaque(false);
		jtaAbout.setLineWrap(true);
		jtaAbout.setWrapStyleWord(true);
		jtaAbout.setFont(FONT_DEPLOY_LABEL);
		setAboutText();
	}
	
	/**
	 * Makes the panels for this dialog
	 */
	private void makePanels()
	{
		JPanel north = new JPanel(new SpringLayout());
		ImageIcon logo = new ImageIcon(getClass().getResource("/images/logo.png"));
		JLabel title = new JLabel(logo);
		north.add(title);
		north.setPreferredSize(DIM_DIALOG_ABOUT_NORTH);
		north.setMaximumSize(DIM_DIALOG_ABOUT_NORTH);
		north.setMinimumSize(DIM_DIALOG_ABOUT_NORTH);
		
		SpringLayout northLayout = (SpringLayout) north.getLayout();
		northLayout.putConstraint(SpringLayout.NORTH, title, 0, SpringLayout.NORTH, north);
		northLayout.putConstraint(SpringLayout.SOUTH, title, 0, SpringLayout.SOUTH, north);
		northLayout.putConstraint(SpringLayout.WEST, title, 0, SpringLayout.WEST, north);
		northLayout.putConstraint(SpringLayout.EAST, title, 0, SpringLayout.EAST, north);
		
		JPanel south = new JPanel(new SpringLayout());
		south.setPreferredSize(DIM_DIALOG_ABOUT_SOUTH);
		south.setMaximumSize(DIM_DIALOG_ABOUT_SOUTH);
		south.setMinimumSize(DIM_DIALOG_ABOUT_SOUTH);
		south.add(jtaAbout);
		south.add(jbConfirm);
		
		SpringLayout southLayout = (SpringLayout) south.getLayout();
		southLayout.putConstraint(SpringLayout.NORTH, jtaAbout, 0, SpringLayout.NORTH, south);
		southLayout.putConstraint(SpringLayout.SOUTH, jtaAbout, -25, SpringLayout.SOUTH, south);
		southLayout.putConstraint(SpringLayout.WEST, jtaAbout, 10, SpringLayout.WEST, south);
		southLayout.putConstraint(SpringLayout.EAST, jtaAbout, -10, SpringLayout.EAST, south);
		southLayout.putConstraint(SpringLayout.SOUTH, jbConfirm, -5, SpringLayout.SOUTH, south);
		southLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, jbConfirm, 0, SpringLayout.HORIZONTAL_CENTER, south);
		
		setLayout(new BorderLayout());
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
	}
	
	/**
	 * Sets the mnemonics
	 */
	private void setMnemonics()
	{
		jbConfirm.setMnemonic('O');
	}
	
	/**
	 * Sets the text in the jtfAbout text field, acting as a mutli-line label
	 */
	private void setAboutText()
	{
		String about = "Created by " + AUTHOR + "\n" +
					   "Version: " + VERSION + " " + STAGE + " Build " + BUILD + "\n\n" + 
					   "Designed for the Department of Network, Security, & Systems Administration " +
					   "and the RIT Honors Program at the Rochester Institute of Technology\n" +
					   "Copyright (c) 2011 Rochester Institute of Technology\n\n" +
					   "This software is free and open source, and is governed by GNU General Public " +
					   "License, version 3 (GPL-3.0)";
		jtaAbout.setText(about);
	}
	
	/**
	 * Action performed method that disposes the dialog
	 * @param event the action event
	 */
	public void actionPerformed(ActionEvent event)
	{
		this.dispose();
	}
}
