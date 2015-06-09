package com.seed.step;

import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import com.seed.step.utils.Steps;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ian on 5/28/2015.
 */
public class CompostStep implements Step<GameObject> {
   @Override
   public StepResult execute(GameObject param) {
      GameObject bin = GameObjects.getNearest("Compost Bin");
      List<String> options = Arrays.asList(bin.getActions());


      if (options.isEmpty() && Inventory.contains("Weeds")) {
         final int count = Inventory.getCount("Weeds");
         Inventory.getItemClosestToMouse("Weeds").click();
         bin.interact(null);
         Time.sleepUntil(new Condition() {
            @Override
            public boolean check() {
               return count != Inventory.getCount("Weeds");
            }
         }, 5000);
      } else if (options.contains("Close") && bin.interact("Close")) {
         Time.sleepUntil(new Condition() {
            @Override
            public boolean check() {
               return Arrays.asList(
                       GameObjects.getNearest("Compost Bin").getActions()).contains("Open");
            }
         }, 5000);
      }

      if (Inventory.isFull()) {
         return StepResult.create(Steps.BANK);
      }
      return StepResult.create(Steps.CHECK_PATCH);
   }
}
