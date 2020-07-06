package comp3350.mealbuddy.business.GoalFactory;

import java.util.List;

import comp3350.mealbuddy.objects.Edible.Micros;
import comp3350.mealbuddy.objects.Goal;
import comp3350.mealbuddy.objects.Goal.GoalType;
import comp3350.mealbuddy.objects.MacroGoal;
import comp3350.mealbuddy.objects.MicroGoal;
import comp3350.mealbuddy.objects.UserInfo;

import static comp3350.mealbuddy.objects.Edible.Macros.Fat;
import static comp3350.mealbuddy.objects.Edible.Macros.Carbohydrates;
import static comp3350.mealbuddy.objects.Edible.Macros.Protein;

public class DefaultGoalFactory extends GoalFactory {

    public DefaultGoalFactory(UserInfo userInfo) {
        super(userInfo);
    }

    @Override
    protected void addMacroGoals(UserInfo userInfo, List<Goal> goalList) {
        int MacroVariance = 5;
        int fatPercent = 25;
        int proteinPercent = 25;
        int carbPercent = 50;
        goalList.add(new MacroGoal(fatPercent-MacroVariance, fatPercent+MacroVariance, GoalType.RATIO, Fat));
        goalList.add(new MacroGoal(proteinPercent-MacroVariance, proteinPercent+MacroVariance, GoalType.RATIO, Protein));
        goalList.add(new MacroGoal(carbPercent-MacroVariance, carbPercent+MacroVariance, GoalType.RATIO, Carbohydrates));
    }

    @Override
    protected void addMicroGoals(UserInfo userInfo, List<Goal> goalList) {
        Object[][] goals = {
                {1,      4,     Micros.VitaminA     },
                {10,     50,    Micros.VitaminB12   },
                {10,     50,    Micros.VitaminC     },
                {15,     100,   Micros.VitaminE     },
                {8,      45,    Micros.Iron         },
                {10,     40,    Micros.Zinc         },
                {1000,   2000,  Micros.Calcium      },
                {500,    3500,  Micros.Choline      },
                {310,    3000,  Micros.Magnesium    },
                {1200,   2300,  Micros.Sodium       },
                {3500,   4700,  Micros.Potassium    },
                {10,     35,    Micros.Niacin       }
        };

        for( Object[] goal: goals){
            goalList.add(new MicroGoal((int)goal[0], (int)goal[1], (Micros)goal[2]));
        }
    }

    @Override
    protected void addLabelGoals(UserInfo userInfo, List<Goal> goalList) {
        //Do nothing.  We dont want any label goals here
    }



}
