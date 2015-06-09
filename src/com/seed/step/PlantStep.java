package com.seed.step;

import com.seed.*;
import com.seed.items.RequiredItem;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import com.seed.step.utils.Steps;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Item;

/**
 * Created by Ian on 5/27/2015.
 */
public class PlantStep implements Step<PatchType> {
   @Override
   public StepResult execute(final PatchType param) {
      if (param == null) {
         SeedUtils.info("Param is null");
      }
      final Seed seed = SeedSettings.getSettings().getSeed(param);
      final GameObject patch =
              GameObjects.getNearest(param.getId());
      if (patch == null)
         SeedUtils.info("Patch is null?? " + param.getId());
      if (!useCompost()) {
         return StepResult.create(Steps.BANK);
      }

      Time.sleep(400, 600);

      SeedUtils.info("Composting");
      patch.interact(null);
      Time.sleep(1200, 1400);

      Item seedItem = Inventory.getItemClosestToMouse(new Filter<Item>() {
         @Override
         public boolean accept(Item item) {
            return item.getName().equals(seed.toString());

         }
      });
      SeedUtils.info("Planting seed " + seedItem.getName());
      seedItem.click();

      Time.sleep(400, 600);
      patch.interact(null);
      Time.sleepUntil(new Condition() {
         @Override
         public boolean check() {
            return patch.getID() != param.getId();
         }
      }, 9000);

      if (param != PatchType.HERB &&
              patch.getID() != param.getId()) {
         return StepResult.create(Steps.WATER, patch);
      }
      return StepResult.create(Steps.CHECK_PATCH);

   }

   public static boolean useCompost() {
      Item item = Inventory.getItemClosestToMouse(new Filter<Item>() {
         @Override
         public boolean accept(Item item) {
            return item.getName().equals(RequiredItem.COMPOST) ||
                    item.getName().equals(RequiredItem.SUPER_COMPOST);
         }
      });
      if (item == null) {
         return false;
      }
      while (!item.click()) {
         Time.sleep(100, 200);
      }
      return true;
   }
}
