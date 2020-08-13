package comp3350.mealbuddy.acceptance;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;

public class ClickXY {

    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                view -> {

                    final int[] screenPos = new int[2];
                    view.getLocationOnScreen(screenPos);

                    final float screenX = screenPos[0] + x;
                    final float screenY = screenPos[1] + y;
                    float[] coordinates = {screenX, screenY};

                    return coordinates;
                },
                Press.FINGER);
    }

}
