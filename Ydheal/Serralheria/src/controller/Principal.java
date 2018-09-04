package controller;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;

import view.jFrames.JFTelalogin;

public class Principal {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFTelalogin frame = new JFTelalogin();
					frame.setVisible(true);
					Image img = new ImageIcon(this.getClass().getResource(
							"/logo.png")).getImage();
					frame.setIconImage(img);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
