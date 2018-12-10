package mainDirectory.utils;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class fxmlUtils {


    public static Parent loadFXML(String path){
        FXMLLoader loader = new FXMLLoader(fxmlUtils.class.getResource(path));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static FXMLLoader returnLoader (String path){
        FXMLLoader loader = new FXMLLoader(fxmlUtils.class.getResource(path));
        return loader;
        }

    }
