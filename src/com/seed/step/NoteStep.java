package com.seed.step;

import com.seed.Seed;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import com.seed.step.utils.Steps;
import org.tbot.methods.Npcs;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Item;
import org.tbot.wrappers.NPC;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Ian on 5/27/2015.
 */
public class NoteStep implements Step<GameObject> {
   @Override
   public StepResult execute(GameObject param) {
      Item[] items = Inventory.getItems(new Filter<Item>() {
         @Override
         public boolean accept(Item item) {
            String name = item.getName();
            for (Seed s : Seed.values()) {
               if (name.toLowerCase().startsWith(s.name().toLowerCase())
                       && !name.endsWith("seed")
                       && !item.isStackable()) {
                  return true;
               }

            }
            return name.startsWith("Grimy");
         }
      });

      NPC noter = Npcs.getNearest("Tool Leprechaun");

      HashSet<Item> itemSet = new HashSet<>(Arrays.asList(items));
      for (final Item item : itemSet) {
         if (item.interact("Use")) {
            Time.sleep(100, 200);
            noter.interact(null);
            Time.sleepUntil(new Condition() {
               @Override
               public boolean check() {
                  return !Inventory.contains(item.getID());
               }
            }, 3000);
         }

      }
      if (Inventory.isFull()) {
         return StepResult.create(Steps.COMPOST);
      }
      return StepResult.create(Steps.CHECK_PATCH);
   }
}
