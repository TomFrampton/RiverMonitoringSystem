package u1171639.rmc.main.java.utils;

import java.io.IOException;

import u1171639.rmc.main.java.view.fxml.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

public class FXMLViewLoader {
	public static void loadView(final ViewController controller, String fxmlResource) {
		FXMLLoader fxmlLoader = new FXMLLoader(FXMLViewLoader.class.getClass().getResource("/u1171639/rmc/main/fxml/" + fxmlResource));
		
		try {
			fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aClass) {
                    return controller;
                }
            });
			
			controller.setView((Parent) fxmlLoader.load());
		} catch(IOException e) {
			System.err.println("Error: " + e.getMessage());
			System.exit(1);
		}
	}
}
