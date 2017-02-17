package application;

import java.io.File;
import java.util.Optional;

import com.niko.xml.digsig.All;
import com.niko.xml.digsig.ExtractXml;
import com.niko.xml.digsig.Verification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

	/**
	 * 키 생성 + 대화형 형식 (YES OR NO)
	 * 
	 * @param event
	 */
	public void genKeyAction(ActionEvent event) {
		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Generate Keys..");
		alert.setHeaderText("키를 생성하시겠습니까?");
		alert.setContentText(
				"Do you want to generate keys?\n※if folder in keys exist but key dosen't\nyou will click this button 'Is key alive'");

		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

		// NO 옵션을 디폴트로 설정하지 않는다.
		Button yesButton = (Button) alert.getDialogPane().lookupButton(ButtonType.NO);
		yesButton.setDefaultButton(false);

		// YES 옵션을 디폴트로 설정한다.
		Button noButton = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
		noButton.setDefaultButton(true);

		final Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.YES) {
			All.generateKeys();
		} else if (result.get() == ButtonType.NO) {
			alert.close();
		} else {
			// Nothing..
		}
	}

	/**
	 * 키가 존재하는지 확인 여부
	 * 
	 * @param event
	 */
	public void keyAliveAction(ActionEvent event) {
		All.keysAlive();
	}

	/**
	 * 파일: 선택
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void selFileAction(ActionEvent event) throws Exception {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\"));
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

	/**
	 * 파일: 서명(Sign) + 서명된 결과는 텍스트로 출력
	 * 
	 * @param event
	 */
	public void signFileAction(ActionEvent event) {
		String result = All.generateAll(xmlFile.getText());
		resultFile.setText(result);
	}

	/**
	 * 문서: 선택
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void selDocAction(ActionEvent event) throws Exception {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\"));
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

	/**
	 * 문서: 서명(Sign) + 서명된 결과는 텍스트로 출력
	 * 
	 * @param event
	 */
	public void signDocAction(ActionEvent event) {
		String result = All.generateAll(xmlDoc.getText());
		resultDoc.setText(result);
	}

	/**
	 * 파일: 검증(Verify)
	 * 
	 * @param event
	 */
	public void verifyFileAction(ActionEvent event) {
		Verification.testSignedXML(resultFile.getText());
	}

	/**
	 * 문서: 검증(Verify)
	 * 
	 * @param event
	 */
	public void verifyDocAction(ActionEvent event) {
		Verification.testSignedXML(resultDoc.getText());
	}

	/**
	 * 파일: 값 추출(Extract or Parsing)
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void extractFileAction(ActionEvent event) throws Exception {
		ExtractXml.main(resultFile.getText());
	}

	/**
	 * 문서: 값 추출(Extract or Parsing)
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void extractDocAction(ActionEvent event) throws Exception {
		ExtractXml.main(resultDoc.getText());
	}

	/**
	 * 파일: 선택 메시지 
	 * ※rb: 라디오버튼
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void rbFileAction(ActionEvent event) throws Exception {
		String message = "파일을 선택하셨습니다.";
		if (rbFile.isSelected()) {
			inform.setText(message);
			selFileAction(null);
		}
	}

	/**
	 * 문서: 선택 메시지
	 * ※rb: 라디오 버튼
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void rbDocAction(ActionEvent event) throws Exception {
		String message = "문서를 선택하셨습니다.";
		if (rbDoc.isSelected()) {
			inform.setText(message);
			// 함수 실행?
			selDocAction(null);
		}
	}
}
