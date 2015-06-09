package com.seed.items;

import com.seed.SeedUtils;
import org.tbot.methods.Bank;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.tabs.Inventory;

/**
 * Created by Ian on 6/7/2015.
 */
public enum SeedItem {
   SPADE(952), RAKE(5341), COMPOST(6034, 6032) {
      @Override
      public int getAmount () {
         return 4;
      }
   },
   SEED_DIBBER(5343),
   SECATEURS(7409, 5329) {
      @Override
      public int getInvCount () {
         return Equipment.contains(7409) ? 1 : super.getInvCount();
      }
   },
   WATERING_CAN(5340, 5339, 5338, 5337, 5336, 5335, 5334, 5333, 5332, 5331, 5332) {
      @Override
      public int getAmount () {
         return 3;
      }
   }, //,5333 empty
   BUCKET(1925);

   private int[] itemIds;

   SeedItem (int... itemIds) {
      this.itemIds = itemIds;
   }

   public int[] getItemIds () {
      return itemIds;
   }

   int cur = 0;

   public int getId () {
      return itemIds[cur++ % itemIds.length];
   }

   public int get () {
      for (int id : itemIds) {
         if (Inventory.contains(id)) {
            return id;
         }
      }
      return -1;
   }

   public boolean widthdraw () {
      final int invCount = getInvCount();
      final int widthdrawAmount = getAmount() - invCount;
      for (int id : itemIds) {
         int min = Math.min(widthdrawAmount, Bank.getCount(id));
         //todo fix
        // System.out.println(String.format("Withdrawing %s x %lf", toString(), min));
         Bank.withdraw(id, min);
         if (getInvCount() == widthdrawAmount) {
            break;
         }
      }
      //todo fix
      return true;


   }

   public int getInvCount () {
      int count = 0;
      for (int id : itemIds) {
         if (Inventory.contains(id)) {
            count++;
         }
      }
      return count;
   }

   public int getBankCount () {
      int count = 0;
      for (int id : itemIds) {
         count += Bank.getCount(id);
      }
      return count;
   }


   @Override
   public String toString () {
      return SeedUtils.enumToString(this);
   }

   public int getAmount () {
      return 1;
   }
}
