package com.seed.step;

import com.seed.Seed;
import com.seed.SeedSettings;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import com.seed.step.utils.Steps;
import org.tbot.methods.Skills;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;

/**
 * Created by Ian on 5/26/2015.
 */
public class StartStep implements Step<Object> {

   @Override
   public StepResult execute(Object param) {
      SeedSettings settings = SeedSettings.getSettings();

      Item[] items = Inventory.getItems();
      int level = Skills.getCurrentLevel(Skills.Skill.Farming);
      for (Item i : items) {
         for (Seed s : Seed.values()) {
            if (s.getLevel() <= level &&
                    i.getName().equals(s.toString())) {
               settings.seed(s);
            }
         }
      }
//      for (String req : RequiredItem.getRequired()) {
//         if (!Inventory.contains(req)) {
//            return StepResult.create(Steps.ERROR, "Missing " + req);
//         }
//      }
//      if (!Inventory.contains(RequiredItem.COMPOST) && !Inventory.contains(RequiredItem.SUPER_COMPOST)) {
//         return StepResult.create(Steps.ERROR, "Missing compost or supercompost");
//      }

      return StepResult.create(Steps.BANK);
   }
}
