package fr.thomas.engine.graphics.ui;

import java.util.ArrayList;

import fr.thomas.engine.graphics.ui.component.Button;
import fr.thomas.engine.graphics.ui.component.CheckBox;
import fr.thomas.engine.graphics.ui.component.GraphicalComponent;
import fr.thomas.engine.graphics.ui.component.ProgressBar;
import fr.thomas.engine.graphics.ui.component.TextField;

public class UserInterface implements IUserInterface {
	
	private ArrayList<GraphicalComponent> components;
	private boolean isVisible;
	private String name;
	
	public UserInterface(String name) {
		this.name = name;
		components = new ArrayList<>();
		isVisible = true;
	}
	
	public ArrayList<GraphicalComponent> getComponents() {
		return components;
	}
	
	public void updateComponentsHitbox() {
		for(GraphicalComponent component : components) {
			component.updateHitbox(component.getPos().x, component.getPos().y, component.getWidth(), component.getHeight());
		}
	}
	
	public void add(GraphicalComponent component) {
		components.add(component);
		
		if(component.haveText()) {
			if(component instanceof CheckBox) {
				components.add(((CheckBox) component).getText());
			}
			
			if(component instanceof Button) {
				components.add(((Button) component).getText());
			}
			
			if(component instanceof TextField) {
				components.add(((TextField) component).getFieldText());
				components.add(((TextField) component).getFieldContent());
			}
		}
		
		if(component instanceof ProgressBar) {
			components.add(((ProgressBar) component).getFill());
		}
	}
	
	public void remove(GraphicalComponent component) {
		components.remove(component);
	}
	
	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void onMousePress() {
	}

	@Override
	public void onCharPress(char key) {
	}

	@Override
	public void onKeyPress(int keycode) {
	}
}
