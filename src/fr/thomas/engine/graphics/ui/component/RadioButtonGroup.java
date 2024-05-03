package fr.thomas.engine.graphics.ui.component;

import java.util.ArrayList;

public class RadioButtonGroup {

	private ArrayList<RadioButton> group;

	public RadioButtonGroup() {
		group = new ArrayList<RadioButton>();
	}

	public void addButton(RadioButton radioButton) {
		group.add(radioButton);
		radioButton.defineGroup(this);
	}

	public void removeButton(RadioButton radioButton) {
		group.remove(radioButton);
		radioButton.defineGroup(null);
	}

	/**
	 * Unchecks all buttons except the one passed in parameters.
	 * 
	 * @param buttonClicked
	 */
	public void updateGroupStatus(RadioButton buttonClicked) {
		for (RadioButton button : group) {
			button.setChecked(false);
			if(button.equals(buttonClicked)) {
				button.setChecked(true);
			}
		}
	}
	
	public void clearAllButtons() {
		for(RadioButton button : group) {
			button.setChecked(false);
		}
	}
	
	public void changeButtonState(int id, boolean state) {
		if(id <= 2 && id >= 0)
			group.get(id).setChecked(state);
	}
	
	public RadioButton getButtonFromLabel(String label) {
		for(RadioButton button : group) {
			if(button.text.getContent().equals(label)) {
				return button;
			}
		}
		
		return null;
	}

}
