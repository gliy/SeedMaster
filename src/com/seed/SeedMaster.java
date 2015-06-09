package com.seed;

import com.seed.step.utils.StepRunner;
import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;

/**
 * Created by Ian on 5/26/2015.
 */
@Manifest(name = "Seed Master")
public class SeedMaster extends AbstractScript {

   @Override
   public int loop() {
//      Inventory.openTab();
//      final Widget widget = Widgets.getWidget(125);
//      if(widget != null && widget.getChildren().length > 0) {
//         WidgetChild child = widget.getChild(68);
//         System.out.println(Arrays.toString(child.getInventoryStackSizes()));
//         System.out.println(Arrays.toString(child.getInventory()));
//         for (int a : child.getInventory()) {
//            System.out.println(a +": " + child.getInventoryItemCount(a));
//         }
//         System.out.println("\n\n");
//         child = widget.getChild(69);
//         System.out.println(Arrays.toString(child.getInventoryStackSizes()));
//         System.out.println(Arrays.toString(child.getInventory()));
//         for (int a : child.getInventory()) {
//            System.out.println(a +": " + child.getInventoryItemCount(a));
//         }
//      } else {
//         System.out.println(widget);
   //   }
      //Inventory.getInSlot(2).click();
      // SeedUtils.info(Inventory.getLastSelectedItemName());

      //  Tile loc = Players.getLocal().getLocation();
      //   Walking.findPath(new Tile(2806,3463)).traverse();
      //   SeedUtils.info(Walking.canReach(loc));
//      for (Item a : Arrays.asList(Inventory.getItems())) {
//         SeedUtils.info(a.getName() + ": " + a.isStackable());
//      }
      SeedSettings.getSettings().seed(Seed.RANARR,
              Seed.TOMATO, Seed.LIMPWURT);
      if(!StepRunner.run()){
         return -1;
      }
     // new BankStep().execute(FarmLocation.CAMALOT);
//
//        Widget buckets =Widgets.getWidget(125);
//        SeedUtils.info(buckets.getChildWithAction("Remove 1 Bucket"));
//        for (WidgetChild child : buckets.getChildren()) {
//            if(child.getInventory().length > 0) {
//                SeedUtils.info(Arrays.toString(child.getInventory()));
//                SeedUtils.info(child.isInventoryWidget() + " "
//                +child.click());
//
//                SeedUtils.info(child.getInventoryItemCount(6034));
//                SeedUtils.info(child.getIndex());
//                SeedUtils.info(Arrays.toString(child.getActions()));
//            }
//        }
//        if (buckets == null) {
//          SeedUtils.info("No widget");
//      } else {
//        //  SeedUtils.info(Arrays.toString(buckets.getActions()));
//          SeedUtils.info(buckets.getChildren().length);
//      }


//      for (FarmLocation f : FarmLocation.values()) {
//         System.out.println(f.toString() + ":" + f.getLoc().distance());
//      }
  // new BankStep().execute(FarmLocation.CAMALOT);
      //SeedUtils.info(Menu.contains("Remove 1 Bucket"));
      return 100000;
   }
}
