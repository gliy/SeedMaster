package com.seed.step;

import com.seed.SeedUtils;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import org.tbot.client.GameObject;
import org.tbot.methods.Widgets;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Widget;
import org.tbot.wrappers.WidgetChild;

import java.util.Arrays;

/**
 * Created by Ian on 6/7/2015.
 */
public class LepStep implements Step<GameObject> {
   @Override
   public StepResult execute (GameObject param) {
      NPC lep = SeedUtils.walkTo("Tool Leprechaun");
      if (lep != null) {
         lep.interact("Exchange");

         //Inventory
         final Widget widget = Widgets.getWidget(149);
         if (widget != null && widget.getChildren().length > 0) {
            WidgetChild child = widget.getChild(0);
            System.out.println(Arrays.toString(child.getInventoryStackSizes()));
            System.out.println(Arrays.toString(child.getInventory()));
         } else {
            System.out.println(widget);
         }
      }
      return null;
   }
}
