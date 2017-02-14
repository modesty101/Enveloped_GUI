package application;

import java.io.File;

import com.niko.xml.digsig.All;
import com.niko.xml.digsig.ExtractXml;
import com.niko.xml.digsig.Verification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController {

	@FXML
	private Button genKey;
	@FXML
	private Button signFile;
	@FXML
	private Button signDoc;
	@FXML
	private Button verifyFile;
	@FXML
	private Button verifyDoc;
	@FXML
	private Button extractFile;
	@FXML
	private Button extractDoc;
	@FXML
	private RadioButton rbFile;
	@FXML
	private RadioButton rbDoc;
	@FXML
	private Label inform;
	@FXML
	private Label xmlFile;
	@FXML
	private Label xmlDoc;
	@FXML
	private Label resultDoc;
	@FXML
	private Label resultFile;

	public void genKeyAction(ActionEvent event) {
		// 키 생성
		All.generateKeys();
	}

	public void keyAliveAction(ActionEvent event) {
		All.keysAlive();
	}

	public void selFileAction(ActionEvent event) throws Exception {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\eclipse\\eclipse\\signature_work\\Enveloped_GUI_Testing"));
		fc.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {
			String fileName = selectedFile.getName();
			inform.setText(fileName);
			// TODO Function here
			All.encodeFileAll(fileName, fileName + "_enc");

			// 라벨
			xmlFile.setText("_File_" + fileName + ".xml");

			// 서명
			// signFileAction(null);
		} else {
			System.out.println("file is not valid");
		}
	}

	public void signFileAction(ActionEvent event) {
		String result = All.generateAll(xmlFile.getText());
		resultFile.setText(result);
	}

	public void selDocAction(ActionEvent event) throws Exception {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\eclipse\\eclipse\\signature_work\\Enveloped_GUI_Testing"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("XML Files", "*.xml"), new ExtensionFilter("Log Files", "*.log"));
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {
			String docName = selectedFile.getName();
			inform.setText(docName);
			// TODO Function here 2
			All.encodeDocAll(docName, docName + "_enc");
			// 서명
			xmlDoc.setText("_Doc_" + docName + ".xml");
		} else {
			System.out.println("file is not valid");
		}
	}

	public void signDocAction(ActionEvent event) {
		String result = All.generateAll(xmlDoc.getText());
		resultDoc.setText(result);
	}

	public void verifyFileAction(ActionEvent event) {
		Verification.testSignedXML(resultFile.getText());
	}

	public void verifyDocAction(ActionEvent event) {
		Verification.testSignedXML(resultDoc.getText());
	}

	public void extractFileAction(ActionEvent event) throws Exception {
		ExtractXml.main(resultFile.getText());
	}
	
	public void extractDocAction(ActionEvent event) throws Exception {
		ExtractXml.main(resultDoc.getText());
	}
	
	public void rbFileAction(ActionEvent event) throws Exception {
		String message = "파일을 선택하셨습니다.";
		if (rbFile.isSelected()) {
			inform.setText(message);

			selFileAction(null);
		}
	}

	public void rbDocAction(ActionEvent event) throws Exception {
		String message = "문서를 선택하셨습니다.";
		if (rbDoc.isSelected()) {
			inform.setText(message);
			// 함수 실행?
			selDocAction(null);
		}
	}
}
