package application;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;

public class MainController {

	@FXML	private Button genKey;
	// @FXML	private Button selFile;
	// @FXML	private Button selDoc;
	@FXML	private Button verify;
	@FXML	private Button extract;
	@FXML	private RadioButton rbFile;
	@FXML	private RadioButton rbDoc;
	@FXML	private Label inform;

	public void genKeyAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\"));
		// fc.getExtensionFilters().add(new ExtensionFilter("PDF files",
		// "*.pdf"));
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {

		} else {
			System.out.println("file is not valid");
		}
	}
	/*
	public void selFileAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\"));
		// fc.getExtensionFilters().add(new ExtensionFilter("PDF files",
		// "*.pdf"));
		List<File> selectedFile = fc.showOpenMultipleDialog(null);

		if (selectedFile != null) {
			for (int i = 0; i < selectedFile.size(); i++) {

			}
		} else {
			System.out.println("file is not valid");
		}
	}

	public void selDocAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\"));
		// fc.getExtensionFilters().add(new ExtensionFilter("PDF files",
		// "*.pdf"));
		List<File> selectedFile = fc.showOpenMultipleDialog(null);

		if (selectedFile != null) {
			for (int i = 0; i < selectedFile.size(); i++) {

			}
		} else {
			System.out.println("file is not valid");
		}
	}
	*/
	public void verifyAction(ActionEvent event) {

	}

	public void extractAction(ActionEvent event) {
	}

	public void rbFileAction(ActionEvent event) {
		String message = "파일을 선택하셨습니다.";
		if (rbFile.isSelected()) {
			inform.setText(message);
			// 함수 실행?
			// selFileAction(null);
		}
	}

	public void rbDocAction(ActionEvent event) {
		String message = "문서를 선택하셨습니다.";
		if (rbDoc.isSelected()) {
			inform.setText(message);
			// 함수 실행?
			// selDocAction(null);
		}
	}
}
