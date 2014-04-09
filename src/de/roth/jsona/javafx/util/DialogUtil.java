package de.roth.jsona.javafx.util;

import insidefx.undecorator.Undecorator;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Simple Dialog generator
 * 
 * @author Frank Roth
 * 
 */
public class DialogUtil {

	/**
	 * Create a dialog and define a parent stage, and a dialog layout.fxml
	 * 
	 * @param parent
	 * @param layoutSource
	 * @return
	 */
	public static Stage createDialog(Stage parent, URL layoutSource, boolean modal) {
		final Stage dialog = new Stage(StageStyle.TRANSPARENT);
		if(modal){
			dialog.initModality(Modality.WINDOW_MODAL);
		} else {
			dialog.initModality(Modality.NONE);
		}
		dialog.initOwner(parent);
		dialog.setResizable(false);

		FXMLLoader loader = new FXMLLoader(layoutSource);
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}

		Undecorator undecorator = new Undecorator(dialog, (Region) root);
		undecorator.getStylesheets().add("insidefx/undecorator/undecorator.css");

		Scene s = new Scene(undecorator);
		s.setFill(Color.TRANSPARENT);
		dialog.setScene(s);
		AlignmentUtil.center(parent, dialog);

		return dialog;
	}
}
