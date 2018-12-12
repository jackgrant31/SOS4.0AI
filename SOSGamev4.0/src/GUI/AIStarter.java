package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class AIStarter extends Application{
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		SOSServer sos = new SOSServer();
		sos.start(new Stage());
		
		AIClient ai1 = new AIClient();
		ai1.setAI(this);
		ai1.start(new Stage());
		
		AIClient ai2 = new AIClient();
		ai2.setAI(this);
		ai2.start(new Stage());
	}

	public void end() {
		//System.exit(0);
		AIStarter ai = new AIStarter();
		try {
			ai.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
