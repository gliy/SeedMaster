package com.seed.step;

import com.seed.SeedUtils;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import com.seed.step.utils.Steps;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Item;

/**
 * Created by Ian on 5/28/2015.
 */
public class WaterStep implements Step<GameObject> {
   @Override
   public StepResult execute(GameObject patch) {
      SeedUtils.info("Watering");
      Item waterCan = Inventory.getItemClosestToMouse(new Filter<Item>() {
         @Override
         public boolean accept(Item item) {
            return item.getName().startsWith("Watering can(");
         }
      });
      if (waterCan != null) {
         waterCan.click();

         Time.sleep(400, 600);
         patch.interact(null);
         Time.sleep(100, 200);
      }
      return StepResult.create(Steps.CHECK_PATCH);
   }
}
